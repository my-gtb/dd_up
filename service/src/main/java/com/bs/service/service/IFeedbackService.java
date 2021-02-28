package com.bs.service.service;

import com.bs.service.entity.Feedback;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bs.service.entity.vo.FeedbackVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dd_up
 * @since 2021-02-25
 */
public interface IFeedbackService extends IService<Feedback> {

    List<FeedbackVo> getFeedbackList(Integer current,Integer limit);
}
