package com.zzitbar.quartzcluster.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzitbar.quartzcluster.entity.JobAndTrigger;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @auther zzitbar
 * @create 2019-05-30 上午 08:57
 * @Description
 */
@Repository
public interface QuartzMapper extends BaseMapper {

    List<JobAndTrigger> getJobAndTriggerDetails(Page page);
}
