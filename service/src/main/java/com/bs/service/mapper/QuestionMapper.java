package com.bs.service.mapper;

import com.bs.service.entity.Option;
import com.bs.service.entity.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bs.service.entity.wx.WxOptionForm;
import com.bs.service.entity.wx.WxQuestionForm;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dd_up
 * @since 2020-12-15
 */
public interface QuestionMapper extends BaseMapper<Question> {
    List<WxQuestionForm> getQuestionList(Integer groupId);

    List<WxQuestionForm> getListByGroupTypeId(Integer groupTypeId);

    Question getRandomQuestion(Integer groupTypeId);

    WxQuestionForm getQuestionFormById(Integer questionId);
}
