package com.kai.check.controller;


import com.kai.check.pojo.*;
import com.kai.check.service.*;
import com.kai.check.utils.RespBean;
import com.kai.check.utils.RespBeanEnum;
import com.kai.check.utils.RespPageBean;
import com.kai.check.utils.commit.CommitFactory;
import com.kai.check.utils.commit.ICommit;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.Synchronized;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
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
@RequestMapping("/student")
public class StudentController {

    @Resource
    private IStudentService studentService;
    @Resource
    private IStuWorkService stuWorkService;
    @Resource
    private IStuGroupService stuGroupService;
    @Resource
    private IStuDesignService stuDesignService;
    @Resource
    private ITeaDesignService teaDesignService;

    @ApiOperation(value = "获得当前用户(学生)信息")
    @GetMapping("/info")
    public Student getInfo(Principal principal) {
        if (null == principal) {
            return null;
        }
        String name = principal.getName();
        return studentService.getInfo(name);
    }

    @ApiOperation(value = "修改当前用户(学生)信息")
    @PutMapping("/updateInfo")
    public RespBean updateInfo(@RequestBody Student student) {
        if (studentService.updateById(student)) {
            return RespBean.success(RespBeanEnum.UPDATE_SUCCESS);
        }
        return RespBean.error(RespBeanEnum.UPDATE_ERROR);
    }

    @ApiOperation(value = "删除作业")
    @DeleteMapping("/deleteWork/{id}")
    public RespBean deleteWork(@PathVariable("id") Integer stuWorkId) {
        return stuWorkService.deleteWork(stuWorkId);
    }

    @ApiOperation(value = "新查作业(分页)")
    @GetMapping("/selectStuWorks")
    public RespPageBean selectStuWorks(@RequestParam(defaultValue = "1") Integer currentPage,
                                       @RequestParam(defaultValue = "10") Integer size,
                                       Principal principal) {
        String name = principal.getName();
        return stuWorkService.selectStuWorks(currentPage, size, name);
    }

    @ApiOperation(value = "新提交作业源代码")
    @PostMapping("/uploadStuWork")
    public RespBean uploadStuWork(@RequestParam("workFile") MultipartFile workFile, @RequestParam("stuWorkId") Integer stuWorkId, Principal principal) {
        if (stuWorkId == null || workFile == null || principal == null) {
            return RespBean.error(RespBeanEnum.COMMIT_ERROR);
        }
        String name = principal.getName();

        return stuWorkService.commitWork(stuWorkId, workFile, name, false);
//        ICommit commit = CommitFactory.getCommit(1);
//        if (commit.commit(stuWorkId, workFile, name)) {
//            return RespBean.success(RespBeanEnum.COMMIT_SUCCESS);
//        }
//        return RespBean.error(RespBeanEnum.COMMIT_ERROR);
    }

    @ApiOperation(value = "新提交作业PDF")
    @PostMapping("/uploadStuWorkPDF")
    public RespBean uploadStuWorkPDF(@RequestParam("workFile") MultipartFile workFile, @RequestParam("stuWorkId") Integer stuWorkId, Principal principal) {
        if (stuWorkId == null || workFile == null || principal == null) {
            return RespBean.error(RespBeanEnum.COMMIT_ERROR);
        }
        String name = principal.getName();
        return stuWorkService.commitWork(stuWorkId, workFile, name, true);
//        ICommit commit = CommitFactory.getCommit(2);
//        if (commit.commit(stuWorkId, workFile, name)) {
//            return RespBean.success(RespBeanEnum.COMMIT_SUCCESS);
//        }
//        return RespBean.error(RespBeanEnum.COMMIT_ERROR);
    }

    @ApiOperation(value = "新删除作业")
    @DeleteMapping("/deleteStudentWork/{stuWorkId}")
    public RespBean deleteStudentWork(@PathVariable("stuWorkId") Integer stuWorkId) {
        if (stuWorkId == null) {
            return RespBean.error(RespBeanEnum.DELETE_ERROR);
        }
        return stuWorkService.deleteStudentWork(stuWorkId);
    }

    @ApiOperation(value = "选择组员和课程设计")
    @PostMapping("/createGroup")
    public RespBean createGroup(@RequestParam("studentIds") String[] studentIds, @RequestParam("designId") Integer designId, Principal principal) {
        String name = principal.getName();
        return stuGroupService.createGroup(studentIds, name, designId);
    }

    @ApiOperation(value = "得到小组和设计信息")
    @GetMapping("/getGroupDesignInfo")
    public StuDesign getGroupDesignInfo(Principal principal) {
        if (principal == null) {
            return null;
        }
        String name = principal.getName();
        return stuDesignService.getGroupDesignInfo(name);
    }

    @ApiOperation(value = "查询班级同学")
    @GetMapping("/listClassStudents")
    public List<String> listClassStudents(Principal principal) {
        if (principal == null) {
            return null;
        }
        String name = principal.getName();
        return studentService.listClassStudents(name);
    }

    @ApiOperation(value = "查询课程设计")
    @GetMapping("/listClassDesigns")
    public List<ClassDesign> listClassDesigns(Principal principal) {
        if (principal == null) {
            return null;
        }
        String name = principal.getName();
        return studentService.listClassDesigns(name);
    }

    @ApiOperation(value = "下载设计的pdf")
    @GetMapping(value = "/downDesignPdf/{designId}", produces = "application/octet-stream")
    public void downDesignPdf(@PathVariable("designId") Integer designId, HttpServletResponse response) {
        if (designId == null) {
            return;
        }
        teaDesignService.downDesignPdf(designId, response);
    }

    @ApiOperation(value = "查询小组成员")
    @GetMapping("/selectGroupMembers")
    public StuGroup selectGroupMembers(Principal principal) {
        String name = principal.getName();
        return stuGroupService.selectGroupMembers(name);
    }

    @ApiOperation(value = "新提交课程设计源代码")
    @PostMapping("/uploadStuDesignCode")
    public RespBean uploadStuDesignCode(@RequestParam("workFile") MultipartFile workFile, @RequestParam("groupDesignId") Integer groupDesignId, Principal principal) {
        if (groupDesignId == null || workFile == null || principal == null) {
            return RespBean.error(RespBeanEnum.COMMIT_ERROR);
        }
        String name = principal.getName();
        return stuDesignService.commitWork(groupDesignId, workFile, name, false);
    }

    @ApiOperation(value = "新提交课程设计PDF")
    @PostMapping("/uploadStuDesignPDF")
    public RespBean uploadStuDesignPDF(@RequestParam("workFile") MultipartFile workFile, @RequestParam("groupDesignId") Integer groupDesignId, Principal principal) {
        if (groupDesignId == null || workFile == null || principal == null) {
            return RespBean.error(RespBeanEnum.COMMIT_ERROR);
        }
        String name = principal.getName();
        return stuDesignService.commitWork(groupDesignId, workFile, name, true);
    }
}
