package com.kai.check.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kai.check.mapper.WorkResultMapper;
import com.kai.check.pojo.WorkResult;
import com.kai.check.service.IWorkResultService;
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
public class WorkResultServiceImpl extends ServiceImpl<WorkResultMapper, WorkResult> implements IWorkResultService {

    @Resource
    private WorkResultMapper workResultMapper;

    @Override
    public RespPageBean listCheckResult(Integer currentPage, Integer size, Integer workId) {
        Page<WorkResult> workResultPage = new Page<>(currentPage, size);
        IPage<WorkResult> res = workResultMapper.listCheckResult(workResultPage, workId);
        return new RespPageBean(res.getTotal(),res.getRecords());
    }
}
