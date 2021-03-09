package com.kai.check.controller;


import com.kai.check.pojo.*;
import com.kai.check.service.*;
import com.kai.check.utils.RespBean;
import com.kai.check.utils.RespBeanEnum;
import com.kai.check.utils.RespPageBean;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
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
    @Resource
    private IWorkResultService workResultService;
    @Resource
    private IStuWorkService stuWorkService;
    @Resource
    private ITeaWorkService teaWorkService;

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
    @GetMapping("/listStudents/{classId}")
    public RespPageBean listStudents(@RequestParam(defaultValue = "1") Integer currentPage,
                                     @RequestParam(defaultValue = "10") Integer size,
                                     @PathVariable("classId") Integer classId) {
        return studentService.listStudents(currentPage, size,classId);
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
    @PostMapping("/createClass/{className}")
    public RespBean createClass(@PathVariable("className") String className, Principal principal) {
        if (className.isEmpty()) {
            return RespBean.error(RespBeanEnum.INSERT_ERROR);
        }
        String teacherId = principal.getName();
        return teacherService.createClass(className, teacherId);
    }
    @ApiOperation(value = "查询班级(分页)")
    @GetMapping("/listClasses")
    public RespPageBean listClasses(@RequestParam(defaultValue = "1")Integer currentPage,
                                    @RequestParam(defaultValue = "10")Integer size,
                                     Principal principal){
        String name = principal.getName();
        return classTeaService.listClasses(currentPage,size,name);
    }
    @ApiOperation(value = "删除班级")
    @DeleteMapping("/deleteClass/{classId}")
    public RespBean deleteClass(@PathVariable(value = "classId") Integer classId,Principal principal){
        String name = principal.getName();
        return teacherService.deleteClass(classId,name);
    }

    @ApiOperation(value = "布置作业")
    @PostMapping("/createWork")
    public RespBean createWork(@RequestBody WorkClass workClass,Principal principal) {
        String name = principal.getName();
        return teacherService.createWork(workClass,name);
    }
    @ApiOperation(value = "查询作业(分页 0查所有)")
    @GetMapping("/listWorks")
    public RespPageBean listWorks(@RequestParam(defaultValue = "1")Integer currentPage,
                                    @RequestParam(defaultValue = "10")Integer size,
                                    String workName,
                                    Principal principal){
        String name = principal.getName();
        return workClassService.listWorks(currentPage,size,workName,name);
    }
    @ApiOperation(value = "修改作业")
    @PutMapping("/updateWork")
    public RespBean updateWork(@RequestBody WorkClass workClass){
        if(workClassService.updateById(workClass)){
            return RespBean.success();
        }
        return RespBean.error();
    }
    @ApiOperation(value = "删除作业")
    @DeleteMapping("/deleteWork/{workId}")
    public RespBean deleteWork(@PathVariable("workId") Integer workId,Principal principal){
        String name = principal.getName();
        return teacherService.deleteWork(workId,name);
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

    @ApiOperation(value = "下载作业(下载或在线查看)")
    @GetMapping("/downWork/{stuWorkId}/{openStyle}")
    public RespBean downWork(@PathVariable("stuWorkId") Integer stuWorkId,@PathVariable("openStyle") Integer isDown, HttpServletResponse response) {
        return stuWorkService.downWork(stuWorkId,isDown,response);
    }

    @ApiOperation(value = "查重")
    @PostMapping("/check/{id}")
    public RespBean check(@PathVariable("id") Integer workId,Principal principal){
        String name = principal.getName();
        return teacherService.teacherCheck(workId,name);
    }

    @ApiOperation(value = "查看查重结果(分页)")
    @GetMapping("/checkResult/{id}")
    public RespPageBean listCheckResult(@RequestParam(defaultValue = "1")Integer currentPage,
                                        @RequestParam(defaultValue = "10")Integer size,
                                        @PathVariable("id") Integer workId){
        return workResultService.listCheckResult(currentPage,size,workId);
    }

    @ApiOperation(value = "新布置作业(传标题,描述,截止日期)")
    @PostMapping("/dispose")
    public RespBean disposeWork(@RequestBody TeaWork teaWork,Integer [] classIds,Principal principal){
        String teaId = principal.getName();
        return teacherService.disposeWork(teaWork,classIds,teaId);
    }
}
