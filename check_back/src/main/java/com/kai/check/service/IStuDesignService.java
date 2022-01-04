package com.kai.check.service;

import com.kai.check.pojo.StuDesign;
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
public interface IStuDesignService extends IService<StuDesign> {

    List<StuDesign> listClassDesigns(Integer classId);

    StuDesign getGroupDesignInfo(String name);

    RespBean commitWork(Integer groupDesignId, MultipartFile workFile, String name, boolean flag);

    void downloadDesign(Integer groupDesignId, Integer flag, HttpServletResponse response);
}
