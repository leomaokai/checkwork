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
 * @since 2021-03-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_tea_work")
@ApiModel(value="TeaWork对象", description="")
public class TeaWork implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "work_id")
    @TableId(value = "work_id", type = IdType.AUTO)
    private Integer workId;

    @ApiModelProperty(value = "教师id")
    private String teaId;

    @ApiModelProperty(value = "作业标题")
    private String workTitle;

    @ApiModelProperty(value = "作业描述")
    private String workDescribe;

    @ApiModelProperty(value = "作业路径")
    private String workDir;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;



}
