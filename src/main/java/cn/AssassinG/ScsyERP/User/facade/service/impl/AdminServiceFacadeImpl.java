package cn.AssassinG.ScsyERP.User.facade.service.impl;

import cn.AssassinG.ScsyERP.User.core.biz.AdminBiz;
import cn.AssassinG.ScsyERP.User.core.biz.LoginableBiz;
import cn.AssassinG.ScsyERP.User.facade.entity.Admin;
import cn.AssassinG.ScsyERP.User.facade.service.AdminServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("AdminServiceFacade")
public class AdminServiceFacadeImpl extends LoginableServiceImpl<Admin> implements AdminServiceFacade {
    @Autowired
    private AdminBiz adminBiz;
    @Override
    protected LoginableBiz<Admin> getLoginableBiz() {
        return this.adminBiz;
    }
}
