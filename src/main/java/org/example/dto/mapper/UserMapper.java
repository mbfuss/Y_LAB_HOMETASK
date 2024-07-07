package org.example.dto.mapper;

import org.example.dto.UserDTO;
import org.example.model.User;

public class UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDTO(User user);
    User toEntity(UserDTO userDTO);
}
