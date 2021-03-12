package com.kai.check.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kai.check.mapper.*;
import com.kai.check.pojo.*;
import com.kai.check.service.IStuWorkService;
import com.kai.check.utils.CheckCode;
import com.kai.check.utils.RespBean;
import com.kai.check.utils.RespBeanEnum;
import com.kai.check.utils.RespPageBean;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
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
    private WorkClassMapper workClassMapper;
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
    public List<StuWork> listWorks(String name) {
        List<StuWork> stuWorks = stuWorkMapper.selectList(new QueryWrapper<StuWork>().eq("stu_id", name));
        for (StuWork stuWork : stuWorks) {
            Integer workId = stuWork.getWorkId();
            if (stuWork.getIsCommit().equals("未提交")) {
                stuWork.setWorkName("未提交");
                stuWork.setWorkUrl("null");
            }
            WorkClass workClass = workClassMapper.selectOne(new QueryWrapper<WorkClass>().eq("id", workId));
            //stuWork.setWorkClass(workClass);
        }
        return stuWorks;
    }

    @Override
    @Transactional
    public RespBean deleteWork(Integer stuWorkId) {
        StuWork stuWork = stuWorkMapper.selectOne(new QueryWrapper<StuWork>().eq("id", stuWorkId));
        stuWork.setIsCommit("未提交");
        if (stuWorkMapper.updateById(stuWork) == 1) {
            return RespBean.success();
        }
        return RespBean.error();
    }

    @Override
    public RespBean downWork(Integer stuWorkId, Integer isDown, HttpServletResponse response) {
        StuWork stuWork = stuWorkMapper.selectById(stuWorkId);
        if (stuWork.getIsCommit().equals("未提交")) {
            return RespBean.error(RespBeanEnum.COMMIT_NOT);
        }
        String workUrl = stuWork.getWorkUrl();
        String openStyle = "attachment";
        if (isDown == 0) {
            // isDown =0 在线打开
            openStyle = "inline";
        }
        try (
                FileInputStream inputStream = new FileInputStream(new File(workUrl));
                ServletOutputStream outputStream = response.getOutputStream();
        ) {
            response.setHeader("content-disposition", openStyle + ";fileName" + URLEncoder.encode(stuWork.getWorkName(), "UTF-8"));
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return RespBean.success();
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

    // 上传作业模块二合一
//    @Override
//    @Transactional
//    public RespBean uploadStuWork(Integer stuWorkId, MultipartFile workFile, String name) {
//        StuWork stuWork = stuWorkMapper.selectById(stuWorkId);
//        if (stuWork != null) {
//            Integer workId = stuWork.getWorkId();
//            TeaWork teaWork = teaWorkMapper.selectById(workId);
//            // 得到作业目录
//            String workDir = teaWork.getWorkDir();
//            StringBuilder workUrl = new StringBuilder(100);
//            workUrl.append(workDir).append("/code/");
//            // 设置作业新名字
//            Student student = studentMapper.selectById(name);
//            String stuName = student.getStuName();
//            ClassTea classTea = classTeaMapper.selectById(student.getStuClassId());
//            String className = classTea.getClassName();
//            // 得到截止时间
//            ClassWork classWork = classWorkMapper.selectOne(new QueryWrapper<ClassWork>().eq("class_id", classTea.getId()).eq("work_id", workId));
//            LocalDateTime endTime = classWork.getEndTime();
//            // 得到作业类型
//            String originalFilename = workFile.getOriginalFilename();
//            String ext = FilenameUtils.getExtension(originalFilename);
//            if (ext == null || ext.isEmpty() || (!ext.equals("java") && !ext.equals("c") && !ext.equals("cpp") && !ext.equals("py"))) {
//                return RespBean.error(RespBeanEnum.COMMIT_ERROR);
//            }
//            // 设置新的作业名
//            StringBuilder newFilename = new StringBuilder(50);
//            newFilename.append(className).append("-").append(name).append("-").append(stuName).append(".").append(ext);
//            workUrl.append(newFilename);
//            if (!this.commitWorkToFile(workFile, workUrl.toString())) {
//                return RespBean.error(RespBeanEnum.COMMIT_ERROR);
//            }
//            stuWork.setWorkName(newFilename.toString()).setWorkUrl(workUrl.toString()).setWorkExt(ext).setIsCommit("已提交");
//            if (LocalDateTime.now().isAfter(endTime)) {
//                stuWork.setIsCommit("超时提交");
//            }
//            if (stuWorkMapper.updateById(stuWork) == 1) {
//                return RespBean.success(RespBeanEnum.COMMIT_SUCCESS);
//            }
//        }
//
//        return RespBean.error(RespBeanEnum.COMMIT_ERROR);
//    }
//
//    @Override
//    @Transactional
//    public RespBean uploadStuWorkPDF(Integer stuWorkId, MultipartFile workFile, String name) {
//        StuWork stuWork = stuWorkMapper.selectById(stuWorkId);
//        if (stuWork != null) {
//            Integer workId = stuWork.getWorkId();
//            TeaWork teaWork = teaWorkMapper.selectById(workId);
//            // 得到作业目录
//            String workDir = teaWork.getWorkDir();
//            StringBuilder workUrl = new StringBuilder(100);
//            workUrl.append(workDir).append("/pdf/");
//            // 设置作业新名字
//            Student student = studentMapper.selectById(name);
//            String stuName = student.getStuName();
//            ClassTea classTea = classTeaMapper.selectById(student.getStuClassId());
//            String className = classTea.getClassName();
//            // 得到截止时间
//            ClassWork classWork = classWorkMapper.selectOne(new QueryWrapper<ClassWork>().eq("class_id", classTea.getId()).eq("work_id", workId));
//            LocalDateTime endTime = classWork.getEndTime();
//            // 得到作业类型
//            String originalFilename = workFile.getOriginalFilename();
//            String ext = FilenameUtils.getExtension(originalFilename);
//            if (ext == null || ext.isEmpty() || !ext.equals("pdf")) {
//                return RespBean.error(RespBeanEnum.COMMIT_ERROR);
//            }
//            // 设置新的作业名
//            StringBuilder newFilename = new StringBuilder(50);
//            newFilename.append(className).append("-").append(name).append("-").append(stuName).append(".").append(ext);
//            workUrl.append(newFilename);
//            if (!this.commitWorkToFile(workFile, workUrl.toString())) {
//                return RespBean.error(RespBeanEnum.COMMIT_ERROR);
//            }
//            stuWork.setWorkName(newFilename.toString()).setWorkUrl(workUrl.toString()).setWorkExt(ext).setIsCommit("已提交");
//            if (LocalDateTime.now().isAfter(endTime)) {
//                stuWork.setIsCommit("超时提交");
//            }
//            if (stuWorkMapper.updateById(stuWork) == 1) {
//                return RespBean.success(RespBeanEnum.COMMIT_SUCCESS);
//            }
//        }
//        return RespBean.error(RespBeanEnum.COMMIT_ERROR);
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
            if (!this.commitWorkToFile(workFile, workPath)) {
                return RespBean.error(RespBeanEnum.COMMIT_ERROR);
            }
            // 相同扩展名的作业在作业结果表中生成数据
            if (!flag) {
                synchronized (StuWorkServiceImpl.class) {
                    List<StuWork> stuWorks = stuWorkMapper.selectList(new QueryWrapper<StuWork>().eq("work_id", workId));
                    if (stuWorks != null || !stuWorks.isEmpty()) {

                        StringBuilder resultPath = new StringBuilder(50);
                        resultPath.append(result).append("/").append(teaId).append("/").append(workTitle);
                        for (StuWork work : stuWorks) {
                            if (!(work.getIsCommit().equals("未提交")) && work.getWorkExt().equals(ext)) {
                                // 交作业时查重并得到结果 重复率高直接返回失败 否则插入数据库
                                // 查重并得到结果
                                Integer currentStuWorkId=stuWork.getId();
                                WorkResult workResult = new WorkResult();
                                workResult.setWorkFirstId(currentStuWorkId).setWorkSecondId(work.getId()).setWorkId(workId);
                                String resourcePath = workDir + "/code";
                                String checkStr = CheckCode.check(resourcePath, resultPath.toString(), newFilename.toString(), work.getWorkName(), ext);
                                float check = Float.parseFloat(checkStr);
                                // 源码中还有中文 上传失败 或 查重结果较大
                                if (checkStr.equals("-1") || check >= resultMax) {
                                    File file = new File(resourcePath, newFilename.toString());
                                    if (file.exists()) {
                                        file.delete();
                                    }
                                    workResultMapper.deleteStuWorkId(currentStuWorkId);
                                    return RespBean.error(RespBeanEnum.COMMIT_ERROR);
                                }
                                workResult.setWorkResult(checkStr);
                                workResultMapper.insert(workResult);
                            }
                        }
                    }
                }
            }


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

    private boolean commitWorkToFile(MultipartFile workFile, String workPath) {
        File file = new File(workPath);
        if (file.exists()) {
            file.delete();
        }
        try (
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file, true));
                BufferedInputStream bufferedInputStream = new BufferedInputStream(workFile.getInputStream());
        ) {
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = bufferedInputStream.read(bytes)) != -1) {
                bufferedOutputStream.write(bytes, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (file.exists()) {
            return true;
        }
        return false;
    }
}
