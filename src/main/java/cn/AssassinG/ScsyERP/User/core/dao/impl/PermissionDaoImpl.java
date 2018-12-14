package cn.AssassinG.ScsyERP.User.core.dao.impl;

import cn.AssassinG.ScsyERP.User.core.dao.PermissionDao;
import cn.AssassinG.ScsyERP.User.facade.entity.Permission;
import cn.AssassinG.ScsyERP.common.core.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository(value="PermissionDao")
public class PermissionDaoImpl extends BaseDaoImpl<Permission> implements PermissionDao {
    public static final String SQL_FINDBYROLEID = "listByRoleId";
    public static final String SQL_FINDBYROLENAME = "listByRolename";

    public Set<Permission> findByRoleId(long id) {
        List<Permission> permissions = super.getSessionTemplate().selectList(super.getStatement(SQL_FINDBYROLEID), id);
        Set<Permission> ret = new HashSet<Permission>();
        ret.addAll(permissions);
        return ret;
    }

    public Set<Permission> findByRolename(String RoleName) {
        List<Permission> permissions = super.getSessionTemplate().selectList(super.getStatement(SQL_FINDBYROLENAME), RoleName);
        Set<Permission> ret = new HashSet<Permission>();
        ret.addAll(permissions);
        return ret;
    }
}
