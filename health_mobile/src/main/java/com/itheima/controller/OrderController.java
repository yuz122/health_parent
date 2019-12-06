package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.util.SMSUtils;
import com.itheima.pojo.Order;
import com.itheima.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private OrderService orderService;

    @RequestMapping("/submit")
    public Result submit(@RequestBody Map map){
        //首先判断验证码是否已经过期或者验证码不正确
        String validateCode = (String) map.get("validateCode");
        //获取缓存中的验证码(验证码的key为手机号+类型)
        String telephone = (String) map.get("telephone");
        String redisCodeKey = telephone+ RedisMessageConstant.SENDTYPE_ORDER;

        String redisCode = jedisPool.getResource().get(redisCodeKey);

        if(redisCode ==null || !redisCode.equals(validateCode)){
            //验证码错误
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        //校验通过,那么就需要将预约进行存储进数据库
        //设置预约方式
        Result result =null;
        try {
            map.put("orderType", Order.ORDERTYPE_WEIXIN);
            result = orderService.submitOrder(map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }
        //预约成功,发送短信
        if(result.isFlag()){
            String orderDate = (String) map.get("orderDate");
            try {
                SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE,telephone,orderDate);
            } catch (ClientException e) {
                e.printStackTrace();
            }
        }
        //所有条件成立
        return result;
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        //返回值包含,体检人姓名,体检套餐名,体检日期,预约类型
        try {
            Map<String,Object> map = orderService.findOrderById(id);
            return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }
}
