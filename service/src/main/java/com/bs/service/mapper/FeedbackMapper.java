package com.bs.service.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bs.service.entity.Feedback;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bs.service.entity.vo.FeedbackVo;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dd_up
 * @since 2021-02-25
 */
public interface FeedbackMapper extends BaseMapper<Feedback> {

    IPage<FeedbackVo> getFeedbackList(Page<FeedbackVo> page);
}
