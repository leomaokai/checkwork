package com.kai.check.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;
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
@TableName("t_work_class")
@ApiModel(value = "WorkTea对象", description = "")
public class WorkClass implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "作业id")
    private Integer workId;

    @ApiModelProperty(value = "作业描述")
    private String workDescribe;

    @ApiModelProperty(value = "作业目录")
    private String workDir;

    @ApiModelProperty(value = "教师id")
    private String teaId;

    @ApiModelProperty(value = "班级id")
    private Integer classId;

    @ApiModelProperty(value = "截至时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "应提交人数")
    private Integer totalNumber;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


}
