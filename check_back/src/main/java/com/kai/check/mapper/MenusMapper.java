package com.kai.check.mapper;

import com.kai.check.pojo.Menus;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kai
 * @since 2021-03-07
 */
public interface MenusMapper extends BaseMapper<Menus> {

    List<Menus> getMenus(String name);
}
