package com.bs.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bs.service.entity.CustomerQuestionLog;
import com.bs.service.entity.wx.QuestionHistoryVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dd_up
 * @since 2021-02-09
 */
public interface CustomerQuestionLogMapper extends BaseMapper<CustomerQuestionLog> {
    List<QuestionHistoryVo> getQuestionHistory(String code);
}
