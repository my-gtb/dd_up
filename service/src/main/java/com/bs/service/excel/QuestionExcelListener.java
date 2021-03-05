package com.bs.service.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bs.common.exceptionhandler.GuliException;
import com.bs.service.entity.Option;
import com.bs.service.entity.Question;
import com.bs.service.service.IOptionService;
import com.bs.service.service.IQuestionService;


public class QuestionExcelListener extends AnalysisEventListener<QuestionFormExcel> {

    public IQuestionService questionService;
    public IOptionService optionService;
    public Integer groupId;

    public QuestionExcelListener(IQuestionService questionService,IOptionService optionService,Integer groupId){
        this.questionService = questionService;
        this.optionService = optionService;
        this.groupId = groupId;
    }

    //按行读取数据
    @Override
    public void invoke(QuestionFormExcel form, AnalysisContext analysisContext) {
        if (form == null) {
            throw new GuliException(20001, "excel 表为空！");
        }
        String questionName = form.getQuestionName();
        Question question = this.existQuestionName(this.groupId, questionName);

        if (question == null){
            question = new Question();
            question.setText(questionName);
            question.setGroupId(groupId);
            question.setParse(form.getParse());
            question.setTypeId(form.getTypeId());
            questionService.save(question);
        }

        if (question.getId() != null){
            Option option = new Option();
            option.setIsKey(form.getIsKey());
            option.setQuestionId(question.getId());
            option.setText(form.getOption());
            optionService.save(option);
        }
    }

    private Question existQuestionName(Integer groupId, String questionName) {
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        wrapper.eq("text",questionName);
        wrapper.eq("group_id",groupId);

        return questionService.getOne(wrapper);
    }

    //读取完成后
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
