package com.epam.pharmacy.model.service;

public interface TimeService {
    int findCurrentYear();

    boolean isDayInMonth(int day, int month, int year);

    long findCurrentTime();

    long findTimeByDayAndMonthAndYear(int day, int month, int year);
}
