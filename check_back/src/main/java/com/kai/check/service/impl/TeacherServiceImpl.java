package com.kai.check.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kai.check.mapper.*;
import com.kai.check.pojo.*;
import com.kai.check.service.ITeacherService;
import com.kai.check.service.IUserService;
import com.kai.check.utils.RespBean;
import com.kai.check.utils.RespBeanEnum;
import jplag.ExitException;
import jplag.Program;
import jplag.options.CommandLineOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    private UserMapper userMapper;
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
    @Resource
    private TeaWorkMapper teaWorkMapper;
    @Resource
    private ClassWorkMapper classWorkMapper;
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
    @Transactional
    public RespBean createClass(String className, String teacherId) {
        ClassTea one = classTeaMapper.selectOne(new QueryWrapper<ClassTea>().eq("class_name", className));
        if(one!=null){
            return RespBean.error(RespBeanEnum.INSERT_ERROR);
        }
        ClassTea classTea = new ClassTea();
        classTea.setClassName(className);
        classTea.setTeaId(teacherId);
        if (classTeaMapper.insert(classTea) == 1) {
            return RespBean.success(RespBeanEnum.INSERT_SUCCESS);
        }
        return RespBean.error(RespBeanEnum.INSERT_ERROR);
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
            for (String studentId : students) {
                if(studentMapper.selectById(studentId)!=null){
                    continue;
                }
                Student stu = new Student();
                stu.setStuId(studentId);
                stu.setStuClassId(classId);
                studentMapper.insert(stu);
            }
        }
        return RespBean.success(RespBeanEnum.INSERT_SUCCESS);
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
    @Transactional
    public RespBean teacherCheck(Integer workId, String name) {
        WorkClass workClass = workClassMapper.selectOne(new QueryWrapper<WorkClass>().eq("id", workId));
        String resourcePath = workClass.getWorkDir();
        String workDescribe = workClass.getWorkDescribe();
        // 得到结果路径
        String resultPath = result + "/" + name + "/" + workDescribe;
        List<WorkResult> workResults = workResultMapper.selectList(new QueryWrapper<WorkResult>().eq("work_id", workId));
        for (WorkResult workResult : workResults) {
            Integer workFirstId = workResult.getWorkFirstId();
            Integer workSecondId = workResult.getWorkSecondId();
            StuWork stuWorkFirst = stuWorkMapper.selectOne(new QueryWrapper<StuWork>().eq("id", workFirstId));
            StuWork stuWorkSecond = stuWorkMapper.selectOne(new QueryWrapper<StuWork>().eq("id", workSecondId));
            String firstWorkExt = stuWorkFirst.getWorkExt();
            String secondWorkExt = stuWorkSecond.getWorkExt();
            if (firstWorkExt == null || secondWorkExt == null) {
                // 有同学还没有提交作业
                continue;
            }
            if (!firstWorkExt.equals(secondWorkExt)) {
                // 文件类型不同无法查重,将结果设为-1
                workResult.setWorkResult("-1");
                continue;
            }
            String stuWorkFirstWorkName = stuWorkFirst.getWorkName();
            String stuWorkSecondWorkName = stuWorkSecond.getWorkName();
            String result = this.check(resourcePath, resultPath, stuWorkFirstWorkName, stuWorkSecondWorkName, firstWorkExt);
            workResult.setWorkResult(result + "%");
            workResultMapper.updateById(workResult);
        }
        return RespBean.success();
    }

    @Override
    @Transactional
    public RespBean deleteWork(Integer workId, String name) {
        if(this.deleteWorkNormal(workId,name)){
            return RespBean.success();
        }
        return RespBean.error();
    }

    @Override
    @Transactional
    public RespBean deleteClass(Integer classId, String name) {
        if(classTeaMapper.deleteById(classId)==1){
            List<Student> students = studentMapper.selectList(new QueryWrapper<Student>().eq("stu_class_id", classId));
            for (Student student : students) {
                String stuId = student.getStuId();
                userService.removeById(stuId);
                studentMapper.deleteById(stuId);
            }
            List<WorkClass> workClasses = workClassMapper.selectList(new QueryWrapper<WorkClass>().eq("class_id", classId));
            for (WorkClass workClass : workClasses) {
                Integer workId = workClass.getId();
                if(this.deleteWorkNormal(workId,name)){
                    continue;
                }
            }
            return RespBean.success();
        }
        return RespBean.error();
    }

    @Override
    @Transactional
    public RespBean disposeWork(TeaWork teaWork, Integer[] classIds, String teaId) {
        // 这里有错 要判断这个作业是否存在(根据teaId 和 作业标题)  所以作业标题最好是一个下拉框(固定选择)
        teaWork.setTeaId(teaId);
        String workTitle = teaWork.getWorkTitle();
        String path = resource + "/" + teaId;
        File workDir = new File(path, workTitle);
        LocalDateTime now = LocalDateTime.now();
        teaWork.setCreateTime(now);
        if (!workDir.exists()) {
            workDir.mkdirs();
        }
        String workDirPath = workDir.getPath();
        teaWork.setWorkDir(workDirPath);
        if (teaWorkMapper.insert(teaWork) == 1) {
            TeaWork work = teaWorkMapper.selectOne(new QueryWrapper<TeaWork>().eq("create_time", now));
            Integer workId = work.getWorkId();
            for (Integer classId : classIds) {
                ClassWork classWork = new ClassWork();
                classWork.setWorkId(work.getWorkId());
                classWork.setTeaId(teaId);
                classWork.setClassId(classId);
                classWorkMapper.insert(classWork);

                List<Student> students = studentMapper.selectList(new QueryWrapper<Student>().eq("stu_class_id", classId));
                for (Student student : students) {
                    StuWork stuWork = new StuWork();
                    stuWork.setStuId(student.getStuId());
                    stuWork.setWorkId(workId);
                    stuWork.setClassId(classId);
                    stuWorkMapper.insert(stuWork);
                }
            }

            // 作业展示是按班级展示,但查重会查做这个作业的所有学生
            workResultMapper.delete(new QueryWrapper<WorkResult>().eq("work_id",workId));
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

        }

    }

    private void cleanFiles(File resDir) {
        if (resDir.listFiles() != null) {
            File[] files = resDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
        }
    }

    private String check(String resourcePath, String resultPath, String workName1, String workName2, String lang) {
        // 如果已经查重过(结果文件夹有文件),将结果清除重新查重
        File resDir = new File(resultPath);
        cleanFiles(resDir);
        try {
            // 查重
            List<String> args = new ArrayList<>();
            // 指定语言
            args.add("-l");
            // java只能检测java9以后版本
            if ("java".equals(lang)) {
                args.add("java19");
            } else if ("cpp".equals(lang) || "c".equals(lang)) {
                args.add("c/c++");
            } else if ("py".equals(lang)) {
                args.add("python3");
            } else {
                args.add(lang);
            }
            args.add("-r");
            args.add(resultPath);
            // 设置相似度检查门限参数值
//            args.add("-m");
//            args.add(sim + "%");
            // 指定源文件存放路径
            args.add("-s");
            args.add(resourcePath);
            args.add("-c");
            args.add(workName1);
            args.add(workName2);
            String[] toPass = new String[args.size()];
            toPass = args.toArray(toPass);
            new Program(new CommandLineOptions(toPass)).run();
        } catch (ExitException e) {
            e.printStackTrace();
        }
        return getCheckResult(resultPath);
    }

    private String getCheckResult(String resultPath) {
        String result = "-1";
        File resultFile = new File(resultPath, "matches_avg.csv");
        if (!resultFile.exists()) {
            return result;
        }
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(resultFile));) {
            byte[] bytes = new byte[(int) resultFile.length()];
            bufferedInputStream.read(bytes);
            StringBuilder resString = new StringBuilder(new String(bytes));
            char c = ';';
            Integer[] integers = new Integer[4];
            int index = 0;
            int length = resString.length();
            for (int i = 0; i < length; i++) {
                if (c == resString.charAt(i)) {
                    integers[index++] = i;
                }
            }
            result = resString.substring(integers[2] + 1, integers[3]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private boolean deleteWorkNormal(Integer workId,String name){
        WorkClass workClass = workClassMapper.selectById(workId);
        String resourceWorkDir = workClass.getWorkDir();
        File file = new File(resourceWorkDir);
        if(file.exists()){
            file.delete();
        }
        String workDescribe = workClass.getWorkDescribe();
        String resultWorkDir=result+"/"+name+"/"+workDescribe;
        File resultFile = new File(resultWorkDir);
        if(resultFile.exists()){
            resultFile.delete();
        }
        if(workClassMapper.deleteById(workId)==1){
            stuWorkMapper.delete(new QueryWrapper<StuWork>().eq("work_id", workId));
            workResultMapper.delete(new QueryWrapper<WorkResult>().eq("work_id", workId));
            return true;
        }
        return false;
    }
}
