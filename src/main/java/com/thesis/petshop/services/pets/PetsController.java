package com.thesis.petshop.services.pets;


import com.thesis.petshop.services.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @GetMapping
    public List<Pets> get(@RequestParam(required = false) String petCode) {
        return service.getPetByPetCode(petCode);
    }

    @GetMapping("/adopt")
    public Response adoptPet(String petCode) {
        return service.adoptPetByPetCode(petCode);
    }

}
