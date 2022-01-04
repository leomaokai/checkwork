package com.kai.check.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kai.check.mapper.*;
import com.kai.check.pojo.*;
import com.kai.check.service.IStuWorkService;
import com.kai.check.utils.*;
import com.kai.check.utils.commit.CommitUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author kai
 * @since 2021-03-03
 */
@Service
public class StuWorkServiceImpl extends ServiceImpl<StuWorkMapper, StuWork> implements IStuWorkService {

    @Resource
    private StuWorkMapper stuWorkMapper;
    @Resource
    private TeaWorkMapper teaWorkMapper;
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private ClassTeaMapper classTeaMapper;
    @Resource
    private ClassWorkMapper classWorkMapper;
    @Resource
    private WorkResultMapper workResultMapper;
    @Value("${kai.result}")
    private String result;
    @Value("${kai.resultMax}")
    private Integer resultMax;


    @Override
    @Transactional
    public RespBean deleteWork(Integer stuWorkId) {
        StuWork stuWork = stuWorkMapper.selectOne(new QueryWrapper<StuWork>().eq("id", stuWorkId));
        stuWork.setIsCommit("未提交");
        stuWork.setWorkName("未提交");
        stuWork.setPdfName("未提交");
        stuWork.setWorkExt("");
        stuWork.setIsChecked(0);
        if (stuWorkMapper.updateById(stuWork) == 1) {
            return RespBean.success();
        }
        return RespBean.error();
    }

    @Override
    public RespPageBean getClassStudentWorks(Integer currentPage, Integer size, Integer classId, Integer workId) {

        Page<StuWork> stuWorkPage = new Page<>(currentPage, size);
        IPage<StuWork> res = stuWorkMapper.getClassStudentWorks(stuWorkPage, classId, workId);
        return new RespPageBean(res.getTotal(), res.getRecords());
    }

    @Override
    public RespPageBean selectStuWorks(Integer currentPage, Integer size, String name) {
        Page<StuWork> stuWorkPage = new Page<>(currentPage, size);
        IPage<StuWork> res = stuWorkMapper.selectStuWorks(stuWorkPage, name);
        return new RespPageBean(res.getTotal(), res.getRecords());
    }

//    public RespBean stuCommitCode(Integer workId,MultipartFile workCode,String name){
//        ICommit commit = CommitFactory.getCommit(1);
//        int commit1 = commit.commit(workId,workCode,name);
//        if(commit1==1){
//
//        }
//        return null;
//    }
//
//    public RespBean stuCommitPdf(Integer workId,MultipartFile workPdf,String name){
//        ICommit commit = CommitFactory.getCommit(2);
//        int commit1 = commit.commit(workId, workPdf, name);
//        if(commit1==1){
//
//        }
//
//        return null;
//    }
//
//    public RespBean stuCommitDesignCode(Integer designId,MultipartFile designCode,String name){
//        ICommit commit = CommitFactory.getCommit(3);
//        int commit1 = commit.commit(designId, designCode, name);
//
//        return null;
//    }
//
//    public RespBean stuCommitDesignPdf(Integer designId,MultipartFile designPdf,String name){
//        ICommit commit = CommitFactory.getCommit(4);
//        int commit1 = commit.commit(designId, designPdf, name);
//        return null;
//    }


