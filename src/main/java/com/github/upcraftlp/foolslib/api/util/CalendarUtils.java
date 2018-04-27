package com.github.upcraftlp.foolslib.api.util;

import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;

@SuppressWarnings({"MagicConstant", "unused"})
public class CalendarUtils {

    private static final boolean APRIL_1ST, EASTER, CHRISTMAS;
    private static final long TWO_DAYS_BEFORE, TWO_DAYS_AFTER, LAUNCH_TIME;
    private static final Date LAUNCH_TIME_DATE;

    static {
        Calendar calendar = Calendar.getInstance();
        LAUNCH_TIME = calendar.getTimeInMillis();
        LAUNCH_TIME_DATE = new Date(LAUNCH_TIME);
        calendar.add(Calendar.DAY_OF_MONTH, -2);
        TWO_DAYS_BEFORE = calendar.getTimeInMillis();
        calendar.add(Calendar.DAY_OF_MONTH, 4);
        TWO_DAYS_AFTER = calendar.getTimeInMillis();


        int year = Year.now().getValue();

        calendar.set(year, Calendar.APRIL, 1);
        APRIL_1ST = isInRange(calendar);

        //eastern, according to the gregorian calendar
        int a = year % 19,
                b = year / 100,
                c = year % 100,
                d = b / 4,
                e = b % 4,
                g = (8 * b + 13) / 25,
                h = (19 * a + b - d - g + 15) % 30,
                j = c / 4,
                k = c % 4,
                m = (a + 11 * h) / 319,
                r = (2 * e + 2 * j - k - h + m + 32) % 7,
                easterMonth = (h - m + r + 90) / 25,
                easterDay = (h - m + r + easterMonth + 19) % 32;

        calendar.set(year, easterMonth-1, easterDay);
        EASTER = isInRange(calendar);

        calendar.set(year, Calendar.DECEMBER, 25);
        CHRISTMAS = isInRange(calendar);
    }

    public static boolean isAprilFoolsDay() {
        return APRIL_1ST;
    }

    public static boolean isEaster() {
        return EASTER;
    }

    public static boolean isChristmas() {
        return CHRISTMAS;
    }

    public static boolean isInRange(Calendar calendar) {
        return isInRange(calendar.getTimeInMillis());
    }

    public static boolean isInRange(long timestamp) {
        return TWO_DAYS_BEFORE <= timestamp && TWO_DAYS_AFTER >= timestamp;
    }

    public static boolean isInRange(Date toCheck) {
        return isInRange(toCheck.getTime());
    }

    public static String getFormattedDateTime() {
        return getFormattedDateTime("yyyyMMddHHmmss");
    }

    public static String getFormattedDateTime(String format) {
        return new SimpleDateFormat(format).format(LAUNCH_TIME_DATE);
    }

    public static long getLaunchTimeStamp() {
        return LAUNCH_TIME;
    }
}
