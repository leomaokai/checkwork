package com.kai.check.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kai.check.pojo.ClassTea;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kai
 * @since 2021-03-03
 */
public interface ClassTeaMapper extends BaseMapper<ClassTea> {

    IPage<ClassTea> listClasses(Page<ClassTea> classTeaPage,
                                @Param("username") String username);
}
