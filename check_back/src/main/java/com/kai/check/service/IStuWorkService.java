package com.kai.check.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kai.check.pojo.StuWork;
import com.kai.check.utils.RespBean;
import com.kai.check.utils.RespPageBean;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kai
 * @since 2021-03-03
 */
public interface IStuWorkService extends IService<StuWork> {

    RespBean deleteWork(Integer stuWorkId);

    RespPageBean getClassStudentWorks(Integer currentPage, Integer size, Integer classId, Integer workId);

    RespPageBean selectStuWorks(Integer currentPage, Integer size, String name);

    RespBean commitWork(Integer stuWorkId, MultipartFile workFile, String name,boolean flag);

    RespBean deleteStudentWork(Integer stuWorkId);

    void downloadWork(Integer stuWorkId, Integer flag, HttpServletResponse response);

    RespBean scoreWork(Integer stuWorkId, Integer score);
}
