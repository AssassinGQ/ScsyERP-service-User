package cn.AssassinG.ScsyERP.User.facade.service.impl;

import cn.AssassinG.ScsyERP.User.core.biz.DriverBiz;
import cn.AssassinG.ScsyERP.User.core.biz.LoginableBiz;
import cn.AssassinG.ScsyERP.User.facade.entity.Driver;
import cn.AssassinG.ScsyERP.User.facade.service.DriverServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("DriverServiceFacade")
public class DriverServiceFacadeImpl extends LoginableServiceImpl<Driver> implements DriverServiceFacade {
    @Autowired
    private DriverBiz driverBiz;
    @Override
    protected LoginableBiz<Driver> getLoginableBiz() {
        return this.driverBiz;
    }
}
