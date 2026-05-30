package com.example.leetcall.entity;

import com.example.leetcall.Reminders.ReminderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "reminders")
public class Reminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String leetcodeUsername;    //  LeetCode username
    private String problemName;
    private String phoneNumber;
    private LocalDateTime remindAt;

    @Enumerated(EnumType.STRING)
    private ReminderStatus status = ReminderStatus.PENDING;


}