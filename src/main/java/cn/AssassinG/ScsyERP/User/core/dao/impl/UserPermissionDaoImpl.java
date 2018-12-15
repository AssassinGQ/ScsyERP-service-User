package cn.AssassinG.ScsyERP.User.core.dao.impl;

import cn.AssassinG.ScsyERP.User.core.dao.UserPermissionDao;
import cn.AssassinG.ScsyERP.User.facade.entity.User_Permission;
import cn.AssassinG.ScsyERP.common.core.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository(value="UserPermissionDao")
public class UserPermissionDaoImpl extends BaseDaoImpl<User_Permission> implements UserPermissionDao {
}
