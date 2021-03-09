package com.kai.check.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill=FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "班级老师")
    @TableField(exist = false)
    private Teacher teacher;

    @ApiModelProperty(value = "学生")
    @TableField(exist = false)
    private List<Student> students;

    @ApiModelProperty(value = "班级学生总数")
    @TableField(exist = false)
    private Integer classStuNumber;

}
