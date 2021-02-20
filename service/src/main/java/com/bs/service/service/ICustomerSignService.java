package com.bs.service.service;

import com.bs.service.entity.CustomerSign;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dd_up
 * @since 2021-02-14
 */
public interface ICustomerSignService extends IService<CustomerSign> {
    Map<String,Object> handleCustomerSign(CustomerSign customerSign);
}
