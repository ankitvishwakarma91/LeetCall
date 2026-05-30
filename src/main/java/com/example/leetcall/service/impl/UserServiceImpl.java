package com.example.leetcall.service.impl;

import com.example.leetcall.dto.LoginDto;
import com.example.leetcall.dto.RegisterDto;
import com.example.leetcall.dto.UserDto;
import com.example.leetcall.entity.User;
import com.example.leetcall.repository.UserRepository;
import com.example.leetcall.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    private final AuthenticationManager authenticationManager;

    private UserServiceImpl(UserRepository userRepository,
                            PasswordEncoder passwordEncoder,
                            AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDto createUser(RegisterDto user) {
        boolean existing = userRepository.findUserByUsername(user.getUsername()).isPresent();
        if(existing){
            throw  new RuntimeException("User Already Exists.");
        }

        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(newUser);

        return mapToUserDto(newUser);
    }


    @Override
    public UserDto login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userRepository.findUserByUsername(loginDto.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found."));

        return mapToUserDto(user);
    }


    private UserDto mapToUserDto(User user){
        UserDto userdto = new UserDto();
        userdto.setName(user.getName());
        userdto.setUsername(user.getUsername());
        return  userdto;
    }
}
