package cn.AssassinG.ScsyERP.User.facade.service.impl;

import cn.AssassinG.ScsyERP.User.core.biz.ConsigneeBiz;
import cn.AssassinG.ScsyERP.User.core.biz.LoginableBiz;
import cn.AssassinG.ScsyERP.User.facade.entity.Consignee;
import cn.AssassinG.ScsyERP.User.facade.service.ConsigneeServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ConsigneeServiceFacade")
public class ConsigneeServiceFacadeImpl extends LoginableServiceImpl<Consignee> implements ConsigneeServiceFacade {
    @Autowired
    private ConsigneeBiz consigneeBiz;
    @Override
    protected LoginableBiz<Consignee> getLoginableBiz() {
        return this.consigneeBiz;
    }
}
