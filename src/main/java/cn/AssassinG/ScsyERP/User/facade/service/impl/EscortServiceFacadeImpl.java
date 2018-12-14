package cn.AssassinG.ScsyERP.User.facade.service.impl;

import cn.AssassinG.ScsyERP.User.core.biz.EscortBiz;
import cn.AssassinG.ScsyERP.User.core.biz.LoginableBiz;
import cn.AssassinG.ScsyERP.User.facade.entity.Escort;
import cn.AssassinG.ScsyERP.User.facade.service.EscortServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("EscortServiceFacade")
public class EscortServiceFacadeImpl extends LoginableServiceImpl<Escort> implements EscortServiceFacade {
    @Autowired
    private EscortBiz escortBiz;
    @Override
    protected LoginableBiz<Escort> getLoginableBiz() {
        return this.escortBiz;
    }
}
