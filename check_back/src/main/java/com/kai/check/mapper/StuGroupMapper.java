package com.kai.check.mapper;

import com.kai.check.pojo.StuGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kai
 * @since 2021-03-20
 */
public interface StuGroupMapper extends BaseMapper<StuGroup> {

    StuGroup selectGroupByOneStudentId(@Param("stuId") String name);

    List<StuGroup> listStudentsGroups(@Param("classId") Integer classId);
}
