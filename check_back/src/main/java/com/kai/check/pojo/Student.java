package com.kai.check.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @since 2021-03-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_student")
@ApiModel(value="Student对象", description="")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "学生id")
    @TableId(value = "stu_id")
    private String stuId;

    @ApiModelProperty(value = "学生姓名")
    private String stuName;

    @ApiModelProperty(value = "学生手机")
    private String stuPhone;

    @ApiModelProperty(value = "学生qq")
    private String stuQq;

    @ApiModelProperty(value = "学生邮箱")
    private String stuMail;

    @ApiModelProperty(value = "分组")
    private Integer isGroup;

    @ApiModelProperty(value = "班级")
    private Integer stuClassId;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @TableField(fill=FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "班级")
    @TableField(exist = false)
    private ClassTea classTea;


}
