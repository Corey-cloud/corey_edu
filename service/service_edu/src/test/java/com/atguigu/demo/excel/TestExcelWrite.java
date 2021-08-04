package com.atguigu.demo.excel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by corey on 2021/7/22
 **/

public class TestExcelWrite {
    public static void main(String[] args) {
        List<ExcelDemo> data = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            ExcelDemo demo = new ExcelDemo();
            demo.setSno(i);
            demo.setSName("lucy"+i);
            data.add(demo);
            System.out.println("i: "+i);
            System.out.println(data);

        }





    }
}
