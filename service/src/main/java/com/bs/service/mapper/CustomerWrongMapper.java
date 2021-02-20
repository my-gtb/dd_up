package com.bs.service.mapper;

import com.bs.service.entity.CustomerWrong;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bs.service.entity.vo.CustomerQuestionCountVo;
import com.bs.service.entity.wx.CustomerWrongForm;
import com.bs.service.entity.wx.WxQuestionForm;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dd_up
 * @since 2021-02-08
 */
public interface CustomerWrongMapper extends BaseMapper<CustomerWrong> {

    List<CustomerQuestionCountVo> getCustomerQuestionCount(CustomerWrong wrong);

    List<WxQuestionForm> getWrongOrCollection(CustomerWrongForm wrong);
}
