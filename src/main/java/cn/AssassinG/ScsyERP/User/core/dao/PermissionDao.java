package cn.AssassinG.ScsyERP.User.core.dao;

import cn.AssassinG.ScsyERP.User.facade.entity.Permission;
import cn.AssassinG.ScsyERP.common.core.dao.BaseDao;
import java.util.Set;

public interface PermissionDao extends BaseDao<Permission> {
    Set<Permission> findByRoleId(long id);
//    Set<Permission> findByRoleIdInherit(long Id);
    Set<Permission> findByRolename(String RoleName);
}
