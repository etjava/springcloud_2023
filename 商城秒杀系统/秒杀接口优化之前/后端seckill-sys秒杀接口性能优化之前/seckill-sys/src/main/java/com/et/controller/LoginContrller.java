package com.et.controller;

import com.et.constant.RedisConstant;
import com.et.entity.User;
import com.et.service.UserService;
import com.et.util.Md5Util;
import com.et.util.R;
import com.et.util.RedisUtil;
import com.et.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * 用户控制层
 */
@RestController
@RequestMapping("/")
public class LoginContrller {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 用户登录
     * @return
     */
    @RequestMapping("/login")
    public R login(@RequestBody User user){
        if(user==null){
            return R.error();
        }
        if(StringUtils.isEmpty(user.getUsername())){
            return R.error("用户名不能为空");
        }
        if(StringUtils.isEmpty(user.getPassword())){
            return R.error("密码不能为空");
        }
        User result = userService.findByUserName(user.getUsername());
        if(result==null){
            return R.error("用户不存在");
        }
        if(!result.getPassword().trim().equals(Md5Util.backMd5(user.getPassword().trim()))){
            return R.error("用户名或者密码错误！");
        }

        String token = UUIDUtil.getUUID();
        // 添加到redis缓存中
        // RedisConstant.REDIS_TOKEN_PREFIX 前缀 与redis的key组合使用 标注redis中这条数据是做什么的
        // 例如 tk3B3A77CEFF504F2CA64FF90BB79B9DB1
        // token redis中key
        // result redis中value
        // RedisConstant.REDIS_TOKEN_EXPIRE 当前token的有效期 30分钟
        redisUtil.set(RedisConstant.REDIS_TOKEN_PREFIX,token,result,RedisConstant.REDIS_TOKEN_EXPIRE);
        return R.ok(token);
    }

    /**
     * 模拟注册2000用户
     * @return
     * @throws IOException
     */
    @RequestMapping("/register")
    public R register() throws IOException {
        for(int i=2;i<=2001;i++){
            User user = new User();
            user.setUsername("Tom"+i);
            user.setPassword("37cbc2f0be822f5ab96485ac11f3dc98");
            user.setId(i);
            user.setRegisterDate(new Date());
            user.setName("Tom"+i);
            user.setAddress("HAWAI'I USA");
            user.setPhoneNumber("123456");
            // 生成token
            String token = UUIDUtil.getUUID();
            // 写入到数据库
            userService.save(user);
            // 写入到缓存
            redisUtil.set(RedisConstant.REDIS_TOKEN_PREFIX,token,user,RedisConstant.REDIS_TOKEN_EXPIRE);
            // 写入到文件
            writeFile(user,token);
        }

        return R.ok();
    }

    private void writeFile(User user,String token) throws IOException {
        String s = user.getUsername()+","+token;
        FileWriter fw = new FileWriter(new File("D:/users.txt"),true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(s+"\r\n");
        bw.flush();
        bw.close();
    }
}