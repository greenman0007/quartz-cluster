package com.zzitbar.quartzcluster.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzitbar.quartzcluster.dto.PageDataDto;
import com.zzitbar.quartzcluster.dto.PageReqDto;

/**
 * @auther zzitbar
 * @create 2019-05-30 上午 09:05
 * @Description
 */
public interface QuartzService extends IService {

    PageDataDto getJobAndTriggerDetails(PageReqDto dto);

    void addJob(String jobClassName, String jobGroupName, String cronExpression) throws Exception;

    void pauseJob(String jobClassName, String jobGroupName) throws Exception;

    void resumeJob(String jobClassName, String jobGroupName) throws Exception;

    void rescheduleJob(String jobClassName, String jobGroupName, String cronExpression) throws Exception;

    void deleteJob(String jobClassName, String jobGroupName) throws Exception;
}
