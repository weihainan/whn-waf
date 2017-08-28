package com.whn.waf.common.utils;

/**
 * This class can help us deal with some problems of common kinds array
 * ,such as find min or max value, find a element's index in an array,
 * reverse an array and get a string from an array.<br/><br/>
 * <p>
 * All methods in this class are static,
 * you can use by ArraysUtil.nameOfMethod().<br/><br/>
 * <p>
 * You also can get other helps from class Arrays and System.<br/><br/>
 *
 * @author Wei
 * @version 1.0
 */

public final class ArraysUtil {

    private ArraysUtil() {
    }

    /**
     * using elements to make a string to describe the byte array
     * like [e1, e2, e3]
     *
     * @param a the target byte array
     * @return a string
     */
    public static String toString(byte[] a) {
        int lastIndex = a.length - 1;
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; ; i++) {
            if (i == lastIndex) {
                return sb.append(a[i]).append("]").toString();
            }
            sb.append(a[i]).append(", ");
        }
    }

    /**
     * using elements to make a string to describe the short array
     * like [e1, e2, e3]
     *
     * @param a the target short array
     * @return a string
     */
    public static String toString(short[] a) {
        int lastIndex = a.length - 1;
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; ; i++) {
            if (i == lastIndex) {
                return sb.append(a[i]).append("]").toString();
            }
            sb.append(a[i]).append(", ");
        }
    }

    /**
     * using elements to make a string to describe the int array
     * like [e1, e2, e3]
     *
     * @param a the target int array
     * @return a string
     */
    public static String toString(int[] a) {
        int lastIndex = a.length - 1;
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; ; i++) {
            if (i == lastIndex) {
                return sb.append(a[i]).append("]").toString();
            }
            sb.append(a[i]).append(", ");
        }
    }

    /**
     * using elements to make a string to describe the long array
     * like [e1, e2, e3]
     *
     * @param a the target long array
     * @return a string
     */
    public static String toString(long[] a) {
        int lastIndex = a.length - 1;
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; ; i++) {
            if (i == lastIndex) {
                return sb.append(a[i]).append("]").toString();
            }
            sb.append(a[i]).append(", ");
        }
    }

    /**
     * using elements to make a string to describe the float array
     * like [e1, e2, e3]
     *
     * @param a the target float array
     * @return a string
     */
    public static String toString(float[] a) {
        int lastIndex = a.length - 1;
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; ; i++) {
            if (i == lastIndex) {
                return sb.append(a[i]).append("]").toString();
            }
            sb.append(a[i]).append(", ");
        }
    }

    /**
     * using elements to make a string to describe the double array
     * like [e1, e2, e3]
     *
     * @param a the target double array
     * @return a string
     */
    public static String toString(double[] a) {
        int lastIndex = a.length - 1;
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; ; i++) {
            if (i == lastIndex) {
                return sb.append(a[i]).append("]").toString();
            }
            sb.append(a[i]).append(", ");
        }
    }

    /**
     * using elements to make a string to describe the char array
     * like [e1, e2, e3]
     *
     * @param a the target char array
     * @return a string
     */
    public static String toString(char[] a) {
        int lastIndex = a.length - 1;
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; ; i++) {
            if (i == lastIndex) {
                return sb.append(a[i]).append("]").toString();
            }
            sb.append(a[i]).append(", ");
        }
    }

    /**
     * to find a max value of all elements in the target byte array
     *
     * @param a the target byte array
     * @return the max value
     */
    public static byte max(byte[] a) {
        byte max = a[0];
        for (int i = 1; i < a.length; i++) {
            if (max < a[i]) {
                max = a[i];
            }
        }
        return max;
    }

    /**
     * to find a max value of all elements in the target short array
     *
     * @param a the target short array
     * @return the max value
     */
    public static short max(short[] a) {
        short max = a[0];
        for (int i = 1; i < a.length; i++) {
            if (max < a[i]) {
                max = a[i];
            }
        }
        return max;
    }


    /**
     * to find a max value of all elements in the target int array
     *
     * @param a the target int array
     * @return the max value
     */
    public static int max(int[] a) {
        int max = a[0];
        for (int i = 1; i < a.length; i++) {
            max = Math.max(a[i], max);
        }
        return max;
    }

    /**
     * to find a max value of all elements in the target long array
     *
     * @param a the target long array
     * @return the max value
     */
    public static long max(long[] a) {
        long max = a[0];
        for (int i = 1; i < a.length; i++) {
            if (max < a[i]) {
                max = a[i];
            }
        }
        return max;
    }

    /**
     * to find a max value of all elements in the target float array
     *
     * @param a the target float array
     * @return the max value
     */
    public static float max(float[] a) {
        float max = a[0];
        for (int i = 1; i < a.length; i++) {
            if (max < a[i]) {
                max = a[i];
            }
        }
        return max;
    }

    /**
     * to find a max value of all elements in the target double array
     *
     * @param a the target double array
     * @return the max value
     */
    public static double max(double[] a) {
        double max = a[0];
        for (int i = 1; i < a.length; i++) {
            if (max < a[i]) {
                max = a[i];
            }
        }
        return max;
    }

    /**
     * to find a max value of all elements in the target char array
     *
     * @param a the target char array
     * @return the max value
     */
    public static char max(char[] a) {
        char max = a[0];
        for (int i = 1; i < a.length; i++) {
            if (max < a[i]) {
                max = a[i];
            }
        }
        return max;
    }

    /**
     * to find a min value of all elements in the target byte array
     *
     * @param a the target byte array
     * @return the min value
     */
    public static byte min(byte[] a) {
        byte min = a[0];
        for (int i = 1; i < a.length; i++) {
            if (min > a[i])
                min = a[i];
        }
        return min;
    }

    /**
     * to find a min value of all elements in the target short array
     *
     * @param a the target short array
     * @return the min value
     */
    public static short min(short[] a) {
        short min = a[0];
        for (int i = 1; i < a.length; i++) {
            if (min > a[i])
                min = a[i];
        }
        return min;
    }

    /**
     * to find a min value of all elements in the target int array
     *
     * @param a the target int array
     * @return the min value
     */
    public static int min(int[] a) {
        int min = a[0];
        for (int i = 1; i < a.length; i++) {
            min = Math.min(a[i], min);
        }
        return min;
    }

    /**
     * to find a min value of all elements in the target long array
     *
     * @param a the target long array
     * @return the min value
     */
    public static long min(long[] a) {
        long min = a[0];
        for (int i = 1; i < a.length; i++) {
            if (min > a[i])
                min = a[i];
        }
        return min;
    }

    /**
     * to find a min value of all elements in the target float array
     *
     * @param a the target float array
     * @return the min value
     */
    public static float min(float[] a) {
        float min = a[0];
        for (int i = 1; i < a.length; i++) {
            if (min > a[i])
                min = a[i];
        }
        return min;
    }

    /**
     * to find a min value of all elements in the target double array
     *
     * @param a the target double array
     * @return the min value
     */
    public static double min(double[] a) {
        double min = a[0];
        for (int i = 1; i < a.length; i++) {
            if (min > a[i])
                min = a[i];
        }
        return min;
    }

    /**
     * to find a min value of all elements in the target char array
     *
     * @param a the target char array
     * @return the min value
     */
    public static char min(char[] a) {
        char min = a[0];
        for (int i = 1; i < a.length; i++) {
            if (min > a[i])
                min = a[i];
        }
        return min;
    }

    /**
     * to change the value of 2 different indexes in a byte array
     *
     * @param a the target byte array
     * @param x the first index
     * @param y the second index
     */
    public static void swap(byte a[], int x, int y) {
        byte temp = a[x];
        a[x] = a[y];
        a[y] = temp;
    }

    /**
     * to change the value of 2 different indexes in a short array
     *
     * @param a the target short array
     * @param x the first index
     * @param y the second index
     */
    public static void swap(short a[], int x, int y) {
        short temp = a[x];
        a[x] = a[y];
        a[y] = temp;
    }

    /**
     * to change the value of 2 different indexes in a int array
     *
     * @param a the target int array
     * @param x the first index
     * @param y the second index
     */
    public static void swap(int a[], int x, int y) {
        int temp = a[x];
        a[x] = a[y];
        a[y] = temp;
    }

    /**
     * to change the value of 2 different indexes in a long array
     *
     * @param a the target long array
     * @param x the first index
     * @param y the second index
     */
    public static void swap(long a[], int x, int y) {
        long temp = a[x];
        a[x] = a[y];
        a[y] = temp;
    }

    /**
     * to change the value of 2 different indexes in a float array
     *
     * @param a the target float array
     * @param x the first index
     * @param y the second index
     */
    public static void swap(float a[], int x, int y) {
        float temp = a[x];
        a[x] = a[y];
        a[y] = temp;
    }

    /**
     * to change the value of 2 different indexes in a double array
     *
     * @param a the target double array
     * @param x the first index
     * @param y the second index
     */
    public static void swap(double a[], int x, int y) {
        double temp = a[x];
        a[x] = a[y];
        a[y] = temp;
    }

    /**
     * to change the value of 2 different indexes in a char array
     *
     * @param a the target char array
     * @param x the first index
     * @param y the second index
     */
    public static void swap(char a[], int x, int y) {
        char temp = a[x];
        a[x] = a[y];
        a[y] = temp;
    }

    /**
     * to reverse the target byte array
     *
     * @param a the target byte array
     */
    public static void reverse(byte[] a) {
        int length = a.length;
        for (int i = 0; i < length / 2; i++) {
            swap(a, i, length - 1 - i);
        }
    }

    /**
     * to reverse the target short array
     *
     * @param a the target short array
     */
    public static void reverse(short[] a) {
        int length = a.length;
        for (int i = 0; i < length / 2; i++) {
            swap(a, i, length - 1 - i);
        }
    }

    /**
     * to reverse the target int array
     *
     * @param a the target int array
     */
    public static void reverse(int[] a) {
        int length = a.length;
        for (int i = 0; i < length / 2; i++) {
            swap(a, i, length - 1 - i);
        }
    }

    /**
     * to reverse the target long array
     *
     * @param a the target long array
     */
    public static void reverse(long[] a) {
        int length = a.length;
        for (int i = 0; i < length / 2; i++) {
            swap(a, i, length - 1 - i);
        }
    }

    /**
     * to reverse the target float array
     *
     * @param a the target float array
     */
    public static void reverse(float[] a) {
        int length = a.length;
        for (int i = 0; i < length / 2; i++) {
            swap(a, i, length - 1 - i);
        }
    }

    /**
     * to reverse the target double array
     *
     * @param a the target double array
     */
    public static void reverse(double[] a) {
        int length = a.length;
        for (int i = 0; i < length / 2; i++) {
            swap(a, i, length - 1 - i);
        }
    }

    /**
     * to reverse the target char array
     *
     * @param a the target char array
     */
    public static void reverse(char[] a) {
        int length = a.length;
        for (int i = 0; i < length / 2; i++) {
            swap(a, i, length - 1 - i);
        }
    }

    /**
     * find the first index of key in a byte array,
     * if array contains no key, return -1
     *
     * @param a target byte array
     * @param j the key
     * @return the first index of key
     */
    public static int indexOf(byte[] a, int j) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == j) {
                return i;
            }
        }
        return -1;
    }

    /**
     * find the first index of key in a short array,
     * if array contains no key, return -1
     *
     * @param a   target short array
     * @param key the key
     * @return the first index of key
     */
    public static int indexOf(short[] a, int key) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == key) {
                return i;
            }
        }
        return -1;
    }

    /**
     * find the first index of key in a int array,
     * if array contains no key, return -1
     *
     * @param a   target int array
     * @param key the key
     * @return the first index of key
     */
    public static int indexOf(int[] a, int key) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == key) {
                return i;
            }
        }
        return -1;
    }

    /**
     * find the first index of key in a long array,
     * if array contains no key, return -1
     *
     * @param a   target long array
     * @param key the key
     * @return the first index of key
     */
    public static int indexOf(long[] a, long key) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == key) {
                return i;
            }
        }
        return -1;
    }

    /**
     * find the first index of key in a float array,
     * if array contains no key, return -1
     *
     * @param a   target float array
     * @param key the key
     * @return the first index of key
     */
    public static int indexOf(float[] a, float key) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == key) {
                return i;
            }
        }
        return -1;
    }

    /**
     * find the first index of key in a double array,
     * if array contains no key, return -1
     *
     * @param a   target double array
     * @param key the key
     * @return the first index of key
     */
    public static int indexOf(double[] a, double key) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == key) {
                return i;
            }
        }
        return -1;
    }

    /**
     * find the first index of key in a char array,
     * if array contains no key, return -1
     *
     * @param a   target char array
     * @param key the key
     * @return the first index of key
     */
    public static int indexOf(char[] a, char key) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == key) {
                return i;
            }
        }
        return -1;
    }

    /**
     * find the last index of key in a byte array,
     * if array contains no key, return -1
     *
     * @param a target byte array
     * @param j the key
     * @return the last index of key
     */
    public static int lastIndexOf(byte[] a, int j) {
        for (int i = a.length - 1; i > -1; i--) {
            if (a[i] == j) {
                return i;
            }
        }
        return -1;
    }

    /**
     * find the last index of key in a short array,
     * if array contains no key, return -1
     *
     * @param a   target short array
     * @param key the key
     * @return the last index of key
     */
    public static int lastIndexOf(short[] a, int key) {
        for (int i = a.length - 1; i > -1; i--) {
            if (a[i] == key) {
                return i;
            }
        }
        return -1;
    }

    /**
     * find the last index of key in a int array,
     * if array contains no key, return -1
     *
     * @param a   target int array
     * @param key the key
     * @return the last index of key
     */
    public static int lastIndexOf(int[] a, int key) {
        for (int i = a.length - 1; i > -1; i--) {
            if (a[i] == key) {
                return i;
            }
        }
        return -1;
    }

    /**
     * find the last index of key in a long array,
     * if array contains no key, return -1
     *
     * @param a   target long array
     * @param key the key
     * @return the last index of key
     */
    public static int lastIndexOf(long[] a, long key) {
        for (int i = a.length - 1; i > -1; i--) {
            if (a[i] == key) {
                return i;
            }
        }
        return -1;
    }

    /**
     * find the last index of key in a float array,
     * if array contains no key, return -1
     *
     * @param a   target float array
     * @param key the key
     * @return the last index of key
     */
    public static int lastIndexOf(float[] a, float key) {
        for (int i = a.length - 1; i > -1; i--) {
            if (a[i] == key) {
                return i;
            }
        }
        return -1;
    }

    /**
     * find the last index of key in a double array,
     * if array contains no key, return -1
     *
     * @param a   target double array
     * @param key the key
     * @return the last index of key
     */
    public static int lastIndexOf(double[] a, double key) {
        for (int i = a.length - 1; i > -1; i--) {
            if (a[i] == key) {
                return i;
            }
        }
        return -1;
    }

    /**
     * find the last index of key in a char array,
     * if array contains no key, return -1
     *
     * @param a   target char array
     * @param key the key
     * @return the last index of key
     */
    public static int lastIndexOf(char[] a, char key) {
        for (int i = a.length - 1; i > -1; i--) {
            if (a[i] == key) {
                return i;
            }
        }
        return -1;
    }

    /**
     * find the times that the key occurred in the byte type array
     *
     * @param bytes target byte array
     * @param key   the key
     * @return the key occurred count
     */
    public static int getCount(byte[] bytes, int key) {
        int count = 0;
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] == key) {
                count++;
            }
        }
        return count;
    }

    /**
     * find the times that the key occurred in the short type array
     *
     * @param bytes target short array
     * @param key   the key
     * @return the key occurred count
     */
    public static int getCount(short[] shorts, int key) {
        int count = 0;
        for (int i = 0; i < shorts.length; i++) {
            if (shorts[i] == key) {
                count++;
            }
        }
        return count;
    }

    /**
     * find the times that the key occurred in the int type array
     *
     * @param bytes target int array
     * @param key   the key
     * @return the key occurred count
     */
    public static int getCount(int[] ints, int key) {
        int count = 0;
        for (int i = 0; i < ints.length; i++) {
            if (ints[i] == key) {
                count++;
            }
        }
        return count;
    }

    /**
     * find the times that the key occurred in the float type array
     *
     * @param bytes target float array
     * @param key   the key
     * @return the key occurred count
     */
    public static int getCount(float[] floats, int key) {
        int count = 0;
        for (int i = 0; i < floats.length; i++) {
            if (floats[i] == key) {
                count++;
            }
        }
        return count;
    }

    /**
     * find the times that the key occurred in the double type array
     *
     * @param bytes target double array
     * @param key   the key
     * @return the key occurred count
     */
    public static int getCount(double[] doubles, int key) {
        int count = 0;
        for (int i = 0; i < doubles.length; i++) {
            if (doubles[i] == key) {
                count++;
            }
        }
        return count;
    }

    /**
     * find the times that the key occurred in the char type array
     *
     * @param bytes char double array
     * @param key   the key
     * @return the key occurred count
     */
    public static int getCount(char[] chars, int key) {
        int count = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == key) {
                count++;
            }
        }
        return count;
    }
}
