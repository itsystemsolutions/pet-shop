package com.thesis.petshop.services.user;


import com.thesis.petshop.services.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public List<User> getUsers (@RequestParam String type) {
        return service.getUsersByType(type);
    }

    @PostMapping
    public Response saveUser(@RequestBody User user){
        return service.saveUser(user);
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password) {
        if (service.login(username, password)) {
            return "OK";
        }
        return "NOK";
    }

    @GetMapping("/forgot-password")
    public Response sendForgotPasswordToEmail(@RequestParam String email) {
        return service.sendForgotPasswordToEmail(email);
    }

//
//    @PostMapping
//    public Pets addProduct(@RequestBody Pets users){
//        return service.saveProDucts(users);
//    }
//
//    @GetMapping()
//    public Pets findProductById(@PathVariable int id){
//        return service.getProductById(id);
//    }
//    @GetMapping()
//    public Pets findProductByName(@PathVariable String name){
//        return service.getProductByName(name);
//    }
//
//    @PutMapping()
//    public Pets updateProduct(@RequestBody Pets user){
//        return service.updateProduct(user);
//    }
//
//    @DeleteMapping()
//    public String deleteProduct(int id){
//        return service.deleteProduct(id);
//    }
}
