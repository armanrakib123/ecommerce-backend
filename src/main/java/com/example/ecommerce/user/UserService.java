package com.example.ecommerce.user;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.exception.DuplicateResourceException;

import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateResourceException("Email already registered");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User updateUser(
            Long id,
            String name,
            String email,
            String password) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Optional<User> userWithEmail =
                userRepository.findByEmail(email);

        if (userWithEmail.isPresent()
                && !userWithEmail.get().getId().equals(id)) {

            throw new DuplicateResourceException(
                    "Email already registered");
        }

        existingUser.setName(name);
        existingUser.setEmail(email);

        if (password != null && !password.isBlank()) {
            existingUser.setPassword(
                    passwordEncoder.encode(password));
        }

        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        userRepository.delete(user);
    }
}