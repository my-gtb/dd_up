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
 * @since 2021-02-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bs_customer_wrong")
@ApiModel(value="BsCustomerWrong对象", description="")
public class CustomerWrong implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer customerId;

    private Integer groupId;

    private Integer questionId;

    @ApiModelProperty(value = "是否错题")
    private Boolean isWrong;

    @ApiModelProperty(value = "是否收藏")
    private Boolean isCollection;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Long createdTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updatedTime;

}
