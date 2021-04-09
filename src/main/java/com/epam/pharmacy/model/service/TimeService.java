package com.epam.pharmacy.model.service;

/**
 * Interface provides actions on time.
 *
 * @author Yauheni Tsitou.
 */
public interface TimeService {
    /**
     * Method for finding current year.
     *
     * @return int containing current year.
     */
    int findCurrentYear();

    /**
     * The method checks if the day is in the month
     *
     * @return true - if the day is in the month
     */
    boolean isDayInMonth(int day, int month, int year);

    /**
     * Returns the number of milliseconds since January 1, 1970, 00:00:00 GMT
     * represented by this {@code TimeServiceImpl} object.
     *
     * @return the number of milliseconds since January 1, 1970, 00:00:00 GMT
     * represented by this date.
     */
    long findCurrentTime();

    /**
     * Returns this Calendar's time value in milliseconds.
     *
     * @return the current time as UTC milliseconds.
     */
    long findTimeByDayAndMonthAndYear(int day, int month, int year);
}
