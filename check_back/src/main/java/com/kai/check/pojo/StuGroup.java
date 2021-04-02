package com.kai.check.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @since 2021-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_stu_group")
@ApiModel(value="StuGroup对象", description="")
public class StuGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "设计id")
    private Integer designId;

    @ApiModelProperty(value = "班级id")
    private Integer classId;

    @ApiModelProperty(value = "组员1")
    private String stuId1;

    @ApiModelProperty(value = "组员2")
    private String stuId2;

    @ApiModelProperty(value = "组员3")
    private String stuId3;

    @ApiModelProperty(value = "组员4")
    private String stuId4;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @TableField(fill=FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "设计标题")
    @TableField(exist = false)
    private String designTitle;

}
