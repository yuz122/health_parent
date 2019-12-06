package com.itheima.controller;

import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.util.SMSUtils;

import com.itheima.util.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {
    @Autowired
    private JedisPool jedisPool;

    /**
     * 发送预约体检验证码
     * @param telephone
     * @return
     */
    @RequestMapping("/send4Order")
    public Result send4Order(String telephone){
        //生成验证码
        Integer validateCode = ValidateCodeUtils.generateValidateCode(4);
        //拿到手机号之后,通过工具类发送验证码
        try {
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,validateCode.toString());
        } catch (ClientException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        //将成功发送的验证码存入缓存用于匹配用户输入的验证码
        //为了避免缓存中键的冲突问题,为键添加类型(并保存五分钟)

        jedisPool.getResource().setex(telephone+ RedisMessageConstant.SENDTYPE_ORDER,60*5,validateCode.toString());
        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }


    /**
     * 发送登录验证码
     * @param telephone
     * @return
     */
    @RequestMapping("/send4Login")
    public Result send4Login(String telephone){
        //生成验证码
        Integer validateCode = ValidateCodeUtils.generateValidateCode(6);
        //拿到手机号之后,通过工具类发送验证码
        try {
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,validateCode.toString());
        } catch (ClientException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        //将成功发送的验证码存入缓存用于匹配用户输入的验证码
        //为了避免缓存中键的冲突问题,为键添加类型(并保存五分钟)
        jedisPool.getResource().setex(telephone+ RedisMessageConstant.SENDTYPE_LOGIN,60*5,validateCode.toString());
        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}
