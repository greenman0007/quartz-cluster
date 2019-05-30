package com.zzitbar.quartzcluster.jobs;

import com.zzitbar.quartzcluster.service.TestService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @auther zzitbar
 * @create 2019-05-30 上午 09:49
 * @Description
 */
@Component
public class HelloJob implements Job {
    private Logger logger = LoggerFactory.getLogger(HelloJob.class);

    @Autowired
    private TestService testService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("JobName: {} start scheduling", context.getJobDetail().getKey().getName());
        testService.printNow();
    }
}
