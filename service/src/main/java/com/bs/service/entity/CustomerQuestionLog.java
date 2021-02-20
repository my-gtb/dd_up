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
 * @since 2021-02-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bs_customer_question_log")
@ApiModel(value="BsCustomerQuestionLog对象", description="")
public class CustomerQuestionLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String code;

    private Integer customerId;

    private Integer questionId;

    private String optionIds;

    private Boolean isOk;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Long createdTime;
}
