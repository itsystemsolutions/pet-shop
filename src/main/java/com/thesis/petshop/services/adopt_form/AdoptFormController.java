package com.thesis.petshop.services.adopt_form;

import lombok.Getter;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/adopt-form")
public class AdoptFormController {

    private final AdoptFormService service;

    public AdoptFormController(AdoptFormService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Integer> saveAdoptForm(@RequestBody AdoptFormDTO adoptForm) {
        return ResponseEntity.ok( service.submitForm(adoptForm) );
    }

    @PostMapping("/missing")
    public ResponseEntity<Integer> adoptMissingPet(@RequestBody AdoptFormDTO adoptForm) {
        service.adoptMissingPet(adoptForm);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<AdoptFormDTO>> getForms(@PathVariable Long id) {
        return ResponseEntity.ok( service.getForms(id) );
    }

    @GetMapping("/request-interview")
    public void save(@RequestParam Long id, @RequestParam String petCode){
        service.requestInterview(id, petCode);
    }

    @GetMapping("/for-interview")
    public ResponseEntity<List<AdoptFormDTO>> getForInterview(){
        return ResponseEntity.ok( service.getForInterview() );
    }

    @PostMapping(value = "/upload/local", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam String petCode) throws IOException {
        File path = new File("C:\\PET_SHOP\\images\\" + petCode + ".jpg");
        if (!path.createNewFile()) {
            throw new RuntimeException();
        }
        FileOutputStream output = new FileOutputStream(path);
        output.write(file.getBytes());
        output.close();
        return ResponseEntity.ok(path);
    }

    @PutMapping("/upload/image")
    public void uploadImage(@RequestParam String code, @RequestParam("file") MultipartFile file){
        service.uploadImage(code, file);
    }

    @PutMapping("/upload/proof-ownership")
    public void uploadProofOwnership(@RequestParam String code, @RequestParam("file") MultipartFile file){
        service.uploadProofOwnership(code, file);
    }

}
