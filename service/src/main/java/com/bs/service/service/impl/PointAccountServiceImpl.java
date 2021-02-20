package com.bs.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bs.service.entity.PointAccount;
import com.bs.service.mapper.PointAccountMapper;
import com.bs.service.service.IPointAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
}
