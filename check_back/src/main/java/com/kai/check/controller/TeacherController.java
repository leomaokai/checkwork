package com.kai.check.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kai.check.pojo.*;
import com.kai.check.service.*;
import com.kai.check.utils.RespBean;
import com.kai.check.utils.RespBeanEnum;
import com.kai.check.utils.RespPageBean;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private IWorkResultService workResultService;
    @Resource
    private IStuWorkService stuWorkService;
    @Resource
    private ITeaWorkService teaWorkService;
    @Resource
    private IClassWorkService classWorkService;
    @Resource
    private ITeaDesignService teaDesignService;
    @Resource
    private IClassDesignService classDesignService;
    @Resource
    private IStuDesignService stuDesignService;
    @Resource
    private IStuGroupService stuGroupService;

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

//    @ApiOperation(value = "创建作业2(标题,一个具体的PDF文件)")
//    @PostMapping("/createDesign")
//    public RespBean createWorkNew(@RequestParam("createWorkPdf") MultipartFile createWorkPdf, @RequestParam("createWorkTitle") String createWorkTitle, Principal principal) {
//        if (createWorkTitle.isEmpty()) {
//            return RespBean.error(RespBeanEnum.INSERT_ERROR);
//        }
//        String name = principal.getName();
//        return teaWorkService.createWorkNew(createWorkTitle, createWorkPdf, name);
//    }

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

    @ApiOperation(value = "作业评分")
    @PostMapping(value = "/scoreWork")
    public RespBean scoreWork(Integer stuWorkId,Integer score){
        return stuWorkService.scoreWork(stuWorkId,score);
    }


//    @ApiOperation(value = "新布置课程设计(传标题,描述,截止日期)")
//    @PostMapping("/disposeDesign")
//    public RespBean disposeDesign(@RequestParam(required = true) String designTitle,
//                                @RequestParam(required = false) String designDescribe,
//                                @RequestParam(required = true) String endTime,
//                                @RequestParam(required = true) Integer[] classIds,
//                                Principal principal) {
//        String teaId = principal.getName();
//        LocalDateTime end = LocalDateTime.parse(endTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//        return teacherService.
//    }
//
//    @ApiOperation(value = "新查询课程设计(班级,作业,截止日期)")
//    @GetMapping("/getAllDesign")
//    public RespPageBean getAllDesign(Principal principal) {
//        String name = principal.getName();
//        return classWorkService.
//    }

    @ApiOperation(value = "新创建课程设计(标题,一个具体的PDF文件)")
    @PostMapping("/createDesign")
    public RespBean createDesign(@RequestParam("designPdf") MultipartFile designPdf, @RequestParam("designTitle") String designTitle, @RequestParam("designLimit") Integer designLimit, Principal principal) {
        if (designTitle.isEmpty()) {
            return RespBean.error(RespBeanEnum.INSERT_ERROR);
        }
        String name = principal.getName();
        return teaDesignService.createDesign(designTitle, designLimit, designPdf, name);
    }

    @ApiOperation(value = "查询课程设计")
    @GetMapping("/listDesigns")
    public List<TeaDesign> listDesignIds(Principal principal) {
        if (principal == null) {
            return null;
        }
        String name = principal.getName();
        return teaDesignService.listDesignIds(name);
    }

    @ApiOperation(value = "布置课程设计")
    @PostMapping("/disposeDesignToClasses")
    public RespBean disposeDesignToClasses(@RequestParam("designIds") Integer[] designIds,
                                           @RequestParam("endTime") String endTime,
                                           @RequestParam("classIds") Integer[] classIds,
                                           Principal principal) {
        String teaId = principal.getName();
        LocalDateTime end = LocalDateTime.parse(endTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return teacherService.disposeDesignToClasses(designIds, end, classIds, teaId);
    }

    @ApiOperation(value = "查询班级(课程设计)")
    @GetMapping("/listClassToDesign")
    public List<ClassDesign> listClassToDesign(Principal principal) {
        if (principal == null) {
            return null;
        }
        String name = principal.getName();
        return classDesignService.listClassToDesign(name);
    }

    @ApiOperation(value = "查询班级小组课程设计情况")
    @GetMapping("/listClassDesigns/{classId}")
    public List<StuDesign> listClassDesigns(@PathVariable("classId") Integer classId) {
        return stuDesignService.listClassDesigns(classId);
    }

    @ApiOperation(value = "查看分组结果")
    @GetMapping("/listStudentsGroups/{classId}")
    public List<StuGroup> listStudentsGroups(@PathVariable("classId") Integer classId) {
        return stuGroupService.listStudentsGroups(classId);
    }

    @ApiOperation(value = "查看该班级的课程设计")
    @GetMapping("/listClassOfDesigns/{classId}")
    public List<String> listClassOfDesigns(@PathVariable("classId") Integer classId){
        return classDesignService.listClassOfDesigns(classId);
    }

    // 仅不能再布置该设计了
    @ApiOperation(value = "删除该课程设计")
    @DeleteMapping("/deleteTheDesign/{designId}")
    public RespBean deleteTheDesign(@PathVariable("designId") Integer designId){
        return teaDesignService.deleteDesignByDesignId(designId);
    }


    // 1 下载源代码, 2 下载pdf,
    @ApiOperation(value = "下载作业课程设计")
    @GetMapping(value = "/downloadDesign", produces = "application/octet-stream")
    public void downloadDesign(Integer groupDesignId, Integer flag, HttpServletResponse response) {
        if (groupDesignId == null || flag == null) {
            // return RespBean.error(RespBeanEnum.DOWN_ERROR);
            return;
        }
        stuDesignService.downloadDesign(groupDesignId, flag, response);
    }


//    @ApiOperation(value = "新查询作业标题")
//    @GetMapping("/listWorkTitles")
//    public List<TeaWork> listWorkTitles(Principal principal) {
//        String name = principal.getName();
//        return teaWorkService.listWorkTitles(name);
//    }
//
//    // 删除作业分为直接通过作业ID删除全
//    @ApiOperation(value = "新通过作业ID删除作业")
//    @DeleteMapping("/deleteWorkById/{workId}")
//    public RespBean deleteWorkById(@PathVariable("workId") Integer workId) {
//        if (workId == null) {
//            return RespBean.error(RespBeanEnum.DELETE_ERROR);
//        }
//        return teacherService.deleteWorkById(workId);
//    }


}
