package com.bs.service.service;

import com.bs.service.entity.PointAccount;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dd_up
 * @since 2021-02-19
 */
public interface IPointAccountService extends IService<PointAccount> {

    Boolean addPoints(Integer customerId, Integer durationDays);

}
