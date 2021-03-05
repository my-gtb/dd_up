package com.bs.demo;


import com.alibaba.excel.EasyExcel;
import com.bs.service.excel.QuestionFormExcel;

import java.util.ArrayList;
import java.util.List;

public class TestExcel {
    public static void main(String[] args) {
        String fileName = "D:\\questionTemplate.xlsx";
        EasyExcel.write(fileName, QuestionFormExcel.class).sheet("题目列表").doWrite(getData());
    }
    public static List<QuestionFormExcel> getData() {
        List<QuestionFormExcel> list = new ArrayList<>();
        QuestionFormExcel model = new QuestionFormExcel();
        model.setIsKey(true);
        model.setQuestionName("题目名称");
        model.setOption("选项名称A");
        model.setParse("解析");
        model.setTypeId(1);
        list.add(model);
        return list;
    }
}
