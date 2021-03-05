package com.kai.check.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kai.check.mapper.RoleUrlMapper;
import com.kai.check.pojo.RoleUrl;
import com.kai.check.service.IRoleUrlService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kai
 * @since 2021-03-03
 */
@Service
public class RoleUrlServiceImpl extends ServiceImpl<RoleUrlMapper, RoleUrl> implements IRoleUrlService {

    @Resource
    private RoleUrlMapper roleUrlMapper;
    @Override
    public RoleUrl getUrlWithRole() {
        return null;
    }
}
