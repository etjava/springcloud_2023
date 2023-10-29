package com.et.interceptor;

import com.alibaba.druid.util.StringUtils;
import com.et.constant.RedisConstant;
import com.et.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统拦截器配置类 - 鉴权
 */
public class SysInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisUtil redisUtil;

    // 在请求处理之前进行拦截
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();
        System.out.println("请求路径："+path);
        // 只处理请求方法的时候
        if(handler instanceof HandlerMethod){
            // 获取前端传递的token
            String token = request.getHeader("token");
            System.out.println("token = "+token);
            if(StringUtils.isEmpty(token)){
                System.out.println("token不存在");
                throw new RuntimeException("验证签名失败 [签名不存在]");
            }else{
                // 在redis中获取
                Object o = redisUtil.get(RedisConstant.REDIS_TOKEN_PREFIX, token);
                if(o!=null){
                    // 验证成功
                    System.out.println("签名验证成功");
                    return true;
                }else{
                    System.out.println("签名验证失败 [签名失效]");
                    throw new RuntimeException("签名验证失败 [签名失效]");
                }
            }
        }else {
            return true;
        }
    }

}
