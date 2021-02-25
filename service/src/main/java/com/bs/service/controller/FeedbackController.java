package com.bs.service.controller;


import com.bs.common.utils.R;
import com.bs.service.entity.Feedback;
import com.bs.service.service.IFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dd_up
 * @since 2021-02-25
 */
@RestController
@RequestMapping("/service/feedback")
public class FeedbackController {

    @Autowired
    private IFeedbackService feedbackService;

    @PostMapping("addFeedback")
    public R addFeedback(@RequestBody Feedback feedback){
        boolean save = feedbackService.save(feedback);
        return save ? R.ok() : R.error();
    }

}

