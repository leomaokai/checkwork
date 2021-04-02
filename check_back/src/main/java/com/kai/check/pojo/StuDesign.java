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
@TableName("t_stu_design")
@ApiModel(value="StuDesign对象", description="")
public class StuDesign implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "组id")
    private Integer groupId;

    @ApiModelProperty(value = "设计id")
    private Integer designId;

    @ApiModelProperty(value = "源代码作业名")
    private String codeName;

    @ApiModelProperty(value = "源代码保存路径")
    private String codePath;

    @ApiModelProperty(value = "源代码类型")
    private String codeExt;

    @ApiModelProperty(value = "pdf名")
    private String pdfName;

    @ApiModelProperty(value = "pdf保存路径")
    private String pdfPath;

    @ApiModelProperty(value = "未提交,已提交,超时提交")
    private String isCommit;

    @ApiModelProperty(value = "0正常,1查重过高")
    private Integer isChecked;

    @ApiModelProperty(value = "得分")
    private Integer teaScore;

    @ApiModelProperty(value = "点评")
    private String teaOpinion;

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
