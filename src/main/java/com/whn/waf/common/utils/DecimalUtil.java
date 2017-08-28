package com.whn.waf.common.utils;

import java.math.BigDecimal;

/**
 * this class can help us solve calculating problems.<br/>
 * if you need accurate calculating result, especially about money,
 * please use this class.<br/>
 * All methods in this class are static,
 * you can use by RandomUtil.nameOfMethod().
 *
 * @author Wei
 * @version 1.0
 */

final public class DecimalUtil {

    /**
     * default scale = 3 for calculating
     */
    public static final int DEFAUT_SCALE_3 = 3;

    private DecimalUtil() {
    }

    /**
     * can give us a accurate sum of two numbers
     *
     * @param v1 in fact, can be any base data type
     * @param v2 in fact, can be any base data type
     * @return the sum of v1 and v2
     */
    public static BigDecimal add(double v1, double v2) {
        return
                new BigDecimal(String.valueOf(v1))
                        .add(new BigDecimal(String.valueOf(v2)));
    }

    /**
     * can give us a accurate result of v1 - v2
     *
     * @param v1 in fact,can be any base data type
     * @param v2 in fact,can be any base data type
     * @return v1-v2
     */
    public static BigDecimal sub(double v1, double v2) {
        return new BigDecimal(String.valueOf(v1))
                .subtract(new BigDecimal(String.valueOf(v2)));
    }

    /**
     * can give us a accurate result of v1 * v2
     *
     * @param v1 in fact,can be any base data type
     * @param v2 in fact,can be any base data type
     * @return v1*v2
     */
    public static BigDecimal mul(double v1, double v2) {
        return new BigDecimal(String.valueOf(v1))
                .multiply(new BigDecimal(String.valueOf(v2)));
    }

    /**
     * can give us a accurate result with a assigned scale of v1 / v2
     *
     * @param v1    in fact,can be any base data type
     * @param v2    in fact,can be any base data type
     * @param scale the scale that you assign,be sure that scale>0
     * @return v1/v2
     */
    public static BigDecimal div(double v1, double v2, int scale) {

        if (scale < 0) throw new RuntimeException("scale must > 0");

        return new BigDecimal(String.valueOf(v1))
                .divide(new BigDecimal(String.valueOf(v2)), scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * can give us a accurate result with the default 2 scale of v1 / v2
     *
     * @param v1    in fact,can be any base data type
     * @param v2    in fact,can be any base data type
     * @return v1/v2
     */
    public static BigDecimal div(double v1, double v2) {
        return div(v1, v2, DEFAUT_SCALE_3);
    }

    /**
     * can give us a accurate result of v1 % v2
     *
     * @param v1 in fact,can be any base data type
     * @param v2 in fact,can be any base data type
     * @return v1%v2
     */
    public static BigDecimal mod(double v1, double v2) {
        return new BigDecimal(String.valueOf(v1))
                .remainder(new BigDecimal(String.valueOf(v2)));
    }

    /**
     * can give us a accurate result with the a assigned scale
     *
     * @param v     the value
     * @param scale the scale that you assign,be sure that scale>=0
     * @return
     */
    public static BigDecimal round(double v, int scale) {
        if (scale < 0) throw new RuntimeException("sacle can not < 0");
        return new BigDecimal(String.valueOf(v))
                .divide(new BigDecimal("1"), scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * can give us a accurate result with the default scale
     *
     * @param v the value
     * @return
     */
    public static BigDecimal round(double v) {
        return round(v, DEFAUT_SCALE_3);
    }

    /**
     * to find a max value of v1 and v2
     *
     * @param v1 in fact,can be any base data type
     * @param v2 in fact,can be any base data type
     * @return the max of v1 and v2
     */
    public static BigDecimal max(double v1, double v2) {
        return new BigDecimal(String.valueOf(v1))
                .max(new BigDecimal(String.valueOf(v2)));
    }

    /**
     * to find a max value of v1 and v2
     *
     * @param v1 in fact,can be any base data type
     * @param v2 in fact,can be any base data type
     * @return the max of v1 and v2
     */
    public static BigDecimal min(double v1, double v2) {
        return new BigDecimal(String.valueOf(v1))
                .min(new BigDecimal(String.valueOf(v2)));
    }

    /**
     * to compare 2 values
     *
     * @param v1
     * @param v2
     * @return
     */
    public static int compareTo(double v1, double v2) {
        return new BigDecimal(String.valueOf(v1))
                .compareTo(new BigDecimal(String.valueOf(v2)));
    }

    /**
     * to calculate the accurate value of v^n
     *
     * @param v
     * @param n 0 -- 999999999
     * @return v^n
     */
    public static BigDecimal pow(double v, int n) {
        return new BigDecimal(String.valueOf(v)).pow(n);
    }

//	public static void main(String [] strs) throws IllegalAccessException
//	{
//		System.out.println(add(1.0, 0.1));
//		System.out.println((1.0+0.1));
//
//		System.out.println(sub(1.0, 0.9));
//		System.out.println((1.0-0.1));
//
//		System.out.println(mul(1.0, 0.9));
//		System.out.println((1.0*0.1));
//
//		System.out.println(div(1.0, 0.9));
//		System.out.println((1.0/3.0));
//
//		System.out.println(div(1.0, 3,3));
//		System.out.println((1.0/3.0));
//
//		System.out.println(round(2.3334436, 3));
//
//		System.out.println(max(1,2));
//		System.out.println(min(1,2));
//
//		System.out.println(compareTo(1,2));
//		System.out.println(compareTo(3,2));
//		System.out.println(compareTo(2,2));
//
//        1.1
//        1.1
//        0.1
//        0.9
//        0.90
//        0.1
//        1.111
//        0.3333333333333333
//        0.333
//        0.3333333333333333
//        2.333
//        2.0
//        1.0
//        -1
//        1
//        0
//	}
}
