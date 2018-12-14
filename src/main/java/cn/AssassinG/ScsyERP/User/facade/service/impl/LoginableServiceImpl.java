package cn.AssassinG.ScsyERP.User.facade.service.impl;

import cn.AssassinG.ScsyERP.User.core.biz.LoginableBiz;
import cn.AssassinG.ScsyERP.User.facade.entity.User;
import cn.AssassinG.ScsyERP.User.facade.service.LoginableService;
import cn.AssassinG.ScsyERP.common.core.biz.BaseBiz;
import cn.AssassinG.ScsyERP.common.core.service.impl.BaseServiceImpl;
import cn.AssassinG.ScsyERP.common.entity.LoginableEntity;

import java.util.Map;

public abstract class LoginableServiceImpl<T extends LoginableEntity> extends BaseServiceImpl<T> implements LoginableService<T> {

    protected abstract LoginableBiz<T> getLoginableBiz();

    protected BaseBiz<T> getBiz(){
        return this.getLoginableBiz();
    }

    /**
     * 屏蔽create方法
     * @param entity
     * @return
     */
    public Long create(T entity){
        return -1L;
    }

    public Long createWithUser(T entity, User user){
        return getLoginableBiz().createWithUser(entity, user);
    }
    public void updateByMap(Long entityId, Map<String, String> paramMap){
        getLoginableBiz().updateByMap(entityId, paramMap);
    }
    public void deleteByUserId(Long userId){
        getLoginableBiz().deleteByUserId(userId);
    }
}
