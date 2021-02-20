package com.bs.service.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bs.service.entity.CustomerQuestionLog;
import com.bs.service.entity.wx.QuestionHistoryForm;
import com.bs.service.entity.wx.QuestionHistoryVo;
import com.bs.service.entity.wx.WxOptionForm;
import com.bs.service.mapper.CustomerQuestionLogMapper;
import com.bs.service.service.ICustomerQuestionLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
 * @since 2021-02-09
 */
@Service
public class CustomerQuestionLogServiceImpl extends ServiceImpl<CustomerQuestionLogMapper, CustomerQuestionLog> implements ICustomerQuestionLogService {

    @Override
    public List<QuestionHistoryVo> getQuestionHistory(String code) {
        List<QuestionHistoryVo> questionList = baseMapper.getQuestionHistory(code);
        return questionList;
    }

    @Override
    public Map<String,Object> getQuestionHistories(String code) {
        List<QuestionHistoryForm> result = new ArrayList<>();
        List<QuestionHistoryVo> questionList = baseMapper.getQuestionHistory(code);
        String[] s =  {"A. ", "B. ", "C. ", "D. ", "E. ", "F. ", "G. ", "H. ","I. "};
        int rightCount = 0;
        for (QuestionHistoryVo vo : questionList) {
            if (vo.getIsOk()){
                rightCount++;
            }
            QuestionHistoryForm form = new QuestionHistoryForm();
            form.setId(vo.getQuestion().getId());
            form.setText(vo.getQuestion().getText());
            form.setParse(vo.getQuestion().getParse());
            form.setJudge(vo.getIsOk());
            form.setTypeId(vo.getQuestion().getTypeId());
            form.setOptions(vo.getOptions());
            List<Integer> optionIdList = JSONObject.parseArray(vo.getOptionIds(), Integer.class);
            form.setChooseIds(optionIdList);
            List<String> keyNames = new ArrayList<>();
            List<String> chooseNames = new ArrayList<>();
            List<WxOptionForm> optionForm = vo.getOptions();
            for (int i = 0; i < optionForm.size(); i++) {
                for (Integer optionId : optionIdList) {
                    if (optionId == (int)optionForm.get(i).getId()){
                        chooseNames.add(s[i] + optionForm.get(i).getText());
                    }
                }
                if (optionForm.get(i).getIsKey()){
                    keyNames.add(s[i] + optionForm.get(i).getText());
                }
            }
            form.setKeyNames(keyNames);
            form.setChooseNames(chooseNames);
            result.add(form);
        }

        Map<String,Object> map = new HashMap<>();
        map.put("total",result.size());
        map.put("rightCount",rightCount);
        map.put("list",result);
        return map;
    }
}
