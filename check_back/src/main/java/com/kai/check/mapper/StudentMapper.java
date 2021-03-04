package com.kai.check.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kai.check.pojo.Student;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kai
 * @since 2021-03-04
 */
public interface StudentMapper extends BaseMapper<Student> {

    IPage<Student> listStudents(Page<Student> studentPage,@Param("id") String studentId,@Param("name") String studentName);

}
