package cn.AssassinG.ScsyERP.User.core.biz.impl;

import cn.AssassinG.ScsyERP.User.core.biz.RoleBiz;
import cn.AssassinG.ScsyERP.User.core.dao.PermissionDao;
import cn.AssassinG.ScsyERP.User.core.dao.RoleDao;
import cn.AssassinG.ScsyERP.User.core.dao.RolePermissionDao;
import cn.AssassinG.ScsyERP.User.facade.entity.Permission;
import cn.AssassinG.ScsyERP.User.facade.entity.Role;
import cn.AssassinG.ScsyERP.User.facade.entity.Role_Permission;
import cn.AssassinG.ScsyERP.User.facade.exceptions.UserBizException;
import cn.AssassinG.ScsyERP.common.core.biz.impl.BaseBizImpl;
import cn.AssassinG.ScsyERP.common.core.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component("RoleBiz")
public class RoleBizImpl extends BaseBizImpl<Role> implements RoleBiz {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Override
    protected BaseDao<Role> getDao() {
        return this.roleDao;
    }

    /**
     * @param entityId
     * @param paramMap 管理员基本信息字段()
     */
    @Override
    @Transactional
    public void updateByMap(Long entityId, Map<String, String> paramMap) {
//        if(entityId == null){
//            throw new AdminBizException(AdminBizException.ADMINBIZ_PARAMS_ILLEGAL, "承运方管理员基本信息主键不能为空");
//        }
//        Admin admin = this.getById(entityId);
//        if(admin == null || admin.getIfDeleted()){
//            throw new AdminBizException(AdminBizException.ADMINBIZ_NOSUIT_RESULT, "没有符合条件的承运方管理员基本信息，entityId: %d", entityId);
//        }
//        String name = paramMap.get("name");
//        if(name != null && !name.isEmpty()) {
//            admin.setName(name);
//            this.update(admin);
//        }
    }

    public List<Role> findAllRoles() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("IfDeleted", false);
        return roleDao.listBy(params);
    }

    /**
     * 根据唯一的角色名查询角色
     * @param roleName 不能为空
     * @return
     */
    public Role findRoleByRoleName(String roleName) {
        if(roleName == null){
            throw new UserBizException(UserBizException.USERBIZ_PARAMS_ILLEGAL, "角色名称不能为空");
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("IfDeleted", false);
        params.put("RoleName", roleName);
        List<Role> roles = roleDao.listBy(params);
        if(roles.size() > 1) {
            throw new UserBizException(UserBizException.USERBIZ_DBUNIQUE_ERROR, "角色名称不唯一:%s", roleName);
        }else if(roles.size() == 1){
            return roles.get(0);
        }else {
            return null;
        }
    }
    /**
     * 查询主键为roleId的角色拥有的权限集合
     * @param roleId 不能为空
     * @return
     */
    public Set<Permission> findRolePermissions(Long roleId) {
        if(roleId == null){
            throw new UserBizException(UserBizException.USERBIZ_PARAMS_ILLEGAL, "角色主键不能为空");
        }
        Role role = roleDao.getById(roleId);
        if(role == null || role.getIfDeleted())
            return new HashSet<Permission>();
        return permissionDao.findByRoleId(roleId);
    }

    /**
     * 查询主键为roleId的角色的父角色的权限集合
     * @param roleId 不能为空
     * @return
     */
    public Set<Permission> findFatherRolePermissions(Long roleId) {
        if(roleId == null){
            throw new UserBizException(UserBizException.USERBIZ_PARAMS_ILLEGAL, "角色主键不能为空");
        }
        Role role = roleDao.getById(roleId);
        if(role == null || role.getIfDeleted())
            return new HashSet<Permission>();
        Set<Permission> permissions = new HashSet<Permission>();
        Role thisrole = role;
        while(thisrole != null && thisrole.getSuperRoleName() != null){
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("IfDeleted", false);
            params.put("RoleName", thisrole.getSuperRoleName());
            List<Role> super_roles = roleDao.listBy(params);
            if(super_roles.size() > 1){
                throw new UserBizException(UserBizException.USERBIZ_DBUNIQUE_ERROR, "角色名称不唯一:%s", thisrole.getSuperRoleName());
            }else if(super_roles.size() == 1){
                thisrole = super_roles.get(0);
                permissions.addAll(permissionDao.findByRoleId(thisrole.getId()));
            }else{
                thisrole = null;
            }
        }
        return permissions;
    }

    @Override
    public List<Role> findChildrenRoles(String superRoleName) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("IfDeleted", false);
        params.put("SuperRoleName", superRoleName);
        return roleDao.listBy(params);
    }

    public void addRolePermission(Long roleId, Long permissionId) {
        if(roleId == null){
            throw new UserBizException(UserBizException.USERBIZ_PARAMS_ILLEGAL, "角色主键不能为空");
        }
        if(permissionId == null){
            throw new UserBizException(UserBizException.USERBIZ_PARAMS_ILLEGAL, "权限主键不能为空");
        }
        Role role = roleDao.getById(roleId);
        if(role == null || role.getIfDeleted())
            throw new UserBizException(UserBizException.USERBIZ_CANNOTOPERATE, "没有对应的角色信息");
        Permission permission = permissionDao.getById(permissionId);
        if(permission == null || permission.getIfDeleted())
            throw new UserBizException(UserBizException.USERBIZ_CANNOTOPERATE, "没有对应的权限信息");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("RoleId", role.getId());
        params.put("PermissionId", permission.getId());
        Role_Permission role_permission = rolePermissionDao.getBy(params);
        if(role_permission == null){
            Role_Permission role_permission_new = new Role_Permission();
            role_permission_new.setRoleId(role.getId());
            role_permission_new.setCorporation(role.getCorporation());
            role_permission_new.setPermissionId(permission.getId());
            rolePermissionDao.insert(role_permission_new);
        }else{
            if(role_permission.getIfDeleted()){
                role_permission.setIfDeleted(false);
                rolePermissionDao.update(role_permission);
            }
        }
    }

    public void removeRolePermission(Long roleId, Long permissionId) {
        if(roleId == null){
            throw new UserBizException(UserBizException.USERBIZ_PARAMS_ILLEGAL, "角色主键不能为空");
        }
        if(permissionId == null){
            throw new UserBizException(UserBizException.USERBIZ_PARAMS_ILLEGAL, "权限主键不能为空");
        }
        Role role = roleDao.getById(roleId);
        if(role == null || role.getIfDeleted())
            throw new UserBizException(UserBizException.USERBIZ_CANNOTOPERATE, "没有对应的角色信息");
        Permission permission = permissionDao.getById(permissionId);
        if(permission == null || permission.getIfDeleted())
            throw new UserBizException(UserBizException.USERBIZ_CANNOTOPERATE, "没有对应的权限信息");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("RoleId", role.getId());
        params.put("PermissionId", permission.getId());
        Role_Permission role_permission = rolePermissionDao.getBy(params);
        if(role_permission != null && !role_permission.getIfDeleted()){
            rolePermissionDao.delete(role_permission);
        }
    }
}
