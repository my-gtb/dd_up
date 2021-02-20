package com.bs.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bs.service.entity.QuestionGroupType;
import com.bs.service.mapper.QuestionGroupTypeMapper;
import com.bs.service.service.IQuestionGroupTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dd_up
 * @since 2021-01-03
 */
@Service
public class QuestionGroupTypeServiceImpl extends ServiceImpl<QuestionGroupTypeMapper, QuestionGroupType> implements IQuestionGroupTypeService {

    @Override
    public List<QuestionGroupType> getList() {

        QueryWrapper<QuestionGroupType> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        return baseMapper.selectList(wrapper);
    }
}
