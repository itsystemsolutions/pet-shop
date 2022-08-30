package com.thesis.petshop.services.pets;

import com.thesis.petshop.services.utils.ImageUploadService;
import com.thesis.petshop.services.utils.RandomService;
import com.thesis.petshop.services.utils.Response;
import com.thesis.petshop.services.utils.Status;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PetsService {

    private final PetsRepository repository;
    private final RandomService randomService;
    private final ImageUploadService imageUploadService;

    public PetsService(PetsRepository repository, RandomService randomService, ImageUploadService imageUploadService) {
        this.repository = repository;
        this.randomService = randomService;
        this.imageUploadService = imageUploadService;
    }

    public String savePet(Pets pets) {
        final String petCode = randomService.generatePetCode();

        pets.setPetCode( petCode );
        pets.setStatus( Status.IN_HOUSE.name() );

        repository.save(pets);
        return petCode;
    }

    public List<Pets> getPetByPetCode(String petCode) {
        if (petCode != null) {
            Optional<Pets> pets = repository.findByPetCode(petCode);
            return Collections.singletonList(pets.orElse(null));
        }

        return repository.findAllByStatus("IN_HOUSE");
    }

    public Response adoptPetByPetCode(String petCode) {
        Optional<Pets> pets = repository.findByPetCode(petCode);
        if (pets.isPresent()) {
            Pets presentPet = pets.get();

            if (presentPet.getStatus().equalsIgnoreCase(Status.IN_HOUSE.name())) {
                presentPet.setStatus(Status.ADOPTED.name());

                repository.save(presentPet);
                return Response.successMessage("Pet is adopted with code " + petCode);
            }
        }

        return Response.failed("Failed to Adopt pet #" + petCode);
    }

    public void uploadImage(String code, MultipartFile file) {
        repository.findByPetCode(code).ifPresent(pet -> {
            String imageLink = imageUploadService.fileUpload(file, "pets\\" + code);

            pet.setImageLink(imageLink);
            repository.save(pet);
        });
    }

    public String getPetNameByPetCode(String petCode) {
        return repository.findByPetCode(petCode).get().getName();
    }

    public void updatePetForPickUpByPetCode(String petCode) {
        repository.findByPetCode(petCode).ifPresent(pet-> {
            pet.setStatus("FOR_PICKUP");
            repository.save(pet);
        });
    }

    public void updatePetForClaimed(String petCode) {
        repository.findByPetCode(petCode).ifPresent(pet-> {
            pet.setStatus("CLAIMED");
            repository.save(pet);
        });
    }
}
