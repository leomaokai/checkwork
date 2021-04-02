package com.kai.check.service.impl;

import com.kai.check.pojo.ClassDesign;
import com.kai.check.mapper.ClassDesignMapper;
import com.kai.check.service.IClassDesignService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kai
 * @since 2021-03-20
 */
@Service
public class ClassDesignServiceImpl extends ServiceImpl<ClassDesignMapper, ClassDesign> implements IClassDesignService {

    @Resource
    private ClassDesignMapper classDesignMapper;
    @Override
    public List<ClassDesign> listClassToDesign(String name) {
        return classDesignMapper.listClassToDesign(name);
    }
}
