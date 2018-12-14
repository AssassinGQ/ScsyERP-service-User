package cn.AssassinG.ScsyERP.User.core.biz.impl;

import cn.AssassinG.ScsyERP.User.core.biz.UserBiz;
import cn.AssassinG.ScsyERP.User.core.dao.*;
import cn.AssassinG.ScsyERP.User.facade.entity.*;
import cn.AssassinG.ScsyERP.User.facade.exceptions.UserBizException;
import cn.AssassinG.ScsyERP.common.core.biz.impl.BaseBizImpl;
import cn.AssassinG.ScsyERP.common.core.dao.BaseDao;
import cn.AssassinG.ScsyERP.common.utils.StringUtils;
import cn.AssassinG.ScsyERP.common.utils.ValidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component("UserBiz")
public class UserBizImpl extends BaseBizImpl<User> implements UserBiz {
    //todo dao-->biz   。。。所以我当时想干嘛，，， 健忘就要写的详细一点啊啊啊啊
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RolePermissionDao rolePermissionDao;
    @Autowired
    private PermissionDao permissionDao;

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    protected BaseDao<User> getDao() {
        return this.userDao;
    }

    @Transactional
    public Long create(User user) {
        ValidUtils.ValidationWithExp(user);
        User user_checkuname = userDao.findByUserName(user.getUserName());
        if(user_checkuname != null && !user_checkuname.getIfDeleted()){
            throw new UserBizException(UserBizException.USERBIZ_CANNOTOPERATE, "用户名被占用:%s", user.getUserName());
        }
        User user_checkphone = this.findUserByPhone(user.getPhone());
        if(user_checkphone != null && !user_checkphone.getIfDeleted()){
            throw new UserBizException(UserBizException.USERBIZ_CANNOTOPERATE, "手机号被占用:%s", user.getPhone());
        }
        return userDao.insert(user);
    }

    @Transactional
    public void update(User user) {
        ValidUtils.ValidationWithExp(user);
        User user_checkuname = userDao.findByUserName(user.getUserName());
        if(user_checkuname != null && !user_checkuname.getIfDeleted() && user_checkuname.getId().longValue() != user.getId().longValue()){
            throw new UserBizException(UserBizException.USERBIZ_CANNOTOPERATE, "用户名被占用:%s", user.getUserName());
        }
        User user_checkphone = this.findUserByPhone(user.getPhone());
        if(user_checkphone != null && !user_checkphone.getIfDeleted() && user_checkphone.getId().longValue() != user.getId().longValue()){
            throw new UserBizException(UserBizException.USERBIZ_CANNOTOPERATE, "手机号被占用:%s", user.getPhone());
        }
        userDao.update(user);
    }

    @Override
    public void updateByMap(Long entityId, Map<String, String> paramMap) {
        throw new UserBizException(UserBizException.USERBIZ_CANNOTOPERATE, "UserBiz的updateByMap方法被屏蔽");
    }

    public User findUserByUname(String userName){
        if(userName == null || userName.isEmpty()){
            throw new UserBizException(UserBizException.USERBIZ_PARAMS_ILLEGAL, "用户名不能为空:%s", userName);
        }
        User user = userDao.findByUserName(userName);
        if(user == null || user.getIfDeleted())
            return null;
        else
            return user;
    }

