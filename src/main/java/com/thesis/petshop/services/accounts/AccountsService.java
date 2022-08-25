package com.thesis.petshop.services.accounts;

import com.thesis.petshop.services.email.JavaMailSenderImpl;
import com.thesis.petshop.services.utils.RandomService;
import com.thesis.petshop.services.utils.Response;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountsService {

    private final UserRepository repository;
    private final JavaMailSenderImpl javaMailSender;
    private final RandomService randomService;

    public AccountsService(UserRepository repository, JavaMailSenderImpl javaMailSender, RandomService randomService) {
        this.repository = repository;
        this.javaMailSender = javaMailSender;
        this.randomService = randomService;
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
        return Response.ok();
    }

    public long login(String username, String password) {
        Optional<User> user = repository.findByUsername(username);

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
            saveUser(existingUser);

            javaMailSender.sendEmail(email, randomPasscode);
            return Response.ok();
        }

        return Response.failed(String.format("Email %s is not existing to our system", email));
    }

    public List<User> getUsersByType(String type) {
        return repository.findAllByType(type);
    }

    public User getUserById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User is did not exists"));
    }
}
