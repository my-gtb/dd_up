package com.bs.service.controller;



import com.bs.common.utils.R;
import com.bs.service.entity.Option;
import com.bs.service.service.IOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dd_up
 * @since 2020-12-15
 */
@RestController
@RequestMapping("/service/option")
@CrossOrigin
public class OptionController {


    @Autowired
    private IOptionService optionService;

    /**
     * 查询所有
     *
     * @return
     */
    @GetMapping("findAll")
    public R list() {
        List<Option> list = optionService.list(null);
        return R.ok().data("list", list);
    }

    /**
     * 添加
     */
    @PostMapping("addOption")
    public R addOption(@RequestBody Option option) {
        boolean save = optionService.save(option);
        return save ? R.ok() : R.error();
    }

    /**
     * 根据id查询
     */
    @GetMapping("getById/{id}")
    public R getById(
            @PathVariable String id) {
        Option option = optionService.getById(id);
        return R.ok().data("item", option);
    }

    /**
     * 修改
     */
    @PostMapping("updateOption")
    public R updateQuestion(
            @RequestBody Option option) {
        boolean update = optionService.updateById(option);
        return update ? R.ok() : R.error();
    }

    /**
     * 根据Id删除
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public R removeById(
            @PathVariable String id) {
        boolean isSuccess = optionService.removeById(id);
        return isSuccess ? R.ok() : R.error();
    }
}

