package com.bs.service.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bs.service.entity.PointLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bs.service.entity.vo.CustomerVo;
import com.bs.service.entity.vo.PointLogQueryVo;
import com.bs.service.entity.vo.PointLogVo;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dd_up
 * @since 2021-02-19
 */
public interface PointLogMapper extends BaseMapper<PointLog> {

    IPage<PointLogVo> getPointLogList(Page<PointLogVo> page, PointLogQueryVo queryVo);

    IPage<PointLogVo> getPointLogs(Page<PointLogVo> page, Integer customerId);
}
