package com.thesis.petshop.services.schedules;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService service;

    @PostMapping
    public void save(@RequestBody Schedule schedule){
        service.saveInterview(schedule);
    }

    @PostMapping("/pick-up")
    public void pickupSave(@RequestBody Schedule schedule){
        service.savePickUp(schedule);
    }

    @PutMapping("/upload/proof-payment")
    public void uploadImage(@RequestParam Long id, @RequestParam("file") MultipartFile file){
        service.uploadImage(id, file);
    }

    @GetMapping("/for-interview")
    public List<ScheduleDTO> getRequestList(@RequestParam(required = false) Long userId){
        return service.getAppointments(userId);
    }

    @GetMapping("/for-pick-up")
    public List<ScheduleDTO> getForPickUp(@RequestParam(required = false) Long userId){
        return service.getForPickUp(userId);
    }

    @GetMapping
    public List<ScheduleDTO> getAll(){
        return service.getAll();
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestParam String decision,
                       @RequestBody(required = false) Checklist checklist,
                       @RequestParam(required = false) String failedReason
    ){
        service.updateAppointment(id, decision, checklist, failedReason);
    }

    @PutMapping("/pick-up/{id}")
    public void pickup(@PathVariable Long id, @RequestParam String decision){
        service.updatePickUpAppointment(id, decision);
    }

}
