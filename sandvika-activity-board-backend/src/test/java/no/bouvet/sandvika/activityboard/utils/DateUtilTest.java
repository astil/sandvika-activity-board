package no.bouvet.sandvika.activityboard.utils;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

public class DateUtilTest
{
    @Test
    public void firstDayOfWeek() throws Exception
    {
        Date d = DateUtil.firstDayOfWeek(1);
        System.out.println(d);
    }

    @Test
    public void firstDayOfWeekByWeeknumber() throws Exception
    {
        Date d = DateUtil.firstDayOfWeek(12,2017);
        System.out.println(d);
    }

    @Test
    public void lastDayOfWeek() throws Exception
    {
        Date d = DateUtil.lastDayOfWeek(1);
        System.out.println(d);
    }

    @Test
    public void getDateDaysAgo() throws Exception {
        Date d = DateUtil.getDateDaysAgo(9999);
        System.out.println(d);
    }

    @Test
    public void dateFromString() throws ParseException
    {
        Date d = DateUtil.getDateFromLocalDateTimeString("2017-12-12T06:14:58Z");
        System.out.println(d);
    }

}