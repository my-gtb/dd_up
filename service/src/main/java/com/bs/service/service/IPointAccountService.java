package com.bs.service.service;

import com.bs.service.entity.PointAccount;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

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

    Map<String, Object> unlockQuestionGroup(Integer customerId, Integer groupId);

    Map<String, Object> addPointOfDaily(Integer customerId, Integer questionId,Integer groupId);
}
