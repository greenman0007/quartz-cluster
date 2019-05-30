package com.zzitbar.quartzcluster.config;

import cn.hutool.json.JSONUtil;
import com.zzitbar.quartzcluster.dto.ResultDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 异常统一处理
 *
 * @auther Administrator
 * @Description
 */
@Component
public class DefaultExceptionHandler extends DefaultHandlerExceptionResolver {

    private static final Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        logger.error("捕获到异常", ex);
        String msg = "系统内部异常"+ex.getMessage();

        if (isResponseBody(handler)) {
            try (PrintWriter writer = response.getWriter()) {
                response.setContentType("application/json");
                writer.write(JSONUtil.toJsonStr(new ResultDto(ResultDto.ERROR, msg)));
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            ModelAndView mv = new ModelAndView();
            mv.addObject("msg", msg);
            mv.setViewName("error/error");
            return mv;
        }
    }

    /**
     * 判断请求是否为ajax请求
     */
    public static boolean isAjax(HttpServletRequest request) {
//        request.getHeader("");
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With")) || "application/json".equalsIgnoreCase(request.getHeader("Content-Type"));
    }

    public boolean isResponseBody(Object handler) {
        if (null != handler && handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            if (null != hm.getMethodAnnotation(ResponseBody.class) || null != hm.getBeanType().getAnnotation(ResponseBody.class) || null != hm.getBeanType().getAnnotation(RestController.class)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
