package com.bs.service.entity.wx;

import com.bs.service.entity.Question;
import lombok.Data;

import java.util.List;

@Data
public class QuestionHistoryVo {
    private Integer id;
    private Boolean isOk;
    private String optionIds;
    private List<WxOptionForm> options;
    private Question question;
}
