package com.kai.check.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kai.check.pojo.ClassWork;
import com.kai.check.mapper.ClassWorkMapper;
import com.kai.check.service.IClassWorkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kai.check.utils.RespBean;
import com.kai.check.utils.RespBeanEnum;
import com.kai.check.utils.RespPageBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author kai
 * @since 2021-03-09
 */
@Service
public class ClassWorkServiceImpl extends ServiceImpl<ClassWorkMapper, ClassWork> implements IClassWorkService {

    @Resource
    private ClassWorkMapper classWorkMapper;

    @Override
    public RespPageBean getAllWorks(Integer currentPage, Integer size, String name) {
        Page<ClassWork> classWorkPage = new Page<>(currentPage, size);
        IPage<ClassWork> res = classWorkMapper.getAllWorks(classWorkPage, name);
        return new RespPageBean(res.getTotal(), res.getRecords());
    }

    @Override
    public RespBean updateWorkEnd(Integer classWorkId, LocalDateTime newEndTime) {
        ClassWork classWork = classWorkMapper.selectById(classWorkId);
        if (classWork != null) {
            classWork.setEndTime(newEndTime);
            classWorkMapper.updateById(classWork);
            return RespBean.success(RespBeanEnum.UPDATE_SUCCESS);
        }
        return RespBean.error(RespBeanEnum.UPDATE_ERROR);
    }
}
