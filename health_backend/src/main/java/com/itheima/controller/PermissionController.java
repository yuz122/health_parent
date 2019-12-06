package com.itheima.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Permission;
import com.itheima.service.PermissionService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Reference
    private PermissionService permissionService;


    @RequestMapping("/add")
    public Result add(@RequestBody Permission Permission){
        try {
            permissionService.add(Permission);
        }catch (Exception e){
            return new Result(false, "");
        }
        return new Result(true,"");
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = permissionService.pageQuery(queryPageBean.getCurrentPage(), queryPageBean.getPageSize(), queryPageBean.getQueryString());
        return  pageResult;

    }


    @RequestMapping("/findAll")
    public Result findAll(){
        List<Permission> permissionList = permissionService.findAll();
        if(permissionList !=null && permissionList.size()>0){
            Result result=new Result(true, "",permissionList);
            return  result;
        }
        return new Result(false, "");
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            Permission permission=permissionService.findById(id);
            return new Result(true,"",permission);
        }catch (Exception e){
            return  new Result(false, "");
        }

    }

    @RequestMapping("/findByRoleIds")
    public Result findByRoleIds(Integer id){
        try {
            List<Integer> checkitemIds=permissionService.findByRoleIds(id);
            return new Result(true, "",checkitemIds);
        }catch (Exception e){
            return  new Result(false,"");
        }

    }


    @RequestMapping("/delete")
    public Result delete(Integer id){
        try{
            permissionService.deleteById(id);
        }catch (Exception e){
            e.printStackTrace();

            return new Result(false,"");
        }
        return  new Result(true, "");
    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody Permission permission){
        try {
            permissionService.edit(permission);

        }catch (Exception e){

            return new Result(false,"");

        }

        return new Result(true,"");

    }

}
