package com.kai.check.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kai.check.pojo.User;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kai
 * @since 2021-03-03
 */
public interface UserMapper extends BaseMapper<User> {

    IPage<User> getStudentByPage(Page<User> page,@Param("id") String studentId);

    IPage<User> getTeacherByPage(Page<User> page, @Param("id") String teacherId);
}
