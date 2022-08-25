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

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService service;

    @PostMapping
    public void save(@RequestBody Schedule schedule){
        service.save(schedule);
    }

    @GetMapping("/for-interview")
    public List<ScheduleDTO> getRequestList(){
        return service.getAppointments();
    }

    @GetMapping
    public List<ScheduleDTO> getAll(){
        return service.getAll();
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestParam String decision){
        service.updateAppointment(id, decision);
    }

}
