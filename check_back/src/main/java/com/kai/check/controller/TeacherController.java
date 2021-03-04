package com.kai.check.controller;


import com.kai.check.pojo.*;
import com.kai.check.service.*;
import com.kai.check.utils.RespBean;
import com.kai.check.utils.RespBeanEnum;
import com.kai.check.utils.RespPageBean;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author kai
 * @since 2021-03-04
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Resource
    private IUserService userService;
    @Resource
    private ITeacherService teacherService;
    @Resource
    private IStudentService studentService;
    @Resource
    private IClassTeaService classTeaService;
    @Resource
    private IWorkClassService workClassService;

    @ApiOperation(value = "获得当前用户(教师)信息")
    @GetMapping("/info")
    public Teacher getInfo(Principal principal) {
        if (null == principal) {
            return null;
        }
        String name = principal.getName();
        return teacherService.getInfo(name);
    }
    @ApiOperation(value = "修改当前用户(教师)信息")
    @PutMapping("/updateInfo")
    public RespBean updateInfo(@RequestBody Teacher teacher){
        if(teacherService.updateById(teacher)){
            return RespBean.success();
        }
        return RespBean.error();
    }

    @ApiOperation(value = "添加学生账号同时到班级")
    @PostMapping("/insertStudent")
    public RespBean insertStudents(String[] students, Integer classId) {
        return teacherService.insertUserByTeacher(students, classId);
    }

    @ApiOperation(value = "查询学生信息(分页)")
    @GetMapping("/listStudents")
    public RespPageBean listStudents(@RequestParam(defaultValue = "1") Integer currentPage,
                                     @RequestParam(defaultValue = "10") Integer size,
                                     String studentId, String studentName) {
        return studentService.listStudents(currentPage, size, studentId, studentName);
    }

    @ApiOperation(value = "初始化学生密码")
    @PutMapping("/updateStuById/{studentId}")
    public RespBean initStuById(@PathVariable("studentId") String studentId) {
        return userService.initStuById(studentId);
    }

    @ApiOperation(value = "修改学生信息")
    @PutMapping("/updateStudent")
    public RespBean updateStudent(@RequestBody Student student) {
        if (studentService.updateById(student)) {
            return RespBean.success();
        }
        return RespBean.error();
    }

    @ApiOperation(value = "创建班级")
    @PostMapping("/createClass")
    public RespBean createClass(String className, Principal principal) {
        if (className.isEmpty()) {
            return RespBean.error(RespBeanEnum.FAIL);
        }
        String teacherId = principal.getName();
        return teacherService.createClass(className, teacherId);
    }
    @ApiOperation(value = "查询班级(分页)")
    @GetMapping("/listClasses")
    public RespPageBean listClasses(@RequestParam(defaultValue = "1")Integer currentPage,
                                    @RequestParam(defaultValue = "10")Integer size,
                                    Integer classId,String className,Principal principal){
        String name = principal.getName();
        return classTeaService.listClasses(currentPage,size,classId,className,name);
    }


    @ApiOperation(value = "布置作业")
    @PostMapping("/createWork")
    public RespBean createWork(@RequestBody WorkClass workClass,Principal principal) {
        String name = principal.getName();
        return teacherService.createWork(workClass,name);
    }
    @ApiOperation(value = "查询作业(分页)")
    @GetMapping("/listWorks")
    public RespPageBean listWorks(@RequestParam(defaultValue = "1")Integer currentPage,
                                    @RequestParam(defaultValue = "10")Integer size,
                                    Integer classId){
        return workClassService.listWorks(currentPage,size,classId);
    }
    @ApiOperation(value = "修改作业")
    @PutMapping("/updateWork")
    public RespBean updateWork(@RequestBody WorkClass workClass){
        if(workClassService.updateById(workClass)){
            return RespBean.success();
        }
        return RespBean.error();
    }

    @ApiOperation(value = "将作业发送给学生")
    @PostMapping("/workToStu")
    public RespBean workToStu(Integer workId) {
        return teacherService.workToStu(workId);
    }

    @ApiOperation(value = "查看作业情况")
    @GetMapping("/workCondition")
    public List<StuWork> workCondition(Integer workId) {
        return teacherService.workCondition(workId);
    }

    @ApiOperation(value = "查重")
    @PostMapping("/check")
    public RespBean check(Integer workId,String lang,Principal principal){
        String name = principal.getName();
        return teacherService.check(workId,lang,name);
    }
}
