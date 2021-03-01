package com.bs.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bs.service.entity.Guide;
import com.bs.service.entity.QuestionGroup;
import com.bs.service.mapper.GuideMapper;
import com.bs.service.service.IGuideService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dd_up
 * @since 2021-02-23
 */
@Service
public class GuideServiceImpl extends ServiceImpl<GuideMapper, Guide> implements IGuideService {

    @Override
    public List<Guide> getGuides(Page<Guide> pageParam, Guide guideQuery) {
        QueryWrapper<Guide> queryWrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(guideQuery.getTitle())){
            queryWrapper.like("title",guideQuery.getTitle());
        }

        IPage<Guide> guideIPage = baseMapper.selectPage(pageParam, queryWrapper);
        return guideIPage.getRecords();
    }
}
