package com.zzitbar.quartzcluster.controller;

import com.zzitbar.quartzcluster.dto.PageDataDto;
import com.zzitbar.quartzcluster.dto.PageReqDto;
import com.zzitbar.quartzcluster.dto.ResultDto;
import com.zzitbar.quartzcluster.service.QuartzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @auther zhangtengfei
 * @create 2019-05-30 上午 09:04
 * @Description
 */
@Controller
@RequestMapping("/job")
public class JobController extends BaseController{

    @Autowired
    private QuartzService quartzService;

    @GetMapping(value = "/list")
    @ResponseBody
    public ResultDto queryjob(PageReqDto dto) {
        PageDataDto pageDataDto = quartzService.getJobAndTriggerDetails(dto);
        return success(pageDataDto);
    }

    /**
     * 新增
     *
     * @param jobClassName
     * @param jobGroupName
     * @param cronExpression
     * @throws Exception
     */
    @PostMapping(value = "/add")
    @ResponseBody
    public ResultDto add(String jobClassName, String jobGroupName, String cronExpression) throws Exception {
        quartzService.addJob(jobClassName, jobGroupName, cronExpression);
        return success();
    }

    /**
     * 暂停
     *
     * @param jobClassName
     * @param jobGroupName
     * @throws Exception
     */
    @PostMapping(value = "/pause")
    @ResponseBody
    public ResultDto pause(String jobClassName, String jobGroupName) throws Exception {
        quartzService.pauseJob(jobClassName, jobGroupName);
        return success();
    }

    /**
     * 恢复
     *
     * @param jobClassName
     * @param jobGroupName
     * @throws Exception
     */
    @PostMapping(value = "/resume")
    @ResponseBody
    public ResultDto resume(String jobClassName, String jobGroupName) throws Exception {
        quartzService.resumeJob(jobClassName, jobGroupName);
        return success();
    }

    /**
     * 修改
     *
     * @param jobClassName
     * @param jobGroupName
     * @param cronExpression
     * @throws Exception
     */
    @PostMapping(value = "/reschedule")
    @ResponseBody
    public ResultDto reschedule(String jobClassName, String jobGroupName, String cronExpression) throws Exception {
        quartzService.rescheduleJob(jobClassName, jobGroupName, cronExpression);
        return success();
    }

    /**
     * 删除
     *
     * @param jobClassName
     * @param jobGroupName
     * @throws Exception
     */
    @PostMapping(value = "/delete")
    @ResponseBody
    public ResultDto delete(String jobClassName, String jobGroupName) throws Exception {
        quartzService.deleteJob(jobClassName, jobGroupName);
        return success();
    }
}
