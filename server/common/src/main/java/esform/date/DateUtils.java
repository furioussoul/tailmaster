package esform.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:20076581@qq.com on 2017/9/17.
 */
public final class DateUtils {

    public final static String yyyyMMddHHmm = "yyyyMMddHHmm";
    public final static String PATTERN_KF_DAY = "yyyyMMdd";

    public final static String PATTERN_KF_TIME = "HHmmss";
    public final static String PATTERN_KF_DAY_TIME = "yyyyMMdd HHmmss";

    public final static String PATTERN_ONE = "yyyy-MM-dd HH:mm:ss";

    public final static String PATTERN_TWO = "HH:mm:ss";

    public final static String PATTERN_THREE = "yyyy-MM-dd";

    public final static String PATTERN_FOUR = "yyyy-MM-dd HH:mm";

    public final static String PATTERN_FIVE = "yyyy-MM";

    public final static String PATTERN_SIX = "yyyy-MM-dd HH";

    public final static String PATTERN_SEVEN = "yyyy-MM-dd HH:mm:ss.hhh";

    public final static String PATTERN_EIGHT = "yyyyMMdd";

    public final static String PATTERN_TEN = "HH";

    public final static String WIFI_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public final static String PATTERN_AUTH = "yyyyMMddHHmmss";

    public final static long ONE_MINUTE_MILLI = 60 * 1000;

    public final static long ONE_HOUR_MILLI = 60 * ONE_MINUTE_MILLI;

    public final static long ONE_DAY_MILLI = 24 * ONE_HOUR_MILLI;
    public final static int OLD_CUSTOMER_PERIOD_DAYS = 180;
    private static Random rd = new Random();

