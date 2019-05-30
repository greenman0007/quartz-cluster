package com.zzitbar.quartzcluster.controller;

import com.zzitbar.quartzcluster.dto.ResultDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected ResultDto success() {
        return success(null);
    }

    protected ResultDto success(Object data) {
        ResultDto dto = new ResultDto(ResultDto.SUCCESS, "success");
        if (null != data) {
            dto.setData(data);
        }
        return dto;
    }

    protected ResultDto error(String msg) {
        return new ResultDto(ResultDto.ERROR, msg);
    }
}
