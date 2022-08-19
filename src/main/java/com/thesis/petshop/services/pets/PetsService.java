package com.thesis.petshop.services.pets;

import com.thesis.petshop.services.utils.RandomService;
import com.thesis.petshop.services.utils.Response;
import com.thesis.petshop.services.utils.Status;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PetsService {

    private final PetsRepository repository;
    private final RandomService randomService;

    public PetsService(PetsRepository repository, RandomService randomService) {
        this.repository = repository;
        this.randomService = randomService;
    }

    public Response savePet(Pets pets) {
        final String petCode = randomService.generatePetCode();

        pets.setPetCode( petCode );
        pets.setStatus( Status.IN_HOUSE.name() );

        repository.save(pets);
        return Response.success("your pet code is " + petCode);
    }

    public List<Pets> getPetByPetCode(String petCode) {
        if (petCode != null) {
            Optional<Pets> pets = repository.findByPetCode(petCode);
            return Collections.singletonList(pets.orElse(null));
        }

        return repository.findAll();
    }

    public Response adoptPetByPetCode(String petCode) {
        Optional<Pets> pets = repository.findByPetCode(petCode);
        if (pets.isPresent()) {
            Pets presentPet = pets.get();

            if (presentPet.getStatus().equalsIgnoreCase(Status.IN_HOUSE.name())) {
                presentPet.setStatus(Status.ADOPTED.name());

                repository.save(presentPet);
                return Response.success("Pet is adopted with code " + petCode);
            }
        }

        return Response.failed("Failed to Adopt pet #" + petCode);
    }
}
