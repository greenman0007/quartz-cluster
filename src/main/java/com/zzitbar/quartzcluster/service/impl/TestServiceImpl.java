package com.zzitbar.quartzcluster.service.impl;

import com.zzitbar.quartzcluster.service.TestService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @auther zhangtengfei
 * @create 2019-05-30 上午 09:57
 * @Description
 */
@Service
public class TestServiceImpl implements TestService {

    @Override
    public void printNow() {
        System.out.println(new Date());
    }
}
