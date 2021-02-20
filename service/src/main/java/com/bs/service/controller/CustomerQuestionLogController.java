package com.bs.service.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bs.common.utils.R;
import com.bs.service.entity.CustomerQuestionLog;
import com.bs.service.entity.QuestionGroup;
import com.bs.service.entity.wx.QuestionHistoryForm;
import com.bs.service.service.ICustomerQuestionLogService;
import com.bs.service.service.IQuestionGroupService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
 * @since 2021-02-09
 */
@RestController
@RequestMapping("/service/customer-question-log")
@CrossOrigin
public class CustomerQuestionLogController {

    @Autowired
    private ICustomerQuestionLogService questionLogService;

    @Autowired
    private IQuestionGroupService questionGroupService;

    @PostMapping("saveQuestionLog")
    public R saveQuestionLog(@RequestBody List<CustomerQuestionLog> list) {
        boolean isSuccess = questionLogService.saveBatch(list);

        return isSuccess ? R.ok().message("批量保存成功") : R.error().message("批量保存失败");
    }

    @GetMapping("getQuestionLog/{code}/{groupId}")
    public R getQuestionLog(@PathVariable String code,@PathVariable Integer groupId) {
        Map<String,Object> map = questionLogService.getQuestionHistories(code);
        QueryWrapper<QuestionGroup> wrapper = new QueryWrapper<>();
        wrapper.eq("id",groupId);
        QuestionGroup questionGroup = questionGroupService.getOne(wrapper);
        return R.ok().data("result",map).data("groupName",questionGroup.getText());
    }

}

