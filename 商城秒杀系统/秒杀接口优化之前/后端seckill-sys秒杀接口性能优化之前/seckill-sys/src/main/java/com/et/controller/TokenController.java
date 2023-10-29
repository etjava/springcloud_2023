package com.et.controller;

import com.et.constant.RedisConstant;
import com.et.util.R;
import com.et.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Token操作相关的Controller
 */
@RestController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/refreshToken")
    public R refreshToken(HttpServletRequest request){
        String token = request.getHeader("token");
        System.out.println("token设置之前的有效期："+redisUtil.getExpire(RedisConstant.REDIS_TOKEN_PREFIX+token));
        // 续期30分钟
        redisUtil.expire(RedisConstant.REDIS_TOKEN_PREFIX,token,RedisConstant.REDIS_TOKEN_EXPIRE);
        System.out.println("token设置之后的有效期："+redisUtil.getExpire(RedisConstant.REDIS_TOKEN_PREFIX+token));
        // 如果是新生成的token则需要将新生成的token传递给前端
        return R.ok();
    }
}
