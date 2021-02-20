package com.bs.service.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bs.common.utils.R;
import com.bs.service.entity.CustomerSign;
import com.bs.service.entity.Option;
import com.bs.service.entity.PointLog;
import com.bs.service.entity.Question;
import com.bs.service.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dd_up
 * @since 2021-01-23
 */
@RestController
@RequestMapping("/service/customer")
@CrossOrigin
public class CustomerController {

    @Autowired
    private ICustomerSignService signService;

    @Autowired
    private IQuestionService questionService;

    @Autowired
    private IPointAccountService pointAccountService;

    @Autowired
    private IPointLogService pointLogService;

    /**
     * 查询所有
     *
     * @return
     */
    @PostMapping("getSignIn")
    public R getSignIn(@RequestBody CustomerSign customerSign) {
        Map<String,Object> map = signService.handleCustomerSign(customerSign);
        Boolean isSuccess = (Boolean) map.get("isSuccess");
        Integer durationDays = (Integer)map.get("durationDays");
        if (isSuccess){
            Boolean canAdd = pointAccountService.addPoints(customerSign.getCustomerId(),durationDays);
            if (canAdd){
                PointLog pointLog = new PointLog();
                pointLog.setCustomerId(customerSign.getCustomerId());
                pointLog.setValue(durationDays);
                pointLog.setContent("用户登录");
                pointLog.setTypeId(+1);
                pointLogService.save(pointLog);
            }
        }
        return isSuccess ? R.ok().data("result",map):R.error().data("result",map);
    }
}

