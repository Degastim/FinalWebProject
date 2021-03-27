package com.epam.Pharmacy.model.service.impl;

import com.epam.Pharmacy.model.service.TimeService;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeServiceImpl implements TimeService {
    private static final TimeService instance = new TimeServiceImpl();

    public static TimeService getInstance() {
        return instance;
    }

    private TimeServiceImpl() {
    }

    @Override
    public int findCurrentYear() {
        Calendar calendar = new GregorianCalendar();
        int result = calendar.getWeekYear();
        return result;
    }

    @Override
    public boolean isDayInMonth(int day, int month, int year) {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.YEAR, year);
        month--;
        calendar.set(Calendar.MONTH, month);
        boolean result = false;
        if (calendar.getActualMaximum(Calendar.DAY_OF_MONTH) >= day) {
            result = true;
        }
        return result;
    }

    @Override
    public long findCurrentTime() {
        Date date = new Date();
        long result = date.getTime();
        return result;
    }

    @Override
    public long findTimeByDayAndMonthAndYear(int day, int month, int year) {
        Calendar calendar = new GregorianCalendar(year, month, day);
        long result = calendar.getTimeInMillis();
        return result;
    }
}
