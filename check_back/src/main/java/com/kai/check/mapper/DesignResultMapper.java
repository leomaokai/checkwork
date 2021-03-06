package com.kai.check.mapper;

import com.kai.check.pojo.DesignResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kai
 * @since 2021-03-20
 */
public interface DesignResultMapper extends BaseMapper<DesignResult> {

    void deleteGroupDesignId(@Param("groupDesignId")Integer groupDesignId);
}
