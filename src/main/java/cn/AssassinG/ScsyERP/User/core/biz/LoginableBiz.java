package cn.AssassinG.ScsyERP.User.core.biz;

import cn.AssassinG.ScsyERP.User.facade.entity.User;
import cn.AssassinG.ScsyERP.common.core.biz.BaseBiz;
import cn.AssassinG.ScsyERP.common.entity.LoginableEntity;

public interface LoginableBiz<T extends LoginableEntity> extends BaseBiz<T> {
    /**
     * 屏蔽create方法
     * @param entity
     * @return
     */
    Long create(T entity);

    /**
     * 屏蔽deleteById方法
     * @param id
     */
    void deleteById(long id);

    /**
     * 屏蔽delete方法
     * @param entity
     */
    void delete(T entity);
    Long createWithUser(T entity, User user);
    void deleteByUserId(Long userId);
}
