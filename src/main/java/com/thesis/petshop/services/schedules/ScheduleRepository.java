package com.thesis.petshop.services.schedules;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findAllByTypeAndStatus(String type, String status);

    List<Schedule> findAllByType(String type);

}
