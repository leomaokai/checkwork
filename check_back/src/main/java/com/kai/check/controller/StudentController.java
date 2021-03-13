package com.kai.check.controller;


import com.kai.check.pojo.StuWork;
import com.kai.check.pojo.Student;
import com.kai.check.service.IStuWorkService;
import com.kai.check.service.IStudentService;
import com.kai.check.utils.RespBean;
import com.kai.check.utils.RespBeanEnum;
import com.kai.check.utils.RespPageBean;
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

    @ApiOperation(value = "查作业")
    @GetMapping("/listWorks")
    public List<StuWork> listWorks(Principal principal) {
        if (null == principal) {
            return null;
        }
        String name = principal.getName();
        return stuWorkService.listWorks(name);
    }

    @ApiOperation(value = "提交作业")
    @PostMapping("/commitWork/{workId}")
    public RespBean commitWork(@PathVariable("workId") Integer workId, MultipartFile workFile, Principal principal) {
        String name = principal.getName();
        return studentService.commitWork(workId, workFile, name);
    }

    @ApiOperation(value = "删除作业")
    @DeleteMapping("/deleteWork/{id}")
    public RespBean deleteWork(@PathVariable("id") Integer stuWorkId) {
        return stuWorkService.deleteWork(stuWorkId);
    }

//    @ApiOperation(value = "下载作业(下载或在线查看)")
//    @GetMapping("/downWork/{stuWorkId}/{openStyle}")
//    public RespBean downWork(@PathVariable("stuWorkId") Integer stuWorkId, @PathVariable("openStyle") Integer isDown, HttpServletResponse response) {
//        return stuWorkService.downWork(stuWorkId, isDown, response);
//    }

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
    public RespBean uploadStuWork(@RequestParam("workFile") MultipartFile workFile,@RequestParam("stuWorkId") Integer stuWorkId,Principal principal) {
        if (stuWorkId == null || workFile == null || principal == null) {
            return RespBean.error(RespBeanEnum.COMMIT_ERROR);
        }
        String name = principal.getName();

        return stuWorkService.commitWork(stuWorkId, workFile, name,false);
    }

    @ApiOperation(value = "新提交作业PDF")
    @PostMapping("/uploadStuWorkPDF")
    public RespBean uploadStuWorkPDF(@RequestParam("workFile") MultipartFile workFile,@RequestParam("stuWorkId") Integer stuWorkId,Principal principal){
        if (stuWorkId == null || workFile == null || principal == null) {
            return RespBean.error(RespBeanEnum.COMMIT_ERROR);
        }
        String name = principal.getName();
        return stuWorkService.commitWork(stuWorkId,workFile,name,true);
    }

    @ApiOperation(value = "新删除作业")
    @DeleteMapping("/deleteStudentWork/{stuWorkId}")
    public RespBean deleteStudentWork(@PathVariable("stuWorkId") Integer stuWorkId){
        if(stuWorkId==null){
            return RespBean.error(RespBeanEnum.DELETE_ERROR);
        }
        return stuWorkService.deleteStudentWork(stuWorkId);
    }
}
