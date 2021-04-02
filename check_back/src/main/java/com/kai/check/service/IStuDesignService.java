package com.kai.check.service;

import com.kai.check.pojo.StuDesign;
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
public interface IStuDesignService extends IService<StuDesign> {

    List<StuDesign> listClassDesigns(Integer classId);

    StuDesign getGroupDesignInfo(String name);
}
