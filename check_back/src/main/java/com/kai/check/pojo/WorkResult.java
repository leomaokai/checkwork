package com.kai.check.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

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
@TableName("t_work_result")
@ApiModel(value="WorkResult对象", description="")
public class WorkResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "作业id")
    private Integer workId;

    @ApiModelProperty(value = "作业1id")
    private Integer workFirstId;

    @ApiModelProperty(value = "作业2id")
    private Integer workSecondId;

    @ApiModelProperty(value = "重复率")
    private String workResult;

    @ApiModelProperty(value = "作业")
    @TableField(exist = false)
    private WorkClass workClass;

    @ApiModelProperty(value = "学生作业1")
    @TableField(exist = false)
    private StuWork stuWorkFirst;

    @ApiModelProperty(value = "学生作业2")
    @TableField(exist = false)
    private StuWork stuWorkSecond;
}
