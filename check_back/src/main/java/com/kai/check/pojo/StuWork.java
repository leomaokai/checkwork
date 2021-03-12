package com.kai.check.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@TableName("t_stu_work")
@ApiModel(value="StuWork对象", description="")
@Accessors(chain = true)
public class StuWork implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "作业id")
    private Integer workId;

    @ApiModelProperty(value = "班级id")
    private Integer classId;

    @ApiModelProperty(value = "作业名")
    private String workName;

    @ApiModelProperty(value = "学生id")
    private String stuId;

    @ApiModelProperty(value = "作业url")
    private String workUrl;

    @ApiModelProperty(value = "作业ext")
    private String workExt;

    @ApiModelProperty(value = "PDF作业名")
    private String pdfName;

    @ApiModelProperty(value = "PDFpath")
    private String pdfPath;

    @ApiModelProperty(value = "未提交,已提交,超时提交")
    private String isCommit;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @TableField(fill=FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

//    @ApiModelProperty(value = "作业详情")
//    @TableField(exist = false)
//    private WorkClass workClass;
//    @ApiModelProperty(value = "作业所属学生")
//    @TableField(exist = false)
//    private Student student;

    @ApiModelProperty(value = "作业标题")
    @TableField(exist = false)
    private String workTitle;

    @ApiModelProperty(value = "作业描述")
    @TableField(exist = false)
    private String workDescribe;

    @ApiModelProperty(value = "作业班级名称")
    @TableField(exist = false)
    private String className;

    @ApiModelProperty(value = "作业截止时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @TableField(exist = false)
    LocalDateTime workEndTime;

}
