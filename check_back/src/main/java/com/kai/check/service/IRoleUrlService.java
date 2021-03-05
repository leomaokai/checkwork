package com.kai.check.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kai.check.pojo.RoleUrl;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kai
 * @since 2021-03-03
 */
public interface IRoleUrlService extends IService<RoleUrl> {

    RoleUrl getUrlWithRole();
}
