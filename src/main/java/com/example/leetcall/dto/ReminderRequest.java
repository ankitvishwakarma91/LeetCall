package com.example.leetcall.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class ReminderRequest {

    @NotBlank
    private String leetcodeUsername;

    @NotBlank
    private String problemName;

    @NotBlank
    private String phoneNumber;

    @NotNull
    private LocalDateTime remindAt;


}