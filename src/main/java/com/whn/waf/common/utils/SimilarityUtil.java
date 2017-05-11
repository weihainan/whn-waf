package com.whn.waf.common.utils;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 字符串列表按照与某一个字符串的相似度降序排列
 * @author weihainan.
 * @since 0.1 created on 2017/5/11.
 */
public class SimilarityUtil {

    private static int compare(String str, String target) {
        int d[][];              // 矩阵
        int n = str.length();
        int m = target.length();
        int i;                  // 遍历str的
        int j;                  // 遍历target的
        char ch1;               // str的
        char ch2;               // target的
        int temp;               // 记录相同字符,在某个矩阵位置值的增量,不是0就是1
        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }
        d = new int[n + 1][m + 1];
        for (i = 0; i <= n; i++) {                       // 初始化第一列
            d[i][0] = i;
        }

        for (j = 0; j <= m; j++) {                       // 初始化第一行
            d[0][j] = j;
        }

        for (i = 1; i <= n; i++) {                       // 遍历str
            ch1 = str.charAt(i - 1);
            // 去匹配target
            for (j = 1; j <= m; j++) {
                ch2 = target.charAt(j - 1);
                if (ch1 == ch2 || ch1 == ch2 + 32 || ch1 + 32 == ch2) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                // 左边+1,上边+1, 左上角+temp取最小
                d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + temp);
            }
        }
        return d[n][m];
    }

    private static int min(int one, int two, int three) {
        return (one = one < two ? one : two) < three ? one : three;
    }

    /**
     * 获取两字符串的相似度
     */
    public static float getSimilarityRatio(String str, String target) {
        return 1 - (float) compare(str, target) / Math.max(str.length(), target.length());
    }


    public static List<String> sortBySimilar(String key, List<String> list) {
        List<Comp> comps = Lists.newArrayList();
        for (String str : list) {
            float ratio = getSimilarityRatio(str, key);
            comps.add(new Comp(ratio, str));
        }

        Collections.sort(comps, new Comparator<Comp>() {
            public int compare(Comp o1, Comp o2) {
                return (o2.getRatio() < o1.getRatio()) ? -1 : ((o2.getRatio() == o1.getRatio()) ? 0 : 1);
            }
        });
        List<String> res = Lists.newArrayList();
        for (Comp comp : comps){
            res.add(comp.getStr());
        }
        return res;
    }

    public static class Comp{
        private String str;
        private float ratio;

        public Comp(float ratio, String str) {
            this.ratio = ratio;
            this.str = str;
        }

        public float getRatio() {
            return ratio;
        }

        public void setRatio(float ratio) {
            this.ratio = ratio;
        }

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Comp comp = (Comp) o;
            return Float.compare(comp.ratio, ratio) == 0 &&
                    Objects.equal(str, comp.str);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(str, ratio);
        }
    }

    public static void main(String[] args) {
        System.out.println(sortBySimilar("1234", Lists.newArrayList("1", "1234", "23", "56")));
    }

}
