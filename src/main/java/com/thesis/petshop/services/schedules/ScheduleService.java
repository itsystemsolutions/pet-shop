package com.thesis.petshop.services.schedules;

import com.thesis.petshop.services.accounts.AccountsService;
import com.thesis.petshop.services.adopt_form.AdoptFormService;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    private final ScheduleRepository repository;
    private final AccountsService accountsService;
    private final AdoptFormService adoptFormService;

    public ScheduleService(ScheduleRepository repository, AccountsService accountsService, AdoptFormService adoptFormService) {
        this.repository = repository;
        this.accountsService = accountsService;
        this.adoptFormService = adoptFormService;
    }

    public void save(Schedule schedule) {
        schedule.setStatus("WAITING");
        repository.save(schedule);
    }

    public List<ScheduleDTO> getAppointments() {
        return repository.findAllByStatus("WAITING").stream().map(entry -> {
            ScheduleDTO dto = new ScheduleDTO();

            dto.setId( entry.getId() );
            dto.setName( accountsService.getUserById(entry.getUserId()).getName() );
            dto.setPetCode( entry.getPetCode() );
            dto.setDate(entry.getDate() );
            dto.setTime(convertTimeTo12HrFormat(entry.getTime()));
            dto.setMessage(entry.getMessage());
            dto.setZoomLink(entry.getZoomLink());

            return dto;
        }).collect(Collectors.toList());
    }

    public List<ScheduleDTO> getAll() {
        return repository.findAll().stream().map(entry -> {
            ScheduleDTO dto = new ScheduleDTO();

            dto.setId( entry.getId() );
            dto.setName( accountsService.getUserById(entry.getUserId()).getName() );
            dto.setPetCode( entry.getPetCode() );
            dto.setDate(entry.getDate() );
            dto.setTime(convertTimeTo12HrFormat(entry.getTime()));
            dto.setMessage(entry.getMessage());
            dto.setZoomLink(entry.getZoomLink());

            return dto;
        }).collect(Collectors.toList());
    }

    private String convertTimeTo12HrFormat(String time) {
        return LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm")).format(DateTimeFormatter.ofPattern("hh:mm a"));
    }

    public void updateAppointment(Long id, String decision) {
        Optional<Schedule> schedule = repository.findById(id);

        if (schedule.isPresent()) {
            Schedule existingSchedule = schedule.get();

            if (decision.equalsIgnoreCase("PASSED")) {
                adoptFormService.approveFormOfUser(existingSchedule.getUserId(), existingSchedule.getPetCode());
            } else {
                adoptFormService.denyFormOfUser(existingSchedule.getUserId(), existingSchedule.getPetCode());
            }

            existingSchedule.setStatus(decision);
            repository.save(existingSchedule);
        }
    }
}
