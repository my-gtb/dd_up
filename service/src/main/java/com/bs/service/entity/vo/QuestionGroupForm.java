package com.bs.service.entity.vo;

import lombok.Data;

@Data
public class QuestionGroupForm {
    private Integer id;
    private String text;
    private Integer top;
    private Integer time;
    private Integer typeId;
    private Integer costPoints;
    private String typeName;  //TODO 完善typeName属性
    private String createdTime;
    private String updatedTime;
}
