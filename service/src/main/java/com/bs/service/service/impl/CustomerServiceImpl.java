package com.bs.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bs.common.utils.DateUtil;
import com.bs.service.entity.Customer;
import com.bs.service.entity.vo.CustomerVo;
import com.bs.service.mapper.CustomerMapper;
import com.bs.service.service.ICustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dd_up
 * @since 2021-01-23
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {

    @Override
    public List<CustomerVo> getCustomerList(Integer current,Integer limit,Customer customer) {
        Page<CustomerVo> page = new Page<>(current,limit);
        IPage<CustomerVo> pageParam = baseMapper.getCustomerList(page,customer.getNickName());
        List<CustomerVo> list = pageParam.getRecords();
        for (CustomerVo customerVo : list) {
            if (customerVo.getGender() == 0){
                customerVo.setGenderName("未知性别");
            }else {
                String genderName = customerVo.getGender() == 1 ? "男" : "女";
                customerVo.setGenderName(genderName);
            }

            customerVo.setCityName(customerVo.getProvince() + "-" + customerVo.getCity());
            customerVo.setCreatedAt(DateUtil.timeStamp2Date(customerVo.getCreatedTime()));
        }
        return list;
    }
}
