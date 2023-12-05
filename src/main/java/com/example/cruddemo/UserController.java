package com.example.cruddemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class UserController {

    @Autowired
    private final UserRepo userRepo;

    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return (List<User>) userRepo.findAll();
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        return userRepo.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    @PostMapping("/user/new")
    public User createUser(@RequestBody User user) {
        return userRepo.save(user);
    }

    @PutMapping("/user/{id}/edit")
    public User updateUser(@PathVariable Long id, @RequestBody User user) throws ChangeSetPersister.NotFoundException {
        User existingUser = userRepo.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setAge(user.getAge());
        return userRepo.save(existingUser);
    }

    @DeleteMapping("/user/{id}/destroy")
    public void deleteUser(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        User existingUser = userRepo.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        userRepo.delete(existingUser);
    }
}
