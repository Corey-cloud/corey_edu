package com.atguigu.demo;

import org.junit.Test;

import java.util.Random;

/**
 * Created by corey on 2021/8/1
 **/

public class Test1 {

    @Test
    public void test123() {
        Random random = new Random();
        int number = random.nextInt(200);
        System.out.println(number);
    }
}
