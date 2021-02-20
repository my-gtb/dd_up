package com.bs.service.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bs.service.entity.QuestionGroup;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bs.service.entity.vo.QuestionGroupForm;
import com.bs.service.entity.vo.QuestionGroupQuery;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dd_up
 * @since 2020-12-15
 */
public interface IQuestionGroupService extends IService<QuestionGroup> {

    void pageQuery(Page<QuestionGroup> pageParam, QuestionGroupQuery questionGroup);

    void pageQuery(Page<QuestionGroup> pageParam);

    List<QuestionGroupForm> parseQuestionGroup(List<QuestionGroup> records);

    void pageQueryByGroupType(Page<QuestionGroup> pageParam, Integer groupType);
}
