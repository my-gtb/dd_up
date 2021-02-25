package com.bs.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bs.service.entity.PointAccount;
import com.bs.service.entity.PointLog;
import com.bs.service.entity.Question;
import com.bs.service.entity.QuestionGroup;
import com.bs.service.mapper.PointAccountMapper;
import com.bs.service.service.IPointAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bs.service.service.IPointLogService;
import com.bs.service.service.IQuestionGroupService;
import com.bs.service.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dd_up
 * @since 2021-02-19
 */
@Service
public class PointAccountServiceImpl extends ServiceImpl<PointAccountMapper, PointAccount> implements IPointAccountService {

    @Autowired
    private IQuestionGroupService questionGroupService;

    @Autowired
    private IPointLogService pointLogService;

    @Autowired
    private IQuestionService questionService;

    @Override
    public Boolean addPoints(Integer customerId, Integer durationDays) {
        QueryWrapper<PointAccount> accountWrapper = new QueryWrapper<>();
        accountWrapper.eq("customer_id",customerId);
        PointAccount account = this.getOne(accountWrapper);
        if (account == null){
            account = new PointAccount();
            account.setCustomerId(customerId);
            account.setBalance(0);
            this.save(account);
        }
        int balance = account.getBalance() + durationDays;
        account.setBalance(balance);
        return this.updateById(account);
    }

    @Override
    public Map<String, Object> unlockQuestionGroup(Integer customerId, Integer groupId) {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<PointAccount> accountWrapper = new QueryWrapper<>();
        accountWrapper.eq("customer_id",customerId);
        PointAccount account = this.getOne(accountWrapper);
        if (account == null){
            map.put("success",false);
            map.put("msg","该账户不存在");
            return map;
        }

        QuestionGroup questionGroup = questionGroupService.getById(groupId);
        if (account.getBalance() < questionGroup.getCostPoints()){
            map.put("success",false);
            map.put("msg","您的积分不足，解锁失败");
            return map;
        }
        int newBalance = account.getBalance() - questionGroup.getCostPoints();
        account.setBalance(newBalance);
        this.updateById(account);
        PointLog pointLog = new PointLog();
        pointLog.setCustomerId(customerId);
        pointLog.setTypeId(3);
        pointLog.setValue(-questionGroup.getCostPoints());
        pointLog.setContent("解锁题组："+questionGroup.getText());
        pointLog.setProductTypeId(1);
        pointLog.setProductId(groupId);
        pointLogService.save(pointLog);
        map.put("success",true);
        map.put("msg","解锁成功");
        return map;
    }

    @Override
    public Map<String, Object> addPointOfDaily(Integer customerId, Integer questionId,Integer groupId) {
        Map<String, Object> map = new HashMap<>();
        QuestionGroup group = questionGroupService.getById(groupId);
        QueryWrapper<PointAccount> wrapper = new QueryWrapper<>();
        wrapper.eq("customer_id",customerId);
        PointAccount account = this.getOne(wrapper);
        if (account == null){
            map.put("success",false);
            map.put("msg","该用户不存在");
            return map;
        }
        int newBalance = account.getBalance() + group.getCostPoints();
        account.setBalance(newBalance);
        this.updateById(account);

        //添加积分日志
        Question question = questionService.getById(questionId);
        PointLog pointLog = new PointLog();
        pointLog.setCustomerId(customerId);
        pointLog.setTypeId(2);
        pointLog.setValue(group.getCostPoints());
        pointLog.setContent("每日一题："+question.getText());
        pointLog.setProductId(questionId);
        pointLogService.save(pointLog);

        map.put("success",true);
        map.put("msg","积分添加成功");
        map.put("point",group.getCostPoints());

        return map;
    }
}
