package com.example.inventory_api.services;

import com.example.inventory_api.domain.entities.User;
import com.example.inventory_api.dtos.loginDTO.LoginRequest;
import com.example.inventory_api.dtos.userDTO.UserRequest;
import com.example.inventory_api.dtos.userDTO.UserResponse;
import com.example.inventory_api.exceptions.BusinessException;
import com.example.inventory_api.exceptions.ResourceNotFoundException;
import com.example.inventory_api.mappers.UserMapper;
import com.example.inventory_api.repositories.UserRepository;
import com.example.inventory_api.validator.UserValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.inventory_api.validator.UserValidator.validatePassword;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final UserValidator validator;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse save(UserRequest request) {
        validator.validateUser(request);
        var user = mapper.toUser(request);
        user.setProfile(request.profile());
        user.setActive(true);
        return mapper.toUserResponse(repository.save(user));
    }

    public List<UserResponse> getAll() {
        var users = repository.findAllByActiveTrue();
        return mapper.toUserResponseList(users);
    }

    public UserResponse getById(Long id) {
        var user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return mapper.toUserResponse(user);
    }

    public UserResponse login(LoginRequest request) {
        var user = repository.findByEmail(request.email())
                .orElseThrow(() -> new ResourceNotFoundException("User account not found"));

        if (!user.isActive()) {
            throw new BusinessException("Your account has been deactivated");
        }

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new BusinessException("E-mail or password is invalid");
        }

        return mapper.toUserResponse(user);
    }

    @Transactional
    public UserResponse update(Long id, UserRequest request) {
        var user = repository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        validator.validateEmailUniqueness(user.getEmail(), request.email());
        validator.validatePasswordChange(request.password(), user.getPasswordHash());

        user.setName(request.name());
        user.setEmail(request.email());
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        return mapper.toUserResponse(repository.save(user));
    }

    @Transactional
    public void updatePassword(Long id, String newPassword) {
        var user = repository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        validator.validatePasswordChange(newPassword, user.getPasswordHash());
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        repository.save(user);
    }

    @Transactional
    public void activateUser(Long id) {
        var user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        validator.ensureUserIsInactive(user);
        user.setActive(true);
    }

    @Transactional
    public void deactivateUser(Long id) {
        var user = repository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setActive(false);
    }
}
