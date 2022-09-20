package com.thesis.petshop.services.accounts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thesis.petshop.services.accounts.qualification.QualificationForm;
import com.thesis.petshop.services.adopt_form.answer.FormAnswer;
import com.thesis.petshop.services.adopt_form.answer.FormAnswerService;
import com.thesis.petshop.services.email.JavaMailSenderImpl;
import com.thesis.petshop.services.utils.ImageUploadService;
import com.thesis.petshop.services.utils.RandomService;
import com.thesis.petshop.services.utils.Response;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
public class AccountsService {

    private final ImageUploadService imageUploadService;
    private final UserRepository repository;
    private final JavaMailSenderImpl javaMailSender;
    private final RandomService randomService;
    private final FormAnswerService formAnswerService;
    private final ObjectMapper objectMapper;

    public AccountsService(ImageUploadService imageUploadService, UserRepository repository, JavaMailSenderImpl javaMailSender, RandomService randomService, FormAnswerService formAnswerService, ObjectMapper objectMapper) {
        this.imageUploadService = imageUploadService;
        this.repository = repository;
        this.javaMailSender = javaMailSender;
        this.randomService = randomService;
        this.formAnswerService = formAnswerService;
        this.objectMapper = objectMapper;
    }

    public Response saveUser(User user) {
        if (repository.findByEmail(user.getEmail()).isPresent()) {
            return Response.failed("existing email " + user.getEmail());
        }
        Optional<User> optionalUser = repository.findByUsername(user.getUsername());
        if (optionalUser.isPresent()) {
            return Response.failed("existing username " + user.getUsername());
        }
        repository.save(user);

        sendVerificationEmail(user.getEmail());
        return Response.ok();
    }

    public long login(String username, String password) {
        Optional<User> user = repository.findByUsername(username);

        if (user.isPresent() && nonNull(user.get().getUserValid()) && Boolean.TRUE.equals(!user.get().getUserValid())) {
            return 0;
        }

        if (user.isPresent() && password.equals(user.get().getPassword())) {
            return user.get().getId();
        }

        return 0;
    }

    public Response sendForgotPasswordToEmail(String email) {
        Optional<User> userWithEmail = repository.findByEmail(email);

        if (userWithEmail.isPresent()) {
            User existingUser = userWithEmail.get();

            String randomPasscode = randomService.randomCode();

            existingUser.setPassword(randomPasscode);
            repository.save(existingUser);

            javaMailSender.sendEmail(email, randomPasscode);
            return Response.ok();
        }

        return Response.failed(String.format("Email %s is not existing to our system", email));
    }

    public Response sendVerificationEmail(String email) {
        Optional<User> userWithEmail = repository.findByEmail(email);

        if (userWithEmail.isPresent()) {
            User existingUser = userWithEmail.get();

            String randomPasscode = randomService.randomCode();

            existingUser.setEmailOTP(randomPasscode);
            repository.save(existingUser);

            javaMailSender.sendEmailOTP(email, randomPasscode);
            return Response.ok();
        }

        return Response.failed(String.format("Email %s is not existing to our system", email));
    }

    public List<UserDTO> getUsersByType(String type) {
        List<User> users = repository.findAllByType(type);

        return users.stream().map(user -> {
            UserDTO userDTO = new UserDTO();

            userDTO.setId( user.getId() );
            userDTO.setName( user.getName() );
            userDTO.setMobile(user.getMobile());
            userDTO.setUsername(user.getUsername());
            userDTO.setEmail(user.getEmail());
            userDTO.setType(user.getType());
            userDTO.setAge(user.getAge());
            userDTO.setAddress(user.getAddress());
            userDTO.setOccupation(user.getOccupation());
            userDTO.setSocial(user.getSocial());
            userDTO.setUserValid(user.getUserValid());
            userDTO.setQualificationAnswers( parseToFormAnswer(user.getQualificationAnswers()) );
            userDTO.setQualificationFormScore(user.getQualificationPassed());

            return userDTO;
        }).collect(Collectors.toList());
    }

    public User getUserById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User is did not exists"));
    }

    public User getUserByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User is did not exists"));
    }

    public void uploadImage(String username, MultipartFile file) {
        repository.findByUsername(username).ifPresent(pet -> imageUploadService.fileUpload(file, "valid-id\\" + username));
    }

    public Integer updateAccountWithFormAnswers(String username, FormAnswer formAnswer) {
        User user = getUserByUsername(username);

        int formScore = formAnswerService.analyzeAnswerScore(formAnswer);

        user.setQualificationPassed(formScore);
        user.setQualificationAnswers( parseObject(formAnswer) );
        user.setUserValid(formScore >= 12);

        repository.save(user);
        return formScore;
    }

    private String parseObject (Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private QualificationForm parseToFormAnswer (String answer) {
        if (answer == null || answer == "") {
            return null;
        }

        try {
            return objectMapper.readValue(answer, QualificationForm.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateUser(User user) {
        User response = getUserById(user.getId());

        response.setName(defaultIfNull(user.getName(), response.getName()));
        response.setEmail(defaultIfNull(user.getEmail(), response.getEmail()));
        response.setMobile(defaultIfNull(user.getMobile(), response.getMobile()));
        response.setAge(defaultIfNull(user.getAge(), response.getAge()));
        response.setAddress(defaultIfNull(user.getAddress(), response.getAddress()));
        response.setOccupation(defaultIfNull(user.getOccupation(), response.getOccupation()));
        response.setSocial(defaultIfNull(user.getSocial(), response.getSocial()));

        repository.save(response);
    }

    private String defaultIfNull(String take, String leave) {
        if (take != null && !"".equals(take)) {
            return take;
        }
        return leave;
    }

    public Response verificationEmail(String email, String otp) {
        Optional<User> user = repository.findByEmail(email);

        if (user.isPresent()) {
            if (!user.get().getEmailOTP().equalsIgnoreCase(otp)) {
                return Response.failed("OTP is wrong");
            }

            user.get().setEmailValid(true);
            repository.save(user.get());
            return Response.ok();
        }
        return Response.failed(String.format("Email %s is not existing to our system", email));
    }

    public Response addQualificationForm(Long id, QualificationForm answer) {
        Optional<User> user = repository.findById(id);
        user.ifPresent(userData -> {
            userData.setQualificationAnswers(parseObject(answer));
            repository.save(userData);
        });
        return Response.ok();
    }

    public Response updateUserToValid(Long id, String decision) {
        Optional<User> user = repository.findById(id);
        user.ifPresent(userData -> {
            userData.setUserValid(Objects.equals(decision, "APPROVE"));
            repository.save(userData);
        });
        return Response.ok();
    }
}
