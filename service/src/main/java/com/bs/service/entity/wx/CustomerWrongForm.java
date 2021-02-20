package com.bs.service.entity.wx;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CustomerWrongForm {
    private Integer customerId;

    private Integer groupId;

    private Boolean isWrong;

    private Boolean isCollection;

    private Long startTime;

    private Long endTime;
}
