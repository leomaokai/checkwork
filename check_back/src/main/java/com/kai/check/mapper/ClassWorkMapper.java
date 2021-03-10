package com.kai.check.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kai.check.pojo.ClassWork;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kai
 * @since 2021-03-09
 */
public interface ClassWorkMapper extends BaseMapper<ClassWork> {

    IPage<ClassWork> getAllWorks(Page<ClassWork> classWorkPage, @Param("teaId") String name);
}
