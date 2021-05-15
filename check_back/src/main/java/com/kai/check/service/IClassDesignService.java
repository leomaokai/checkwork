package com.kai.check.service;

import com.kai.check.pojo.ClassDesign;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kai
 * @since 2021-03-20
 */
public interface IClassDesignService extends IService<ClassDesign> {

    List<ClassDesign> listClassToDesign(String name);

    List<String> listClassOfDesigns(Integer classId);

}
