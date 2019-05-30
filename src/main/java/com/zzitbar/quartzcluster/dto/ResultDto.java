package com.zzitbar.quartzcluster.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * @auther Administrator
 * @create 2019-04-01 下午 03:55
 * @Description
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ResultDto implements Serializable {

    private Integer code;
    private String msg;
    private Object data;

    public static final Integer SUCCESS = 200;
    public static final Integer ERROR = 500;

    public ResultDto() {
    }

    public ResultDto(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultDto(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


}
