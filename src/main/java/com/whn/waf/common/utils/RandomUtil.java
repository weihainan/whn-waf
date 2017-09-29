package com.whn.waf.common.utils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class can helps us get some kinds of random number
 * <p>
 * All methods in this class are static,
 * you can use by RandomUtil.nameOfMethod().
 *
 * @author Wei
 * @version 1.0
 */
public final class RandomUtil {

    private RandomUtil() {
    }

    /**
     * get a random int value which is bigger than 0<br/>
     * warning:
     * new Random().nextInt();may get a value which is small than 0
     *
     * @return a random int
     */
    public static int nextInt() {
        int i = 0;
        do {
            i = ThreadLocalRandom.current().nextInt();
        } while (!(i > 0));

        return i;
    }

    /**
     * get a random long value which is bigger than 0
     * new Random().nextLong();may get a value which is small than 0
     *
     * @return a random long
     */
    public static long nextLong() {
        long l = 0L;
        do {
            l = ThreadLocalRandom.current().nextLong();
        } while (l <= 0L);
        return l;
    }

    /**
     * get a random float value which is bigger than 0
     * new Random().nextFloat();may get a value which is small than 0
     *
     * @return a random float
     */
    public static float nextFloat() {
        float f = 0F;
        do {
            f = ThreadLocalRandom.current().nextFloat();
        } while (f <= 0F);

        return f;
    }

    /**
     * get a random double value which is bigger than 0
     * new Random().nextDouble();may get a value which is small than 0
     *
     * @return a random double
     */
    public static double nextDouble() {
        double d = 0;
        do {
            d = ThreadLocalRandom.current().nextDouble();
        } while (d <= 0);
        return d;
    }

    /**
     * get a random int value x which  0<= x < max.
     * using return new Random().nextInt(max);
     *
     * @param max the max value
     * @return a random int
     */
    public static int nextInt(int max) {
        return ThreadLocalRandom.current().nextInt(max);
    }

    /**
     * get a  a random int value x, which min <= x <=max
     *
     * @param min the min value
     * @param max the max value  we need max>min
     * @return a random int
     */
    public static int nextInt(int min, int max) {

		/*Random random = new Random();
        return random.nextInt(max)%(max-min+1) + min;*/
        return (int) (Math.random() * (max - min + 1) + min);
    }

    /**
     * get n random int values ,which one value x: min<=x<max
     *
     * @param min the min value
     * @param max the max value
     * @param n   the number you want get
     * @return an array contains those values
     * @throws IllegalAccessException if(max <= min || n > max-min+1){
     */
    public static int[] getNoRepeatArray(int min, int max, int n) {
        int len = max - min + 1;

        if (max <= min || n > len) {
            throw new RuntimeException("min can not be bigger than max "
                    + "or length > (max-min+1)");
        }
        int[] source = new int[len];
        for (int i = min; i < min + len; i++) {
            source[i - min] = i;
        }

        int[] result = new int[n];
        Random rd = new Random();
        int index = 0;
        for (int i = 0; i < result.length; i++) {
            index = Math.abs(rd.nextInt() % len--);
            result[i] = source[index];
            source[index] = source[len];
        }
        return result;
    }

    private static final String BASE_CHARS_STRINGS = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-";

    public static String getRandomString(int length) { //length表示生成字符串的长度

        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(BASE_CHARS_STRINGS.length());
            sb.append(BASE_CHARS_STRINGS.charAt(number));
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        System.out.println(getRandomString(1));
        System.out.println(getRandomString(5));
        System.out.println(getRandomString(20));
        System.out.println(getRandomString(13));
    }

}
