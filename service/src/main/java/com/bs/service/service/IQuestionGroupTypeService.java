package com.bs.service.service;

import com.bs.service.entity.QuestionGroupType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dd_up
 * @since 2021-01-03
 */
public interface IQuestionGroupTypeService extends IService<QuestionGroupType> {

    List<QuestionGroupType> getList();
}
