package com.bs.service.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bs.common.utils.R;
import com.bs.service.entity.Question;
import com.bs.service.entity.QuestionGroup;
import com.bs.service.entity.QuestionGroupType;
import com.bs.service.entity.vo.QuestionGroupForm;
import com.bs.service.entity.vo.QuestionGroupQuery;
import com.bs.service.service.IQuestionGroupService;
import com.bs.service.service.IQuestionGroupTypeService;
import com.bs.service.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author dd_up
 * @since 2020-12-15
 */
@RestController
@RequestMapping("/service/question-group")
@CrossOrigin
public class QuestionGroupController {

    @Autowired
    private IQuestionGroupService questionGroupService;

    @Autowired
    private IQuestionGroupTypeService questionGroupTypeService;

    @Autowired
    private IQuestionService questionService;

    /**
     * 查询所有
     * @return
     */
    @GetMapping("findAll")
    public R list() {
        List<QuestionGroup> list = questionGroupService.list(null);
        return R.ok().data("list", list);
    }

    /**
     * 条件查询
     *
     * @param questionGroup
     * @param current
     * @param limit
     * @return
     */
    @PostMapping("pageCondition/{current}/{limit}")
    public R pageCondition(
            @PathVariable Long current,
            @PathVariable Long limit,
            @RequestBody QuestionGroupQuery questionGroup) {

        Page<QuestionGroup> pageParam = new Page<>(current, limit);
        questionGroupService.pageQuery(pageParam, questionGroup);
        List<QuestionGroup> records = pageParam.getRecords();
        List<QuestionGroupForm> newList = questionGroupService.parseQuestionGroup(records);
        long total = pageParam.getTotal();
        return R.ok().data("total", total).data("rows", newList);
    }

    /**
     * 分页查询
     * @param current
     * @param limit
     * @return
     */
    @GetMapping("pageCondition/{current}/{limit}")
    public R pageCondition(
            @PathVariable Long current,
            @PathVariable Long limit) {

        Page<QuestionGroup> pageParam = new Page<>(current, limit);
        questionGroupService.pageQuery(pageParam);
        List<QuestionGroup> records = pageParam.getRecords();
        long total = pageParam.getTotal();

        return R.ok().data("total", total).data("rows", records);
    }

    /**
     * 添加
     */
    @PostMapping("addQuestionGroup")
    public R addQuestionGroup(@RequestBody QuestionGroup questionGroup) {
        boolean save = questionGroupService.save(questionGroup);
        return save ? R.ok() : R.error();
    }

    /**
     * 根据id查询
     */
    @GetMapping("getById/{id}")
    public R getById(
            @PathVariable Integer id) {
        QuestionGroup questionGroup = questionGroupService.getById(id);
        return R.ok().data("item", questionGroup);
    }

    /**
     * 修改
     */
    @PostMapping("updateQuestionGroup")
    public R updateQuestionGroup(
            @RequestBody QuestionGroup questionGroup) {
        boolean update = questionGroupService.updateById(questionGroup);
        return update ? R.ok() : R.error();
    }

    /**
     * 根据Id删除
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public R removeById(
            @PathVariable Integer id) {
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        wrapper.eq("group_id",id);
        Question one = questionService.getOne(wrapper);
        if (one != null){
            return R.error().message("该问题组下还有问题记录，删除失败");
        }
        boolean isSuccess = questionGroupService.removeById(id);
        return isSuccess ? R.ok() : R.error();
    }

    @GetMapping("getEnumList")
    public R getEnumList() {
        List<QuestionGroupType> typeList = questionGroupTypeService.list(null);

        return R.ok().data("list",typeList);
    }


    //API--------------------------------------------

    @PostMapping("pageCondition/{current}/{limit}/{groupType}")
    public R pageCondition(
            @PathVariable Long current,
            @PathVariable Long limit,
            @PathVariable Integer groupType) {

        Page<QuestionGroup> pageParam = new Page<>(current, limit);
        questionGroupService.pageQueryByGroupType(pageParam, groupType);
        List<QuestionGroup> records = pageParam.getRecords();
        List<QuestionGroupForm> newList = questionGroupService.parseQuestionGroup(records);
        long total = pageParam.getTotal();
        return R.ok().data("total", total).data("rows", newList);
    }
}

