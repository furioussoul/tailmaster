package esform.math;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:200765821@qq.com on 2017/9/17.
 */
public class MathUtils {
    private static final Logger logger = LoggerFactory.getLogger(MathUtils.class);
    // 默认除法运算精度
    private static final int DEF_DIV_SCALE = 2;

    private MathUtils() {
    }

    /**
     * 元转分
     *
     * @param in
     * @return
     */
    public static Integer moneyDecimal2Integer(BigDecimal in) {
        return null == in ? null : in.multiply(new BigDecimal(100)).intValue();
    }

    /**
     * 分转元
     *
     * @param in
     * @return
     */
    public static BigDecimal moneyInteger2Decimal(Integer in) {
        return null == in ? null : new BigDecimal(in).multiply(new BigDecimal("0.01"));
    }

    public static Integer parseInt(String value) {
        if (value == null) {
            return null;
        }
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
        return null;
    }


    public static int parseInt(String value, int defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
        return defaultValue;
    }

    public static short parseShort(String value, short defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        try {
            return Short.parseShort(value);
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
        return defaultValue;
    }


    public static long parseLong(String value) {
        return parseLong(value, 0L);
    }

    public static long parseLong(String value, Long defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        try {
            return Long.parseLong(value);
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
        return defaultValue;
    }

    public static float parseFloat(String value, float defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        try {
            return Float.parseFloat(value);
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
        return defaultValue;
    }

    public static boolean parseBoolean(String value, boolean defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        try {
            return Boolean.parseBoolean(value);
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
        return defaultValue;
    }


    /**
     * 提供精确的加法运算。
     *
     * @param v1 * 被数
     * @param v2 * 加数
     * @return 两个参数的和
     */

    public static double add(double v1, double v2) {
        return add(Double.toString(v1), Double.toString(v2));
    }

    public static double add(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 * 被减数
     * @param v2 * 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {
        return sub(Double.toString(v1), Double.toString(v2));
    }

    public static double sub(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 * 被乘数
     * @param v2 * 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后2位，以后的数字四舍五入。
     *
     * @param v1 * 被除数
     * @param v2 * 除数
     * @return 两个参数的商
     */

    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     *
     * @param v1    * 被除数
     * @param v2    * 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        return div(Double.toString(v1), Double.toString(v2), scale);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，默认精确到两位小数，以后的数字四舍五入。
     *
     * @param v1 * 被除数
     * @param v2 * 除数
     * @return 两个参数的商
     */
    public static double div(String v1, String v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     *
     * @param v1    * 被除数
     * @param v2    * 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(String v1, String v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     * 需要四舍五入的数字
     * @param scale * 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
