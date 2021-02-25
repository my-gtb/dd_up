package com.bs.service.controller;


import com.bs.common.utils.R;
import com.bs.service.entity.Guide;
import com.bs.service.service.IGuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dd_up
 * @since 2021-02-23
 */
@RestController
@RequestMapping("/service/guide")
public class GuideController {

    @Autowired
    private IGuideService guideService;

    @GetMapping("getGuideList")
    public R getGuideList(){
        List<Guide> list = guideService.list(null);
        return R.ok().data("list",list);
    }

}

