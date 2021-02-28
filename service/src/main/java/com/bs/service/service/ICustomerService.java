package com.bs.service.service;

import com.bs.service.entity.Customer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bs.service.entity.vo.CustomerVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dd_up
 * @since 2021-01-23
 */
public interface ICustomerService extends IService<Customer> {

    List<CustomerVo> getCustomerList(Integer current,Integer limit,Customer customer);
}
