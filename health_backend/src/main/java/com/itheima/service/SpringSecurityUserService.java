package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class SpringSecurityUserService implements UserDetailsService {
    @Reference
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //判断该用户时候被禁用
        String station = userService.findStationByUsername(username);

        if(station.equals("是")){
            return null;
        }
        //调用远程服务查询数据库的用户信息
        User userDB = userService.findByUsername(username);
        if (userDB == null) {
            //用户名不存在
            return null;
        }
        //添加权限列表
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();

        //授予角色列表
        Set<Role> roles = userDB.getRoles();
        if (roles != null && roles.size() > 0) {
            for (Role role : roles) {
                //添加角色
                list.add(new SimpleGrantedAuthority(role.getKeyword()));//role.getKeyword()为角色码
                //授予权限
                Set<Permission> permissions = role.getPermissions();
                if(permissions.size()>0 && permissions != null){
                    for (Permission permission : permissions) {
                        //添加权限
                        list.add(new SimpleGrantedAuthority(permission.getKeyword()));
                    }
                }
            }
        }

        //将用户登录信息和权限列表封装到框架的User对象中
        org.springframework.security.core.userdetails.User userDetails =
                new org.springframework.security.core.userdetails.User(username,userDB.getPassword(),list);
        return userDetails;
    }

    public static void main(String[] args) {
        System.out.println(BCrypt.hashpw("123456",BCrypt.gensalt()));
    }
}
