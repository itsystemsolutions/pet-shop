package com.thesis.petshop.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    public String saveUser(@RequestBody User user){
        if (service.saveUser(user)) {
            return "OK";
        }
        return "NOK";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password) {
        if (service.login(username, password)) {
            return "OK";
        }
        return "NOK";
    }

//
//    @PostMapping
//    public User addProduct(@RequestBody User users){
//        return service.saveProDucts(users);
//    }
//
//    @GetMapping()
//    public User findProductById(@PathVariable int id){
//        return service.getProductById(id);
//    }
//    @GetMapping()
//    public User findProductByName(@PathVariable String name){
//        return service.getProductByName(name);
//    }
//
//    @PutMapping()
//    public User updateProduct(@RequestBody User user){
//        return service.updateProduct(user);
//    }
//
//    @DeleteMapping()
//    public String deleteProduct(int id){
//        return service.deleteProduct(id);
//    }
}
