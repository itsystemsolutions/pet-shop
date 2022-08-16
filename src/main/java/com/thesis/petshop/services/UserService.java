package com.thesis.petshop.services;



import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User saveProDucts(User users){
        return repository.save(users);
    }

    public User getProductById(int id){
        return repository.findById((long) id) .orElse(null);
    }
//    public User getProductByName(String name){
//        return repository.findByName(name);
//    }

    public String deleteProduct(int id){
        repository.deleteById((long) id);
        return "product removed !! " +id;
    }

    public User updateProduct(User user){
        User existingUser =repository.findById((long) user.getId()). orElse(null);
        assert existingUser != null;
        existingUser.setName(user.getName());
        existingUser.setMobile(user.getMobile());
        return repository.save(existingUser);
    }

    public boolean saveUser(User user) {
        repository.save(user);

        return true;
    }

    public boolean login(String username, String password) {
        User user = repository.findByUsername(username);

        if (password.equals(user.getPassword())) {
            return true;
        }

        return false;
    }
}
