package com.thesis.petshop.services.adopt_form;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thesis.petshop.services.accounts.AccountsService;
import com.thesis.petshop.services.adopt_form.answer.FormAnswer;
import com.thesis.petshop.services.adopt_form.answer.FormAnswerService;
import com.thesis.petshop.services.exceptions.ExistingException;
import com.thesis.petshop.services.pets.Pets;
import com.thesis.petshop.services.pets.PetsService;
import com.thesis.petshop.services.utils.ImageUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdoptFormService {

    private static final String FOR_INTERVIEW = "FOR_INTERVIEW";

    @Autowired
    private PetsService petsService;

    @Autowired
    private AccountsService userService;

    @Autowired
    private FormAnswerService formAnswerService;

    @Autowired
    private AdoptFormRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ImageUploadService imageUploadService;

    public Integer submitForm(AdoptFormDTO adoptFormDTO) {
        int formScore = formAnswerService.analyzeAnswerScore(adoptFormDTO.getFormAnswer());

        AdoptForm adoptForm = new AdoptForm();

        adoptForm.setAnswer( parseObject(adoptFormDTO.getFormAnswer()) );
        adoptForm.setUserId(adoptFormDTO.getUserId());
        adoptForm.setTs(LocalDateTime.now().toString());
        adoptForm.setValidIdUrl(adoptFormDTO.getValidIdUrl());
        adoptForm.setFormPass( formScore > 12 );
        adoptForm.setFormScore( formScore );
        adoptForm.setPetCode( adoptFormDTO.getPetCode() );
        adoptForm.setStatus( "PENDING" );
        adoptForm.setPetType( "IN_HOUSE" );

        repository.save(adoptForm);

        Pets pet = petsService.findByPetCode(adoptFormDTO.getPetCode());
        pet.setStatus("IN_PROCESS");
        petsService.save(pet);
        return formScore;
    }

    public void adoptMissingPet(AdoptFormDTO adoptFormDTO) {
        Optional.ofNullable(repository.findByPetCode(adoptFormDTO.getPetCode()))
                .ifPresentOrElse(
                        pet -> {
                            throw new ExistingException("Pet already on process");
                        }, () -> {
                            AdoptForm adoptForm = new AdoptForm();

                            adoptForm.setUserId( adoptFormDTO.getUserId() );
                            adoptForm.setTs( LocalDateTime.now().toString() );
                            adoptForm.setPetCode( adoptFormDTO.getPetCode() );
                            adoptForm.setPetType( "MISSING" );
                            adoptForm.setStatus( "PENDING" );

                            repository.save(adoptForm);
                        }
                );


    }

    public List<AdoptFormDTO> getForms(Long userId) {
        List<AdoptForm> petsToAdopt = repository.findAllByUserId(userId)
                .stream()
                .filter(adoptForm -> {
                    if (adoptForm.getPetType().equalsIgnoreCase("MISSING")) {
                        return true;
                    }

                    if (adoptForm.getPetType().equalsIgnoreCase("IN_HOUSE")) {
                        return adoptForm.getFormPass();
                    }
                    return false;
                }).collect(Collectors.toList());

        return mapToDTO( petsToAdopt );
    }

    private String parseObject (Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private FormAnswer parseToFormAnswer (String answer) {
        try {
            return objectMapper.readValue(answer, FormAnswer.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void requestInterview(Long id, String petCode) {
        AdoptForm adoptForm = repository.findByPetCodeAndUserId(petCode, id);

        if (adoptForm.getStatus().equalsIgnoreCase(FOR_INTERVIEW)) {
            throw new RuntimeException("Cannot request again");
        }

        adoptForm.setStatus(FOR_INTERVIEW);
        repository.save(adoptForm);
    }

    public List<AdoptFormDTO> getForInterview() {
        return mapToDTO(repository.findAllByStatus(FOR_INTERVIEW));
    }

    private List<AdoptFormDTO> mapToDTO(List<AdoptForm> adoptForms) {
        return adoptForms
                .stream().map(item -> {
                    AdoptFormDTO adoptFormDTO = new AdoptFormDTO();
                    adoptFormDTO.setId(item.getId());

                    if (item.getPetType().equalsIgnoreCase("IN_HOUSE")) {
                        adoptFormDTO.setFormScore( item.getFormScore() );
                        adoptFormDTO.setFormResult(Boolean.TRUE.equals(item.getFormPass()) ? "PASSED" : "FAILED");
                        adoptFormDTO.setFormAnswer( parseToFormAnswer(item.getAnswer()) );
                    }

                    adoptFormDTO.setName( userService.getUserById(item.getUserId()).getName() );
                    adoptFormDTO.setUserId(item.getUserId());
                    adoptFormDTO.setPetName( petsService.getPetNameByPetCode(item.getPetCode()) );
                    adoptFormDTO.setPetCode( item.getPetCode() );
                    adoptFormDTO.setTimestamp( item.getTs() );
                    adoptFormDTO.setStatus( item.getStatus() );
                    adoptFormDTO.setType( item.getPetType() );
                    adoptFormDTO.setHasProofOwnerShip ( item.getHasProofOwnership() );
                    return adoptFormDTO;
                }).collect(Collectors.toList());
    }

    public void updateFormForPickup(Long userId, String petCode) {
        AdoptForm adoptForm = repository.findByPetCodeAndUserId(petCode, userId);
        adoptForm.setStatus("FOR_PICKUP");
        repository.save(adoptForm);

        petsService.updatePetForPickUpByPetCode(petCode);
    }

    public void denyFormOfUser(Long userId, String petCode) {
        AdoptForm adoptForm = repository.findByPetCodeAndUserId(petCode, userId);
        adoptForm.setStatus("DENIED");
        repository.save(adoptForm);
    }

    public void approveForm(Long userId, String petCode) {
        AdoptForm adoptForm = repository.findByPetCodeAndUserId(petCode, userId);
        adoptForm.setStatus("APPROVED");
        repository.save(adoptForm);

        petsService.updatePetForClaimed(petCode);
    }

    public void uploadImage(String code, MultipartFile file) {
        imageUploadService.fileUpload(file, "pet-policy\\" + code);
    }

    public void uploadProofOwnership(String code, MultipartFile file) {
        imageUploadService.fileUpload(file, "proof-ownership\\" + code);

        AdoptForm adoptForm = repository.findByPetCode(code);
        adoptForm.setHasProofOwnership(true);
        repository.save(adoptForm);
    }

    public String getPetType(String petCode) {
        return repository.findByPetCode(petCode).getPetType();
    }
}
