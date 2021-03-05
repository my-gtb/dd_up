package com.bs.service.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bs.common.utils.DateUtil;
import com.bs.service.entity.*;
import com.bs.service.entity.vo.QuestionChildren;
import com.bs.service.entity.vo.QuestionForm;
import com.bs.service.entity.vo.QuestionQuery;
import com.bs.service.entity.wx.WxOptionForm;
import com.bs.service.entity.wx.WxQuestionForm;
import com.bs.service.excel.QuestionExcelListener;
import com.bs.service.excel.QuestionFormExcel;
import com.bs.service.mapper.QuestionMapper;
import com.bs.service.service.IOptionService;
import com.bs.service.service.IQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bs.service.service.IQuestionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dd_up
 * @since 2020-12-15
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements IQuestionService {

    @Autowired
    private IQuestionTypeService questionTypeService;

    @Autowired
    private IOptionService optionService;

    @Override
    public void pageQuery(Page<Question> pageParam, QuestionQuery questionQuery) {
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("top");

        if (questionQuery == null){
            baseMapper.selectPage(pageParam,queryWrapper);
            return;
        }

        String text = questionQuery.getText();
        Integer typeId = questionQuery.getTypeId();
        Integer groupId = questionQuery.getGroupId();

        if (!StringUtils.isEmpty(text)){
            queryWrapper.like("text",text);
        }

        if (!StringUtils.isEmpty(typeId)){
            queryWrapper.eq("type_id",typeId);
        }

        if (!StringUtils.isEmpty(groupId)){
            queryWrapper.eq("group_id",groupId);
        }

        baseMapper.selectPage(pageParam,queryWrapper);
    }

    @Override
    public List<Map<String, Object>> parseQuestion(List<Question> records) {
        List<Map<String, Object>> list = new ArrayList<>();

        Map<Integer, String> typeMap = this.getTypeMap();

        for (Question question : records) {
            Map<String, Object> map = new HashMap<>();
            //TODO 优化foreach数据库查询
            map.put("id",question.getId());
            map.put("text",question.getText());
            map.put("typeName",typeMap.get(question.getTypeId()));
            map.put("parse",question.getParse());
            map.put("top",question.getTop());
            map.put("createdTime", DateUtil.timeStamp2Date(question.getCreatedTime()));
            map.put("updatedTime",DateUtil.timeStamp2Date(question.getUpdatedTime()));
            Map<String,Object> optionMap = getOptionByQuestionId(question.getId());
            map.put("answer",optionMap.get("key"));
            map.put("options",optionMap.get("options"));
            list.add(map);
        }
        return list;
    }

    @Override
    public boolean addQuestion(QuestionForm questionForm) {
        Question question = new Question();
        question.setText(questionForm.getText());
        question.setTypeId(questionForm.getTypeId());
        question.setParse(questionForm.getParse());
        question.setGroupId(questionForm.getGroupId());
        boolean save = this.save(question);
        if (!save){
            return false;
        }

        List<String> answer = questionForm.answer;
        List<Option> options = new ArrayList<>();
        if (questionForm.getTypeId() == 1){
            for (QuestionChildren children : questionForm.children) {
                Option option = new Option();
                option.setText(children.getText());
                option.setQuestionId(question.getId());
                if (answer.contains(children.getText())) {
                    option.setIsKey(true);
                }
                options.add(option);
            }
        }else if (questionForm.getTypeId() == 2){
            for (QuestionChildren children : questionForm.children) {
                Option option = new Option();
                option.setText(children.getText());
                option.setQuestionId(question.getId());
                boolean flag = false;
                for (String s : answer) {
                    if (children.getText().equals(s)) {
                        option.setIsKey(true);
                        options.add(option);
                        flag = true;
                        break;
                    }
                }
                if (flag){
                    continue;
                }
                options.add(option);
            }
        }

        return optionService.saveBatch(options);
    }

    @Override
    public boolean updateQuestion(QuestionForm questionForm) {
        Question question = new Question();
        question.setId(questionForm.getId());
        question.setText(questionForm.getText());
        question.setTypeId(questionForm.getTypeId());
        question.setParse(questionForm.getParse());
        question.setGroupId(questionForm.getGroupId());
        boolean update = this.updateById(question);
        if (!update){
            return false;
        }

        List<String> answer = questionForm.answer;
        List<Option> options = new ArrayList<>();
        if (questionForm.getTypeId() == 1){
            for (QuestionChildren children : questionForm.children) {
                Option option = new Option();
                option.setId(children.getOptionId());
                option.setText(children.getText());
                option.setQuestionId(question.getId());
                option.setIsKey(false);
                if (answer.contains(children.getText())) {
                    option.setIsKey(true);
                }
                options.add(option);
            }
        }else if (questionForm.getTypeId() == 2){
            for (QuestionChildren children : questionForm.children) {
                Option option = new Option();
                option.setId(children.getOptionId());
                option.setText(children.getText());
                option.setQuestionId(question.getId());
                option.setIsKey(false);
                boolean flag = false;
                for (String s : answer) {
                    if (children.getText().equals(s)) {
                        option.setIsKey(true);
                        options.add(option);
                        flag = true;
                        break;
                    }
                }
                if (flag){
                    continue;
                }
                options.add(option);
            }
        }
        return optionService.updateBatchById(options);
    }

    @Override
    public QuestionForm getQuestionById(Integer id) {
        Question question = baseMapper.selectById(id);
        QuestionForm form = new QuestionForm();
        form.setId(question.getId());
        form.setGroupId(question.getGroupId());
        form.setParse(question.getParse());
        form.setText(question.getText());
        form.setTypeId(question.getTypeId());

        List<QuestionChildren> optionList = new ArrayList<>();
        List<String> answers = new ArrayList<>();
        QueryWrapper<Option> wrapper = new QueryWrapper<>();
        wrapper.eq("question_id",id);
        List<Option> list = optionService.list(wrapper);
        for (Option option : list) {
            if (option.getIsKey()){
                answers.add(option.getText());
            }
            QuestionChildren children = new QuestionChildren();
            children.setOptionId(option.getId());
            children.setText(option.getText());
            optionList.add(children);
        }
        form.setAnswer(answers);
        form.setChildren(optionList);
        return form;
    }

    @Override
    public boolean deleteQuestionById(Integer id) {
        QueryWrapper<Option> wrapper = new QueryWrapper<>();
        wrapper.eq("question_id",id);
        boolean remove = optionService.remove(wrapper);
        if (!remove){
            return false;
        }

        return this.removeById(id);
    }

    @Override
    public List<WxQuestionForm> getQuestionList(Integer groupId) {
        List<WxQuestionForm> questionList = baseMapper.getQuestionList(groupId);
        for ( WxQuestionForm questionForm : questionList) {
            List<WxOptionForm> optionFormList = questionForm.getOptions();
            List<Integer> keyList = new ArrayList<>();
            for ( WxOptionForm optionForm : optionFormList) {
                if (optionForm.getIsKey()){
                    keyList.add(optionForm.getId());
                }
            }
            questionForm.setKeyIds(keyList);
        }

        return questionList;
    }

    @Override
    public void saveQuestionWrong(CustomerWrong wrong) {
    }

    @Override
    public List<WxQuestionForm> getListByGroupTypeId(Integer groupTypeId) {
        List<WxQuestionForm> questionList = baseMapper.getListByGroupTypeId(groupTypeId);
        for ( WxQuestionForm questionForm : questionList) {
            List<WxOptionForm> optionFormList = questionForm.getOptions();
            List<Integer> keyList = new ArrayList<>();
            for ( WxOptionForm optionForm : optionFormList) {
                if (optionForm.getIsKey()){
                    keyList.add(optionForm.getId());
                }
            }
            questionForm.setKeyIds(keyList);
        }

        return questionList;
    }

    @Override
    public Question getRandomQuestion(Integer groupTypeId) {
        return baseMapper.getRandomQuestion(groupTypeId);
    }

    @Override
    public WxQuestionForm getQuestionFormById(Integer questionId) {
        WxQuestionForm questionForm = baseMapper.getQuestionFormById(questionId);
        List<Integer> keyList = new ArrayList<>();
        for ( WxOptionForm optionForm : questionForm.getOptions()) {
            if (optionForm.getIsKey()){
                keyList.add(optionForm.getId());
            }
        }
        questionForm.setKeyIds(keyList);

        return questionForm;
    }

    @Override
    public void batchSaveQuestion(MultipartFile file, Integer groupId) {
        try{
            //文件输入流
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, QuestionFormExcel.class,new QuestionExcelListener(this,optionService,groupId)).sheet().doRead();

        }catch (Exception e){

        }
    }

    public Map<String,Object> getOptionByQuestionId(Integer questionId){
        Map<String,Object> map = new HashMap<>();
        QueryWrapper<Option> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("question_id",questionId);
        List<Option> options = optionService.list(queryWrapper);
        if (options.size() > 0){
            List<String> optionNameList = new ArrayList<>();
            List<String> keys = new ArrayList<>();
            String[] s =  {"A. ", "B. ", "C. ", "D. ", "E. ", "F. ", "G. ", "H. ","I. "};
            for (int i = 0; i < options.size(); i++) {
                if (options.get(i).getIsKey()){
                    keys.add(s[i] + options.get(i).getText());
                }
                optionNameList.add(s[i] + options.get(i).getText());
            }
            map.put("key",String.join("，",keys));
            map.put("options",optionNameList);
        }
        return map;
    }

    public Map<Integer, String> getTypeMap(){
        List<QuestionType> questionTypes = questionTypeService.list(null);
        Map<Integer, String> typeMap = new HashMap<>();
        if(questionTypes.size() > 0){
            for (QuestionType questionType : questionTypes) {
                typeMap.put(questionType.getId(),questionType.getName());
            }
        }
        return typeMap;
    }
}
