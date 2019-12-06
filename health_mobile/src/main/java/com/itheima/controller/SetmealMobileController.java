package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetmealMobileController {
    @Reference
    private SetmealService setmealService;

    @RequestMapping("/getSetmeal")
    public Result getSetmeal(){
        try {
            List<Setmeal> setmealList = setmealService.findAllSetmeal();
            return new Result(true,MessageConstant.GET_SETMEAL_LIST_SUCCESS,setmealList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_LIST_FAIL);
        }
    }

    /**
     * 查询套餐中的检查组
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(Integer id){
        //前台返回数据是Setmel对象接收
        try {
            Setmeal setmeal = setmealService.findSetmealById(id);
            return new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS);
        }
    }
}
