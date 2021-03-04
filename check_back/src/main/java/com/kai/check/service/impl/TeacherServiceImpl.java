package com.kai.check.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kai.check.mapper.*;
import com.kai.check.pojo.*;
import com.kai.check.service.ITeacherService;
import com.kai.check.service.IUserService;
import com.kai.check.utils.RespBean;
import jplag.ExitException;
import jplag.Program;
import jplag.options.CommandLineOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author kai
 * @since 2021-03-04
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {

    @Resource
    private ClassTeaMapper classTeaMapper;
    @Resource
    private IUserService userService;
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private WorkClassMapper workClassMapper;
    @Resource
    private StuWorkMapper stuWorkMapper;
    @Resource
    private TeacherMapper teacherMapper;
    @Resource
    private WorkResultMapper workResultMapper;
    @Value("${kai.resource}")
    private String resource;
    @Value("${kai.result}")
    private String result;
    @Value("${kai.sim}")
    private String sim;

    /**
     * 老师创建班级
     *
     * @param className
     * @param teacherId
     * @return
     */
    @Override
    public RespBean createClass(String className, String teacherId) {
        ClassTea classTea = new ClassTea();
        classTea.setClassName(className);
        classTea.setTeaId(teacherId);
        if (classTeaMapper.insert(classTea) == 1) {
            return RespBean.success();
        }
        return RespBean.error();
    }

    /**
     * 通过老师添加学生
     * 需要添加学生表
     *
     * @param students
     * @param classId
     * @return
     */
    @Override
    @Transactional
    public RespBean insertUserByTeacher(String[] students, Integer classId) {
        Integer roleId = 3;
        if (userService.insertUser(roleId, students)) {
            int len = students.length;
            int ret = 0;
            for (String studentId : students) {
                Student stu = new Student();
                stu.setStuId(studentId);
                stu.setStuClassId(classId);
                ret += studentMapper.insert(stu);
            }
            if (len == ret) {
                return RespBean.success();
            }
        }
        return RespBean.error();
    }

    /**
     * 布置作业
     *
     * @param workClass
     * @return
     */
    @Override
    @Transactional
    public RespBean createWork(WorkClass workClass, String name) {
        // 创建作业目录
        String workDescribe = workClass.getWorkDescribe();
        String path = resource + "/" + name;
        File workDir = new File(path, workDescribe);
        if (!workDir.exists()) {
            workDir.mkdirs();
        }
        String workDirPath = workDir.getPath();
        workClass.setWorkDir(workDirPath);
        if (workClassMapper.insert(workClass) == 1) {
            return RespBean.success();
        }
        return RespBean.error();
    }

    @Override
    @Transactional
    public RespBean workToStu(Integer workId) {
        WorkClass workClass = workClassMapper.selectOne(new QueryWrapper<WorkClass>().eq("id", workId));
        Integer classId = workClass.getClassId();
        List<Student> students = studentMapper.selectList(new QueryWrapper<Student>().eq("stu_class_id", classId));
        int len = students.size();
        int ret = 0;
        for (Student student : students) {
            String stuId = student.getStuId();
            // 判断是否已经存在
            StuWork stuWork1 = stuWorkMapper.selectOne(new QueryWrapper<StuWork>().eq("stu_id", stuId).eq("work_id", workId));
            if (stuWork1 != null) {
                ret++;
                continue;
            }
            StuWork stuWork = new StuWork();
            stuWork.setStuId(stuId);
            stuWork.setWorkId(workId);
            ret += stuWorkMapper.insert(stuWork);
        }
        if (len == ret) {
            //return RespBean.success();
            List<StuWork> stuWorks = stuWorkMapper.selectList(new QueryWrapper<StuWork>().eq("work_id", workId));
            int size = stuWorks.size();
            for (int i = 0; i < size - 1; i++) {
                for (int j = i + 1; j < size; j++) {
                    WorkResult workResult = new WorkResult();
                    workResult.setWorkId(workId);
                    workResult.setWorkFirstId(stuWorks.get(i).getId());
                    workResult.setWorkSecondId(stuWorks.get(j).getId());
                    workResultMapper.insert(workResult);
                }
            }
            return RespBean.success();
        }
        return RespBean.error();
    }

    @Override
    @Transactional
    public List<StuWork> workCondition(Integer workId) {
        List<StuWork> stuWorks = stuWorkMapper.selectList(new QueryWrapper<StuWork>().eq("work_id", workId));
        return stuWorks;
    }

    @Override
    public Teacher getInfo(String name) {
        return teacherMapper.selectOne(new QueryWrapper<Teacher>().eq("tea_id", name));
    }

    @Override
    public RespBean check(Integer workId, String lang, String name) {
        WorkClass workClass = workClassMapper.selectOne(new QueryWrapper<WorkClass>().eq("id", workId));
        String workDescribe = workClass.getWorkDescribe();
        String workDir = workClass.getWorkDir();
        // 得到结果路径,若已有查询结果文件则将文件清除
        String resPath = result + "/" + name + "/" + workDescribe;
        File resDir = new File(resPath);
        if (resDir.listFiles() != null) {
            File[] files = resDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
        }
        try {
            // 查重
            List<String> args = new ArrayList<>();
            // 指定语言
            args.add("-l");
            // java只能检测java9以后版本
            if ("Java".equals(lang)) {
                args.add("java19");
            } else if ("C".equals(lang) || "C++".equals(lang)) {
                args.add("c/c++");
            } else if ("Python".equals(lang)) {
                args.add("python3");
            } else {
                args.add(lang);
            }
            args.add("-r");
            args.add(resPath);
            // 设置相似度检查门限参数值
            args.add("-m");
            args.add(sim + "%");
            // 指定源文件存放路径
            args.add("-s");
            args.add(workDir);
            String[] toPass = new String[args.size()];
            toPass = args.toArray(toPass);
            new Program(new CommandLineOptions(toPass)).run();
        } catch (ExitException e) {
            e.printStackTrace();
        }

        return null;
    }
}
