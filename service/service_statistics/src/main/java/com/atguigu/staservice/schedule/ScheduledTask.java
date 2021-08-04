package com.atguigu.staservice.schedule;

import com.atguigu.staservice.service.StatisticsDailyService;
import com.atguigu.staservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by corey on 2021/8/1
 **/
@Component
public class ScheduledTask {

    @Autowired
    private StatisticsDailyService dailyService;

//    @Scheduled(cron = "0/1 * * * * ?")
//    public void task1() {
//        System.out.println(new Date());
//    }

    /**
     * 每天0点执行定时
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void task2() {
        //获取上一天的日期
        String day = DateUtil.formatDate(DateUtil.addDays(new Date(), -1));
        dailyService.createStatisticsByDay(day);
    }
}
