package com.kai.check.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kai.check.mapper.*;
import com.kai.check.pojo.*;
import com.kai.check.service.ITeacherService;
import com.kai.check.service.IUserService;
import com.kai.check.utils.CheckCode;
import com.kai.check.utils.RespBean;
import com.kai.check.utils.RespBeanEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.*;
import java.time.LocalDateTime;
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
    private UserMapper userMapper;
    @Resource
    private StudentMapper studentMapper;
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
    @Resource
    private ClassDesignMapper classDesignMapper;
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
        if (one != null) {
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
                if (studentMapper.selectById(studentId) != null) {
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


//    @Override
//    @Transactional
//    public RespBean createWork(WorkClass workClass, String name) {
//        // 创建作业目录
//        String workDescribe = workClass.getWorkDescribe();
//        String path = resource + "/" + name;
//        File workDir = new File(path, workDescribe);
//        if (!workDir.exists()) {
//            workDir.mkdirs();
//        }
//        String workDirPath = workDir.getPath();
//        workClass.setWorkDir(workDirPath);
//        if (workClassMapper.insert(workClass) == 1) {
//            return RespBean.success();
//        }
//        return RespBean.error();
//    }

//    @Override
//    @Transactional
//    public RespBean workToStu(Integer workId) {
//        WorkClass workClass = workClassMapper.selectOne(new QueryWrapper<WorkClass>().eq("id", workId));
//        Integer classId = workClass.getClassId();
//        List<Student> students = studentMapper.selectList(new QueryWrapper<Student>().eq("stu_class_id", classId));
//        int len = students.size();
//        int ret = 0;
//        for (Student student : students) {
//            String stuId = student.getStuId();
//            // 判断是否已经存在
//            StuWork stuWork1 = stuWorkMapper.selectOne(new QueryWrapper<StuWork>().eq("stu_id", stuId).eq("work_id", workId));
//            if (stuWork1 != null) {
//                ret++;
//                continue;
//            }
//            StuWork stuWork = new StuWork();
//            stuWork.setStuId(stuId);
//            stuWork.setWorkId(workId);
//            ret += stuWorkMapper.insert(stuWork);
//        }
//        if (len == ret) {
//            //return RespBean.success();
//            List<StuWork> stuWorks = stuWorkMapper.selectList(new QueryWrapper<StuWork>().eq("work_id", workId));
//            int size = stuWorks.size();
//            for (int i = 0; i < size - 1; i++) {
//                for (int j = i + 1; j < size; j++) {
//                    WorkResult workResult = new WorkResult();
//                    workResult.setWorkId(workId);
//                    workResult.setWorkFirstId(stuWorks.get(i).getId());
//                    workResult.setWorkSecondId(stuWorks.get(j).getId());
//                    workResultMapper.insert(workResult);
//                }
//            }
//            return RespBean.success();
//        }
//        return RespBean.error();
//    }

//    @Override
//    @Transactional
//    public List<StuWork> workCondition(Integer workId) {
//        List<StuWork> stuWorks = stuWorkMapper.selectList(new QueryWrapper<StuWork>().eq("work_id", workId));
//        return stuWorks;
//    }

    @Override
    public Teacher getInfo(String name) {
        return teacherMapper.selectOne(new QueryWrapper<Teacher>().eq("tea_id", name));
    }

//    @Override
//    @Transactional
//    public RespBean teacherCheck(Integer workId, String name) {
//        WorkClass workClass = workClassMapper.selectOne(new QueryWrapper<WorkClass>().eq("id", workId));
//        String resourcePath = workClass.getWorkDir();
//        String workDescribe = workClass.getWorkDescribe();
//        // 得到结果路径
//        String resultPath = result + "/" + name + "/" + workDescribe;
//        List<WorkResult> workResults = workResultMapper.selectList(new QueryWrapper<WorkResult>().eq("work_id", workId));
//        for (WorkResult workResult : workResults) {
//            Integer workFirstId = workResult.getWorkFirstId();
//            Integer workSecondId = workResult.getWorkSecondId();
//            StuWork stuWorkFirst = stuWorkMapper.selectOne(new QueryWrapper<StuWork>().eq("id", workFirstId));
//            StuWork stuWorkSecond = stuWorkMapper.selectOne(new QueryWrapper<StuWork>().eq("id", workSecondId));
//            String firstWorkExt = stuWorkFirst.getWorkExt();
//            String secondWorkExt = stuWorkSecond.getWorkExt();
//            if (firstWorkExt == null || secondWorkExt == null) {
//                // 有同学还没有提交作业
//                continue;
//            }
//            if (!firstWorkExt.equals(secondWorkExt)) {
//                // 文件类型不同无法查重,将结果设为-1
//                workResult.setWorkResult("-1");
//                continue;
//            }
//            String stuWorkFirstWorkName = stuWorkFirst.getWorkName();
//            String stuWorkSecondWorkName = stuWorkSecond.getWorkName();
//            String result = this.check(resourcePath, resultPath, stuWorkFirstWorkName, stuWorkSecondWorkName, firstWorkExt);
//            workResult.setWorkResult(result + "%");
//            workResultMapper.updateById(workResult);
//        }
//        return RespBean.success();
//    }

//    @Override
//    @Transactional
//    public RespBean deleteWork(Integer workId, String name) {
//        if (this.deleteWorkNormal(workId, name)) {
//            return RespBean.success();
//        }
//        return RespBean.error();
//    }

    @Override
    @Transactional
    public RespBean deleteClass(Integer classId, String name) {
        if (classTeaMapper.deleteById(classId) == 1) {
            List<Student> students = studentMapper.selectList(new QueryWrapper<Student>().eq("stu_class_id", classId));
            for (Student student : students) {
                String stuId = student.getStuId();
                userMapper.deleteById(stuId);
                studentMapper.deleteById(stuId);
            }
            List<StuWork> stuWorks = stuWorkMapper.selectList(new QueryWrapper<StuWork>().eq("class_id", classId));
            this.deleteWorkByStuWorks(stuWorks);
            stuWorkMapper.delete(new QueryWrapper<StuWork>().eq("class_id", classId));
//            workResultMapper.delete(new QueryWrapper<WorkResult>().eq("class_id", classId));
            return RespBean.success(RespBeanEnum.DELETE_SUCCESS);
        }
        return RespBean.error(RespBeanEnum.DELETE_ERROR);
    }

    @Override
    @Transactional
    public RespBean disposeWork(String workTitle, String workDescribe, LocalDateTime endTime, Integer[] classIds, String teaId) {
        // 查该作业是否存在
        TeaWork oneWork = teaWorkMapper.selectOne(new QueryWrapper<TeaWork>().eq("work_title", workTitle).eq("tea_id", teaId));
        if (oneWork == null) {
            TeaWork teaWork = new TeaWork();
            teaWork.setTeaId(teaId);
            teaWork.setWorkTitle(workTitle);
            teaWork.setWorkDescribe(workDescribe);
            String path = resource + "/" + teaId;
            File workDir = new File(path, workTitle);
            if (!workDir.exists()) {
                workDir.mkdirs();
            }
            teaWork.setWorkDir(path + "/" + workTitle);
            teaWorkMapper.insert(teaWork);
        } else {
            oneWork.setWorkDescribe(workDescribe);
            teaWorkMapper.updateById(oneWork);
        }

        TeaWork work = teaWorkMapper.selectOne(new QueryWrapper<TeaWork>().eq("work_title", workTitle).eq("tea_id", teaId));
        Integer workId = work.getWorkId();
        for (Integer classId : classIds) {
            // 该班级已经布置过了,删除重新布置,前端要做确认操作
            classWorkMapper.delete(new QueryWrapper<ClassWork>().eq("class_id", classId).eq("work_id", workId));
            stuWorkMapper.delete(new QueryWrapper<StuWork>().eq("work_id", workId).eq("class_id", classId));
            ClassWork classWork = new ClassWork();
            classWork.setWorkId(work.getWorkId());
            classWork.setTeaId(teaId);
            classWork.setClassId(classId);
            classWork.setEndTime(endTime);
            classWorkMapper.insert(classWork);

            List<Student> students = studentMapper.selectList(new QueryWrapper<Student>().eq("stu_class_id", classId));
            for (Student student : students) {
                StuWork stuWork = new StuWork();
                stuWork.setStuId(student.getStuId());
                stuWork.setWorkId(workId);
                stuWork.setClassId(classId);
                stuWorkMapper.insert(stuWork);
            }

            // 已修改为在学生交作业时创建作业结果
//            // 作业展示是按班级展示,但查重会查做这个作业的所有学生
//            workResultMapper.delete(new QueryWrapper<WorkResult>().eq("work_id", workId));
//            List<StuWork> stuWorks = stuWorkMapper.selectList(new QueryWrapper<StuWork>().eq("work_id", workId));
//            int size = stuWorks.size();
//            for (int i = 0; i < size - 1; i++) {
//                for (int j = i + 1; j < size; j++) {
//                    WorkResult workResult = new WorkResult();
//                    workResult.setWorkId(workId);
////                    workResult.setClassId(classId);
//                    workResult.setWorkFirstId(stuWorks.get(i).getId());
//                    workResult.setWorkSecondId(stuWorks.get(j).getId());
//                    workResultMapper.insert(workResult);
//                }
//            }
        }


        return RespBean.success(RespBeanEnum.DISPOSE_SUCCESS);

    }

    @Override
    @Transactional
    public RespBean deleteWorkById(Integer workId) {
        TeaWork teaWork = teaWorkMapper.selectById(workId);
        if (teaWork != null) {
            File file = new File(teaWork.getWorkDir());
            if (file.exists()) {
                file.delete();
            }
            classWorkMapper.delete(new QueryWrapper<ClassWork>().eq("work_id", workId));
            stuWorkMapper.delete(new QueryWrapper<StuWork>().eq("work_id", workId));
            workResultMapper.delete(new QueryWrapper<WorkResult>().eq("work_id", workId));
            teaWorkMapper.deleteById(workId);
            return RespBean.success(RespBeanEnum.DELETE_SUCCESS);
        }
        return RespBean.error(RespBeanEnum.DELETE_ERROR);
    }

    @Override
    @Transactional
    public RespBean deleteWorkByClass(Integer classWorkId) {
        ClassWork classWork = classWorkMapper.selectById(classWorkId);
        if (classWork != null) {
            Integer classId = classWork.getClassId();
            Integer workId = classWork.getWorkId();

            List<StuWork> stuWorks = stuWorkMapper.selectList(new QueryWrapper<StuWork>().eq("class_id", classId).eq("work_id", workId));
            this.deleteWorkByStuWorks(stuWorks);
//            workResultMapper.delete(new QueryWrapper<WorkResult>().eq("class_id", classId).eq("work_id", workId));
            classWorkMapper.deleteById(classWorkId);
            stuWorkMapper.delete(new QueryWrapper<StuWork>().eq("class_id", classId).eq("work_id", workId));
            return RespBean.success(RespBeanEnum.DELETE_SUCCESS);
        }
        return RespBean.error(RespBeanEnum.DELETE_ERROR);
    }

    @Override
    @Transactional
    public RespBean deleteStudent(String studentId) {
        if (userMapper.deleteById(studentId) == 1) {
            List<StuWork> stuWorks = stuWorkMapper.selectList(new QueryWrapper<StuWork>().eq("stu_id", studentId));
            for (StuWork stuWork : stuWorks) {
                Integer stuWorkId = stuWork.getId();
                workResultMapper.deleteStuWorkId(stuWorkId);
            }
            stuWorkMapper.delete(new QueryWrapper<StuWork>().eq("stu_id", studentId));
            return RespBean.success(RespBeanEnum.DELETE_SUCCESS);
        }
        return RespBean.error(RespBeanEnum.DELETE_ERROR);
    }

    @Override
    @Transactional
    public RespBean disposeDesignToClasses(Integer[] designIds, LocalDateTime end, Integer[] classIds, String teaId) {
        for (Integer classId : classIds) {
            for (Integer designId : designIds) {
                ClassDesign classDesign = new ClassDesign();
                classDesign.setClassId(classId);
                classDesign.setDesignId(designId);
                classDesign.setTeaId(teaId);
                classDesign.setEndTime(end);
                classDesignMapper.insert(classDesign);
            }
        }
        return RespBean.success(RespBeanEnum.DISPOSE_SUCCESS);
    }

    // 在学生交作业时查重,老师只查看结果
//    @Override
//    @Transactional
//    public RespBean checkCode(Integer workId, String name) {
//        List<WorkResult> workResults = workResultMapper.selectList(new QueryWrapper<WorkResult>().eq("work_id", workId));
//        TeaWork teaWork = teaWorkMapper.selectById(workId);
//
//        if (workResults.isEmpty() || teaWork == null) {
//            return RespBean.error(RespBeanEnum.CHECK_ERROR);
//        }
//        // 结果路径
//        String resultPath = result + "name" + teaWork.getWorkTitle();
//        // 作业目录
//        String resourcePath = teaWork.getWorkDir();
//        // 查重
//        for (WorkResult workResult : workResults) {
//            Integer workFirstId = workResult.getWorkFirstId();
//            Integer workSecondId = workResult.getWorkSecondId();
//            StuWork stuWork1 = stuWorkMapper.selectById(workFirstId);
//            StuWork stuWork2 = stuWorkMapper.selectById(workSecondId);
//            // 结果表中存在的作业数据因为是在学生交作业时生成的,所以一定存在
////            if (stuWork1 == null || stuWork2 == null || stuWork1.getIsCommit().equals("未提交") || stuWork2.getIsCommit().equals("未提交")) {
////                continue;
////            }
//            String lang = stuWork1.getWorkExt();
//            String workName1 = stuWork1.getWorkName();
//            String workName2 = stuWork2.getWorkName();
//        }
//
//        return null;
//    }

    // 修改作业标题存在问题??...
//    @Override
//    @Transactional
//    public RespBean updateWorkTitle(Integer workId, String workTitle) {
//        TeaWork teaWork = teaWorkMapper.selectById(workId);
//        teaWork.setWorkTitle(workTitle);
//        String workDir = teaWork.getWorkDir();
//        // 建立新的文件夹
//        String newWorkDir=workDir.substring(0,workDir.lastIndexOf('/')+1)+workTitle;
//        File file1 = new File(newWorkDir);
//        if(!file1.exists()){
//            file1.mkdirs();
//        }
//        File file = new File(workDir);
//        if (file.exists()) {
//            List<StuWork> stuWorks = stuWorkMapper.selectList(new QueryWrapper<StuWork>().eq("work_id", workId));
//            for (StuWork stuWork : stuWorks) {
//                String workUrl = stuWork.getWorkUrl();
//                String substring = workUrl.substring(workUrl.indexOf('/'));
//                stuWork.setWorkUrl(newWorkDir + substring);
//                stuWorkMapper.updateById(stuWork);
//            }
//        }
//        return RespBean.success(RespBeanEnum.UPDATE_SUCCESS);
//    }

    // 重重模块改成了工具栏
//    private void cleanFiles(File resDir) {
//        if (resDir.listFiles() != null) {
//            File[] files = resDir.listFiles();
//            if (files != null) {
//                for (File file : files) {
//                    file.delete();
//                }
//            }
//        }
//    }
//
//    private String check(String resourcePath, String resultPath, String workName1, String workName2, String lang) {
//        // 如果已经查重过(结果文件夹有文件),将结果清除重新查重
//        File resDir = new File(resultPath);
//        cleanFiles(resDir);
//        try {
//            // 查重
//            List<String> args = new ArrayList<>();
//            // 指定语言
//            args.add("-l");
//            // java只能检测java9以后版本
//            if ("java".equals(lang)) {
//                args.add("java19");
//            } else if ("cpp".equals(lang) || "c".equals(lang)) {
//                args.add("c/c++");
//            } else if ("py".equals(lang)) {
//                args.add("python3");
//            } else {
//                args.add(lang);
//            }
//            args.add("-r");
//            args.add(resultPath);
//            // 设置相似度检查门限参数值
////            args.add("-m");
////            args.add(sim + "%");
//            // 指定源文件存放路径
//            args.add("-s");
//            args.add(resourcePath);
//            args.add("-c");
//            args.add(workName1);
//            args.add(workName2);
//            String[] toPass = new String[args.size()];
//            toPass = args.toArray(toPass);
//            new Program(new CommandLineOptions(toPass)).run();
//        } catch (ExitException e) {
//            e.printStackTrace();
//        }
//        return getCheckResult(resultPath);
//    }
//
//    private String getCheckResult(String resultPath) {
//        String result = "-1";
//        File resultFile = new File(resultPath, "matches_avg.csv");
//        if (!resultFile.exists()) {
//            return result;
//        }
//        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(resultFile));) {
//            byte[] bytes = new byte[(int) resultFile.length()];
//            bufferedInputStream.read(bytes);
//            StringBuilder resString = new StringBuilder(new String(bytes));
//            char c = ';';
//            Integer[] integers = new Integer[4];
//            int index = 0;
//            int length = resString.length();
//            for (int i = 0; i < length; i++) {
//                if (c == resString.charAt(i)) {
//                    integers[index++] = i;
//                }
//            }
//            result = resString.substring(integers[2] + 1, integers[3]);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }

//    private boolean deleteWorkNormal(Integer workId, String name) {
//        WorkClass workClass = workClassMapper.selectById(workId);
//        String resourceWorkDir = workClass.getWorkDir();
//        File file = new File(resourceWorkDir);
//        if (file.exists()) {
//            file.delete();
//        }
//        String workDescribe = workClass.getWorkDescribe();
//        String resultWorkDir = result + "/" + name + "/" + workDescribe;
//        File resultFile = new File(resultWorkDir);
//        if (resultFile.exists()) {
//            resultFile.delete();
//        }
//        if (workClassMapper.deleteById(workId) == 1) {
//            stuWorkMapper.delete(new QueryWrapper<StuWork>().eq("work_id", workId));
//            workResultMapper.delete(new QueryWrapper<WorkResult>().eq("work_id", workId));
//            return true;
//        }
//        return false;
//    }

    private void deleteWorkByStuWorks(List<StuWork> stuWorks) {
        for (StuWork stuWork : stuWorks) {
            String workUrl = stuWork.getWorkUrl();
            CheckCode.deleteWorkFileByPath(workUrl);
            String pdfPath = stuWork.getPdfPath();
            CheckCode.deleteWorkFileByPath(pdfPath);
            Integer id = stuWork.getId();
            workResultMapper.deleteStuWorkId(id);
        }
    }

}
