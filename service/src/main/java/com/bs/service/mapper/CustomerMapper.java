package com.bs.service.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bs.service.entity.Customer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bs.service.entity.vo.CustomerVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dd_up
 * @since 2021-01-23
 */
public interface CustomerMapper extends BaseMapper<Customer> {

    IPage<CustomerVo> getCustomerList(Page<CustomerVo> page, @Param("nickName") String nickName);

}
