package com.rs2.notetaking.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.rs2.notetaking.dto.SignUpDTO;
import com.rs2.notetaking.dto.UserDTO;
import com.rs2.notetaking.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toUserDto(User user);

    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDTO signUpDto);
}