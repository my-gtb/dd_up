package com.bs.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bs.common.utils.DateUtil;
import com.bs.service.entity.QuestionGroup;
import com.bs.service.entity.QuestionGroupType;
import com.bs.service.entity.vo.QuestionGroupForm;
import com.bs.service.entity.vo.QuestionGroupQuery;
import com.bs.service.mapper.QuestionGroupMapper;
import com.bs.service.service.IQuestionGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bs.service.service.IQuestionGroupTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dd_up
 * @since 2020-12-15
 */
@Service
public class QuestionGroupServiceImpl extends ServiceImpl<QuestionGroupMapper, QuestionGroup> implements IQuestionGroupService {

    @Autowired
    private IQuestionGroupTypeService questionGroupTypeService;

    @Override
    public void pageQuery(Page<QuestionGroup> pageParam, QuestionGroupQuery questionGroup) {
        QueryWrapper<QuestionGroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("top");

        if (!StringUtils.isEmpty(questionGroup.getText())){
            queryWrapper.like("text",questionGroup.getText());
        }
        if (!StringUtils.isEmpty(questionGroup.getTypeId())){
            queryWrapper.like("type_id",questionGroup.getTypeId());
        }
        baseMapper.selectPage(pageParam,queryWrapper);
    }

    @Override
    public void pageQuery(Page<QuestionGroup> pageParam) {
        QueryWrapper<QuestionGroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("top");
        baseMapper.selectPage(pageParam,queryWrapper);
    }

    @Override
    public List<QuestionGroupForm> parseQuestionGroup(List<QuestionGroup> records) {
        List<QuestionGroupType> typeList = questionGroupTypeService.list(null);
        List<QuestionGroupForm> result = new ArrayList<>();
        Map<Integer,String> map = new HashMap<>();
        for (QuestionGroupType type : typeList) {
            map.put(type.getId(),type.getName());
        }
        for (QuestionGroup group : records) {
            QuestionGroupForm groupForm = new QuestionGroupForm();
            BeanUtils.copyProperties(group,groupForm);
            groupForm.setTypeName(map.get(group.getTypeId()));
            groupForm.setCreatedTime(DateUtil.timeStamp2Date(group.getCreatedTime()));
            groupForm.setUpdatedTime(DateUtil.timeStamp2Date(group.getUpdatedTime()));
            result.add(groupForm);
        }
        return result;
    }

    @Override
    public void pageQueryByGroupType(Page<QuestionGroup> pageParam, Integer groupType) {
        QueryWrapper<QuestionGroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type_id",groupType);

        baseMapper.selectPage(pageParam,queryWrapper);
    }
}
