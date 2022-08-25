package com.thesis.petshop.services.adopt_form;

import com.thesis.petshop.services.schedules.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdoptFormRepository extends JpaRepository<AdoptForm, Long> {

    List<AdoptForm> findAllByUserIdAndIsFormPass(Long userId, Boolean isFormPass);

    AdoptForm findByPetCodeAndUserId(String petCode, Long id);

    List<AdoptForm> findAllByStatus(String forInterview);

}
