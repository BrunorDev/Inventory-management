package com.example.inventory_api.mappers;

import com.example.inventory_api.domain.entities.User;
import com.example.inventory_api.dtos.userDTO.UserRequest;
import com.example.inventory_api.dtos.userDTO.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "active", ignore = true)
    @Mapping(target = "stockMovements", ignore = true)
    @Mapping(target = "passwordHash", source = "password")
    @Mapping(target = "id", ignore = true)
    User toUser(UserRequest request);

    UserResponse toUserResponse(User user);

    List<UserResponse> toUserResponseList(List<User> users);
}
