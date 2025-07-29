package com.example.inventory_api.validator;

import com.example.inventory_api.domain.entities.User;
import com.example.inventory_api.dtos.userDTO.UserRequest;
import com.example.inventory_api.exceptions.BusinessException;
import com.example.inventory_api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public static final String PASSWORD_REGEX =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    public static void validatePassword(String password) {
        if (!password.matches(PASSWORD_REGEX)) {
            throw new BusinessException("Invalid password format");
        }
    }

    public void validateUser(UserRequest request) {
        if (repository.existsByEmail(request.email())) {
            throw new BusinessException("E-mail already in use");
        }
        validatePassword(request.password());
    }
    public void validateEmailUniqueness(String currentEmail, String newEmail) {
        if (!currentEmail.equals(newEmail) && repository.existsByEmail(newEmail)) {
            throw new BusinessException("E-mail already in use");
        }
    }

    public void validatePasswordChange(String newPassword, String currentPasswordHash) {
        validatePassword(newPassword);

        if (passwordEncoder.matches(newPassword, currentPasswordHash)) {
            throw new BusinessException("The new password must differ from current one");
        }
    }

    public void ensureUserIsActive(User user) {
        if (!user.isActive()) {
            throw new BusinessException("User is deactivated");
        }
    }

    public void ensureUserIsInactive(User user) {
        if (user.isActive()) {
            throw new BusinessException("User is already active");
        }
    }
}
