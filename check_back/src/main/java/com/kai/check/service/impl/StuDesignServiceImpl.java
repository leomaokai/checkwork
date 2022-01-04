package com.kai.check.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kai.check.mapper.*;
import com.kai.check.pojo.*;
import com.kai.check.service.IStuDesignService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kai.check.utils.CheckCode;
import com.kai.check.utils.DownFileUtil;
import com.kai.check.utils.RespBean;
import com.kai.check.utils.RespBeanEnum;
import com.kai.check.utils.commit.CommitUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author kai
 * @since 2021-03-20
 */
@Service
public class StuDesignServiceImpl extends ServiceImpl<StuDesignMapper, StuDesign> implements IStuDesignService {

    @Resource
    private StuGroupMapper stuGroupMapper;
    @Resource
    private StuDesignMapper stuDesignMapper;
    @Resource
    private TeaDesignMapper teaDesignMapper;
    @Resource
    private ClassDesignMapper classDesignMapper;
    @Resource
    private ClassTeaMapper classTeaMapper;
    @Resource
    private DesignResultMapper designResultMapper;
    @Value("${kai.designResult}")
    private String designResult;
    @Value("${kai.resultMax}")
    private Integer resultMax;

    @Override
    public List<StuDesign> listClassDesigns(Integer classId) {
        return stuDesignMapper.listClassDesigns(classId);
    }

    @Override
    public StuDesign getGroupDesignInfo(String name) {
        StuGroup group = stuGroupMapper.selectGroupByOneStudentId(name);
        if (group != null) {
            Integer id = group.getId();
            return stuDesignMapper.getGroupDesignInfo(id);

        }
        return null;
    }

    @Override
    @Transactional
    public RespBean commitWork(Integer groupDesignId, MultipartFile workFile, String name, boolean flag) {
        StuDesign stuDesign = stuDesignMapper.selectById(groupDesignId);
        if (stuDesign != null) {
            Integer designId = stuDesign.getDesignId();
            TeaDesign teaDesign = teaDesignMapper.selectById(designId);
            String designDir = teaDesign.getDesignDir();
            String designTitle = teaDesign.getDesignTitle();
            StringBuilder designUrl = new StringBuilder(100);
            String dir;
            if (flag) {
                dir = "pdf";
            } else {
                dir = "code";
            }
            designUrl.append(designDir).append("/").append(dir).append("/");
            Integer groupId = stuDesign.getGroupId();
            StuGroup stuGroup = stuGroupMapper.selectById(stuDesign.getGroupId());

            ClassDesign classDesign = classDesignMapper.selectOne(new QueryWrapper<ClassDesign>().eq("class_id", stuGroup.getClassId()).eq("design_id", stuGroup.getDesignId()));
            LocalDateTime endTime = classDesign.getEndTime();
            ClassTea classTea = classTeaMapper.selectById(classDesign.getClassId());
            String className = classTea.getClassName();
            String teaId = classTea.getTeaId();
            String originalFilename = workFile.getOriginalFilename();
            String ext = FilenameUtils.getExtension(originalFilename);
            StringBuilder newFileName = new StringBuilder(50);
            newFileName.append(className).append("-").append(stuGroup.getStuId1()).append("-").append(designTitle).append(".").append(ext);
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
            designUrl.append(newFileName);
            String designPath = designUrl.toString();

            synchronized (CommitUtils.class) {
                if (CommitUtils.commitWorkToFile(workFile, designPath)) {

                    if (!flag) {
                        List<StuDesign> stuDesigns = stuDesignMapper.selectList(new QueryWrapper<StuDesign>().eq("design_id", designId));
                        if (stuDesigns != null && !stuDesigns.isEmpty()) {
                            int maxResult = 0;
                            StringBuilder resultPath = new StringBuilder(50);
                            resultPath.append(designResult).append("/").append(teaId).append("/").append(designTitle);

                            String resourcePath = designDir + "/code";
                            // 先自己查自己,排除中文
                            if (CheckCode.checkSelfFunction(ext, newFileName, resultPath, resourcePath))
                                return RespBean.error(RespBeanEnum.COMMIT_ERROR);

                            for (StuDesign design : stuDesigns) {
                                if (!(design.getIsCommit().equals("未提交")) &&
                                        design.getCodeExt() != null &&
                                        design.getCodeExt().equals(ext) &&
                                        design.getCodePath() != null &&
                                        !design.getCodePath().isEmpty()) {

                                    File file1 = new File(designPath);
                                    File file2 = new File(design.getCodePath());
                                    if (!file1.exists() || !file2.exists()) {
                                        continue;
                                    }

                                    Integer currentGroupDesignId = design.getId();
                                    DesignResult designResult = new DesignResult();
                                    designResult.setDesignFirstId(groupDesignId);
                                    designResult.setDesignSecondId(currentGroupDesignId);
                                    designResult.setDesignId(designId);

                                    String checkStr = CheckCode.check(resourcePath, resultPath.toString(), newFileName.toString(), design.getCodeName(), ext);

                                    float check = Float.parseFloat(checkStr);

                                    if (checkStr.equals("-1")) {
                                        File file = new File(resourcePath, newFileName.toString());
                                        if (file.exists()) {
                                            file.delete();
                                        }
//                                        if (check >= resultMax) {
//                                            stuDesign.setIsChecked(1);
//                                            stuDesignMapper.updateById(stuDesign);
//                                        }
//                                        designResultMapper.deleteGroupDesignId(groupDesignId);
                                        return RespBean.error(RespBeanEnum.COMMIT_ERROR);
                                    }

                                    maxResult = Math.max(maxResult, (int) check);
                                    designResult.setWorkResult(checkStr);
                                    designResultMapper.insert(designResult);
                                }
                            }
                            stuDesign.setIsChecked(maxResult);
                            stuDesignMapper.updateById(stuDesign);
                        }
                    }
                    if (flag) {
                        stuDesign.setPdfName(newFileName.toString()).setPdfPath(designPath).setIsCommit("按时提交");
                    } else {
                        stuDesign.setCodeName(newFileName.toString()).setCodePath(designPath).setCodeExt(ext).setIsCommit("未提交PDF");
                    }

                    if (LocalDateTime.now().isAfter(endTime)) {
                        stuDesign.setIsCommit("超时提交");
                    }
                    if (stuDesignMapper.updateById(stuDesign) == 1) {
                        return RespBean.success(RespBeanEnum.COMMIT_SUCCESS);
                    }
                }
            }
        }
        return RespBean.error(RespBeanEnum.COMMIT_ERROR);
    }


    @Override
    public void downloadDesign(Integer groupDesignId, Integer flag, HttpServletResponse response) {
        String url = "";
        String fileName = "";
        response.setHeader("content-type", "application/octet-stream");
        StuDesign stuDesign = stuDesignMapper.selectById(groupDesignId);
        if (flag == 1) {
            url = stuDesign.getCodePath();
            fileName = stuDesign.getCodeName();
        } else if (flag == 2) {
            url = stuDesign.getPdfPath();
            fileName = stuDesign.getPdfName();
        }
        DownFileUtil.downFile(url, fileName, response);
    }
}
