package no.bouvet.sandvika.activityboard.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;

public class DateUtil
{
    public static Date firstDayOfCurrentWeek()
    {
        Calendar cal = Calendar.getInstance();
        clearCalendar(cal);
        return cal.getTime();
    }

    public static Date firstDayOfCurrentMonth()
    {
        Calendar cal = Calendar.getInstance();
        clearCalendar(cal);
        // cal.add(Calendar.MONTH, 1);
        return cal.getTime();
    }

    private static void clearCalendar(Calendar cal)
    {
        cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
    }

    public static Date lastDayOfMonth(int month, int year)
    {
        Calendar cal = Calendar.getInstance();
        clearCalendar(cal);
        cal.set(year, month, 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    public static Date firstDayOfMonth(int month, int year)
    {
        Calendar cal = Calendar.getInstance();
        clearCalendar(cal);
        cal.set(year, month, 1);
        return cal.getTime();
    }

    public static Date addHours(Date date, int hours)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, hours);
        return cal.getTime();
    }

    public static Date getDateFromLocalDateTime(LocalDateTime input)
    {
        Instant instant = input.toInstant(ZoneOffset.ofHours(0));
        return Date.from(instant);
    }

}
