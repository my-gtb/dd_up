package com.bs.service.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class QuestionFormExcel {
    @ExcelProperty(value = "题干",index = 0)
    private String questionName;

    @ExcelProperty(value = "题目类型",index = 1)
    private Integer typeId;

    @ExcelProperty(value = "解析",index = 2)
    private String parse;

    @ExcelProperty(value = "选项",index = 4)
    private String option;

    @ExcelProperty(value = "答案",index = 3)
    private Boolean isKey;

}
