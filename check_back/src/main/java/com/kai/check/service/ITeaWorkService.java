package com.kai.check.service;

import com.kai.check.pojo.TeaWork;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kai.check.utils.RespBean;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kai
 * @since 2021-03-07
 */
public interface ITeaWorkService extends IService<TeaWork> {


    RespBean createWorkTitle(String workTitle, String name);

    List<TeaWork> listWorkTitles(String name);

}
