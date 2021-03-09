package com.kai.check.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kai.check.mapper.ClassTeaMapper;
import com.kai.check.pojo.ClassTea;
import com.kai.check.pojo.WorkClass;
import com.kai.check.service.IClassTeaService;
import com.kai.check.utils.RespPageBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author kai
 * @since 2021-03-03
 */
@Service
public class ClassTeaServiceImpl extends ServiceImpl<ClassTeaMapper, ClassTea> implements IClassTeaService {

    @Resource
    private ClassTeaMapper classTeaMapper;

    @Override
    public RespPageBean listClasses(Integer currentPage, Integer size, String name) {
        Page<ClassTea> classTeaPage = new Page<>(currentPage, size);
        IPage<ClassTea> res = classTeaMapper.listClasses(classTeaPage, name);
        return new RespPageBean(res.getTotal(), res.getRecords());
    }

}
