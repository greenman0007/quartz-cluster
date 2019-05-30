package com.zzitbar.quartzcluster.config;

import java.io.IOException;
import java.util.Properties;

import org.quartz.Scheduler;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class SchedulerConfig {

    @Bean(name = "SchedulerFactory")
    public SchedulerFactoryBean schedulerFactoryBean(QuartzJobFactory myJobFactory) throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        //使job实例支持spring 容器管理
        factory.setOverwriteExistingJobs(true);
        factory.setJobFactory(myJobFactory);

        factory.setQuartzProperties(quartzProperties());
        // 延迟10s启动quartz
        factory.setStartupDelay(10);
        return factory;
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        //在quartz.properties中的属性被读取并注入后再初始化对象
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    /*
     * quartz初始化监听器, 可以监听到工程的启动，在工程停止再启动时可以让已有的定时任务继续进行
     */
    @Bean
    public QuartzInitializerListener executorListener() {
        return new QuartzInitializerListener();
    }

    /*
     * 通过SchedulerFactoryBean获取Scheduler的实例
     */
    @Bean(name = "scheduler")
    public Scheduler scheduler(@Autowired QuartzJobFactory jobFactory) throws IOException {
        return schedulerFactoryBean(jobFactory).getScheduler();
    }
}