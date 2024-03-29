package com.gyulo94.todo.service;

import com.gyulo94.todo.dto.JwtAuthResponse;
import com.gyulo94.todo.dto.LoginDTO;
import com.gyulo94.todo.dto.RegisterDTO;

public interface AuthService {
    String register(RegisterDTO registerDTO);

    JwtAuthResponse login(LoginDTO loginDTO);
}
