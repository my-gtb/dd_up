package com.bs.service.controller;


import com.bs.common.utils.R;
import com.bs.service.service.IPointAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
public class PointAccountController {

    @Autowired
    private IPointAccountService pointAccountService;

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

