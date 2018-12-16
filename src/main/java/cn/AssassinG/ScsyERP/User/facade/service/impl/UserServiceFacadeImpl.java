package cn.AssassinG.ScsyERP.User.facade.service.impl;

import cn.AssassinG.ScsyERP.User.core.biz.UserBiz;
import cn.AssassinG.ScsyERP.User.facade.entity.Permission;
import cn.AssassinG.ScsyERP.User.facade.entity.Role;
import cn.AssassinG.ScsyERP.User.facade.entity.User;
import cn.AssassinG.ScsyERP.User.facade.entity.User_Permission;
import cn.AssassinG.ScsyERP.User.facade.service.UserServiceFacade;
import cn.AssassinG.ScsyERP.common.core.biz.BaseBiz;
import cn.AssassinG.ScsyERP.common.core.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service("UserServiceFacade")
public class UserServiceFacadeImpl extends BaseServiceImpl<User> implements UserServiceFacade {

    @Autowired
    private UserBiz userBiz;

    @Override
    protected BaseBiz<User> getBiz() {
        return this.userBiz;
    }

    @Override
    public void updateByMap(Long entityId, Map<String, String> paramMap) {
        userBiz.updateByMap(entityId, paramMap);
    }

    public User findUserByUname(String username) {
        return userBiz.findUserByUname(username);
    }

    public User findUserByPhone(String phone) {
        return userBiz.findUserByPhone(phone);
    }

    @Override
    public List<User> findAllUser() {
        return userBiz.findAllUser();
    }

    @Override
    public String getVcode(String phone) {
        return userBiz.getVcode(phone);
    }

    @Override
    public boolean login(String userName, String passWord) {
        return userBiz.login(userName, passWord);
    }

    @Override
    public void ChangePSW(String phone, String Vcode, String newPassWord) {
        userBiz.ChangePSW(phone, Vcode, newPassWord);
    }

    @Override
    public void ChangeUserName(Long userId, String Vcode, String newUserName) {
        userBiz.ChangeUserName(userId, Vcode, newUserName);
    }

    @Override
    public void ChangeUserName(User user, String Vcode, String newUserName) {
        userBiz.ChangeUserName(user, Vcode, newUserName);
    }

    @Override
    public void ChangePhone(Long userId, String Vcode, String newPhone) {
        userBiz.ChangePhone(userId, Vcode, newPhone);
    }

    @Override
    public void ChangePhone(User user, String Vcode, String newPhone) {
        userBiz.ChangePhone(user, Vcode, newPhone);
    }

    public Set<Role> findUserRoles(Long userid) {
        return userBiz.findUserRoles(userid);
    }

    public List<User_Permission> findUserPermissions(Long userid) {
        return userBiz.findUserPermissions(userid);
    }

    public Set<Permission> findUserFinalPermissions(Long userId){
        return userBiz.findUserFinalPermissions(userId);
    }

    public void addUserRole(Long userid, Long roleid) {
        userBiz.addUserRole(userid, roleid);
    }

    public void removeUserRole(Long userid, Long roleid) {
        userBiz.removeUserRole(userid, roleid);
    }

    public void addUserPermission(Long userId, Long permissionId) {
        userBiz.addUserPermission(userId, permissionId);
    }

    public void removeUserPermission(Long userId, Long permissionId) {
        userBiz.removeUserPermission(userId, permissionId);
    }
}
