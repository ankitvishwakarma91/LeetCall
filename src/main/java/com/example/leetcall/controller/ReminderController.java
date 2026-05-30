package com.example.leetcall.controller;

import com.example.leetcall.dto.ReminderRequest;
import com.example.leetcall.entity.Reminder;
import com.example.leetcall.service.impl.ReminderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reminder")
@RequiredArgsConstructor
public class ReminderController {

    private final ReminderService reminderService;

    @PostMapping("/schedule")
    public ResponseEntity<Reminder> schedule(@RequestBody @Valid ReminderRequest request,
                                             Authentication authentication) {
        Reminder reminder = reminderService.scheduleReminder(
                request.getLeetcodeUsername(),
                request
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(reminder);
    }
}