package com.kai.check.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kai.check.pojo.ClassDesign;
import com.kai.check.pojo.Student;
import com.kai.check.pojo.TeaDesign;
import com.kai.check.utils.RespBean;
import com.kai.check.utils.RespPageBean;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kai
 * @since 2021-03-04
 */
public interface IStudentService extends IService<Student> {

    RespPageBean listStudents(Integer currentPage, Integer size, Integer classId);

    Student getInfo(String name);

    List<String> listClassStudents(String name);

    List<ClassDesign> listClassDesigns(String name);
}
