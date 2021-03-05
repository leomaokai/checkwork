package com.kai.check.controller;


import com.kai.check.pojo.User;
import com.kai.check.pojo.UserLoginParam;
import com.kai.check.service.IUserService;
import com.kai.check.utils.RespBean;
import com.kai.check.utils.RespBeanEnum;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author kai
 * @since 2021-03-03
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @ApiOperation(value = "登录之后返回token")
    @PostMapping("/login")
    public RespBean login(@RequestBody UserLoginParam userLoginParam, HttpServletRequest request) {
        return userService.login(userLoginParam.getUsername(), userLoginParam.getPassword(), userLoginParam.getCode(), request);
    }

    @ApiOperation(value = "获取当前登录的用户信息")
    @GetMapping("/userInfo")
    public User getUserInfo(Principal principal) {
        if (null == principal) {
            return null;
        }
        String name = principal.getName();
        User user = userService.getUserByUsername(name);
        user.setPassword(null);
        return user;
    }

    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public RespBean logout() {
        // 前端直接将token删除即可,之后访问会被拦截器拦截
        return RespBean.success();
    }

    @ApiOperation(value = "更新当前用户密码")
    @PutMapping("/update")
    public RespBean updatePassword(@RequestBody Map<String, String> info, Principal principal) {
        String oldPassword = info.get("oldPassword");
        String newPassword = info.get("newPassword");
        String name = principal.getName();
        return userService.updatePassword(oldPassword, newPassword, name);
    }
}
