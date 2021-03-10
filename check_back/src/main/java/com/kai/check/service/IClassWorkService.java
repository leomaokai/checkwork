package com.kai.check.service;

import com.kai.check.pojo.ClassWork;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kai.check.utils.RespBean;
import com.kai.check.utils.RespPageBean;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kai
 * @since 2021-03-09
 */
public interface IClassWorkService extends IService<ClassWork> {

    RespPageBean getAllWorks(Integer currentPage, Integer size, String name);

    RespBean updateWorkEnd(Integer classWorkId, LocalDateTime newEndTime);
}
