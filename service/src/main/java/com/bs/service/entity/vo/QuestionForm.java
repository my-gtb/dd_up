package com.bs.service.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuestionForm {
    private Integer id;
    private Integer typeId;
    private Integer groupId;
    private String text;
    public List<String> answer;
    private String parse;
    private String customData;
    public List<QuestionChildren> children = new ArrayList<>();
}
