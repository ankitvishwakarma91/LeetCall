package com.example.leetcall.entity;

import com.example.leetcall.Reminders.ReminderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "reminders")
public class Reminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String leetcodeUsername;    //  LeetCode username
    private String phoneNumber;
    private LocalTime remindAt;
    private String timeZone;

    private boolean active = true; // for reminder to check is active or not ?

//    @Enumerated(EnumType.STRING)
//    private ReminderStatus status = ReminderStatus.PENDING;


}