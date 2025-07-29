package com.example.inventory_api.validator;

import com.example.inventory_api.dtos.userDTO.UserRequest;
import com.example.inventory_api.exceptions.BusinessException;
import com.example.inventory_api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository repository;

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
}
