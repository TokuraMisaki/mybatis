package com.hc;

/**
 * @Descrption: reflection
 * @Author: HC
 * @Date: 2020/8/28 13:25
 */
public class Reflection {
    public static void main(String[] args) {
        try {
            Class c1 = Class.forName("com.hc.Reflection");
            System.out.println(c1.hashCode());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Reflection reflection = new Reflection();
        Class c2 = reflection.getClass();
        System.out.println(c2.hashCode());
    }
}
