package com.kai.check.mapper;

import com.kai.check.pojo.TeaDesign;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kai
 * @since 2021-03-20
 */
public interface TeaDesignMapper extends BaseMapper<TeaDesign> {


    List<TeaDesign> listDesignIds(@Param("teaId") String name);

    String selectByDesignTitle(@Param("designTitle") String designTitle);
}
