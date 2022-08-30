package com.thesis.petshop.services.accounts;


import com.thesis.petshop.services.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/user")
public class AccountsController {

    @Autowired
    private AccountsService service;

    @GetMapping
    public List<User> getUsers (@RequestParam String type) {
        return service.getUsersByType(type);
    }

    @GetMapping("/info")
    public User getUserById (@RequestParam Long id) {
        return service.getUserById(id);
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

    @PutMapping("/upload/image")
    public void uploadImage(@RequestParam String username, @RequestParam("file") MultipartFile file){
        service.uploadImage(username, file);
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
