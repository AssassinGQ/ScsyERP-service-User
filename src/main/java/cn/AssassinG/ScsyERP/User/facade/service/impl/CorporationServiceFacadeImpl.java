package cn.AssassinG.ScsyERP.User.facade.service.impl;

import cn.AssassinG.ScsyERP.User.core.biz.CorporationBiz;
import cn.AssassinG.ScsyERP.User.core.biz.LoginableBiz;
import cn.AssassinG.ScsyERP.User.facade.entity.Corporation;
import cn.AssassinG.ScsyERP.User.facade.entity.User;
import cn.AssassinG.ScsyERP.User.facade.service.CorporationServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("CorporationServiceFacade")
public class CorporationServiceFacadeImpl extends LoginableServiceImpl<Corporation> implements CorporationServiceFacade {
    @Autowired
    private CorporationBiz corporationBiz;
    @Override
    protected LoginableBiz<Corporation> getLoginableBiz() {
        return this.corporationBiz;
    }

    @Override
    public Long create(String token, User user, Map<String, Object> paramMap) {
        return corporationBiz.create(token, user, paramMap);
    }
}
