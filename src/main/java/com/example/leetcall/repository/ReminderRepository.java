package com.example.leetcall.repository;

import com.example.leetcall.Reminders.ReminderStatus;
import com.example.leetcall.entity.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Long> {

    List<Reminder> findByStatusAndRemindAtBefore(ReminderStatus status, LocalDateTime time);
}