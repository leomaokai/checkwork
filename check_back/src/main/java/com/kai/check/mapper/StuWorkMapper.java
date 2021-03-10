package com.kai.check.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    IPage<StuWork> getClassStudentWorks(Page<StuWork> stuWorkPage,@Param("classId") Integer classId,@Param("workId") Integer workId);
}
