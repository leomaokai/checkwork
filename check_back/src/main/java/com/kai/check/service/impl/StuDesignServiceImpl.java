package com.kai.check.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kai.check.mapper.StuGroupMapper;
import com.kai.check.pojo.StuDesign;
import com.kai.check.mapper.StuDesignMapper;
import com.kai.check.pojo.StuGroup;
import com.kai.check.service.IStuDesignService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author kai
 * @since 2021-03-20
 */
@Service
public class StuDesignServiceImpl extends ServiceImpl<StuDesignMapper, StuDesign> implements IStuDesignService {

    @Resource
    private StuGroupMapper stuGroupMapper;
    @Resource
    private StuDesignMapper stuDesignMapper;

    @Override
    public List<StuDesign> listClassDesigns(Integer classId) {
        return stuDesignMapper.listClassDesigns(classId);
    }

    @Override
    public StuDesign getGroupDesignInfo(String name) {
        StuGroup group = stuGroupMapper.selectGroupByOneStudentId(name);
        if (group != null) {
            Integer id = group.getId();
            return stuDesignMapper.getGroupDesignInfo(id);

        }
        return null;
    }
}
