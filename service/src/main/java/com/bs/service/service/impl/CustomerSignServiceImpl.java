package com.bs.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bs.common.utils.R;
import com.bs.service.entity.CustomerSign;
import com.bs.service.entity.Question;
import com.bs.service.mapper.CustomerSignMapper;
import com.bs.service.service.ICustomerSignService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bs.service.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dd_up
 * @since 2021-02-14
 */
@Service
public class CustomerSignServiceImpl extends ServiceImpl<CustomerSignMapper, CustomerSign> implements ICustomerSignService {

    @Autowired
    private IQuestionService questionService;

    @Override
    public Map<String,Object> handleCustomerSign(CustomerSign customerSign) {
        Map<String,Object> map = new HashMap<>();
        QueryWrapper<CustomerSign> wrapper = new QueryWrapper<>();
        wrapper.eq("customer_id",customerSign.getCustomerId());
        wrapper.eq("sign_time",customerSign.getSignTime());
        CustomerSign one = this.getOne(wrapper);
        if (one != null){
            Question byId = questionService.getById(one.getQuestionId());
            map.put("questionId",byId.getId());
            map.put("questionName",byId.getText());
            map.put("durationDays",one.getDurationDays());
            map.put("isSuccess",false);
            return map;
        }
        String yesterdayTimestamp = this.yesterdayTimestamp();
        QueryWrapper<CustomerSign> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("customer_id",customerSign.getCustomerId());
        wrapper1.eq("sign_time",yesterdayTimestamp);
        CustomerSign yesterdaySign = this.getOne(wrapper1);
        if (yesterdaySign != null){
            if (yesterdaySign.getDurationDays() == 7 ){
                customerSign.setDurationDays(1);
            }else {
                customerSign.setDurationDays(yesterdaySign.getDurationDays()+1);
            }
        }else {
            customerSign.setDurationDays(1);
        }

        Question question = questionService.getRandomQuestion(5); //每日一题
        customerSign.setIsSignIn(true);
        customerSign.setQuestionId(question.getId());
        boolean save = this.save(customerSign);
        map.put("questionId",question.getId());
        map.put("questionName",question.getText());
        map.put("durationDays",customerSign.getDurationDays());
        map.put("isSuccess",save);
        return map;
    }

    private String yesterdayTimestamp(){
        try {
            Date today = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String yesterday = simpleDateFormat.format(today);//获取昨天日期
            Date date = simpleDateFormat.parse(yesterday);
            long yesterdayTimestamp = date.getTime()/1000;
            return String.valueOf(yesterdayTimestamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}
