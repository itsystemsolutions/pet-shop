package com.thesis.petshop.services.adopt_form;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thesis.petshop.services.accounts.AccountsService;
import com.thesis.petshop.services.adopt_form.answer.FormAnswerService;
import com.thesis.petshop.services.exceptions.ExistingException;
import com.thesis.petshop.services.pets.PetsService;
import com.thesis.petshop.services.schedules.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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

        repository.save(adoptForm);

        return formScore;
    }

    public List<AdoptFormDTO> getForms(Long userId) {
        return mapToDTO(repository.findAllByUserIdAndIsFormPass(userId, true));
    }

    private String parseObject (Object object) {
        try {
            return objectMapper.writeValueAsString(object);
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

                    adoptFormDTO.setFormScore( item.getFormScore() );
                    adoptFormDTO.setName( userService.getUserById(item.getUserId()).getName() );
                    adoptFormDTO.setUserId(item.getUserId());
                    adoptFormDTO.setPetName(petsService.getPetNameByPetCode(item.getPetCode()));
                    adoptFormDTO.setPetCode( item.getPetCode() );
                    adoptFormDTO.setTimestamp( item.getTs() );
                    adoptFormDTO.setStatus( item.getStatus() );
                    adoptFormDTO.setFormResult( item.getFormPass() ? "PASSED" : "FAILED");
                    return adoptFormDTO;
                }).collect(Collectors.toList());
    }

    public void approveFormOfUser(Long userId, String petCode) {
        AdoptForm adoptForm = repository.findByPetCodeAndUserId(petCode, userId);
        adoptForm.setStatus("APPROVED");
        repository.save(adoptForm);
    }

    public void denyFormOfUser(Long userId, String petCode) {
        AdoptForm adoptForm = repository.findByPetCodeAndUserId(petCode, userId);
        adoptForm.setStatus("DENIED");
        repository.save(adoptForm);
    }
}
