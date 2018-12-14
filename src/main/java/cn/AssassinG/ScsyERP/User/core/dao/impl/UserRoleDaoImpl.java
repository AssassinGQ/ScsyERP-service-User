package cn.AssassinG.ScsyERP.User.core.dao.impl;

import cn.AssassinG.ScsyERP.User.core.dao.UserRoleDao;
import cn.AssassinG.ScsyERP.User.facade.entity.User_Role;
import cn.AssassinG.ScsyERP.common.core.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository(value="UserRoleDao")
public class UserRoleDaoImpl extends BaseDaoImpl<User_Role> implements UserRoleDao {
}
