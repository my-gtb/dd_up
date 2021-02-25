package com.bs.service.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bs.common.utils.R;
import com.bs.service.entity.CustomerWrong;
import com.bs.service.entity.Question;
import com.bs.service.entity.wx.CustomerWrongForm;
import com.bs.service.entity.wx.WxQuestionForm;
import com.bs.service.service.ICustomerWrongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dd_up
 * @since 2021-02-08
 */
@RestController
@RequestMapping("/service/customer-wrong")
public class CustomerWrongController {

    @Autowired
    private ICustomerWrongService wrongService;

    @PostMapping("getCustomerQuestionCount")
    public R getCustomerQuestionCount(@RequestBody CustomerWrong wrong) {

        Map<String,Object> map = wrongService.getCustomerQuestionCount(wrong);

        return R.ok().data("map", map);
    }

    /**
     * 获取用户错题或者收藏题
     * @return
     */
    @PostMapping("getWrongOrCollection")
    public R getWrongOrCollection(@RequestBody CustomerWrongForm form) {

        List<WxQuestionForm> list = wrongService.getWrongOrCollection(form);

        int total = list.size();
        return R.ok().data("questionList", list).data("total",total);
    }

    /**
     * 获取收藏用户已收藏的题目id
     * @return
     */
    @GetMapping("getCollectionQuestionId/{customerId}")
    public R getCollectionQuestionId(@PathVariable Integer customerId) {

        QueryWrapper<CustomerWrong> wrapper = new QueryWrapper<>();
        wrapper.eq("customer_id",customerId);
        wrapper.eq("is_collection",true);
        wrapper.select("question_id");
        wrapper.orderByAsc("question_id");
        List<CustomerWrong> list = wrongService.list(wrapper);
        List<Integer> questionIds = new ArrayList<>();
        for (CustomerWrong form : list) {
            questionIds.add(form.getQuestionId());
        }

        return R.ok().data("questionIds", questionIds);
    }

    @GetMapping("clearCustomerQuestion/{customerId}/{isWrong}")
    public R clearCustomerQuestion(@PathVariable Integer customerId,@PathVariable Boolean isWrong){
        QueryWrapper<CustomerWrong> wrapper = new QueryWrapper<>();
        wrapper.eq("customer_id",customerId);
        List<CustomerWrong> list = wrongService.list(wrapper);
        if (isWrong){
            for (CustomerWrong wrong : list) {
                wrong.setIsWrong(false);
            }
        }else {
            for (CustomerWrong wrong : list) {
                wrong.setIsCollection(false);
            }
        }
        boolean b = wrongService.updateBatchById(list);
        return b ? R.ok() : R.error();
    }

    @GetMapping("hasDailyWrong/{customerId}/{questionId}")
    public R hasDailyWrong(@PathVariable Integer customerId, @PathVariable Integer questionId){
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = LocalDate.now();
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        Date date = Date.from(zdt.toInstant());
        long time = date.getTime()/1000;
        long tTime = time + 24 * 60 * 60;
        QueryWrapper<CustomerWrong> wrapper = new QueryWrapper<>();
        wrapper.eq("customer_id",customerId);
        wrapper.eq("question_id",questionId);
        wrapper.eq("is_wrong",true);
        wrapper.ge("updated_time",time);
        wrapper.lt("updated_time",tTime);
        CustomerWrong one = wrongService.getOne(wrapper);
        return one == null ? R.ok() : R.error();
    }
}

