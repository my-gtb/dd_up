package com.bs.service.entity.vo;

import lombok.Data;

@Data
public class FeedbackVo {
    private String name;
    private String telephone;
    private String content;
    private Long createdTime;
    private String createdAt;
}
