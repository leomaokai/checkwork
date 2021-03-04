package com.kai.check.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kai.check.pojo.StuWork;

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

    List<StuWork> listWorks(String name);
}
