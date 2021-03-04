package com.kai.check.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kai.check.pojo.StuWork;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kai
 * @since 2021-03-03
 */
public interface StuWorkMapper extends BaseMapper<StuWork> {

    void deleteByStuId(@Param("stuId") String stuId);
}
