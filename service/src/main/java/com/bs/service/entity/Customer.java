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
 * @since 2021-01-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bs_customer")
@ApiModel(value="BsCustomer对象", description="")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String openId;

    private String nickName;

    @ApiModelProperty(value = "值为 1 时是男性，值为 2 时是女性，值为 0 时是未知")
    private Integer gender;

    private String city;

    private String province;

    private String country;

    private String avatarUrl;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Long createdTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updatedTime;


}