    public User findUserByPhone(String phone){
        if(!StringUtils.isMobileNum(phone)){
            throw new UserBizException(UserBizException.USERBIZ_PARAMS_ILLEGAL, "请输入合法的手机号码:%s", phone);
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("IfDeleted", false);
        params.put("Phone", phone);
        List<User> users = userDao.listBy(params);
        if(users.size() > 1){
            throw new UserBizException(UserBizException.USERBIZ_DBUNIQUE_ERROR, "用户登录信息手机号不唯一:%s", phone);
        }else if(users.size() == 1){
            return users.get(0);
        }else
            return null;
    }

    @Transactional
    public String getVcode(String phone) {
        if(!StringUtils.isMobileNum(phone)){
            throw new UserBizException(UserBizException.USERBIZ_PARAMS_ILLEGAL, "请输入合法的手机号码:%s", phone);
        }
        User user = this.findUserByPhone(phone);
        if(user == null || user.getIfDeleted()){
            throw new UserBizException(UserBizException.USERBIZ_NOSUIT_RESULT, "没有对应手机号的记录:%s", phone);
        }
        String vcode = StringUtils.getRandomStr(6);
        user.setVcode(vcode);
        user.setVcodeTime(new Date());
        userDao.update(user);
        return user.getVcode();
    }

    public boolean login(String userName, String password) {
        if(userName == null || userName.isEmpty()){
            throw new UserBizException(UserBizException.USERBIZ_PARAMS_ILLEGAL, "用户名不能为空");
        }
        if(password == null || password.isEmpty()){
            throw new UserBizException(UserBizException.USERBIZ_PARAMS_ILLEGAL, "密码不能为空");
        }
        User user_find = this.findUserByUname(userName);
        if(user_find == null || user_find.getIfDeleted()){
            throw new UserBizException(UserBizException.USERBIZ_NOSUIT_RESULT, "没有对应用户名的已经注册的记录:%s", userName);
        }
        return user_find.getPassWord().equals(password);
    }

    @Transactional
    public void ChangePSW(String phone, String vcode, String newPassWord) {
        if(!StringUtils.isMobileNum(phone)) {
            throw new UserBizException(UserBizException.USERBIZ_PARAMS_ILLEGAL, "请输入合法的手机号码:%s", phone);
        }
        if(vcode == null || newPassWord == null) {
            throw new UserBizException(UserBizException.USERBIZ_PARAMS_ILLEGAL, "验证码和新密码不能为空，验证码:%s,新密码:%s", vcode, newPassWord);
        }
        User user = this.findUserByPhone(phone);
        if(user == null || user.getIfDeleted()){
            throw new UserBizException(UserBizException.USERBIZ_NOSUIT_RESULT, "没有符合条件的用户记录，手机号:%s", phone);
        }
        if(!user.getVcode().equals(vcode) || System.currentTimeMillis() - user.getVcodeTime().getTime() > 1000*60*60*4){//验证码有效期四个小时
            throw new UserBizException(UserBizException.USERBIZ_CANNOTOPERATE, "验证码过期，手机号:%s", phone);
        }
        user.setPassWord(newPassWord);
        userDao.update(user);
    }

    @Override
    @Transactional
    public void ChangeUserName(Long userId, String Vcode, String newUserName) {
        if(userId == null){
            throw new UserBizException(UserBizException.USERBIZ_PARAMS_ILLEGAL, "用户主键不能为空");
        }
        User user = userDao.getById(userId);
        if(user == null || user.getIfDeleted()){
            throw new UserBizException(UserBizException.USERBIZ_NOSUIT_RESULT, "没有符合条件的用户记录，主键:%d", userId);
        }
        ChangeUserName(user, Vcode, newUserName);
    }

    @Override
    @Transactional
    public void ChangeUserName(User user, String Vcode, String newUserName) {
        if(user == null || !ValidUtils.Validation(user)){
            throw new UserBizException(UserBizException.USERBIZ_NOSUIT_RESULT, "用户不合法");
        }
        if(newUserName == null || newUserName.isEmpty()){
            throw new UserBizException(UserBizException.USERBIZ_PARAMS_ILLEGAL, "新用户名不能为空:%s", newUserName);
        }
        User user_checkuname = userDao.findByUserName(newUserName);
        if(user_checkuname != null && !user_checkuname.getIfDeleted() && user_checkuname.getId().longValue() != user.getId().longValue()){
            throw new UserBizException(UserBizException.USERBIZ_CANNOTOPERATE, "用户名被占用:%s", newUserName);
        }
        if(!user.getVcode().equals(Vcode) || System.currentTimeMillis() - user.getVcodeTime().getTime() > 1000*60*60*4){//验证码有效期四个小时//todo 應該用user的id查一下，其他幾個地方同理
            throw new UserBizException(UserBizException.USERBIZ_CANNOTOPERATE, "验证码过期，手机号:%s", user.getPhone());
        }
        user.setUserName(newUserName);
        userDao.update(user);
    }

    @Override
    @Transactional
    public void ChangePhone(Long userId, String Vcode, String newPhone) {
        if(userId == null){
            throw new UserBizException(UserBizException.USERBIZ_PARAMS_ILLEGAL, "用户主键不能为空");
        }
        User user = userDao.getById(userId);
        if(user == null || user.getIfDeleted()) {
            throw new UserBizException(UserBizException.USERBIZ_NOSUIT_RESULT, "没有符合条件的用户记录，手机号:%d", userId);
        }
        ChangePhone(user, Vcode, newPhone);
    }

    @Override
    @Transactional
    public void ChangePhone(User user, String Vcode, String newPhone) {
        if(user == null || !ValidUtils.Validation(user)){
            throw new UserBizException(UserBizException.USERBIZ_NOSUIT_RESULT, "没有符合条件的用户记录，手机号");
        }
        if(newPhone == null || newPhone.isEmpty()){
            throw new UserBizException(UserBizException.USERBIZ_PARAMS_ILLEGAL, "新手机号不能为空:%s", newPhone);
        }
        User user_checkphone = this.findUserByPhone(newPhone);
        if(user_checkphone != null && !user_checkphone.getIfDeleted() && user_checkphone.getId().longValue() != user.getId().longValue()){
            throw new UserBizException(UserBizException.USERBIZ_CANNOTOPERATE, "手机号被占用:%s", newPhone);
        }
        if(!user.getVcode().equals(Vcode) || System.currentTimeMillis() - user.getVcodeTime().getTime() > 1000*60*60*4){//验证码有效期四个小时
            throw new UserBizException(UserBizException.USERBIZ_CANNOTOPERATE, "验证码过期，手机号:%s", user.getPhone());
        }
        user.setPhone(newPhone);
        userDao.update(user);
    }

    /**
     * 查询主键为Id的用户拥有的所有角色，包括子孙角色
     * @param userId
     * @return
     */
    public Set<Role> findUserRoles(Long userId) {
        if(userId == null){
            throw new UserBizException(UserBizException.USERBIZ_PARAMS_ILLEGAL, "用户主键不能为空");
        }
        User user = userDao.getById(userId);
        if(user == null || user.getIfDeleted())
            return new HashSet<Role>();
        return roleDao.findByUserId(userId);
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

//    @Override
//    public List<Role> findRolesInherit() {
//        return null;
//    }
//
//    @Override
//    public List<Role> findChileRoles(Long fatherid) {
//        if(fatherid == null || fatherid.longValue() < 0)
//            return new ArrayList<Role>();
//        List<Role> ret = new ArrayList<Role>();
//        if(fatherid.longValue() == 0) {
//            ret.add(roleDao.getById(1L));
//            return ret;
//        }
//        Role father_role = roleDao.getById(fatherid);
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("SuperRoleName", father_role.getRoleName());
//        ret = roleDao.listBy(params);
//        return ret;
//    }

    /**
     * 查询主键为Id的用户拥有的所有子孙角色拥有的权限集合
     * @param userId 不能为空
     * @return
     */
    public Set<Permission> findUserPermissions(Long userId) {
        if(userId == null){
            throw new UserBizException(UserBizException.USERBIZ_PARAMS_ILLEGAL, "用户主键不能为空");
        }
        User user = userDao.getById(userId);
        if(user == null || user.getIfDeleted())
            return new HashSet<Permission>();
        Set<Role> roles = roleDao.findByUserId(user.getId());
        Set<Permission> permissions = new HashSet<Permission>();
        for(Role role : roles){
            permissions.addAll(permissionDao.findByRoleId(role.getId()));
        }
        return permissions;
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

//    @Override
//    public Set<Permission> findInheritRolePermissions(Long roleid) {
//        Role role = roleDao.getById(roleid);
//        if(role == null || role.IsDeleted())
//            return new HashSet<Permission>();
//        Set<Role> roles = new HashSet<Role>();
//        roles.add(role);
//        Queue<Role> queue = new LinkedList<Role>();
//        queue.offer(role);
//        while(queue.size() > 0){
//            Role tmprole = queue.poll();
//            Map<String, Object> params = new HashMap<String, Object>();
//            params.put("SuperRoleName", tmprole.getRoleName());
//            List<Role> tmproles = roleDao.listBy(params);
//            roles.addAll(tmproles);
//            for(Role r : tmproles){
//                queue.offer(r);
//            }
//        }
//        Set<Permission> permissions = new HashSet<Permission>();
//        for(Role role1 : roles){
//            permissions.addAll(permissionDao.findByRoleId(role1.getId()));
//        }
//        return permissions;
//    }

    public List<Permission> findAllPermission() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("IfDeleted", false);
        return permissionDao.listBy(params);
    }

    public boolean addPermissionToRole(Long roleId, Long permissionId) {
        if(roleId == null){
            throw new UserBizException(UserBizException.USERBIZ_PARAMS_ILLEGAL, "角色主键不能为空");
        }
        if(permissionId == null){
            throw new UserBizException(UserBizException.USERBIZ_PARAMS_ILLEGAL, "权限主键不能为空");
        }
        Role role = roleDao.getById(roleId);
        if(role == null || role.getIfDeleted())
            return false;
        Permission permission = permissionDao.getById(permissionId);
        if(permission == null || permission.getIfDeleted())
            return false;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("RoleId", role.getId());
        params.put("PermissionId", permission.getId());
        Role_Permission role_permission = rolePermissionDao.getBy(params);
        if(role_permission == null){
            Role_Permission role_permission_new = new Role_Permission();
            role_permission_new.setRoleId(role.getId());
            role_permission_new.setPermissionId(permission.getId());
            rolePermissionDao.insert(role_permission_new);
            return true;
        }else{
            if(role_permission.getIfDeleted()){
                role_permission.setIfDeleted(false);
                rolePermissionDao.update(role_permission);
                return true;
            }else{
                return false;
            }
        }
    }

    public boolean removePermissionFromRole(Long roleId, Long permissionId) {
        if(roleId == null){
            throw new UserBizException(UserBizException.USERBIZ_PARAMS_ILLEGAL, "角色主键不能为空");
        }
        if(permissionId == null){
            throw new UserBizException(UserBizException.USERBIZ_PARAMS_ILLEGAL, "权限主键不能为空");
        }
        Role role = roleDao.getById(roleId);
        if(role == null || role.getIfDeleted())
            return false;
        Permission permission = permissionDao.getById(permissionId);
        if(permission == null || permission.getIfDeleted())
            return false;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("RoleId", role.getId());
        params.put("PermissionId", permission.getId());
        Role_Permission role_permission = rolePermissionDao.getBy(params);
        if(role_permission == null || role_permission.getIfDeleted()){
            return false;
        }else{
            rolePermissionDao.delete(role_permission);
            return true;
        }
    }

    public boolean addUserRole(Long userId, Long roleId) {
        if(roleId == null){
            throw new UserBizException(UserBizException.USERBIZ_PARAMS_ILLEGAL, "角色主键不能为空");
        }
        if(userId == null){
            throw new UserBizException(UserBizException.USERBIZ_PARAMS_ILLEGAL, "用户主键不能为空");
        }
        User user = userDao.getById(userId);
        if(user == null || user.getIfDeleted())
            return false;
        Role role = roleDao.getById(roleId);
        if(role == null || role.getIfDeleted())
            return false;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("UserId", user.getId());
        params.put("RoleId", role.getId());
        User_Role user_role = userRoleDao.getBy(params);
        if(user_role == null){
            User_Role user_role_new = new User_Role();
            user_role_new.setUserId(user.getId());
            user_role_new.setRoleId(role.getId());
            userRoleDao.insert(user_role_new);
            return true;
        }else{
            if(user_role.getIfDeleted()){
                user_role.setIfDeleted(false);
                userRoleDao.update(user_role);
                return true;
            }else
                return false;
        }
    }

    public boolean removeUserRole(Long userId, Long roleId) {
        if(roleId == null){
            throw new UserBizException(UserBizException.USERBIZ_PARAMS_ILLEGAL, "角色主键不能为空");
        }
        if(userId == null){
            throw new UserBizException(UserBizException.USERBIZ_PARAMS_ILLEGAL, "用户主键不能为空");
        }
        User user = userDao.getById(userId);
        if(user == null || user.getIfDeleted())
            return false;
        Role role = roleDao.getById(roleId);
        if(role == null || role.getIfDeleted())
            return false;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("UserId", user.getId());
        params.put("RoleId", role.getId());
        User_Role user_role = userRoleDao.getBy(params);
        if(user_role == null || user_role.getIfDeleted()){
            return false;
        }else{
            userRoleDao.delete(user_role);
            return true;
        }
    }
}
