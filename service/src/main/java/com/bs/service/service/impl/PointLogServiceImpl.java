package com.bs.service.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bs.common.utils.DateUtil;
import com.bs.service.entity.PointLog;
import com.bs.service.entity.vo.CustomerVo;
import com.bs.service.entity.vo.PointLogQueryVo;
import com.bs.service.entity.vo.PointLogVo;
import com.bs.service.mapper.PointLogMapper;
import com.bs.service.service.IPointLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dd_up
 * @since 2021-02-19
 */
@Service
public class PointLogServiceImpl extends ServiceImpl<PointLogMapper, PointLog> implements IPointLogService {

    @Override
    public List<PointLogVo> getPointLogList(Integer current, Integer limit, PointLogQueryVo queryVo) {
        Page<PointLogVo> page = new Page<>(current,limit);
        IPage<PointLogVo> pageParam = baseMapper.getPointLogList(page,queryVo);
        List<PointLogVo> list = pageParam.getRecords();
        for (PointLogVo pointLogVo : list) {
            String timeName = DateUtil.timeStamp2Date(pointLogVo.getCreatedTime());

            pointLogVo.setCreatedAt(timeName);
        }
        return list;
    }

    @Override
    public List<PointLogVo> getPointLogs(Integer current, Integer limit, Integer customerId) {
        Page<PointLogVo> page = new Page<>(current,limit);
        IPage<PointLogVo> pageParam = baseMapper.getPointLogs(page,customerId);
        List<PointLogVo> list = pageParam.getRecords();
        for (PointLogVo pointLogVo : list) {
            String timeName = DateUtil.timeStamp2Date(pointLogVo.getCreatedTime());

            pointLogVo.setCreatedAt(timeName);
        }
        return list;
    }
}
