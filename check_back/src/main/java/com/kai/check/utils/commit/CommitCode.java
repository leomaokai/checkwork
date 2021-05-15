package com.kai.check.utils.commit;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kai.check.pojo.*;
import com.kai.check.service.*;
import com.kai.check.utils.CheckCode;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

//@Service
public class CommitCode implements ICommit {

    @Resource
    private IStuWorkService stuWorkService;
    @Resource
    private IWorkResultService workResultService;
    @Resource
    private ITeaWorkService teaWorkService;
    @Resource
    private IStudentService studentService;
    @Resource
    private IClassTeaService classTeaService;
    @Resource
    private IClassWorkService classWorkService;
    @Value("${kai.result}")
    private String result;
    @Value("${kai.resultMax}")
    private Integer resultMax;


    @Override
    @Transactional
    public boolean commit(Integer id, MultipartFile file, String name) {
        StuWork stuWork = stuWorkService.getById(id);
        if (stuWork != null) {
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
            ClassTea classTea = classTeaService.getById(student.getStuClassId());
            // 班级名
            String className = classTea.getClassName();
            // 教师id
            String teaId = classTea.getTeaId();
            // 截止日期
            ClassWork classWork = classWorkService.getOne(new QueryWrapper<ClassWork>().eq("class_id", classTea.getId()).eq("work_id", workId));
            LocalDateTime endTime = classWork.getEndTime();
            // 作业类型
            String originalFilename = file.getOriginalFilename();
            String ext = FilenameUtils.getExtension(originalFilename);
            if (ext == null || (!ext.equals("java") && !ext.equals("c") && !ext.equals("cpp") && !ext.equals("py"))) {
                return false;
            }
            // 设置新的作业名
            StringBuilder newFileName = new StringBuilder(50);
            newFileName.append(className).append("-").append(name).append("_").append(stuName).append(".").append(ext);
            workUrl.append(newFileName);
            if (CommitUtils.commitWorkToFile(file, workUrl.toString())) {
                return false;
            }
            String resourcePath = workDir + "/code";
            synchronized (CommitCode.class) {
                List<StuWork> stuWorks = stuWorkService.list(new QueryWrapper<StuWork>().eq("work_id", workId));
                if (stuWorks == null || stuWorks.isEmpty()) {
                    return false;
                }
                int ret = checkStudentWorkCode(stuWorks, id, newFileName.toString(), ext, workId, result + "/" + teaId + "/" + workTitle, resourcePath);

                if (ret == 1) {
                    // 代码中含有中文
                    return false;
                } else if (ret == 2) {
                    // 重复率过高
                    stuWork.setIsChecked(1);
                    stuWorkService.updateById(stuWork);
                    workResultService.remove(new QueryWrapper<WorkResult>().eq("work_first_id", id));
                    return false;
                }
                stuWork.setWorkName(newFileName.toString()).setWorkExt(ext).setIsCommit("未提交PDF").setIsChecked(0).setWorkUrl(workUrl.toString());
                if (LocalDateTime.now().isAfter(endTime)) {
                    stuWork.setIsCommit("超时提交");
                }
                return stuWorkService.updateById(stuWork);
            }
        }
        return false;
    }


    private Integer checkStudentWorkCode(List<StuWork> stuWorks, Integer currentStuWorkId, String newFileName, String ext, Integer workId, String resultPath, String resourcePath) {
        for (StuWork stuWork : stuWorks) {
            if (!(stuWork.getIsCommit().equals("未提交")) && stuWork.getWorkExt().equals(ext)) {
                WorkResult workResult = new WorkResult();
                workResult.setWorkFirstId(currentStuWorkId).setWorkSecondId(stuWork.getId()).setWorkId(workId);
                String checkStr = CheckCode.check(resourcePath, resultPath, newFileName, stuWork.getWorkName(), ext);
                // 代码中含有中文
                if (checkStr.equals("-1")) {
                    this.deleteFile(resourcePath, newFileName);
                    return 1;
                }
                float checkFloat = Float.parseFloat(checkStr);
                // 查重结果过大
                if (checkFloat >= resultMax) {
                    this.deleteFile(resourcePath, newFileName);
                    return 2;
                }
                workResult.setWorkResult(checkStr);
                workResultService.save(workResult);
            }
        }
        return 1;
    }

    private void deleteFile(String resourcePath, String fileName) {
        File file = new File(resourcePath, fileName);
        if (file.exists()) {
            file.delete();
        }
    }
}
