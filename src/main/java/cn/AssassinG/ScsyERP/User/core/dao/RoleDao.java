package cn.AssassinG.ScsyERP.User.core.dao;

import cn.AssassinG.ScsyERP.User.facade.entity.Role;
import cn.AssassinG.ScsyERP.common.core.dao.BaseDao;

import java.util.Set;

public interface RoleDao extends BaseDao<Role> {
    Set<Role> findByUserId(long Id);
    Set<Role> findByUsername(String UserName);
}
