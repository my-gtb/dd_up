package com.bs.service.entity.vo;

import lombok.Data;

@Data
public class CustomerVo {
    private Integer id;

    private String nickName;

    private Integer gender;

    private String genderName;

    private String cityName;

    private String province;

    private String city;

    private String avatarUrl;

    private Integer pointBalance;

    private Long createdTime;

    private String createdAt;
}
