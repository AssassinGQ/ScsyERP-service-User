package cn.AssassinG.ScsyERP.User.core.biz;

import cn.AssassinG.ScsyERP.User.facade.entity.Permission;
import cn.AssassinG.ScsyERP.User.facade.entity.Role;
import cn.AssassinG.ScsyERP.common.core.biz.BaseBiz;

import java.util.List;
import java.util.Set;

public interface RoleBiz extends BaseBiz<Role> {
    /**
     * @param roleId 不能为空
     * @param permissionId 不能为空
     * @return
     * 抛出运行异常：参数不合法、DAO异常
     */
    void addRolePermission(Long roleId, Long permissionId);

    /**
     * @param roleId 不能为空
     * @param permissionId 不能为空
     * @return
     * 抛出运行异常：参数不合法、DAO异常
     */
    void removeRolePermission(Long roleId, Long permissionId);
    /**
     * @return 返回所有角色信息的列表
     * 抛出运行异常：DAO异常
     */
    List<Role> findAllRoles();
    /**
     * @param roleName 不能为空
     * @return null或者Role
     * 抛出运行异常：角色名不唯一、DAO异常
     */
    Role findRoleByRoleName(String roleName);

    //    List<Role> findRolesInherit();
    //    List<Role> findChileRoles(Long fatherid);
    /**
     * @param roleId 不能为空
     * @return 权限集合
     * 抛出运行异常：参数不合法、DAO异常
     */
    Set<Permission> findRolePermissions(Long roleId);
    /**
     * 获得所有父角色的全部权限集合
     * @param roleId 不能为空
     * @return
     * 抛出运行异常：角色名称不唯一、参数不合法、DAO异常
     */
    Set<Permission> findFatherRolePermissions(Long roleId);

    List<Role> findChildrenRoles(String superRoleName);
}
