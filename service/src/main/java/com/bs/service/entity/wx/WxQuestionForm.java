package com.bs.service.entity.wx;

import lombok.Data;

import java.util.List;

@Data
public class WxQuestionForm {
    private Integer id;
    private Integer typeId;
    private Integer groupId;
    private String groupName;
    private List<Integer> keyIds;
    private String text;
    private String parse;
    private List<WxOptionForm> options;
}
