package com.bs.service.controller;


import com.bs.service.service.IPointAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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

//    @GetMapping("getCustomerPointAccount")
}

