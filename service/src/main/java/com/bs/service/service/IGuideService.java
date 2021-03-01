package com.bs.service.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bs.service.entity.Guide;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dd_up
 * @since 2021-02-23
 */
public interface IGuideService extends IService<Guide> {

    List<Guide> getGuides(Page<Guide> pageParam, Guide guideQuery);
}
