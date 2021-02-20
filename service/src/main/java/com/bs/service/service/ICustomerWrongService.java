package com.bs.service.service;

import com.bs.service.entity.CustomerWrong;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bs.service.entity.wx.CustomerWrongForm;
import com.bs.service.entity.wx.WxQuestionForm;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dd_up
 * @since 2021-02-08
 */
public interface ICustomerWrongService extends IService<CustomerWrong> {

    Map<String, Object> getCustomerQuestionCount(CustomerWrong wrong);

    List<WxQuestionForm> getWrongOrCollection(CustomerWrongForm form);
}
