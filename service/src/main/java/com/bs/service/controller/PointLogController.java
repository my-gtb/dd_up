package com.bs.service.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bs.common.utils.R;
import com.bs.service.entity.PointLog;
import com.bs.service.service.IPointLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dd_up
 * @since 2021-02-19
 */
@RestController
@RequestMapping("/service/point-log")
public class PointLogController {

    @Autowired
    private IPointLogService pointLogService;

    @GetMapping("getHasPointLog/{customerId}/{groupId}")
    public R getHasPointLog(@PathVariable Integer customerId, @PathVariable Integer groupId){
        QueryWrapper<PointLog> wrapper = new QueryWrapper<>();
        wrapper.eq("customer_id",customerId);
        wrapper.eq("product_id",groupId);
        PointLog one = pointLogService.getOne(wrapper);
        return one != null ? R.ok() : R.error();
    }

    @GetMapping("hasDailyPointLog/{customerId}/{questionId}")
    public R hasDailyPointLog(@PathVariable Integer customerId, @PathVariable Integer questionId){
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = LocalDate.now();
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        Date date = Date.from(zdt.toInstant());
        long time = date.getTime()/1000;
        long tTime = time + 24 * 60 * 60;
        QueryWrapper<PointLog> wrapper = new QueryWrapper<>();
        wrapper.eq("customer_id",customerId);
        wrapper.eq("type_id",2);
        wrapper.eq("product_id",questionId);
        wrapper.ge("created_time",time);
        wrapper.lt("created_time",tTime);
        PointLog one = pointLogService.getOne(wrapper);
        return one != null ? R.ok() : R.error();
    }
}

