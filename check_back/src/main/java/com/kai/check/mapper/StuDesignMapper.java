package com.kai.check.mapper;

import com.kai.check.pojo.StuDesign;
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
public interface StuDesignMapper extends BaseMapper<StuDesign> {

    List<StuDesign> listClassDesigns(@Param("classId") Integer classId);

    StuDesign getGroupDesignInfo(@Param("groupId") Integer id);
}
