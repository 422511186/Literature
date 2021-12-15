package com.example.demo;

/**
 * @Auther: hzy
 * @Date: 2021/11/4 14:31
 * @Description:
 */
public class Rect {
    private double width;
    private double length;

    public Rect(double width, double length) {
        setLength(length);
        setWidth(width);
    }

    public void setWidth(double w) {
        this.width = w;
    }

    public void setLength(double len) {
        this.length = len;
    }
    public double calArea(){
        return width*length;
    }
}
