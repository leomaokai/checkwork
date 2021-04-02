package com.kai.check.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
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
@TableName("t_design_result")
@ApiModel(value="DesignResult对象", description="")
public class DesignResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "设计id")
    private Integer designId;

    @ApiModelProperty(value = "设计1id")
    private Integer designFirstId;

    @ApiModelProperty(value = "设计2id")
    private Integer designSecondId;

    @ApiModelProperty(value = "重复率")
    private String workResult;


}
