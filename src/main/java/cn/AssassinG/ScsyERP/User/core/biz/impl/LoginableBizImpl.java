package cn.AssassinG.ScsyERP.User.core.biz.impl;

import cn.AssassinG.ScsyERP.User.core.biz.LoginableBiz;
import cn.AssassinG.ScsyERP.User.core.biz.UserBiz;
import cn.AssassinG.ScsyERP.User.facade.entity.User;
import cn.AssassinG.ScsyERP.User.facade.enums.UserType;
import cn.AssassinG.ScsyERP.User.facade.exceptions.UserBizException;
import cn.AssassinG.ScsyERP.common.core.biz.impl.BaseBizImpl;
import cn.AssassinG.ScsyERP.common.entity.LoginableEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public abstract class LoginableBizImpl<T extends LoginableEntity> extends BaseBizImpl<T> implements LoginableBiz<T> {

    @Autowired
    protected UserBiz userBiz;

    @Override
    public Long create(T entity) {
        throw new UserBizException(UserBizException.USERBIZ_CANNOTOPERATE, "可登录基本信息不能使用create方法创建");
    }

    @Override
    public void deleteById(long id) {
        throw new UserBizException(UserBizException.USERBIZ_CANNOTOPERATE, "可登录基本信息不能使用deleteById方法删除");
    }

    @Override
    @Transactional
    public Long createWithUser(T entity, User user) {
        if(entity.getName() == null || entity.getName().isEmpty()){
            entity.setName("-1");
        }
        long user_info = super.create(entity);
        if(entity.getName().equals("-1")){
            entity.setName(entity.getClass().getSimpleName()+user_info);
            this.update(entity);
        }
        if(user == null){
            throw new UserBizException(UserBizException.USERBIZ_PARAMS_ILLEGAL, "用户登录信息不能为空");
        }
        user.setUserType(UserType.getEnumByClassName(entity.getClass().getName()));
        user.setUserInfo(user_info);
        userBiz.create(user);
        return user_info;
    }
}
