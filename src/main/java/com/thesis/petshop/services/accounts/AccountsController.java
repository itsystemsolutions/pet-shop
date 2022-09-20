package com.thesis.petshop.services.accounts;


import com.thesis.petshop.services.accounts.qualification.QualificationForm;
import com.thesis.petshop.services.adopt_form.answer.FormAnswer;
import com.thesis.petshop.services.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/user")
public class AccountsController {

    @Autowired
    private AccountsService service;

    @GetMapping
    public List<UserDTO> getUsers (@RequestParam String type) {
        return service.getUsersByType(type);
    }

    @GetMapping("/info")
    public User getUserById (@RequestParam Long id) {
        return service.getUserById(id);
    }

    @GetMapping("/data")
    public User getAccountByUsername (@RequestParam String username) {
        return service.getUserByUsername(username);
    }

    @PutMapping("/qualification-form")
    public Integer updateUserQualificationForm (@RequestParam String username, @RequestBody FormAnswer formAnswer) {
        return service.updateAccountWithFormAnswers(username, formAnswer);
    }

    @PostMapping
    public Response saveUser(@RequestBody User user){
        return service.saveUser(user);
    }

    @PostMapping("/login")
    public Long loginUser(@RequestParam String username, @RequestParam String password) {
        return service.login(username, password);
    }

    @GetMapping("/forgot-password")
    public Response sendForgotPasswordToEmail(@RequestParam String email) {
        return service.sendForgotPasswordToEmail(email);
    }

    @GetMapping("/verify-email")
    public Response sendEmailVerification(@RequestParam String email, @RequestParam String otp) {
        return service.verificationEmail(email, otp);
    }

    @PutMapping("/upload/image")
    public void uploadImage(@RequestParam String username, @RequestParam("file") MultipartFile file){
        service.uploadImage(username, file);
    }

    @PutMapping
    public ResponseEntity<Object> uploadImage(@RequestBody User user){
        service.updateUser(user);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/quiz-answer/{id}")
    public ResponseEntity<Response> updateQuizForm(@PathVariable Long id, @RequestBody QualificationForm answer) {
        return ResponseEntity.ok(service.addQualificationForm(id, answer));
    }

    @PutMapping("/valid/{id}")
    public ResponseEntity<Response> updateQuizForm(@PathVariable Long id, @RequestParam String decision) {
        return ResponseEntity.ok(service.updateUserToValid(id, decision));
    }
//
//    @PostMapping
//    public AdoptForm addProduct(@RequestBody AdoptForm accounts){
//        return service.saveProDucts(accounts);
//    }
//
//    @GetMapping()
//    public AdoptForm findProductById(@PathVariable int id){
//        return service.getProductById(id);
//    }
//    @GetMapping()
//    public AdoptForm findProductByName(@PathVariable String name){
//        return service.getProductByName(name);
//    }
//
//    @PutMapping()
//    public AdoptForm updateProduct(@RequestBody AdoptForm accounts){
//        return service.updateProduct(accounts);
//    }
//
//    @DeleteMapping()
//    public String deleteProduct(int id){
//        return service.deleteProduct(id);
//    }
}
