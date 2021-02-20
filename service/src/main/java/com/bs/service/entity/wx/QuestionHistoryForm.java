package com.bs.service.entity.wx;

import lombok.Data;

import java.util.List;

@Data
public class QuestionHistoryForm {
    private Integer id;
    private Integer typeId;
    private String text;
    private String parse;
    private Boolean judge;
    private List<WxOptionForm> options;
    private List<Integer> chooseIds;
    private List<String> keyNames;
    private List<String> chooseNames;
}