    @Override
    @Transactional
    public RespBean commitWork(Integer stuWorkId, MultipartFile workFile, String name, boolean flag) {
        StuWork stuWork = stuWorkMapper.selectById(stuWorkId);
        if (stuWork != null) {
            Integer workId = stuWork.getWorkId();
            TeaWork teaWork = teaWorkMapper.selectById(workId);
            // 得到作业目录
            String workDir = teaWork.getWorkDir();
            // 作业标题
            String workTitle = teaWork.getWorkTitle();
            // 学生作业存放路径
            StringBuilder workUrl = new StringBuilder(100);
            String dir = "";
            if (flag) {
                dir = "pdf";
            } else {
                dir = "code";
            }
            workUrl.append(workDir).append("/").append(dir).append("/");
            // 得到学生名
            Student student = studentMapper.selectById(name);
            String stuName = student.getStuName();
            ClassTea classTea = classTeaMapper.selectById(student.getStuClassId());
            // 班级名
            String className = classTea.getClassName();
            // 教师id
            String teaId = classTea.getTeaId();
            // 得到截止时间
            ClassWork classWork = classWorkMapper.selectOne(new QueryWrapper<ClassWork>().eq("class_id", classTea.getId()).eq("work_id", workId));
            LocalDateTime endTime = classWork.getEndTime();
            // 得到作业类型
            String originalFilename = workFile.getOriginalFilename();
            String ext = FilenameUtils.getExtension(originalFilename);
            // 设置新的作业名
            StringBuilder newFilename = new StringBuilder(50);
            newFilename.append(className).append("-").append(name).append("-").append(stuName).append(".").append(ext);
            if (ext == null || ext.isEmpty()) {
                return RespBean.error(RespBeanEnum.COMMIT_ERROR);
            }
            if (flag) {
                if (!ext.equals("pdf")) {
                    return RespBean.error(RespBeanEnum.COMMIT_ERROR);
                }
            } else if (!ext.equals("java") && !ext.equals("c") && !ext.equals("cpp") && !ext.equals("py")) {
                return RespBean.error(RespBeanEnum.COMMIT_ERROR);
            }
            workUrl.append(newFilename);
            String workPath = workUrl.toString();

            synchronized (CommitUtils.class) {
                if (CommitUtils.commitWorkToFile(workFile, workPath)) {

                    if (!flag) {
                        List<StuWork> stuWorks = stuWorkMapper.selectList(new QueryWrapper<StuWork>().eq("work_id", workId));
                        if (stuWorks != null || !stuWorks.isEmpty()) {

                            int maxResult = 0;
                            StringBuilder resultPath = new StringBuilder(50);
                            resultPath.append(result).append("/").append(teaId).append("/").append(workTitle);
                            String resourcePath = workDir + "/code";
                            Integer currentStuWorkId = stuWork.getId();

                            // 先自己查自己,排除中文
                            if (CheckCode.checkSelfFunction(ext, newFilename, resultPath, resourcePath))
                                return RespBean.error(RespBeanEnum.COMMIT_ERROR);

                            for (StuWork work : stuWorks) {

                                if (!(work.getIsCommit().equals("未提交")) &&
                                        work.getWorkExt() != null &&
                                        work.getWorkExt().equals(ext) &&
                                        work.getWorkUrl() != null &&
                                        !work.getWorkUrl().isEmpty()) {

                                    File file1 = new File(workPath);
                                    File file2 = new File(work.getWorkUrl());
                                    if (!file1.exists() || !file2.exists()) {
                                        continue;
                                    }

                                    // 交作业时查重并得到结果 重复率高直接返回失败 否则插入数据库
                                    // 查重并得到结果

                                    WorkResult workResult = new WorkResult();
                                    workResult.setWorkFirstId(currentStuWorkId).setWorkSecondId(work.getId()).setWorkId(workId);

                                    String checkStr = CheckCode.check(resourcePath, resultPath.toString(), newFilename.toString(), work.getWorkName(), ext);
                                    float check = Float.parseFloat(checkStr);
                                    // 源码中还有中文 上传失败 或 查重结果较大
                                    // 2021.11.14 不再限制查重结果,将最高结果保存
//                                    if (checkStr.equals("-1") || check >= resultMax) {
                                    if (checkStr.equals("-1")) {

                                        File file = new File(resourcePath, newFilename.toString());
                                        if (file.exists()) {
                                            file.delete();
                                        }
//                                        if (check >= resultMax) {
//                                            stuWork.setIsChecked(1);
//                                            stuWorkMapper.updateById(stuWork);
//                                        }
//                                        workResultMapper.deleteStuWorkId(currentStuWorkId);
                                        return RespBean.error(RespBeanEnum.COMMIT_ERROR);
                                    }

                                    // 得到最高查重率
                                    maxResult = Math.max(maxResult, (int) check);

                                    workResult.setWorkResult(checkStr);
                                    workResultMapper.insert(workResult);
                                }
                            }

                            stuWork.setIsChecked(maxResult);
                            stuWorkMapper.updateById(stuWork);
                        }
                    }

                    // 相同扩展名的作业在作业结果表中生成数据
                    if (flag) {
                        stuWork.setPdfName(newFilename.toString()).setPdfPath(workPath).setIsCommit("按时提交");
                    } else {
                        stuWork.setWorkName(newFilename.toString()).setWorkUrl(workPath).setWorkExt(ext).setIsCommit("未提交PDF");
                    }
                    if (LocalDateTime.now().isAfter(endTime)) {
                        stuWork.setIsCommit("超时提交");
                    }
                    if (stuWorkMapper.updateById(stuWork) == 1) {
                        return RespBean.success(RespBeanEnum.COMMIT_SUCCESS);
                    }
                }
            }
        }
        return RespBean.error(RespBeanEnum.COMMIT_ERROR);
    }

    @Override
    @Transactional
    public RespBean deleteStudentWork(Integer stuWorkId) {
        StuWork stuWork = stuWorkMapper.selectById(stuWorkId);
        if (stuWork == null) {
            return RespBean.error(RespBeanEnum.DELETE_ERROR);
        }
        String workUrl = stuWork.getWorkUrl();
        CheckCode.deleteWorkFileByPath(workUrl);
        String pdfPath = stuWork.getPdfPath();
        CheckCode.deleteWorkFileByPath(pdfPath);
        stuWork.setWorkName("未提交").setWorkUrl("").setPdfName("未提交").setPdfPath("").setIsCommit("未提交").setWorkExt("");
        if (stuWorkMapper.updateById(stuWork) == 1) {
            workResultMapper.deleteStuWorkId(stuWorkId);
            return RespBean.success(RespBeanEnum.DELETE_SUCCESS);
        }

        return RespBean.error(RespBeanEnum.DELETE_ERROR);
    }

    @Override
    public void downloadWork(Integer stuWorkId, Integer flag, HttpServletResponse response) {
        String url = "";
        String fileName = "";
        response.setHeader("content-type", "application/octet-stream");
        StuWork stuWork = stuWorkMapper.selectById(stuWorkId);
        if (flag == 1) {
            url = stuWork.getWorkUrl();
            fileName = stuWork.getWorkName();
        } else if (flag == 2) {
            url = stuWork.getPdfPath();
            fileName = stuWork.getPdfName();
        }
        DownFileUtil.downFile(url, fileName, response);
    }

    @Override
    public RespBean scoreWork(Integer stuWorkId, Integer score) {
        if (stuWorkMapper.scoreWork(stuWorkId, score)) {
            return RespBean.success(RespBeanEnum.SCORE_SUCCESS);
        }
        return RespBean.error(RespBeanEnum.SCORE_ERROR);
    }
}
