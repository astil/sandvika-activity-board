package no.bouvet.sandvika.activityboard.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;

import no.bouvet.sandvika.activityboard.domain.PeriodType;

public class DateUtil
{
    // Denne må virkelig legges et annet sted!
    private static final Date COMPETITION_START = DateUtil.getDate(1, 1, 2018);

    private static Date getDate(int day, int month, int year)
    {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day, 0, 0, 0);
        return cal.getTime();
    }

    public static Date firstDayOfCurrentWeek()
    {
        Calendar cal = Calendar.getInstance();
        clearCalendar(cal);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        return cal.getTime();
    }

    public static Date firstDayOfCurrentMonth()
    {
        Calendar cal = Calendar.getInstance();
        clearCalendar(cal);
        cal.set(Calendar.DAY_OF_MONTH, 1);
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

    public static Date firstDayOfWeek(int weeksAgo)
    {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.add(Calendar.WEEK_OF_YEAR, -weeksAgo);
        clearCalendar(cal);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
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

    public static Date getDateFromLocalDateTimeString(String input)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date d = null;
        try
        {
            d = sdf.parse(input.substring(0, input.length()-1));
        } catch (ParseException e)
        {
            e.printStackTrace();
            return new Date();
        }
        return d;
    }

    public static Date lastDayOfWeek(int weeksAgo)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(firstDayOfWeek(weeksAgo));
        cal.add(Calendar.DAY_OF_YEAR, 6);
        return cal.getTime();
    }

    public static Period getPeriod(PeriodType periodType, int periodNumber, int year)
    {
        switch (periodType)
        {
            case MONTH:
                return getPeriodForMonth(periodNumber, year);
            case WEEK:
                return getPeriodForWeek(periodNumber, year);
            case COMPETITION:
                return getPeriodForCompetition();
            default:
                return null;
        }
    }

    private static Period getPeriodForWeek(int week, int year)
    {
        Period period = new Period();
        period.setStart(firstDayOfWeek(week, year));
        period.setEnd(setEndOfDay(lastDayOfWeek(week, year)));
        return period;
    }

    private static Date lastDayOfWeek(int week, int year)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(firstDayOfWeek(week, year));
        cal.add(Calendar.DAY_OF_YEAR, 6);
        return cal.getTime();
    }

    protected static Date firstDayOfWeek(int week, int year)
    {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, week);
        clearCalendar(cal);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        return cal.getTime();
    }

    private static Period getPeriodForMonth(int month, int year)
    {
        Period period = new Period();
        period.setStart(firstDayOfMonth(month - 1, year));
        period.setEnd(setEndOfDay(lastDayOfMonth(month - 1, year)));
        return period;
    }

    private static Date setEndOfDay(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    public static Period getCurrentPeriod(PeriodType periodType)
    {
        switch (periodType)
        {
            case MONTH:
                return getPeriodForCurrentMonth();
            case WEEK:
                return getPeriodForCurrentWeek();
            case COMPETITION:
                return getPeriodForCompetition();
            default:
                return null;
        }
    }

    private static Period getPeriodForCompetition()
    {
        Period period = new Period();
        period.setStart(COMPETITION_START);
        period.setEnd(new Date());
        return period;
    }

    private static Period getPeriodForCurrentWeek()
    {
        Period period = new Period();
        period.setStart(firstDayOfCurrentWeek());
        period.setEnd(setEndOfDay(lastDayOfCurrentWeek()));
        return period;
    }

    private static Period getPeriodForCurrentMonth()
    {
        Period period = new Period();
        period.setStart(firstDayOfCurrentMonth());
        period.setEnd(setEndOfDay(lastDayOfCurrentMonth()));
        return period;
    }

    private static Date lastDayOfCurrentMonth()
    {
        Calendar cal = Calendar.getInstance();
        clearCalendar(cal);
        cal.set(Calendar.DAY_OF_MONTH, cal.getMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    private static Date lastDayOfCurrentWeek()
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(firstDayOfCurrentWeek());
        cal.add(Calendar.DAY_OF_YEAR, 6);
        return cal.getTime();
    }

    public static Date getDateDaysAgo(int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -days);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        return cal.getTime();
    }

    public static Period getPeriodFromCompetitionStartToDate(Date date)
    {
        Period period = new Period();
        period.setStart(COMPETITION_START);
        period.setEnd(date);
        return period;
    }

    public static Period getPeriodFromWeekStartToDate(Date date)
    {
        Period period = new Period();
        period.setStart(firstDayOfCurrentWeek());
        period.setEnd(date);
        return period;
    }

    public static Period getPeriodFromMonthStartToDate(Date date)
    {
        Period period = new Period();
        period.setStart(firstDayOfCurrentMonth());
        if (date.before(firstDayOfCurrentMonth()))
        {
            period.setEnd(firstDayOfCurrentMonth());
        } else
        {
            period.setEnd(date);
        }
        return period;
    }
}
