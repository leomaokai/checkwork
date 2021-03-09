package com.kai.check.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kai.check.mapper.WorkClassMapper;
import com.kai.check.pojo.WorkClass;
import com.kai.check.service.IWorkClassService;
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
public class WorkClassServiceImpl extends ServiceImpl<WorkClassMapper, WorkClass> implements IWorkClassService {

    @Resource
    private WorkClassMapper workClassMapper;

    @Override
    public RespPageBean listWorks(Integer currentPage, Integer size, String workName, String username) {
        Page<WorkClass> workClassPage = new Page<>(currentPage, size);
        IPage<WorkClass> res = workClassMapper.listWorks(workClassPage, workName, username);
        return new RespPageBean(res.getTotal(), res.getRecords());
    }
}
