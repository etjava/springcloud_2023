package com.et.exception;

import com.et.util.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 */
@ControllerAdvice // 织入
@ResponseBody // 这里需要返回给前端
public class GlobalExceptionHandler {

    /*
    处理所有的异常
     */
    @ExceptionHandler(value = Exception.class)// 要捕获的异常
    public R exceptionHandler(HttpServletRequest request,Exception e){
        System.out.println("捕获到异常 "+e.getMessage());
        return R.error("服务器异常 ["+e.getMessage()+"]<br/>"+
                "异常栈信息 "+e.getStackTrace());
    }
}
