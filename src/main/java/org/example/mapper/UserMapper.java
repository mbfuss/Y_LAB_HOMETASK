package org.example.mapper;

import org.example.dto.UserDTO;
import org.example.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toEntity(UserDTO userDTO);

    UserDTO toDTO(User user);
}
