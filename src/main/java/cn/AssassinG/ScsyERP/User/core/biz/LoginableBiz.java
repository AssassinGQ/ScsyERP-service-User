package cn.AssassinG.ScsyERP.User.core.biz;

import cn.AssassinG.ScsyERP.User.facade.entity.User;
import cn.AssassinG.ScsyERP.common.core.biz.BaseBiz;
import cn.AssassinG.ScsyERP.common.entity.LoginableEntity;

public interface LoginableBiz<T extends LoginableEntity> extends BaseBiz<T> {
    Long createWithUser(T entity, User user);
    void deleteByUserId(Long userId);
}
