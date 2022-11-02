package com.thesis.petshop.services.schedules;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thesis.petshop.services.accounts.AccountsService;
import com.thesis.petshop.services.accounts.User;
import com.thesis.petshop.services.adopt_form.AdoptFormService;
import com.thesis.petshop.services.email.JavaMailSenderImpl;
import com.thesis.petshop.services.pets.Pets;
import com.thesis.petshop.services.pets.PetsService;
import com.thesis.petshop.services.utils.ImageUploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    private static final String WAITING = "WAITING";

    private final ImageUploadService imageUploadService;
    private final ScheduleRepository repository;
    private final AccountsService accountsService;
    private final AdoptFormService adoptFormService;
    private final PetsService petsService;
    private final ObjectMapper objectMapper;
    private final JavaMailSenderImpl mailService;

    public ScheduleService(ImageUploadService imageUploadService, ScheduleRepository repository,
                           AccountsService accountsService, AdoptFormService adoptFormService,
                           PetsService petsService, ObjectMapper objectMapper, JavaMailSenderImpl mailService) {
        this.imageUploadService = imageUploadService;
        this.repository = repository;
        this.accountsService = accountsService;
        this.adoptFormService = adoptFormService;
        this.petsService = petsService;
        this.objectMapper = objectMapper;
        this.mailService = mailService;
    }

    public void saveInterview(Schedule schedule) {
        schedule.setType( "INTERVIEW" );
        schedule.setProofPaymentCount(0);
        schedule.setStatus( WAITING );
        schedule.setPetType( petsService.getPetType(schedule.getPetCode()) );
        repository.save(schedule);
    }

    public void savePickUp(Schedule schedule) {
        schedule.setType("PICKUP");
        schedule.setProofPaymentCount(0);
        schedule.setStatus(WAITING);
        repository.save(schedule);
    }

    public List<ScheduleDTO> getAppointments(Long userId) {
        if (Objects.nonNull(userId)) {
            return repository.findAllByTypeAndUserId("INTERVIEW", userId)
                    .stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
        }

        return repository.findAllByType("INTERVIEW")
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ScheduleDTO> getForPickUp(Long userId) {
        if (Objects.nonNull(userId)) {
            return repository.findAllByTypeAndUserId("PICKUP", userId)
                    .stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
        }
        return repository.findAllByType("PICKUP")
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ScheduleDTO> getAll() {
        return repository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private ScheduleDTO mapToDTO(Schedule entry) {
        ScheduleDTO dto = new ScheduleDTO();

        dto.setId( entry.getId() );
        dto.setUserId( entry.getUserId() );
        dto.setName( accountsService.getUserById(entry.getUserId()).getName() );
        dto.setPetCode( entry.getPetCode() );
        dto.setDate(entry.getDate() );
        dto.setTime(convertTimeTo12HrFormat(entry.getTime()));
        dto.setMessage(entry.getMessage());
        dto.setZoomLink(entry.getZoomLink());
        dto.setProofPaymentCount( entry.getProofPaymentCount());
        dto.setStatus(entry.getStatus());
        dto.setPetType(entry.getPetType());
        dto.setChecklist(parseToCheckListAnswer(entry.getInterviewChecklist()));
        dto.setFailedReason(entry.getFailedReason());

        return dto;
    }

    private String convertTimeTo12HrFormat(String time) {
        return LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm")).format(DateTimeFormatter.ofPattern("hh:mm a"));
    }

    public void updateAppointment(Long id, String decision, Checklist checklist, String failedReason) {
        Optional<Schedule> schedule = repository.findById(id);

        if (schedule.isPresent()) {
            Schedule existingSchedule = schedule.get();

            User user = accountsService.getUserById(existingSchedule.getUserId());

            if (decision.equalsIgnoreCase("PASSED")) {
                if (checklist != null) {
                    existingSchedule.setInterviewChecklist( parseObject(checklist) );
                }

                mailService.sendInterviewNotification(user.getEmail(), user.getName(), existingSchedule.getPetCode(),"PASSED");

                if (!existingSchedule.getPetType().equals("MISSING")) {
                    adoptFormService.updateFormForPickup(existingSchedule.getUserId(), existingSchedule.getPetCode());
                }
            } else {
                adoptFormService.denyFormOfUser(existingSchedule.getUserId(), existingSchedule.getPetCode());
                Pets pet = petsService.findByPetCode(existingSchedule.getPetCode());
                pet.setStatus("IN_HOUSE");
                petsService.save(pet);

                mailService.sendInterviewNotification(user.getEmail(), user.getName(), existingSchedule.getPetCode(),"FAILED");

                existingSchedule.setFailedReason(failedReason);
            }

            existingSchedule.setStatus(decision);
            repository.save(existingSchedule);
        }
    }

    private Checklist parseToCheckListAnswer(String answer) {
        try {
            if (answer == null) return null;

            return objectMapper.readValue(answer, Checklist.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String parseObject(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updatePickUpAppointment(Long id, String decision) {
        Optional<Schedule> schedule = repository.findById(id);

        if (schedule.isPresent()) {
            Schedule existingSchedule = schedule.get();

            if (decision.equalsIgnoreCase("PASSED")) {
                adoptFormService.approveForm(existingSchedule.getUserId(), existingSchedule.getPetCode());
            } else {
                Pets pet = petsService.findByPetCode(existingSchedule.getPetCode());
                pet.setStatus("IN_HOUSE");
                petsService.save(pet);
                adoptFormService.denyFormOfUser(existingSchedule.getUserId(), existingSchedule.getPetCode());
            }

            existingSchedule.setStatus(decision);
            repository.save(existingSchedule);
        }
    }

    public void uploadImage(Long id, MultipartFile file) {
        repository.findById(id).ifPresent(schedule -> {
            imageUploadService.fileUpload(file, "payment\\" + id + "-" +
                    (schedule.getProofPaymentCount() + 1));

            schedule.setProofPaymentCount( schedule.getProofPaymentCount() + 1 );
            repository.save(schedule);
        });
    }
}
