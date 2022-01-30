package com.lagou.service;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.Role;
import com.lagou.domain.User;
import com.lagou.domain.UserVo;

import java.util.List;
import java.util.Map;

public interface UserService {
    /**
     * 用户分页&多条件查询
     */
    public PageInfo findAllUserByPage(UserVo userVo);
    /**
     * 修改用户状态
     */
    public void updateUserStatus(int id, String status);
    /**
     * 用户登录
     */
    public User login(User user) throws Exception;

    /*** 获取用户拥有的角色 * */
    public List<Role> findUserRelationRoleById(int id) ;
    /*用户关联角色 */
    void userContextRole(UserVo userVo);
    Map<String,Object> getUserPermissions(Integer id);
}
