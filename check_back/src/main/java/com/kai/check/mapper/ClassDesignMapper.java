package com.kai.check.mapper;

import com.kai.check.pojo.ClassDesign;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kai.check.pojo.TeaDesign;
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
public interface ClassDesignMapper extends BaseMapper<ClassDesign> {


    List<ClassDesign> listClassDesigns(@Param("stuId") String name);

    List<ClassDesign> listClassToDesign(@Param("teaId") String name);
}
