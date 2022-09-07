package com.thesis.petshop.services.adopt_form;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdoptFormRepository extends JpaRepository<AdoptForm, Long> {

    List<AdoptForm> findAllByUserIdAndIsFormPass(Long userId, Boolean isFormPass);

    AdoptForm findByPetCodeAndUserId(String petCode, Long id);

    List<AdoptForm> findAllByStatus(String forInterview);

    List<AdoptForm> findAllByUserId(Long userId);

    AdoptForm findByPetCode(String petCode);
}
