package com.lagou.service;

import com.lagou.domain.Menu;

import java.util.List;

public interface MenuService {
    /**
     * 查询所有父子菜单信息
     */
    public List<Menu> findSubMenuListByPid(int pid);
    /**
     * 查询所有菜单
     */
    public List<Menu> findAllMenu();

    /**
     * 回显
     */
    public Menu findMenuById(Integer id);
}
