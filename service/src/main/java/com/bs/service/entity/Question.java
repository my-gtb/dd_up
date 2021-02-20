package com.bs.service.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author dd_up
 * @since 2020-12-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bs_question")
@ApiModel(value="BsQuestion对象", description="")
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type= IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "题目")
    private String text;

    @ApiModelProperty(value = "题目类型")
    private Integer typeId;

    @ApiModelProperty(value = "题目组")
    private Integer groupId;

    @ApiModelProperty(value = "解析")
    private String parse;

    @ApiModelProperty(value = "排序")
    private Integer top;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Long createdTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updatedTime;

}
