package com.example.leetcall.service.impl;

import com.example.leetcall.Reminders.ReminderStatus;
import com.example.leetcall.dto.ReminderRequest;
import com.example.leetcall.entity.Reminder;
import com.example.leetcall.repository.ReminderRepository;
import com.example.leetcall.service.LeetcodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReminderService{

    private final ReminderRepository reminderRepository;
    private final CallService callService;
    private final LeetcodeService leetCodeService;

    public Reminder scheduleReminder(String username, ReminderRequest request) {
        Reminder reminder = new Reminder();
        reminder.setUsername(username);
        reminder.setLeetcodeUsername(request.getLeetcodeUsername());
        reminder.setProblemName(request.getProblemName());
        reminder.setPhoneNumber(request.getPhoneNumber());
        reminder.setRemindAt(request.getRemindAt());
        reminder.setStatus(ReminderStatus.PENDING);

        return reminderRepository.save(reminder);
    }

    @Scheduled(fixedRate = 60000)
    public void processDueReminders() {
        List<Reminder> dueReminders = reminderRepository
                .findByStatusAndRemindAtBefore(ReminderStatus.PENDING, LocalDateTime.now());

        for (Reminder reminder : dueReminders) {
            try {
                boolean alreadySolved = leetCodeService.hasAcceptedToday(
                        reminder.getLeetcodeUsername(),
                        reminder.getProblemName()
                );

                if (alreadySolved) {
                    reminder.setStatus(ReminderStatus.SOLVED);
                } else {
                    callService.makeCall(reminder.getPhoneNumber(), reminder.getProblemName());
                    reminder.setStatus(ReminderStatus.CALLED);
                }

            } catch (Exception e) {
                reminder.setStatus(ReminderStatus.FAILED);
            }

            reminderRepository.save(reminder);
        }
    }
}