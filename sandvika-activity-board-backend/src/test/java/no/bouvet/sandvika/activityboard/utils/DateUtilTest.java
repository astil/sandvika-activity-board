package no.bouvet.sandvika.activityboard.utils;

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
    public void lastDayOfWeek() throws Exception
    {
        Date d = DateUtil.lastDayOfWeek(1);
        System.out.println(d);
    }

}