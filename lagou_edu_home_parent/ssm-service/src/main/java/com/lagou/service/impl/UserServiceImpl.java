package com.lagou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.dao.UserMapper;
import com.lagou.domain.*;
import com.lagou.service.UserService;
import com.lagou.utils.Md5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public PageInfo findAllUserByPage(UserVo userVo) {
        PageHelper.offsetPage(userVo.getCurrentPage(),userVo.getPageSize());
        List<User> allByPage = userMapper.findAllUserByPage(userVo);
        PageInfo<User> pageInfo = new PageInfo<>(allByPage);
        return pageInfo;
    }

    @Override
    public void updateUserStatus(int id, String status) {
        User user = new User();
        user.setId(id);
        user.setStatus(status);
        user.setUpdate_time(new Date());
        userMapper.updateUserStatus(user);
    }

    @Override
    public User login(User user) throws Exception {
        User tum = userMapper.login(user);
        if (tum != null && Md5.verify(user.getPassword(),"lagou",tum.getPassword())){
            return tum;
        }else {
            return null;
        }
    }

    @Override
    public List<Role> findUserRelationRoleById(int id) {
        List<Role> roles = userMapper.findUserRelationRoleById(id);
        return roles;
    }

    @Override
    public void userContextRole(UserVo userVo) {
        //清空原有
        userMapper.deleteUserContextRole(userVo.getUserId());
        for (Integer roleid : userVo.getRoleIdList()) {
            User_Role_relation user_role_relation = new User_Role_relation();
            user_role_relation.setRoleId(roleid);
            //补全信息
            Date date = new Date();
            user_role_relation.setCreatedTime(date);
            user_role_relation.setUpdatedTime(date);
            user_role_relation.setCreatedBy("system");
            user_role_relation.setUpdatedby("system");
            userMapper.userContextRole(user_role_relation);
        }
    }

    @Override
    public Map<String, Object> getUserPermissions(Integer id) {
        List<Role> roleList = userMapper.findUserRelationRoleById(id);
        //2.获取角色id,保存到List
        List<Integer> list = new ArrayList<>();
        for (Role role : roleList) {
            list.add(role.getId());
        }
        //3.根据角色id查询 父菜单
        List<Menu> parentMenu = userMapper.findParentMenuByRoleId(list);
        //4.封装父菜单下的子菜单
        for (Menu menu : parentMenu) {
            List<Menu> menuList = userMapper.findSubMenuByPid(menu.getId());
            menu.setSubMenuList(menuList);
        }
        //5.获取权限资源
        List<Resource> resourceList = userMapper.findResourceByRoleId(list);
        Map<String,Object> map = new HashMap<>();
        map.put("menuList",parentMenu);
        map.put("resourceList",resourceList);

        return map;
    }

}
