package com.kai.check.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kai.check.pojo.TeaWork;
import com.kai.check.mapper.TeaWorkMapper;
import com.kai.check.service.ITeaWorkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kai.check.utils.RespBean;
import com.kai.check.utils.RespBeanEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author kai
 * @since 2021-03-07
 */
@Service
public class TeaWorkServiceImpl extends ServiceImpl<TeaWorkMapper, TeaWork> implements ITeaWorkService {

    @Resource
    private TeaWorkMapper teaWorkMapper;
    @Value("${kai.resource}")
    private String resource;

    @Override
    public RespBean createWorkTitle(String workTitle, String name) {
        TeaWork teaWork = new TeaWork();
        teaWork.setTeaId(name);
        teaWork.setWorkTitle(workTitle);
        StringBuilder pathBuilder = new StringBuilder(50);
        pathBuilder.append(resource).append("/").append(name).append("/").append(workTitle);
        String path = pathBuilder.toString();
        File workDir1 = new File(path, "code");
        if (!workDir1.exists()) {
            workDir1.mkdirs();
        }
        File workDir2 = new File(path, "pdf");
        if (!workDir2.exists()) {
            workDir2.mkdirs();
        }
        teaWork.setWorkDir(path);
        if (teaWorkMapper.insert(teaWork) == 1) {
            return RespBean.success(RespBeanEnum.INSERT_SUCCESS);
        }
        return RespBean.error(RespBeanEnum.INSERT_ERROR);
    }

    @Override
    public List<TeaWork> listWorkTitles(String name) {
        List<TeaWork> teaWorks = teaWorkMapper.selectList(new QueryWrapper<TeaWork>().eq("tea_id", name));
        return teaWorks;
    }
}
