package com.example.leetcall.service.impl;

import com.example.leetcall.Reminders.ReminderStatus;
import com.example.leetcall.dto.ReminderRequest;
import com.example.leetcall.entity.Reminder;
import com.example.leetcall.repository.ReminderRepository;
import com.example.leetcall.service.LeetcodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReminderService {

    private final ReminderRepository reminderRepository;
    private final CallService callService;
    private final LeetcodeService leetCodeService;

    public Reminder scheduleReminder(String username, ReminderRequest request) {
        Reminder reminder = new Reminder();
        reminder.setUsername(username);
        reminder.setLeetcodeUsername(request.getLeetcodeUsername());
        reminder.setPhoneNumber(request.getPhoneNumber());
        reminder.setRemindAt(request.getRemindAt());
        reminder.setTimeZone(request.getTimeZone());
        reminder.setActive(true);

        return reminderRepository.save(reminder);
    }

    @Scheduled(fixedRate = 60000)
    public void processDueReminders() {
        List<Reminder> activeReminder = reminderRepository.findByActiveTrue();

        log.info("Scheduler fired. Active reminders found: {}", activeReminder.size());

        for (Reminder reminder : activeReminder) {

            ZoneId zone = ZoneId.of(reminder.getTimeZone());
            LocalTime nowInUserZone = LocalTime.now(zone);

            log.info("User: {} | Now: {} | RemindAt: {}",
                    reminder.getUsername(),
                    nowInUserZone,
                    reminder.getRemindAt());

            if (!isDueNow(reminder)) {
                log.info("Not due yet for user: {}", reminder.getLeetcodeUsername());
                continue;
            }

            log.info("Due now! Checking LeetCode for user: {}", reminder.getLeetcodeUsername());

            try {
                boolean alreadySolved = leetCodeService.hasSubmittedToday(
                        reminder.getLeetcodeUsername(),
                        reminder.getTimeZone()
                );

                log.info("Has submitted today: {}", alreadySolved);


                if (!alreadySolved) {
                    log.info("No submission found — initiating call to: {}", reminder.getPhoneNumber());
                    callService.makeCall(reminder.getPhoneNumber());
                    log.info("Call initiated successfully.");
                } else {
                    log.info("Already submitted today - no call needed");
                }

            } catch (Exception e) {
                log.error("Failed for user: {} → {}", reminder.getUsername(), e.getMessage());
            }

            reminderRepository.save(reminder);
        }
    }

    private boolean isDueNow(Reminder reminder) {
        ZoneId zone = ZoneId.of(reminder.getTimeZone());
        LocalTime localTime = LocalTime.now(zone);

        return
                localTime.getHour() == reminder.getRemindAt().getHour()
                        && localTime.getMinute() == reminder.getRemindAt().getMinute();
    }

}