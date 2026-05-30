package com.example.leetcall.controller;


import com.example.leetcall.dto.LoginDto;
import com.example.leetcall.dto.RegisterDto;
import com.example.leetcall.dto.UserDto;
import com.example.leetcall.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/v1")
public class AuthController {

    private final AuthService authService;


    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> createUser(@RequestBody RegisterDto user){
        UserDto user1 = authService.createUser(user);
        return new ResponseEntity<>(user1, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginDto loginDto){
        UserDto login = authService.login(loginDto);
        return  new ResponseEntity<>(login,HttpStatus.OK);
    }
}
