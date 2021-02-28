package com.bs.service.controller;


import com.bs.common.utils.R;
import com.bs.service.entity.Feedback;
import com.bs.service.entity.PointType;
import com.bs.service.entity.vo.FeedbackVo;
import com.bs.service.service.IFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
@CrossOrigin
public class FeedbackController {

    @Autowired
    private IFeedbackService feedbackService;

    @PostMapping("addFeedback")
    public R addFeedback(@RequestBody Feedback feedback){
        boolean save = feedbackService.save(feedback);
        return save ? R.ok() : R.error();
    }

    @PostMapping("getFeedbackList/{current}/{limit}")
    public R getFeedbackList(@PathVariable Integer current, @PathVariable Integer limit){
        List<FeedbackVo> list = feedbackService.getFeedbackList(current,limit);
        int total = list.size();

        return R.ok().data("list",list).data("total",total);
    }

}

