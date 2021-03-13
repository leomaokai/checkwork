package com.kai.check.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kai.check.pojo.*;
import com.kai.check.service.*;
import com.kai.check.utils.RespBean;
import com.kai.check.utils.RespBeanEnum;
import com.kai.check.utils.RespPageBean;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
    @Resource
    private IClassWorkService classWorkService;

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
    public RespBean updateInfo(@RequestBody Teacher teacher) {
        if (teacherService.updateById(teacher)) {
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
        return studentService.listStudents(currentPage, size, classId);
    }

    @ApiOperation(value = "初始化学生密码")
    @PutMapping("/updateStuById/{studentId}")
    public RespBean initStuById(@PathVariable("studentId") String studentId) {
        return userService.initStuById(studentId);
    }

    //    @ApiOperation(value = "修改学生信息")
//    @PutMapping("/updateStudent")
//    public RespBean updateStudent(@RequestBody Student student) {
//        if (studentService.updateById(student)) {
//            return RespBean.success();
//        }
//        return RespBean.error();
//    }
    @ApiOperation(value = "删除学生")
    @DeleteMapping("/deleteStudent/{studentId}")
    public RespBean deleteStudent(@PathVariable String studentId) {
        return teacherService.deleteStudent(studentId);
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
    public RespPageBean listClasses(@RequestParam(defaultValue = "1") Integer currentPage,
                                    @RequestParam(defaultValue = "10") Integer size,
                                    Principal principal) {
        String name = principal.getName();
        return classTeaService.listClasses(currentPage, size, name);
    }

    @ApiOperation(value = "删除班级")
    @DeleteMapping("/deleteClass/{classId}")
    public RespBean deleteClass(@PathVariable(value = "classId") Integer classId, Principal principal) {
        String name = principal.getName();
        return teacherService.deleteClass(classId, name);
    }

//    @ApiOperation(value = "布置作业")
//    @PostMapping("/createWork")
//    public RespBean createWork(@RequestBody WorkClass workClass, Principal principal) {
//        String name = principal.getName();
//        return teacherService.createWork(workClass, name);
//    }
//
//    @ApiOperation(value = "查询作业(分页 0查所有)")
//    @GetMapping("/listWorks")
//    public RespPageBean listWorks(@RequestParam(defaultValue = "1") Integer currentPage,
//                                  @RequestParam(defaultValue = "10") Integer size,
//                                  String workName,
//                                  Principal principal) {
//        String name = principal.getName();
//        return workClassService.listWorks(currentPage, size, workName, name);
//    }

//    @ApiOperation(value = "修改作业")
//    @PutMapping("/updateWork")
//    public RespBean updateWork(@RequestBody WorkClass workClass) {
//        if (workClassService.updateById(workClass)) {
//            return RespBean.success();
//        }
//        return RespBean.error();
//    }

//    @ApiOperation(value = "删除作业")
//    @DeleteMapping("/deleteWork/{workId}")
//    public RespBean deleteWork(@PathVariable("workId") Integer workId, Principal principal) {
//        String name = principal.getName();
//        return teacherService.deleteWork(workId, name);
//    }

//    @ApiOperation(value = "将作业发送给学生")
//    @PostMapping("/workToStu")
//    public RespBean workToStu(Integer workId) {
//        return teacherService.workToStu(workId);
//    }
//
//    @ApiOperation(value = "查看作业情况")
//    @GetMapping("/workCondition")
//    public List<StuWork> workCondition(Integer workId) {
//        return teacherService.workCondition(workId);
//    }
//
//    @ApiOperation(value = "下载作业(下载或在线查看)")
//    @GetMapping("/downWork/{stuWorkId}/{openStyle}")
//    public RespBean downWork(@PathVariable("stuWorkId") Integer stuWorkId, @PathVariable("openStyle") Integer isDown, HttpServletResponse response) {
//        return stuWorkService.downWork(stuWorkId, isDown, response);
//    }

//    @ApiOperation(value = "查重")
//    @PostMapping("/check/{id}")
//    public RespBean check(@PathVariable("id") Integer workId, Principal principal) {
//        String name = principal.getName();
//        return teacherService.teacherCheck(workId, name);
//    }

//    @ApiOperation(value = "查看查重结果(分页)")
//    @GetMapping("/checkResult/{id}")
//    public RespPageBean listCheckResult(@RequestParam(defaultValue = "1") Integer currentPage,
//                                        @RequestParam(defaultValue = "10") Integer size,
//                                        @PathVariable("id") Integer workId) {
//        return workResultService.listCheckResult(currentPage, size, workId);
//    }

    @ApiOperation(value = "新布置作业(传标题,描述,截止日期)")
    @PostMapping("/dispose")
    public RespBean disposeWork(@RequestParam(required = true) String workTitle,
                                @RequestParam(required = false) String workDescribe,
                                @RequestParam(required = true) String endTime,
                                @RequestParam(required = true) Integer[] classIds,
                                Principal principal) {
        String teaId = principal.getName();
        LocalDateTime end = LocalDateTime.parse(endTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return teacherService.disposeWork(workTitle, workDescribe, end, classIds, teaId);
    }

    @ApiOperation(value = "新查询作业(班级,作业,截止日期)")
    @GetMapping("/getAllWorks")
    public RespPageBean getAllWorks(@RequestParam(defaultValue = "1") Integer currentPage,
                                    @RequestParam(defaultValue = "10") Integer size,
                                    Principal principal) {
        String name = principal.getName();
        return classWorkService.getAllWorks(currentPage, size, name);
    }

    @ApiOperation(value = "新创建作业(标题)")
    @PostMapping("/createWorkTitle/{workTitle}")
    public RespBean createWorkTitle(@PathVariable("workTitle") String workTitle, Principal principal) {
        if (workTitle.isEmpty()) {
            return RespBean.error(RespBeanEnum.INSERT_ERROR);
        }
        String name = principal.getName();
        return teaWorkService.createWorkTitle(workTitle, name);
    }

    @ApiOperation(value = "新查询作业标题")
    @GetMapping("/listWorkTitles")
    public List<TeaWork> listWorkTitles(Principal principal) {
        String name = principal.getName();
        return teaWorkService.listWorkTitles(name);
    }

    // 删除作业分为直接通过作业ID删除全
    @ApiOperation(value = "新通过作业ID删除作业")
    @DeleteMapping("/deleteWorkById/{workId}")
    public RespBean deleteWorkById(@PathVariable("workId") Integer workId) {
        if (workId == null) {
            return RespBean.error(RespBeanEnum.DELETE_ERROR);
        }
        return teacherService.deleteWorkById(workId);
    }

    @ApiOperation(value = "新删除该班级作业")
    @DeleteMapping("/deleteWorkByClass/{classWorkId}")
    public RespBean deleteWorkByClass(@PathVariable("classWorkId") Integer classWorkId) {
        if (classWorkId == null) {
            return RespBean.error(RespBeanEnum.DELETE_SUCCESS);
        }
        return teacherService.deleteWorkByClass(classWorkId);
    }

//    @ApiOperation(value = "新修改作业标题")
//    @PutMapping("/updateWorkTitle")
//    public RespBean updateWorkTitle(Integer workId,String workTitle) {
//        if (workId == null || workTitle.isEmpty()) {
//            return RespBean.error(RespBeanEnum.UPDATE_ERROR);
//        }
//        return teacherService.updateWorkTitle(workId,workTitle);
//    }

    @ApiOperation(value = "新修改该班级的该作业截止日期")
    @PutMapping("/updateWorkEnd")
    public RespBean updateWorkEnd(Integer classWorkId, String newEndTime) {
        if (classWorkId == null || newEndTime == null) {
            return RespBean.error(RespBeanEnum.UPDATE_ERROR);
        }
        LocalDateTime end = LocalDateTime.parse(newEndTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return classWorkService.updateWorkEnd(classWorkId, end);
    }

    @ApiOperation(value = "新查询班级(不分页)")
    @GetMapping("/getClasses")
    public List<ClassTea> getClasses(Principal principal) {
        String name = principal.getName();
        return classTeaService.list(new QueryWrapper<ClassTea>().eq("tea_id", name));
    }

    @ApiOperation(value = "新查询该班级的作业完成情况")
    @GetMapping("/getClassStudentWorks")
    public RespPageBean getClassStudentWorks(@RequestParam(defaultValue = "1") Integer currentPage,
                                             @RequestParam(defaultValue = "10") Integer size,
                                             Integer classId,
                                             Integer workId) {
        return stuWorkService.getClassStudentWorks(currentPage, size, classId, workId);
    }

//    // 在学生交作业时查重,老师只查看结果
//    @ApiOperation(value = "新作业查重")
//    @PutMapping("/checkCode/{workId}")
//    public RespBean checkCode(@PathVariable("workId") Integer workId,Principal principal){
//        if(workId==null){
//            return RespBean.error(RespBeanEnum.CHECK_ERROR);
//        }
//        String name = principal.getName();
//        return teacherService.checkCode(workId,name);
//    }

    @ApiOperation(value = "新查看查重结果(分页)")
    @GetMapping("/checkResult")
    public RespPageBean listCheckResult(@RequestParam(defaultValue = "1") Integer currentPage,
                                        @RequestParam(defaultValue = "10") Integer size,
                                        Integer workId) {
        return workResultService.listCheckResult(currentPage, size, workId);
    }

    // 1 下载源代码, 2 下载pdf,
    @ApiOperation(value = "下载作业")
    @GetMapping(value = "/download", produces = "application/octet-stream")
    public void downloadWork(Integer stuWorkId, Integer flag, HttpServletResponse response) {
        if (stuWorkId == null || flag == null) {
           // return RespBean.error(RespBeanEnum.DOWN_ERROR);
            return;
        }
        stuWorkService.downloadWork(stuWorkId, flag, response);
    }


}
