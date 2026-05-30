package com.example.leetcall.dto;


import lombok.Data;

@Data
public class LeetcodeSubmission {

    private String title;
    private String statusDisplay;   // "Accepted", "Wrong Answer"
    private String timestamp;       // Unix timestamp
    private String lang;
}
