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
@TableName("bs_question_group")
@ApiModel(value="BsQuestionGroup对象", description="")
public class QuestionGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type= IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "问题组内容")
    private String text;

    @ApiModelProperty(value = "排序")
    private Integer top;

    @ApiModelProperty(value = "分类")
    private Integer typeId;

    @ApiModelProperty(value = "答题时间（分钟）")
    private Integer time;

    @ApiModelProperty(value = "花费积分值")
    private Integer costPoints;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Long createdTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updatedTime;
}
