package com.kai.check.service;

import com.kai.check.pojo.TeaDesign;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kai.check.utils.RespBean;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kai
 * @since 2021-03-20
 */
public interface ITeaDesignService extends IService<TeaDesign> {

    RespBean createDesign(String designTitle, MultipartFile designPdf, String name);

    List<TeaDesign> listDesignIds(String name);

    void downDesignPdf(Integer designId, HttpServletResponse response);

    RespBean deleteDesignByDesignId(Integer designId);
}
