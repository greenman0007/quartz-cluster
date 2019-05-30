package com.zzitbar.quartzcluster;

import org.mybatis.spring.annotation.MapperScan;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan("com.zzitbar.quartzcluster.mapper")
public class QuartzClusterApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuartzClusterApplication.class, args);
        ConfigurableApplicationContext context = SpringApplication.run(QuartzClusterApplication.class, args);
        Scheduler scheduler = context.getBean(Scheduler.class);
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
