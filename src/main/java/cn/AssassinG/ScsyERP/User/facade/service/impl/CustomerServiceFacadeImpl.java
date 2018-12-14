package cn.AssassinG.ScsyERP.User.facade.service.impl;

import cn.AssassinG.ScsyERP.User.core.biz.CustomerBiz;
import cn.AssassinG.ScsyERP.User.core.biz.LoginableBiz;
import cn.AssassinG.ScsyERP.User.facade.entity.Customer;
import cn.AssassinG.ScsyERP.User.facade.service.CustomerServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("CustomerServiceFacade")
public class CustomerServiceFacadeImpl extends LoginableServiceImpl<Customer> implements CustomerServiceFacade {
    @Autowired
    private CustomerBiz customerBiz;
    @Override
    protected LoginableBiz<Customer> getLoginableBiz() {
        return this.customerBiz;
    }
}
