package com.kai.check.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kai.check.pojo.ClassTea;
import com.kai.check.utils.RespPageBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kai
 * @since 2021-03-03
 */
public interface IClassTeaService extends IService<ClassTea> {

    RespPageBean listClasses(Integer currentPage, Integer size, String name);

}
