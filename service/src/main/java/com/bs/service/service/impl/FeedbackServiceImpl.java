package com.bs.service.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bs.common.utils.DateUtil;
import com.bs.service.entity.Feedback;
import com.bs.service.entity.vo.FeedbackVo;
import com.bs.service.entity.vo.PointLogVo;
import com.bs.service.mapper.FeedbackMapper;
import com.bs.service.service.IFeedbackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dd_up
 * @since 2021-02-25
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements IFeedbackService {

    @Override
    public List<FeedbackVo> getFeedbackList(Integer current,Integer limit) {
        Page<FeedbackVo> page = new Page<>(current,limit);
        IPage<FeedbackVo> pageParam = baseMapper.getFeedbackList(page);
        List<FeedbackVo> list = pageParam.getRecords();
        for (FeedbackVo feedbackVo : list) {
            String timeName = DateUtil.timeStamp2Date(feedbackVo.getCreatedTime());
            feedbackVo.setCreatedAt(timeName);
        }
        return list;
    }
}
