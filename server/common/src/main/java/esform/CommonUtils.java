package esform;

import esform.date.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:20076581@qq.com on 2017/9/17.
 */
public class CommonUtils {
    public static Date[] getVisitFreqTimeRange(Date st, Date et) {
        Date nSt = st;
        Date nEt = et;
        // endTime往前退29天，看时间区间是否小于30天，如果是，则补齐30天
        Date tempD = DateUtils.addTime(et, Calendar.DAY_OF_MONTH, -29);
        if (tempD.compareTo(st) < 0) {
            nSt = tempD;
        }

        return new Date[]{nSt, nEt};
    }

    public static long getDays(Date st, Date et) {
        long sDays = st.getTime() / DateUtils.ONE_DAY_MILLI;
        long eDays = et.getTime() / DateUtils.ONE_DAY_MILLI;

        return eDays - sDays + 1;
    }

    public static Map<Integer, Integer> getPagesIndex(Integer listSize, Integer pageSize) {
        Map<Integer, Integer> retMap = new HashMap<>();
        if (pageSize == null || pageSize.intValue() == 0) {
            pageSize = 1024;
        }

        if (listSize < pageSize) {
            retMap.put(0, listSize);
        } else {
            Integer pages = listSize / pageSize;
            for (int i = 0; i < pages; i++) {
                retMap.put((i * pageSize), ((i + 1) * pageSize));
            }
            if ((pages * pageSize) != listSize) {
                retMap.put((pages * pageSize), listSize);
            }
        }
        return retMap;
    }

    public static boolean isMobile(String mobile) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(mobile);
        b = m.matches();
        return b;
    }

    public static boolean isInt(String integer) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[0-9]+$"); // 验证整数
        m = p.matcher(integer);
        b = m.matches();
        return b;
    }

    public static boolean isDouble(String doub) {

        return doub.matches("\\d+(\\.\\d+)*$");

    }
}
