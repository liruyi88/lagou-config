package com.lagou.controller;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.ResponseResult;
import com.lagou.domain.Role;
import com.lagou.domain.User;
import com.lagou.domain.UserVo;
import com.lagou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/findAllUserByPage")
    public ResponseResult findAllUserByPage(@RequestBody UserVo userVo){
        PageInfo pageInfo = userService.findAllUserByPage(userVo);
        ResponseResult responseResult = new ResponseResult(true, 200, "用户分页查询成功", pageInfo);
        return responseResult;
    }
    /**
     * 修改用户状态
     */
    @RequestMapping("/updateUserStatus")
    public ResponseResult updateUserStatus(int id,String status){
        if ("ENABLE".equals(status)){
            status = "DISABLE";
        }else {
            status = "ENABLE";
        }
        userService.updateUserStatus(id,status);
        ResponseResult responseResult = new ResponseResult(true, 200, "修改用户状态成功", status);
        return responseResult;
    }
    /** *
     *  用户登录 * */
    @RequestMapping("/login")
    public ResponseResult login(User user, HttpServletRequest request) throws Exception {
        User login = userService.login(user);
        if (login != null) {
            Map<String,Object> map = new HashMap<>();
            String access_token = UUID.randomUUID().toString();
            map.put("access_token",access_token);
            map.put("user_id",login.getId());
            HttpSession session = request.getSession();
            session.setAttribute("user_id",login.getId());
            session.setAttribute("access_token",access_token);
            ResponseResult responseResult = new ResponseResult(true, 200, "登录成功", map);
            return responseResult;
        }else {
            ResponseResult responseResult = new ResponseResult(false, 400, "用户名或密码错误", null);
            return responseResult;
        }
    }
    @RequestMapping("/findUserRoleById")
    public ResponseResult findUserRoleById(int id){
        List<Role> roleList = userService.findUserRelationRoleById(id);
        ResponseResult responseResult = new ResponseResult(true, 200, "分配角色成功", roleList);
        return responseResult;
    }
    @RequestMapping("/userContextRole")
    public ResponseResult userContextRole(@RequestBody UserVo userVo){
        userService.userContextRole(userVo);
        return new ResponseResult(true,200,"分配角色成功",null);
    }
    @RequestMapping("/getUserPermissions")
    public ResponseResult getUserPermissions(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        HttpSession session = request.getSession();
        String access_token = (String) session.getAttribute("access_token");
        //判断
        if (token.equals(access_token)){
            Integer user_id = (Integer) session.getAttribute("user_id");
            Map<String, Object> map = userService.getUserPermissions(user_id);
            ResponseResult responseResult = new ResponseResult(true, 200, "响应成功", map);
            return responseResult;
        }else {
            ResponseResult responseResult = new ResponseResult(false, 400, "获取失败", null);
            return responseResult;
        }
    }


}
