package com.example.leetcall.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;


@Data
public class ReminderRequest {

    @NotBlank
    private String leetcodeUsername;

    @NotBlank
    private String phoneNumber;

    @NotNull
    private LocalTime remindAt;

    @NotNull
    private String timeZone;

}