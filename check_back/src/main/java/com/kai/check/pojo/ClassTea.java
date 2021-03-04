package com.kai.check.pojo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author kai
 * @since 2021-03-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_class_tea")
@ApiModel(value="ClassTea对象", description="")
public class ClassTea implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "班级id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "班级名")
    private String className;

    @ApiModelProperty(value = "老师id")
    private String teaId;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill=FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "班级老师")
    @TableField(exist = false)
    private Teacher teacher;

}
