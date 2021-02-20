package com.bs.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bs.common.utils.DateUtil;
import com.bs.service.entity.CustomerWrong;
import com.bs.service.entity.vo.CustomerQuestionCountVo;
import com.bs.service.entity.wx.CustomerWrongForm;
import com.bs.service.entity.wx.WxOptionForm;
import com.bs.service.entity.wx.WxQuestionForm;
import com.bs.service.mapper.CustomerWrongMapper;
import com.bs.service.service.ICustomerWrongService;
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
 * @since 2021-02-08
 */
@Service
public class CustomerWrongServiceImpl extends ServiceImpl<CustomerWrongMapper, CustomerWrong> implements ICustomerWrongService {

    @Override
    public Map<String, Object> getCustomerQuestionCount(CustomerWrong wrong) {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<CustomerWrong> wrapper = new QueryWrapper<>();
        wrapper.eq("customer_id",wrong.getCustomerId());
        if (wrong.getIsWrong() != null){
            wrapper.eq("is_wrong",wrong.getIsWrong());
        }
        if (wrong.getIsCollection() != null){
            wrapper.eq("is_collection",wrong.getIsCollection());
        }
        int totalCount = this.count(wrapper);
        map.put("totalCount",totalCount);
        long todayTimeStamp = DateUtil.getTodayTimeStamp();
        long tomorrowTimeStamp = todayTimeStamp + 24 * 60 * 60;
        wrapper.ge("updated_time",todayTimeStamp);
        wrapper.lt("updated_time",tomorrowTimeStamp);
        int todayCount = this.count(wrapper);
        map.put("todayCount",todayCount);
        List<CustomerQuestionCountVo> customerQuestionCount = baseMapper.getCustomerQuestionCount(wrong);
        map.put("countMap",customerQuestionCount);
        return map;
    }

    @Override
    public List<WxQuestionForm> getWrongOrCollection(CustomerWrongForm form) {
        List<WxQuestionForm> questionList = baseMapper.getWrongOrCollection(form);
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
}
