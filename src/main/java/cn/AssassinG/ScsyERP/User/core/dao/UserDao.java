package cn.AssassinG.ScsyERP.User.core.dao;

import cn.AssassinG.ScsyERP.User.facade.entity.User;
import cn.AssassinG.ScsyERP.common.core.dao.BaseDao;

public interface UserDao extends BaseDao<User> {
    /**
     * @param UserName 不能为空
     * @return 返回null或者user，会抛出运行异常
     */
    User findByUserName(String UserName);
}
