package com.springsecurity.scrumproject.service;

import com.springsecurity.scrumproject.model.Users;
import com.springsecurity.scrumproject.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    private JWTService jwtService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    @Autowired
    private UserRepo usersRepository;

    public String verify(Users users) {
        Authentication authentication =
                authManager
                        .authenticate(new UsernamePasswordAuthenticationToken(users.getUsername(), users.getPassword()));
        if (authentication.isAuthenticated())
            return jwtService.generateToken(users.getUsername());
        else return "Failed..!";
    }

    public Users createUser(Users user) {
        try {
            user.setPassword(encoder.encode(user.getPassword()));
            return usersRepository.save(user);
        } catch (ObjectOptimisticLockingFailureException e) { // Handle exception, maybe retry or log
            throw new RuntimeException("Failed to save role due to concurrent modification.", e);
        }
    }

    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    public Optional<Users> getUserById(Long id) {
        return usersRepository.findById(id);
    }

    public void deleteUserById(Long id) {
        usersRepository.deleteById(id);
    }

    public void deleteUser(Users user) {
        usersRepository.delete(user);
    }

    public Users updateUserById(Long id, Users userDetails) {
        Optional<Users> userOptional = usersRepository.findById(id);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            user.setUsername(userDetails.getUsername());
            user.setPassword(userDetails.getPassword());
            user.setEmail(userDetails.getEmail());
            user.setRoles(userDetails.getRoles());
            user.setEnabled(userDetails.isEnabled());
            return usersRepository.save(user);
        } else {
            throw new RuntimeException("User not found with id " + id);
        }
    }
}