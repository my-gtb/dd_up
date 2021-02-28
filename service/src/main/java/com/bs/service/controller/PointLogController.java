package com.bs.service.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bs.common.utils.R;
import com.bs.service.entity.Customer;
import com.bs.service.entity.PointAccount;
import com.bs.service.entity.PointLog;
import com.bs.service.entity.PointType;
import com.bs.service.entity.vo.PointLogQueryVo;
import com.bs.service.entity.vo.PointLogVo;
import com.bs.service.service.IPointAccountService;
import com.bs.service.service.IPointLogService;
import com.bs.service.service.IPointTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

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
@CrossOrigin
public class PointLogController {

    @Autowired
    private IPointLogService pointLogService;

    @Autowired
    private IPointTypeService pointTypeService;

    @Autowired
    private IPointAccountService pointAccountService;


    @PostMapping("getPointLogList/{current}/{limit}")
    public R getPointLogList(@PathVariable Integer current,
                             @PathVariable Integer limit,
                             @RequestBody PointLogQueryVo queryVo){
        List<PointLogVo> list = pointLogService.getPointLogList(current,limit,queryVo);
        int total = list.size();
        List<PointType> types = pointTypeService.list(null);

        return R.ok().data("list",list).data("total",total).data("types",types);
    }

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

    //***************************API*********************8
    @GetMapping("getPointLogs/{current}/{limit}/{customerId}")
    public R getPointLogs(@PathVariable Integer current,
                             @PathVariable Integer limit,
                             @PathVariable Integer customerId){
        List<PointLogVo> list = pointLogService.getPointLogs(current,limit,customerId);
        int total = list.size();

        QueryWrapper<PointAccount> wrapper = new QueryWrapper<>();
        wrapper.eq("customer_id",customerId);
        PointAccount one = pointAccountService.getOne(wrapper);
        return R.ok().data("list",list).data("total",total).data("points",one.getBalance());
    }
}

