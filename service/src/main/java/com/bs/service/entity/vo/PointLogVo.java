package com.bs.service.entity.vo;

import lombok.Data;

@Data
public class PointLogVo {
    private String customerName;
    private String typeName;
    private Integer pointValue;
    private String intro;
    private Long createdTime;
    private String createdAt;
}
