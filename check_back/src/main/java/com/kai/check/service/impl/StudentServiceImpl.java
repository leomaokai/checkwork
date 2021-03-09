package com.kai.check.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kai.check.mapper.ClassTeaMapper;
import com.kai.check.mapper.StuWorkMapper;
import com.kai.check.mapper.StudentMapper;
import com.kai.check.mapper.WorkClassMapper;
import com.kai.check.pojo.ClassTea;
import com.kai.check.pojo.StuWork;
import com.kai.check.pojo.Student;
import com.kai.check.pojo.WorkClass;
import com.kai.check.service.IStudentService;
import com.kai.check.utils.RespBean;
import com.kai.check.utils.RespPageBean;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.security.Principal;
import java.time.LocalDateTime;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author kai
 * @since 2021-03-04
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    @Resource
    private StudentMapper studentMapper;
    @Resource
    private WorkClassMapper workClassMapper;
    @Resource
    private StuWorkMapper stuWorkMapper;
    @Resource
    private ClassTeaMapper classTeaMapper;

    @Override
    public RespPageBean listStudents(Integer currentPage, Integer size, Integer classId) {
        Page<Student> studentPage = new Page<>(currentPage, size);
        IPage<Student> res = studentMapper.listStudents(studentPage,classId);
        return new RespPageBean(res.getTotal(), res.getRecords());
    }

    @Override
    public Student getInfo(String name) {
        return studentMapper.selectOne(new QueryWrapper<Student>().eq("stu_id", name));
    }

    @Override
    @Transactional
    public RespBean commitWork(Integer workId, MultipartFile workFile, String name) {
        WorkClass workClass = workClassMapper.selectOne(new QueryWrapper<WorkClass>().eq("id", workId));
        LocalDateTime endTime = workClass.getEndTime();
        // 得到存放作业的目录
        String workDir = workClass.getWorkDir();
        // 得到作业的类型(cpp,java,python)
        String originalFilename = workFile.getOriginalFilename();
        String ext = FilenameUtils.getExtension(originalFilename);
        // 设置新的名字
        Student student = studentMapper.selectOne(new QueryWrapper<Student>().eq("stu_id", name));
        String stuName = student.getStuName();
        Integer stuClassId = student.getStuClassId();
        ClassTea classTea = classTeaMapper.selectOne(new QueryWrapper<ClassTea>().eq("id", stuClassId));
        String className = classTea.getClassName();
        String newName = className + "-" + stuName + "-" + name + "." + ext;
        // 文件上传
        BufferedOutputStream bufferedOutputStream = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            File resourcePath = new File(workDir, newName);
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(resourcePath, true));
            bufferedInputStream = new BufferedInputStream(workFile.getInputStream());
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = bufferedInputStream.read(bytes)) != -1) {
                bufferedOutputStream.write(bytes, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedInputStream.close();
                bufferedOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 将文件信息存入数据库
        StuWork stuWork = stuWorkMapper.selectOne(new QueryWrapper<StuWork>().eq("work_id", workId).eq("stu_id", name));
        stuWork.setWorkName(newName).setWorkUrl(workDir + "/" + newName).setWorkExt(ext).setIsCommit(1);
        if(LocalDateTime.now().isAfter(endTime)){
            // 超时提交
            stuWork.setIsCommit(2);
        }
        if (stuWorkMapper.updateById(stuWork) == 1) {
            return RespBean.success();
        }
        return RespBean.error();
    }
}
