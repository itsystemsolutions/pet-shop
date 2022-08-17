package com.thesis.petshop.services.pets;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PetsRepository extends JpaRepository<Pets, Long> {

    Optional<Pets> findByPetCode(String petCode);

}
