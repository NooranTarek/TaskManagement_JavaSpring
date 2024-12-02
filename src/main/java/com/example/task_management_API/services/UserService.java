package com.example.task_management_API.services;

import com.example.task_management_API.DTO.TaskDto;
import com.example.task_management_API.DTO.UserDto;
import com.example.task_management_API.entities.User;
import com.example.task_management_API.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user){
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            throw new DataIntegrityViolationException("Username already exists");
        }

        Optional<User> existingEmail = userRepository.findByEmail(user.getEmail());
        if (existingEmail.isPresent()) {
            throw new DataIntegrityViolationException("Email already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    public Optional<User> findByUserName(String username){
        return userRepository.findByUsername(username);
    }
    public User findUserById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }
    public boolean deleteUser (Integer userId){
        if(userRepository.existsById(userId)){
            userRepository.deleteById(userId);
            return true;
        }
        else {
            return false;
        }
    }
    public Page<UserDto> getAllUsers(Pageable pageable){
        Page <User> allUsers=userRepository.findAll(pageable);
        return allUsers.map(user -> new UserDto(user));

    }
//public List<UserDto> getAllUsers() {
//    List<User> allUsers = userRepository.findAll();
//    return allUsers.stream()
//            .map(user -> new UserDto(user))
//            .collect(Collectors.toList());
//}

}
