package com.bs.service.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bs.common.utils.R;
import com.bs.service.entity.PointAccount;
import com.bs.service.entity.PointLog;
import com.bs.service.entity.vo.PointAccountVo;
import com.bs.service.service.IPointAccountService;
import com.bs.service.service.IPointLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dd_up
 * @since 2021-02-19
 */
@RestController
@RequestMapping("/service/point-account")
@CrossOrigin
public class PointAccountController {

    @Autowired
    private IPointAccountService pointAccountService;

    @Autowired
    private IPointLogService pointLogService;

    @PostMapping("updateCustomerAccount")
    public R updateCustomerAccount(@RequestBody PointAccountVo pointVo){
        QueryWrapper<PointAccount> wrapper = new QueryWrapper<>();
        wrapper.eq("customer_id",pointVo.getCustomerId());
        PointAccount one = pointAccountService.getOne(wrapper);
        if (one == null){
            return R.error().message("该账户不存在");
        }
        int newBalance = one.getBalance() + pointVo.getValue();
        one.setBalance(newBalance);
        boolean b = pointAccountService.updateById(one);

        //添加积分日志
        PointLog pointLog = new PointLog();
        pointLog.setCustomerId(pointVo.getCustomerId());
        pointLog.setTypeId(4);
        pointLog.setValue(pointVo.getValue());
        pointLog.setContent("后台充值：" + pointVo.getContent());
        pointLogService.save(pointLog);

        return b ? R.ok() : R.error();
    }

    @GetMapping("unlockQuestionGroup/{customerId}/{groupId}")
    public R unlockQuestionGroup(@PathVariable Integer customerId, @PathVariable Integer groupId){
        Map<String,Object> map = pointAccountService.unlockQuestionGroup(customerId,groupId);
        return R.ok().data("data",map);
    }

    @GetMapping("addPointOfDaily/{customerId}/{questionId}/{groupId}")
    public R addPointOfDaily(@PathVariable Integer customerId, @PathVariable Integer questionId, @PathVariable Integer groupId){
        Map<String,Object> map = pointAccountService.addPointOfDaily(customerId,questionId,groupId);
        return R.ok().data("data",map);
    }
}

