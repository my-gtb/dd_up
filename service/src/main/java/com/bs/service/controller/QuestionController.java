package com.bs.service.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bs.common.utils.R;
import com.bs.service.entity.CustomerWrong;
import com.bs.service.entity.Question;
import com.bs.service.entity.QuestionGroupType;
import com.bs.service.entity.QuestionType;
import com.bs.service.entity.vo.QuestionForm;
import com.bs.service.entity.vo.QuestionGroupQuery;
import com.bs.service.entity.vo.QuestionQuery;
import com.bs.service.entity.wx.CustomerWrongForm;
import com.bs.service.entity.wx.WxQuestionForm;
import com.bs.service.service.ICustomerWrongService;
import com.bs.service.service.IQuestionService;
import com.bs.service.service.IQuestionTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dd_up
 * @since 2020-12-15
 */
@RestController
@RequestMapping("/service/question")
@CrossOrigin
public class QuestionController {

    @Autowired
    private IQuestionService questionService;

    @Autowired
    private IQuestionTypeService questionTypeService;

    @Autowired
    private ICustomerWrongService customerWrongService;

    /**
     * 查询所有
     *
     * @return
     */
    @GetMapping("findAll")
    public R list() {
        List<Question> list = questionService.list(null);
        return R.ok().data("list", list);
    }

    /**
     * 条件查询
     *
     * @param questionQuery
     * @param current
     * @param limit
     * @return
     */
    @PostMapping("pageCondition/{current}/{limit}")
    public R pageTeacherCondition(
            @PathVariable Long current,
            @PathVariable Long limit,
            @RequestBody(required = false) QuestionQuery questionQuery) {

        Page<Question> pageParam = new Page<>(current, limit);
        questionService.pageQuery(pageParam, questionQuery);
        List<Question> records = pageParam.getRecords();
        List<Map<String,Object>> result = questionService.parseQuestion(records);
        long total = pageParam.getTotal();

        return R.ok().data("total", total).data("rows", result);
    }

    /**
     * 添加
     */
    @PostMapping("addQuestion")
    public R addQuestion(@RequestBody QuestionForm questionForm) {
        boolean save = questionService.addQuestion(questionForm);
        return save ? R.ok() : R.error();
    }

    @PostMapping("updateQuestion")
    public R updateQuestion(@RequestBody QuestionForm questionForm) {
        boolean update = questionService.updateQuestion(questionForm);
        return update ? R.ok() : R.error();
    }

    /**
     * 根据id查询
     */
    @GetMapping("getQuestionById/{id}")
    public R getQuestionById(
            @PathVariable Integer id) {
        QuestionForm question = questionService.getQuestionById(id);
        return R.ok().data("item", question);
    }

    /**
     * 根据Id删除
     * @param id
     * @return
     */
    @DeleteMapping("deleteQuestionById/{id}")
    public R deleteQuestionById(
            @PathVariable Integer id) {
        boolean isSuccess = questionService.deleteQuestionById(id);
        return isSuccess ? R.ok() : R.error();
    }

    /**
     * 获取问题类型
     */
    @GetMapping("getQuestionTypeList")
    public R getQuestionTypeList() {
        List<QuestionType> typeList = questionTypeService.list(null);
        return R.ok().data("typeList", typeList);
    }

    @PostMapping("batchAddQuestion/{groupId}")
    public R batchAddQuestion(@PathVariable Integer groupId, MultipartFile file) {
        questionService.batchSaveQuestion(file,groupId);
        return R.ok();
    }





    //*********************************API接口*********************************
    /**
     * 获取题目
     */
    @GetMapping("getQuestionList/{groupId}")
    public R getQuestionList(@PathVariable Integer groupId) {
        List<WxQuestionForm> list = questionService.getQuestionList(groupId);
        int total = list.size();
        return R.ok().data("questionList", list).data("total",total);
    }

    @GetMapping("getListByGroupTypeId/{groupTypeId}")
    public R getListByGroupTypeId(@PathVariable Integer groupTypeId) {
        List<WxQuestionForm> list = questionService.getListByGroupTypeId(groupTypeId);
        int total = list.size();
        return R.ok().data("questionList", list).data("total",total);
    }

    /**
     * 通过id获取题目信息
     * @param questionId
     * @return
     */
    @GetMapping("getQuestionFormById/{questionId}")
    public R getQuestionFormById(@PathVariable Integer questionId) {
        WxQuestionForm questionForm = questionService.getQuestionFormById(questionId);
        return R.ok().data("questionForm", questionForm);
    }

    /**
     * 保存错题
     * @param wrong
     * @return
     */
    @PostMapping("saveQuestionWrong")
    public R saveQuestionWrong(@RequestBody CustomerWrong wrong) {
        System.out.println(wrong);
        QueryWrapper<CustomerWrong> wrapper = new QueryWrapper<>();
        wrapper.eq("customer_id",wrong.getCustomerId());
        wrapper.eq("question_id",wrong.getQuestionId());
        CustomerWrong customerWrong = customerWrongService.getOne(wrapper);
        CustomerWrong model = new CustomerWrong();
        if (customerWrong != null){
            BeanUtils.copyProperties(customerWrong,model);
            if (wrong.getIsWrong() != null){
                model.setIsWrong(wrong.getIsWrong());
            }
            if (wrong.getIsCollection() != null){
                model.setIsCollection(wrong.getIsCollection());
            }
        }else {
            model = wrong;
        }
        boolean save = customerWrongService.saveOrUpdate(model);
        return save ? R.ok() : R.error();
    }

}

