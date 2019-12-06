package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckItem;

public interface CheckItemService {
    public void add(CheckItem checkItem);

    public PageResult pageQuery(Integer currentPage,Integer pageSize, String queryString);

    void deleteOneItem(Integer id);

    CheckItem findById(Integer id);

    void update(CheckItem checkItem);
}
