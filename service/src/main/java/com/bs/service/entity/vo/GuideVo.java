package com.bs.service.entity.vo;

import lombok.Data;

@Data
public class GuideVo {
    private Integer id;

    private String title;

    private String content;

    private String createdAt;

    private String updatedAt;
}
