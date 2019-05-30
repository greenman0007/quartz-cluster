package com.zzitbar.quartzcluster.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzitbar.quartzcluster.dto.JobAndTriggerDto;
import com.zzitbar.quartzcluster.dto.PageDataDto;
import com.zzitbar.quartzcluster.dto.PageReqDto;
import com.zzitbar.quartzcluster.mapper.QuartzMapper;
import com.zzitbar.quartzcluster.service.QuartzService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther zhangtengfei
 * @create 2019-05-30 上午 09:06
 * @Description
 */
@Service
public class QuartzServiceImpl extends ServiceImpl implements QuartzService {

    @Autowired
    private QuartzMapper quartzMapper;
    @Autowired
    @Qualifier("scheduler")
    private Scheduler scheduler;

    @Override
    public PageDataDto getJobAndTriggerDetails(PageReqDto dto) {
        List<JobAndTriggerDto> data = quartzMapper.getJobAndTriggerDetails(dto.getPage());
        return PageDataDto.buildPageData(dto, data);
    }

    @Override
    public void addJob(String jobClassName, String jobGroupName, String cronExpression) throws Exception {
        JobDetail jobDetail = JobBuilder.newJob(getClass(jobClassName).getClass()).withIdentity(jobClassName, jobGroupName).build();
        //表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobClassName, jobGroupName).withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, trigger);
    }

    @Override
    public void pauseJob(String jobClassName, String jobGroupName) throws Exception {
        scheduler.pauseJob(JobKey.jobKey(jobClassName, jobGroupName));
    }

    @Override
    public void resumeJob(String jobClassName, String jobGroupName) throws Exception {
        scheduler.resumeJob(JobKey.jobKey(jobClassName, jobGroupName));
    }

    @Override
    public void rescheduleJob(String jobClassName, String jobGroupName, String cronExpression) throws Exception {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobClassName, jobGroupName);
        // 表达式调度构建器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        // 按新的cronExpression表达式重新构建trigger
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
        // 按新的trigger重新设置job执行
        scheduler.rescheduleJob(triggerKey, trigger);
    }

    @Override
    public void deleteJob(String jobClassName, String jobGroupName) throws Exception {
        scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName, jobGroupName));
        scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName, jobGroupName));
        scheduler.deleteJob(JobKey.jobKey(jobClassName, jobGroupName));
    }

    public static Job getClass(String classname) throws Exception {
        Class<?> class1 = Class.forName(classname);
        return (Job) class1.newInstance();
    }
}
