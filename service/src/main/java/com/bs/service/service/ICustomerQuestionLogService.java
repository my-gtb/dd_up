package com.bs.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bs.service.entity.CustomerQuestionLog;
import com.bs.service.entity.wx.QuestionHistoryForm;
import com.bs.service.entity.wx.QuestionHistoryVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dd_up
 * @since 2021-02-09
 */
public interface ICustomerQuestionLogService extends IService<CustomerQuestionLog> {
    List<QuestionHistoryVo> getQuestionHistory(String code);

    Map<String,Object> getQuestionHistories(String code);
}
