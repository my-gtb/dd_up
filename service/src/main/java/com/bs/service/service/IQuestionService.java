package com.bs.service.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bs.service.entity.CustomerWrong;
import com.bs.service.entity.Question;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bs.service.entity.vo.QuestionForm;
import com.bs.service.entity.vo.QuestionQuery;
import com.bs.service.entity.wx.WxQuestionForm;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dd_up
 * @since 2020-12-15
 */
public interface IQuestionService extends IService<Question> {

    void pageQuery(Page<Question> pageParam, QuestionQuery questionQuery);

    List<Map<String, Object>> parseQuestion(List<Question> records);

    boolean addQuestion(QuestionForm questionForm);

    boolean updateQuestion(QuestionForm questionForm);

    QuestionForm getQuestionById(Integer id);

    boolean deleteQuestionById(Integer id);

    //*********************************API接口*********************************
    List<WxQuestionForm> getQuestionList(Integer groupId);

    void saveQuestionWrong(CustomerWrong wrong);

    List<WxQuestionForm> getListByGroupTypeId(Integer groupTypeId);

    Question getRandomQuestion(Integer groupTypeId);

    WxQuestionForm getQuestionFormById(Integer questionId);

    void batchSaveQuestion(MultipartFile file, Integer groupId);
}
