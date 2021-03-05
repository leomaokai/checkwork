package com.kai.check.controller;


import com.kai.check.pojo.StuWork;
import com.kai.check.pojo.Student;
import com.kai.check.service.IStuWorkService;
import com.kai.check.service.IStudentService;
import com.kai.check.utils.RespBean;
import com.kai.check.utils.RespBeanEnum;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;

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
            return RespBean.success();
        }
        return RespBean.error();
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

    @ApiOperation(value = "下载作业(下载或在线查看)")
    @GetMapping("/downWork/{stuWorkId}/{openStyle}")
    public RespBean downWork(@PathVariable("stuWorkId") Integer stuWorkId,@PathVariable("openStyle") Integer isDown, HttpServletResponse response) {
        return stuWorkService.downWork(stuWorkId,isDown,response);
    }
}
