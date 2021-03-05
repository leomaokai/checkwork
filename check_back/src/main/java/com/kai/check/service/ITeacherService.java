package com.kai.check.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kai.check.pojo.StuWork;
import com.kai.check.pojo.Teacher;
import com.kai.check.pojo.WorkClass;
import com.kai.check.utils.RespBean;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kai
 * @since 2021-03-04
 */
public interface ITeacherService extends IService<Teacher> {

    RespBean createClass(String className, String teacherId);

    RespBean insertUserByTeacher(String[] students, Integer classId);

    RespBean createWork(WorkClass workClass,String name);

    RespBean workToStu(Integer workId);

    List<StuWork> workCondition(Integer workId);

    Teacher getInfo(String name);

    RespBean teacherCheck(Integer workId, String name);

    RespBean deleteWork(Integer workId);

    RespBean deleteClass(Integer classId);
}
