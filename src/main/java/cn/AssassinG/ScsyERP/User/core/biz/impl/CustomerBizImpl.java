package cn.AssassinG.ScsyERP.User.core.biz.impl;

import cn.AssassinG.ScsyERP.User.core.biz.CustomerBiz;
import cn.AssassinG.ScsyERP.User.core.dao.CustomerDao;
import cn.AssassinG.ScsyERP.User.facade.entity.Customer;
import cn.AssassinG.ScsyERP.User.facade.entity.User;
import cn.AssassinG.ScsyERP.User.facade.enums.UserType;
import cn.AssassinG.ScsyERP.User.facade.exceptions.CustomerBizException;
import cn.AssassinG.ScsyERP.common.core.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Component("CustomerBiz")
public class CustomerBizImpl extends LoginableBizImpl<Customer> implements CustomerBiz {

    @Autowired
    private CustomerDao customerDao;

    @Override
    protected BaseDao<Customer> getDao() {
        return this.customerDao;
    }


    /**
     * @param entityId
     * @param paramMap 客户基本信息字段(name,address,bank,taxNumber)
     */
    @Override
    @Transactional
    public void updateByMap(Long entityId, Map<String, String> paramMap) {
        if(entityId == null){
            throw new CustomerBizException(CustomerBizException.CUSTOMERBIZ_PARAMS_ILLEGAL, "客户基本信息主键不能为空");
        }
        Customer customer = this.getById(entityId);
        if(customer == null || customer.getIfDeleted()){
            throw new CustomerBizException(CustomerBizException.CUSTOMERBIZ_NOSUIT_RESULT, "没有符合条件的客户基本信息，entityId: %d", entityId);
        }
        String name = paramMap.get("name");
        String address = paramMap.get("address");
        String bank = paramMap.get("bank");
        String taxNumber = paramMap.get("taxNumber");
        boolean flag = false;
        if(name != null && !name.isEmpty()) {
            customer.setName(name);
            flag = true;
        }
        if(address != null && !address.isEmpty()) {
            customer.setAddress(address);
            flag = true;
        }
        if(bank != null && !bank.isEmpty()) {
            customer.setBank(bank);
            flag = true;
        }
        if(taxNumber != null && !taxNumber.isEmpty()) {
            customer.setTaxNumber(taxNumber);
            flag = true;
        }
        if(flag) {
            this.update(customer);
        }
    }

    @Override
    @Transactional
    public void deleteByUserId(Long userId) {
        if(userId == null){
            throw new CustomerBizException(CustomerBizException.CUSTOMERBIZ_PARAMS_ILLEGAL, "用户登录信息主键不能为空");
        }
        User user = userBiz.getById(userId);
        if(user == null || user.getIfDeleted() || user.getUserType().getValue().intValue() != UserType.Customer.getValue().intValue()){
            throw new CustomerBizException(CustomerBizException.CUSTOMERBIZ_NOSUIT_RESULT, "没有符合条件的用户登录信息，userId: %d", userId);
        }
        Customer customer = this.getById(user.getUserInfo());
        if(customer == null || customer.getIfDeleted()){
            throw new CustomerBizException(CustomerBizException.CUSTOMERBIZ_NOSUIT_RESULT, "没有对应的客户基本信息，userId: %d", userId);
        }
        this.delete(customer);
        userBiz.delete(user);
    }

}
