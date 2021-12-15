package com.example.demo;

import org.junit.jupiter.api.Test;

/**
 * @Auther: hzy
 * @Date: 2021/10/17 18:41
 * @Description:
 */
public class test {
    public static void main(String[] args) {
        String[] str = {"1", "2", "3", "4", "5"};
        String a = "0,3,";
        String[] x = a.split(",");

        String[] str1 = new String[x.length];
        for (int i = 0; i < str1.length; i++) {
            str1[i] = str[Integer.parseInt(x[i])];
        }

        for (String s : str1) {
            System.out.println("s = " + s);
        }
    }

    @Test
    public void A() {
        String s = "1";
        String[] split = s.split("/");
        for (String x : split) {
            System.out.println("x = " + x);
        }
    }
}
