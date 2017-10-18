package com.whn.waf.common.utils;

import java.text.MessageFormat;

/**
 * This class can help us deal with some problems of
 * class String and string array
 * <br/><br/>
 * <p>
 * All methods in this class are static,
 * you can use by StringUtils.nameOfMethod().
 *
 * @author Wei
 * @version 1.0
 */

final public class StringUtil {

    private StringUtil() {
    }

    /**
     * to reverse the target string. "abc" -- "cba"
     *
     * @param str the target String
     */
    public static String reverse(String str) {
        char[] chs = str.toCharArray();
        StringBuilder sb = new StringBuilder();

        for (int x = chs.length - 1; ; x--) {
            if (x == 0) {
                return sb.append(chs[x]).toString();
            }
            sb.append(chs[x]);
        }
    }

    /**
     * to find out the time
     * that the short string occurred in the long string
     *
     * @param longStr  the long string
     * @param shortStr the short string
     * @return the occurred time
     */
    public static int repeatTimes(String longStr, String shortStr) {
        int count = 0;
        int shortLength = shortStr.length();
        int index = 0;

        while ((index = longStr.indexOf(shortStr)) != -1) {
            count++;
            longStr = longStr.substring(index + shortLength);
        }

        return count;
    }

    /**
     * find the longest child string between two parent strings
     *
     * @param s1 the first parent string
     * @param s2 the second parent string
     * @return the longest child, if it does not exit,return null
     */
    public static String getMaxSubstring(String s1, String s2) {

        String max = null;
        String min = null;
        max = (s1.length() > s2.length()) ? s1 : s2;
        min = max.equals(s1) ? s2 : s1;

        for (int i = 0; i < min.length(); i++) {

            for (int a = 0, b = min.length() - i; b != min.length() + 1; a++, b++) {

                String sub = min.substring(a, b);
                if (max.contains(sub)) {
                    return sub;
                }
            }
        }
        return null;
    }

    /**
     * to sort a string array by nature way
     *
     * @param strs the string array needs to be sorted
     */
    public static void sort(String[] strs) {
        for (int i = 0; i < strs.length - 1; i++) {
            for (int j = i + 1; j < strs.length; j++) {
                if (strs[i].compareTo(strs[j]) > 0) {
                    swap(strs, i, j);
                }
            }
        }
    }

    /**
     * using elements to make a string to describe the string array
     * like [str1, str2, str3]
     *
     * @param strs the target byte array
     * @return a string
     */
    public static String toString(String[] strs) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int x = 0; ; x++) {
            if (x == strs.length - 1) {
                return sb.append(strs[x]).append("]").toString();
            }
            sb.append(strs[x]).append(", ");
        }
    }

    /**
     * find the first index of key in a string array,
     * if array contains no key, return -1
     *
     * @param strs target string array
     * @param key  the key
     * @return the first index of key
     */
    public static int indexOf(String[] strs, String key) {
        for (int i = 0; i < strs.length; i++) {
            if (key.equals(strs[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * find the last index of key in a String array,
     * if array contains no key, return -1
     *
     * @param strs target String array
     * @param key  the key
     * @return the last index of key
     */
    public static int lastIndexOf(String[] strs, String key) {
        for (int i = strs.length - 1; i > -1; i--) {
            if (strs[i] == key) {
                return i;
            }
        }
        return -1;
    }

    /**
     * to reverse the target String array
     *
     * @param strs the target string array
     */
    public static void reverse(String[] strs) {
        int length = strs.length;
        for (int i = 0; i < length / 2; i++) {
            swap(strs, i, length - 1 - i);
        }
    }

    /**
     * to change the value of 2 different indexes in a string array
     *
     * @param strs the target string array
     * @param x    the first index
     * @param y    the second index
     */
    public static void swap(String[] strs, int x, int y) {
        String temp = strs[x];
        strs[x] = strs[y];
        strs[y] = temp;
    }

    /**
     * str: i am a {0},my age is {2}
     * --
     * objs:{"wei",12}
     * -->i am a wei,my age is 12
     *
     * @param str  target string
     * @param objs the values we need
     * @return
     */
    public static String takePart(String str, Object... objs) {
        return MessageFormat.format(str, objs);
    }
}
