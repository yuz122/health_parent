package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();

    PageResult findPage(QueryPageBean queryPageBean);

    void add(Role role, Integer[] checkitemIds);

    Role findById(Integer id);

    void edit(Role role, Integer[] checkitemIds, Integer[] menuIds);

    Long findUserById(Integer id);

    void deleteOne(Integer id);
    /*PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);*/
}
