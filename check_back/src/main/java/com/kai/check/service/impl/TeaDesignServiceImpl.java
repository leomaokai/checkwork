package com.kai.check.service.impl;

import com.kai.check.pojo.TeaDesign;
import com.kai.check.mapper.TeaDesignMapper;
import com.kai.check.service.ITeaDesignService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kai.check.utils.DownFileUtil;
import com.kai.check.utils.RespBean;
import com.kai.check.utils.RespBeanEnum;
import com.kai.check.utils.commit.CommitUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
public class TeaDesignServiceImpl extends ServiceImpl<TeaDesignMapper, TeaDesign> implements ITeaDesignService {

    @Resource
    private TeaDesignMapper teaDesignMapper;
    @Value("${kai.design}")
    private String designDir;
    @Value("${kai.designResource}")
    private String designResource;

    @Override
    @Transactional
    public RespBean createDesign(String designTitle, MultipartFile designPdf, String name) {
        String teaId = teaDesignMapper.selectByDesignTitle(designTitle);
        System.out.println(teaId);
        if (teaId != null && teaId.equals(name)) {
            return RespBean.error(RespBeanEnum.INSERT_ERROR);
        }
        TeaDesign teaDesign = new TeaDesign();
        teaDesign.setDesignTitle(designTitle);
        teaDesign.setTeaId(name);
        // pdf 目录
        String designDirPdf = designDir + "/" + name;
        File file1 = new File(designDirPdf);
        if (!file1.exists()) {
            file1.mkdirs();
        }
        String originalFilename = designPdf.getOriginalFilename();
        String pdf = FilenameUtils.getExtension(originalFilename);
        if (!pdf.equals("pdf")) {
            return RespBean.error(RespBeanEnum.INSERT_ERROR);
        }
        String url = designDirPdf + "/" + designTitle + "." + pdf;
        teaDesign.setDesignUrl(url);
        // 提交目录
        String designResourceDir = designResource + "/" + name + "/" + designTitle;
        File file = new File(designResourceDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        teaDesign.setDesignDir(designResourceDir);
        teaDesignMapper.insert(teaDesign);
        if (CommitUtils.commitWorkToFile(designPdf, url)) {
            return RespBean.error(RespBeanEnum.INSERT_ERROR);
        }
        return RespBean.success(RespBeanEnum.INSERT_SUCCESS);
    }

    @Override
    public List<TeaDesign> listDesignIds(String name) {
        return teaDesignMapper.listDesignIds(name);
    }

    @Override
    public void downDesignPdf(Integer designId, HttpServletResponse response) {
        response.setHeader("content-type", "application/octet-stream");
        TeaDesign teaDesign = teaDesignMapper.selectById(designId);
        String designUrl = teaDesign.getDesignUrl();
        String designTitle = teaDesign.getDesignTitle();
        String fileName = designTitle + ".pdf";
        DownFileUtil.downFile(designUrl, fileName, response);
    }
}
