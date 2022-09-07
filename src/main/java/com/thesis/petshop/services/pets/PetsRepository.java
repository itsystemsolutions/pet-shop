package com.thesis.petshop.services.pets;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PetsRepository extends JpaRepository<Pets, Long> {

    Optional<Pets> findByPetCode(String petCode);

    List<Pets> findAllByStatus(String status);

    List<Pets> findAllByStatusAndApprovalStatus(String status, String approvalStatus);

    List<Pets> findAllByOwnerIdAndStatus(Long userId, String name);
}
