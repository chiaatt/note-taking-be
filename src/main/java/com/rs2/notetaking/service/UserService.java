package com.rs2.notetaking.service;

import com.rs2.notetaking.dto.CredentialsDTO;
import com.rs2.notetaking.dto.SignUpDTO;
import com.rs2.notetaking.dto.UserDTO;

public interface UserService {

    public UserDTO login(CredentialsDTO credentialsDto);

    public UserDTO register(SignUpDTO userDto);

    public UserDTO findByLogin(String login);

}
