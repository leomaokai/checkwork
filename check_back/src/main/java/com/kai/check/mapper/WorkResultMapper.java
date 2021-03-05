package com.kai.check.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kai.check.pojo.WorkResult;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kai
 * @since 2021-03-03
 */
public interface WorkResultMapper extends BaseMapper<WorkResult> {

    void deleteByWorkId(@Param("workId") Integer workId);

    IPage<WorkResult> listCheckResult(Page<WorkResult> workResultPage,@Param("id") Integer workId);
}
