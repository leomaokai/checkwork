package com.kai.check.controller;


import com.kai.check.pojo.User;
import com.kai.check.service.IUserService;
import com.kai.check.utils.RespBean;
import com.kai.check.utils.RespBeanEnum;
import com.kai.check.utils.RespPageBean;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private IUserService userService;

    @ApiOperation(value = "得到学生的用户信息(分页)")
    @GetMapping("/student")
    public RespPageBean getStudent(@RequestParam(defaultValue = "1")Integer currentPage,
                                   @RequestParam(defaultValue = "10")Integer size,
                                   String studentId){
        return userService.getStudentByPage(currentPage,size,studentId);
    }

    @ApiOperation(value = "得到老师的用户信息(分页)")
    @GetMapping("/teacher")
    public RespPageBean getTeacher(@RequestParam(defaultValue = "1")Integer currentPage,
                                   @RequestParam(defaultValue = "10")Integer size,
                                   String teacherId){
        return userService.getTeacherByPage(currentPage,size,teacherId);
    }

    @ApiOperation(value = "修改用户信息")
    @PutMapping("/update")
    public RespBean updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

    @ApiOperation(value = "添加老师用户(初始账号密码都为工号)")
    @PostMapping("/insert")
    public RespBean insertTeachersByAdmin(String[] teachersId){
        Integer roleId=2;
        if(userService.insertUser(roleId,teachersId)){
            return RespBean.success();
        }
        return RespBean.error(RespBeanEnum.FAIL);
    }

    @ApiOperation(value = "删除教师(包括其班级和学生)")
    @DeleteMapping("/delete/{id}")
    public RespBean deleteTeacher(@PathVariable("id") String id){
        if(id.isEmpty()){
            return RespBean.error();
        }
        return userService.deleteTeacher(id);
    }

}
