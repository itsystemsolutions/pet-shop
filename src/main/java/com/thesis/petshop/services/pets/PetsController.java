package com.thesis.petshop.services.pets;


import com.thesis.petshop.services.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import java.util.Objects;

@RestController
@RequestMapping("/pets")
public class PetsController {

    @Autowired
    private PetsService service;

    @PostMapping
    public String save(@RequestBody Pets pets){
        return service.savePet(pets);
    }

    @PutMapping("/upload/image")
    public void uploadImage(@RequestParam String code, @RequestParam("file") MultipartFile file){
        service.uploadImage(code, file);
    }

    @GetMapping("/missing")
    public List<PetsDTO> getMissingPets(@RequestParam(required = false) Long userId){
        return service.getMissingPets(userId);
    }

    @GetMapping("/missing/approved")
    public List<PetsDTO> getMissingApprovedPets(){
        return service.getMissingPetsAndApproved();
    }

    @PutMapping("/approve/{petCode}")
    public ResponseEntity<Object> approveMissing(@PathVariable String petCode, @RequestParam String decision){
        service.approveMissingPet(petCode, decision);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<Pets> get(@RequestParam(required = false) String petCode) {
        return service.getPetByPetCode(petCode);
    }

    @GetMapping("/adopt")
    public Response adoptPet(String petCode) {
        return service.adoptPetByPetCode(petCode);
    }

}
