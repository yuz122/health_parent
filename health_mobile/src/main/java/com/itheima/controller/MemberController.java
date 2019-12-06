package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import com.itheima.util.SMSUtils;
import com.itheima.util.ValidateCodeUtils;
import com.qiniu.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private JedisPool jedisPool;

    @Reference
    private MemberService memberService;

    @RequestMapping("/login")
    public Result login(HttpServletResponse response,@RequestBody Map map){
        //map封装了手机号和登录验证码
        String telephone = (String) map.get("telephone");
        String validateCode = (String) map.get("validateCode");

        //从缓存中获取验证码
        String redisCode = jedisPool.getResource().get(telephone+ RedisMessageConstant.SENDTYPE_LOGIN);


        if(redisCode!=null && validateCode != null && redisCode.equals(validateCode)){
            //验证码输入正确，判断该用户是否是会员
            Member member = memberService.findByTelephone(telephone);
            if(member == null){
//                不是会员，注册会员
                 member = new Member();
                 member.setPhoneNumber(telephone);//电话号码
                 member.setRegTime(new Date());//注册时间
                memberService.add(member);
            }
            //如果是会员，则将会员信息存入cookie,跟踪用户
            Cookie cookie = new Cookie("login_member_telphone", telephone);
            cookie.setPath("/");//设置cookie路径
            cookie.setMaxAge(60*60*24*30);//保存cookie30天
            response.addCookie(cookie);
            //登录之后将用户信息保存到Redis中,由于redis中不能存储实体对象，转换成json字符串
            String json = JSON.toJSON(member).toString();
            jedisPool.getResource().setex(telephone,60*30,json);
            return new Result(true,MessageConstant.LOGIN_SUCCESS);
        }else {
            //验证码输入错误
            return new Result(false,MessageConstant.VALIDATECODE_ERROR);
        }
    }
}
