package cn.AssassinG.ScsyERP.User.facade.service.impl;

import cn.AssassinG.ScsyERP.User.core.biz.RoleBiz;
import cn.AssassinG.ScsyERP.User.facade.entity.Permission;
import cn.AssassinG.ScsyERP.User.facade.entity.Role;
import cn.AssassinG.ScsyERP.User.facade.service.RoleServiceFacade;
import cn.AssassinG.ScsyERP.common.core.biz.BaseBiz;
import cn.AssassinG.ScsyERP.common.core.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service("RoleServiceFacade")
public class RoleServiceFacadeImpl extends BaseServiceImpl<Role> implements RoleServiceFacade {

    @Autowired
    private RoleBiz roleBiz;

    @Override
    protected BaseBiz<Role> getBiz() {
        return this.roleBiz;
    }

    @Override
    public void updateByMap(Long entityId, Map<String, String> paramMap) {
        roleBiz.updateByMap(entityId, paramMap);
    }

    public List<Role> findAllRoles() {
        return roleBiz.findAllRoles();
    }

    public Role findRoleByRoleName(String rolename) {
        return roleBiz.findRoleByRoleName(rolename);
    }

    public List<Role> findChildrenRoles(String superRoleName) {
        return roleBiz.findChildrenRoles(superRoleName);
    }

    public Set<Permission> findRolePermissions(Long roleid) {
        return roleBiz.findRolePermissions(roleid);
    }

    public Set<Permission> findFatherRolePermissions(Long roleid) {
        return roleBiz.findFatherRolePermissions(roleid);
    }

    public void addRolePermission(Long roleid, Long permissionid) {
        roleBiz.addRolePermission(roleid, permissionid);
    }

    public void removeRolePermission(Long roleid, Long permissionid) {
        roleBiz.removeRolePermission(roleid, permissionid);
    }
}
