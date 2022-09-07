package com.thesis.petshop.services.pets;

import com.thesis.petshop.services.accounts.AccountsService;
import com.thesis.petshop.services.utils.ImageUploadService;
import com.thesis.petshop.services.utils.RandomService;
import com.thesis.petshop.services.utils.Response;
import com.thesis.petshop.services.utils.Status;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetsService {

    private final AccountsService accountsService;
    private final PetsRepository repository;
    private final RandomService randomService;
    private final ImageUploadService imageUploadService;

    public PetsService(AccountsService accountsService, PetsRepository repository, RandomService randomService, ImageUploadService imageUploadService) {
        this.accountsService = accountsService;
        this.repository = repository;
        this.randomService = randomService;
        this.imageUploadService = imageUploadService;
    }

    public String savePet(Pets pets) {
        final String petCode = randomService.generatePetCode();
        pets.setPetCode( petCode );

        if (Objects.equals(pets.getStatus(), Status.MISSING.name())) {
            pets.setApprovalStatus( "PENDING" );
        }

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
        repository.findByPetCode(code).ifPresent(pet -> imageUploadService.fileUpload(file, "pets\\" + code));
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

    public List<PetsDTO> getMissingPets(Long userId) {
        if (Objects.nonNull(userId)) {
            return repository.findAllByOwnerIdAndStatus(userId, Status.MISSING.name())
                    .stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        }

        return  repository.findAllByStatus(Status.MISSING.name())
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private PetsDTO toDTO(Pets pets) {
        PetsDTO dto = new PetsDTO();

        dto.setId(pets.getId());
        dto.setPetCode(pets.getPetCode());
        dto.setName(pets.getName());
        dto.setGender(pets.getGender());
        dto.setBreed(pets.getBreed());
        dto.setAge(pets.getAge());
        dto.setSize(pets.getSize());
        dto.setShelterResidentYear(pets.getShelterResidentYear());
        dto.setStatus(pets.getStatus());
        dto.setDescription(pets.getDescription());
        dto.setLastSeen(pets.getLastSeen());
        dto.setApprovalStatus(pets.getApprovalStatus());
        dto.setUser( accountsService.getUserById(pets.getOwnerId()) );

        return dto;
    }

    public void approveMissingPet(String petCode, String decision) {
        repository.findByPetCode(petCode)
                .ifPresent(pet -> {
                    if (Objects.equals(decision, "FOUND")) {
                        pet.setStatus("FOUND");
                    } else {
                        pet.setApprovalStatus(decision);
                    }
                    repository.save(pet);
                });
    }

    public List<PetsDTO> getMissingPetsAndApproved() {
        return repository.findAllByStatusAndApprovalStatus(Status.MISSING.name(), "APPROVED")
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
