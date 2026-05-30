package com.example.leetcall.service;


import com.example.leetcall.dto.LoginDto;
import com.example.leetcall.dto.RegisterDto;
import com.example.leetcall.dto.UserDto;

public interface UserService {


    public UserDto createUser(RegisterDto user);

    public UserDto login(LoginDto login);


}
