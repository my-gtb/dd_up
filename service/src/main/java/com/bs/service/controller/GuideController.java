package com.bs.service.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bs.common.utils.DateUtil;
import com.bs.common.utils.R;
import com.bs.service.entity.Guide;
import com.bs.service.entity.Question;
import com.bs.service.entity.QuestionGroup;
import com.bs.service.entity.vo.GuideVo;
import com.bs.service.entity.vo.QuestionGroupQuery;
import com.bs.service.service.IGuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dd_up
 * @since 2021-02-23
 */
@RestController
@RequestMapping("/service/guide")
@CrossOrigin
public class GuideController {

    @Autowired
    private IGuideService guideService;

    @PostMapping("getGuides/{current}/{limit}")
    public R getGuides(@PathVariable Long current,
                       @PathVariable Long limit,
                       @RequestBody Guide guideQuery){
        Page<Guide> pageParam = new Page<>(current, limit);
        List<Guide> list = guideService.getGuides(pageParam,guideQuery);
        List<GuideVo> list1 = new ArrayList<>();
        for (Guide guide : list) {
            GuideVo vo = new GuideVo();
            vo.setId(guide.getId());
            vo.setTitle(guide.getTitle());
            vo.setContent(guide.getContent());
            vo.setCreatedAt(DateUtil.timeStamp2Date(guide.getCreatedTime()));
            vo.setUpdatedAt(DateUtil.timeStamp2Date(guide.getUpdatedTime()));
            list1.add(vo);
        }
        int total = list.size();
        return R.ok().data("list",list1).data("total",total);
    }

    /**
     * 添加
     */
    @PostMapping("addGuide")
    public R addGuide(@RequestBody Guide guide) {
        boolean save = guideService.save(guide);
        return save ? R.ok() : R.error();
    }

    /**
     * 根据id查询
     */
    @GetMapping("getById/{id}")
    public R getById(@PathVariable Integer id) {
        Guide guide = guideService.getById(id);
        return R.ok().data("item", guide);
    }

    /**
     * 修改
     */
    @PostMapping("updateGuide")
    public R updateGuide(
            @RequestBody Guide guide) {
        boolean update = guideService.updateById(guide);
        return update ? R.ok() : R.error();
    }

    /**
     * 根据Id删除
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public R removeById(@PathVariable Integer id) {
        boolean isSuccess = guideService.removeById(id);
        return isSuccess ? R.ok() : R.error();
    }

    //********************API***************************
    @GetMapping("getGuideList")
    public R getGuideList(){
        List<Guide> list = guideService.list(null);
        return R.ok().data("list",list);
    }

}

