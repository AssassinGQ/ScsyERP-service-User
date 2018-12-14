package cn.AssassinG.ScsyERP.User.facade.service.impl;

import cn.AssassinG.ScsyERP.User.core.biz.GovernmentBiz;
import cn.AssassinG.ScsyERP.User.core.biz.LoginableBiz;
import cn.AssassinG.ScsyERP.User.facade.entity.Government;
import cn.AssassinG.ScsyERP.User.facade.entity.User;
import cn.AssassinG.ScsyERP.User.facade.service.GovernmentServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("GovernmentServiceFacade")
public class GovernmentServiceFacadeImpl extends LoginableServiceImpl<Government> implements GovernmentServiceFacade {
    @Autowired
    private GovernmentBiz governmentBiz;
    @Override
    protected LoginableBiz<Government> getLoginableBiz() {
        return this.governmentBiz;
    }

    @Override
    public Long create(String token, User user, Map<String, Object> paramMap) {
        return governmentBiz.create(token, user, paramMap);
    }
}
