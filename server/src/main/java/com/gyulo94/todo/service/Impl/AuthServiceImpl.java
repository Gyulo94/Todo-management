package com.gyulo94.todo.service.Impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gyulo94.todo.dto.JwtAuthResponse;
import com.gyulo94.todo.dto.LoginDTO;
import com.gyulo94.todo.dto.RegisterDTO;
import com.gyulo94.todo.entity.Role;
import com.gyulo94.todo.entity.User;
import com.gyulo94.todo.exception.TodoAPIException;
import com.gyulo94.todo.repository.RoleRepository;
import com.gyulo94.todo.repository.UserRepository;
import com.gyulo94.todo.security.JwtTokenProvider;
import com.gyulo94.todo.service.AuthService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public String register(RegisterDTO registerDTO) {

        // DB에 해당 유저이름이 있는지 확인
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            throw new TodoAPIException(HttpStatus.BAD_REQUEST, "해당 이름이 이미 존재합니다.");
        }

        // DB에 해당 이메일이 있는지 확인
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            throw new TodoAPIException(HttpStatus.BAD_REQUEST, "해당 이메일이 이미 존재합니다.");
        }

        User user = new User();
        user.setName(registerDTO.getName());
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRoles = roleRepository.findByName("ROLE_USER");
        roles.add(userRoles);

        user.setRoles(roles);

        userRepository.save(user);

        return "회원가입이 완료되었습니다.";
    }

    @Override
    public JwtAuthResponse login(LoginDTO loginDTO) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getUsernameOrEmail(),
                loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        Optional<User> userOptional = userRepository.findByUsernameOrEmail(loginDTO.getUsernameOrEmail(),
                loginDTO.getUsernameOrEmail());

        String role = null;
        if (userOptional.isPresent()) {
            User loggedInUser = userOptional.get();
            Optional<Role> optionalRole = loggedInUser.getRoles().stream().findFirst();

            if (optionalRole.isPresent()) {
                Role userRole = optionalRole.get();
                role = userRole.getName();
            }
        }
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setRole(role);
        jwtAuthResponse.setAccessToken(token);
        return jwtAuthResponse;
    }

}
