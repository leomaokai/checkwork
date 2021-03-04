package com.kai.check.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kai.check.mapper.StuWorkMapper;
import com.kai.check.mapper.WorkClassMapper;
import com.kai.check.pojo.StuWork;
import com.kai.check.pojo.WorkClass;
import com.kai.check.service.IStuWorkService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kai
 * @since 2021-03-03
 */
@Service
public class StuWorkServiceImpl extends ServiceImpl<StuWorkMapper, StuWork> implements IStuWorkService {

    @Resource
    private StuWorkMapper stuWorkMapper;
    @Resource
    private WorkClassMapper workClassMapper;

    @Override
    @Transactional
    public List<StuWork> listWorks(String name) {
        List<StuWork> stuWorks = stuWorkMapper.selectList(new QueryWrapper<StuWork>().eq("stu_id", name));
        for (StuWork stuWork : stuWorks) {
            Integer workId = stuWork.getWorkId();
            WorkClass workClass = workClassMapper.selectOne(new QueryWrapper<WorkClass>().eq("id", workId));
            stuWork.setWorkClass(workClass);
        }
        return stuWorks;
    }
}
