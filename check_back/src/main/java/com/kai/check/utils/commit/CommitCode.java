package com.kai.check.utils.commit;


import com.kai.check.pojo.StuWork;
import com.kai.check.pojo.Student;
import com.kai.check.pojo.TeaWork;
import com.kai.check.service.IStuWorkService;
import com.kai.check.service.IStudentService;
import com.kai.check.service.ITeaWorkService;
import com.kai.check.service.IWorkResultService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

public class CommitCode implements ICommit{

    @Resource
    private IStuWorkService stuWorkService;
    @Resource
    private IWorkResultService workResultService;
    @Resource
    private ITeaWorkService teaWorkService;
    @Resource
    private IStudentService studentService;

    @Override
    @Transactional
    public int commit(Integer id, MultipartFile file, String name) {
        StuWork stuWork = stuWorkService.getById(id);
        if(stuWork!=null){
            Integer workId = stuWork.getWorkId();
            TeaWork teaWork = teaWorkService.getById(workId);
            // 得到作业目录
            String workDir = teaWork.getWorkDir();
            // 作业标题
            String workTitle = teaWork.getWorkTitle();
            // 学生存放作业路径
            StringBuilder workUrl = new StringBuilder(50);
            workUrl.append(workDir).append("/").append("code").append("/");
            // 得到学生名
            Student student = studentService.getById(name);
            String stuName = student.getStuName();


        }


        //
        String originalFilename = file.getOriginalFilename();
        String extension = FilenameUtils.getExtension(originalFilename);
        return 0;
    }
}
