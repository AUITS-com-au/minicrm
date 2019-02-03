package com.sh.crm.jpa.repos.global;

import com.sh.crm.jpa.entities.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface CalendarRepo extends JpaRepository<Calendar, Long> {
    Calendar findByIsHolidayFalseAndIsWeekdayTrueAndDate(Date date);
}
