package com.kai.check.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author kai
 * @since 2021-03-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_menus")
@ApiModel(value="Menus对象", description="")
public class Menus implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "菜单id")
    @TableId(value = "menu_id", type = IdType.AUTO)
    private Integer menuId;

    @ApiModelProperty(value = "角色id")
    private Integer menuRid;

    @ApiModelProperty(value = "菜单url")
    private String menuUrl;

    @ApiModelProperty(value = "菜单路径")
    private String menuPath;

    @ApiModelProperty(value = "菜单名字")
    private String menuName;

    @ApiModelProperty(value = "菜单组件")
    private String menuComponent;

    @ApiModelProperty(value = "父id")
    private Integer menuPid;

    @ApiModelProperty(value = "子菜单")
    @TableField(exist = false)//表中无children字段
    private List<Menus> children;


}
