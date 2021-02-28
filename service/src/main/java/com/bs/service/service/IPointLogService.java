package com.bs.service.service;

import com.bs.service.entity.PointLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bs.service.entity.vo.PointLogQueryVo;
import com.bs.service.entity.vo.PointLogVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dd_up
 * @since 2021-02-19
 */
public interface IPointLogService extends IService<PointLog> {

    List<PointLogVo> getPointLogList(Integer current, Integer limit, PointLogQueryVo queryVo);

    List<PointLogVo> getPointLogs(Integer current, Integer limit, Integer customerId);
}
