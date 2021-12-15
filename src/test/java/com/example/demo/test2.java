package com.example.demo;

import java.util.UUID;

/**
 * @Auther: hzy
 * @Date: 2021/11/1 16:33
 * @Description:
 */
public class test2 {
    public static void main(String[] args) {
        String s = UUID.randomUUID().toString().replace("-", "");
        System.out.println("s = " + s);

    }
}
