package com.kai.check.service;

import com.kai.check.pojo.StuGroup;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kai.check.utils.RespBean;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kai
 * @since 2021-03-20
 */
public interface IStuGroupService extends IService<StuGroup> {

    RespBean createGroup(String[] studentIds, String name, Integer designId);

    StuGroup selectGroupMembers(String name);

    List<StuGroup> listStudentsGroups(Integer classId);
}