    public static String formatDate(Date inputDate, String pattern) {
        String output = null;
        if (null == inputDate || null == pattern) {
            return output;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            output = sdf.format(inputDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

    public static Date parseDate(String dateStr, String pattern) {
        if (null == dateStr || null == pattern) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(dateStr);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date addTime(Date curData, int field, int value) {
        Calendar caldedar = Calendar.getInstance();
        caldedar.setTime(curData);
        caldedar.add(field, value);

        return caldedar.getTime();
    }

    public static Date setTime(Date curData, int field, int value) {
        Calendar caldedar = Calendar.getInstance();
        caldedar.setTime(curData);
        caldedar.set(field, value);

        return caldedar.getTime();
    }

    public synchronized static long timeRandomNum() {
        long ts = System.currentTimeMillis();
        ts = ts * 100 + rd.nextInt(100);

        return ts;
    }

    public static int getHour(Date inputDate) {
        return Integer.valueOf(formatDate(inputDate, PATTERN_TEN));
    }

    public static String getPrevDay(String day) {
        Date tempDate = parseDate(day, PATTERN_THREE);
        tempDate = new Date(tempDate.getTime() - ONE_DAY_MILLI);

        return formatDate(tempDate, PATTERN_THREE);
    }

    public static String getNextDay(String day) {
        Date tempDate = parseDate(day, PATTERN_THREE);
        tempDate = addTime(tempDate, Calendar.DAY_OF_YEAR, 1);

        return formatDate(tempDate, PATTERN_THREE);
    }

    public static Date getDayLastTime(Date time) {
        Calendar ca = new GregorianCalendar();
        ca.setTime(time);
        ca.set(Calendar.HOUR_OF_DAY, 23);
        ca.set(Calendar.MINUTE, 59);
        ca.set(Calendar.SECOND, 59);
        Date dayLastTime = ca.getTime();
        return dayLastTime;
    }

    public static Long getHourOfTime(Date time) {
        Calendar ca = new GregorianCalendar();
        ca.setTime(time);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND, 0);
        return ca.getTime().getTime();
    }

    public static Long getDayOfTime(Date time) {
        Calendar ca = new GregorianCalendar();
        ca.setTime(time);
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND, 0);
        return ca.getTime().getTime();
    }

    public static Date getPrevWeekOfTime(Date time) {
        Calendar ca = new GregorianCalendar();
        ca.setTime(time);
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.add(Calendar.WEEK_OF_YEAR, -1);
        return ca.getTime();
    }

    public static int distanceDays(Date startDate, Date endDate) {
        String startDateStr = formatDate(startDate, PATTERN_THREE);
        startDate = parseDate(startDateStr, PATTERN_THREE);

        String endDateStr = formatDate(endDate, PATTERN_THREE);
        endDate = parseDate(endDateStr, PATTERN_THREE);

        long distanceDays = (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24);
        return (int) distanceDays;

    }

    /**
     * 用于同比计算时，取得上个周期的开始时间（开始时间 - （结束时间-开始时间）） -->
     * StartTime = StartTime - (EndTime-StartTime)-1
     */
    public static Date getLastStartDate(Date startDate, Date endDate) {
        int distanceDays = distanceDays(startDate, endDate);

        Date lastStartDate = addTime(startDate, Calendar.DAY_OF_MONTH, 0 - distanceDays - 1);
        return lastStartDate;

    }

    /**
     * 用于同比计算时，取得上个周期的结束时间（结束时间 - （结束时间-开始时间）） -->
     * 开始（时间-1）
     */
    public static Date getLastEndDate(Date startDate, Date endDate) {
//		int distanceDays = distanceDays(startDate, endDate);
//
//		Date lastEndDate = addTime(endDate, Calendar.DAY_OF_MONTH, 0 - distanceDays);
        Date lastEndDate = addTime(startDate, Calendar.DAY_OF_MONTH, -1);
        return lastEndDate;

    }

    /**
     * 在计算趋势时使用，取得下一个星期一
     */
    public static Date getNextMonday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        while (true) {
            if (calendar.get(Calendar.DAY_OF_WEEK) == 2) {// 星期一
                return calendar.getTime();
            }
            calendar.add(Calendar.DATE, 1);
        }

    }

    /**
     * 在计算趋势时使用，取得上一个星期日
     */
    public static Date getLastSunday(Date endDate) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        while (true) {
            if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
                return calendar.getTime();
            }
            calendar.add(Calendar.DATE, -1);
        }

    }

    /**
     * 在计算趋势时使用，取得下一月一号
     */
    public static Date getNextNo1(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        while (true) {
            if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {// 1号
                return calendar.getTime();
            }
            calendar.add(Calendar.DATE, 1);
        }

    }

    /**
     * 在计算趋势时使用，取得上一月最后一日
     */
    public static Date getLastNo30(Date endDate) {
        //endDate calendar
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        //endDate那个月的calendar，最后一天
        Calendar thisCalendar = Calendar.getInstance();
        thisCalendar.setTime(endDate);
        int value = thisCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        thisCalendar.set(Calendar.DAY_OF_MONTH, value);

        if (calendar.get(Calendar.YEAR) == thisCalendar.get(Calendar.YEAR) &&
                calendar.get(Calendar.MONTH) == thisCalendar.get(Calendar.MONTH) &&
                calendar.get(Calendar.DATE) == thisCalendar.get(Calendar.DATE)) {//这个月
            return calendar.getTime();
        } else {//下个月
            calendar.add(Calendar.MONTH, -1);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            return calendar.getTime();
        }

    }

    public static Date getOldCustomerPeriodDay(Date startDate) {
        return DateUtils.addTime(startDate, Calendar.DAY_OF_YEAR, -OLD_CUSTOMER_PERIOD_DAYS);
    }

    /**
     * 用于WS中，取得上个周期的日
     */
    public static Date getLastCircleDate(int timeType, Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (timeType == 2) {
            cal.add(Calendar.WEEK_OF_MONTH, -1);//上周
        } else if (timeType == 3) {
            cal.add(Calendar.MONTH, -1);//上月
        } else if (timeType == 4) {
            cal.add(Calendar.MONTH, -3);//上季度
        } else {
            cal.add(Calendar.DATE, -1);//昨天
        }
        return cal.getTime();

    }

    /**
     * 用于WS中，取得本周期的最后一天
     */
    public static Date getEndDateByTimeType(int timeType, Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (timeType == 2) {
            cal.add(Calendar.DATE, 7);//下周
            cal.add(Calendar.DATE, -1);
        } else if (timeType == 3) {
            cal.add(Calendar.MONTH, 1);//下月
            cal.add(Calendar.DATE, -1);
        } else if (timeType == 4) {//下个季度 - 1天
            cal.add(Calendar.MONTH, 3);//下季度
            cal.add(Calendar.DATE, -1);
        }
        return cal.getTime();

    }

    /**
     * 取得该日所在月的，月的timeStamp
     *
     * @param date
     * @return
     */
    public static long getOneMonthTimeStamp(Date date) {
        Calendar thisCalendar = Calendar.getInstance();
        thisCalendar.setTime(date);
        int value = thisCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        long ts = value * ONE_DAY_MILLI;
        return ts;
    }


    public static Date getDateByDayAndTime(Date date, String dateFormate, Date time, String timeFormate) {

        if (date == null) {
            date = new Date();
        }
        Date rd = date;
        String formate = dateFormate + " " + timeFormate;
        String rdString = formatDate(date, dateFormate) + " " + formatDate(time, timeFormate);
        rd = parseDate(rdString, formate);
        return rd;
    }

    /**
     * 获取当前时间,统一时间获取方式
     * 如果以后有多台服务器时,需要有一台服务器提供标准时间,只需要更改此方法实现即可
     *
     * @return
     */
    public static Date now() {
        return new Date();
    }

    public static boolean isValid(Date date) {
        Date now = DateUtils.parseDate(DateUtils.formatDate(now(), DateUtils.PATTERN_THREE), DateUtils.PATTERN_THREE);
        return date.getTime() >= now.getTime();
    }

}
