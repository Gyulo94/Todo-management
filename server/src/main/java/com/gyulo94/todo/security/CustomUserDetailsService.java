package com.gyulo94.todo.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gyulo94.todo.entity.User;
import com.gyulo94.todo.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

        private UserRepository userRepository;

        @Override
        public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

                User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                                .orElseThrow(() -> new UsernameNotFoundException("해당 유저이름이나 이메일이 존재하지 않습니다."));

                Set<GrantedAuthority> authorities = user.getRoles().stream()
                                .map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
                return new org.springframework.security.core.userdetails.User(
                                usernameOrEmail,
                                user.getPassword(),
                                authorities);
        }

}
