package com.kai.check.controller;


import com.kai.check.pojo.User;
import com.kai.check.service.IUserService;
import com.kai.check.utils.RespBean;
import com.kai.check.utils.RespBeanEnum;
import com.kai.check.utils.RespPageBean;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private IUserService userService;

    @ApiOperation(value = "得到学生的用户信息(分页)")
    @GetMapping("/student")
    public RespPageBean getStudent(@RequestParam(defaultValue = "1") Integer currentPage,
                                   @RequestParam(defaultValue = "10") Integer size,
                                   String studentId) {
        return userService.getStudentByPage(currentPage, size, studentId);
    }

    @ApiOperation(value = "得到老师的用户信息(分页)")
    @GetMapping("/teacher")
    public RespPageBean getTeacher(@RequestParam(defaultValue = "1") Integer currentPage,
                                   @RequestParam(defaultValue = "10") Integer size,
                                   String teacherId) {
        return userService.getTeacherByPage(currentPage, size, teacherId);
    }

    @ApiOperation(value = "修改用户信息")
    @PutMapping("/update")
    public RespBean updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @ApiOperation(value = "添加老师用户(初始账号密码都为工号)")
    @PostMapping("/insert")
    public RespBean insertTeachersByAdmin(String[] teachersId) {
        Integer roleId = 2;
        if (userService.insertUser(roleId, teachersId)) {
            return RespBean.success(RespBeanEnum.INSERT_SUCCESS);
        }
        return RespBean.error(RespBeanEnum.INSERT_ERROR);
    }

    @ApiOperation(value = "添加一个老师用户")
    @PostMapping("/insert/{id}")
    public RespBean insertTeachersByAdmin(@PathVariable("id") String teachersId) {
        if(teachersId.equals("") || teachersId.isEmpty()){
            return RespBean.error(RespBeanEnum.INSERT_ERROR);
        }
        Integer roleId = 2;
        if (userService.insertOneUser(roleId, teachersId)) {
            return RespBean.success(RespBeanEnum.INSERT_SUCCESS);
        }
        return RespBean.error(RespBeanEnum.INSERT_ERROR);
    }

    @ApiOperation(value = "删除教师(包括其班级和学生)")
    @DeleteMapping("/delete/{id}")
    public RespBean deleteTeacher(@PathVariable(value = "id", required = true) String id) {
        return userService.deleteTeacher(id);
    }

    @ApiOperation(value = "查询用户信息")
    @GetMapping("/list")
    public RespPageBean listUsers(@RequestParam(defaultValue = "1") Integer currentPage,
                                  @RequestParam(defaultValue = "10") Integer size,
                                  String userId) {
        return userService.listUsersByPage(currentPage, size, userId);
    }

    @ApiOperation(value = "初始化用户密码")
    @PutMapping("/init/{id}")
    public RespBean initUserPassword(@PathVariable("id") String username) {
        return userService.initUserPassword(username);
    }

}
