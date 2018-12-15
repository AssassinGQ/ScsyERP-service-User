package cn.AssassinG.ScsyERP.User.core.biz;

import cn.AssassinG.ScsyERP.User.facade.entity.Permission;
import cn.AssassinG.ScsyERP.User.facade.entity.Role;
import cn.AssassinG.ScsyERP.User.facade.entity.User;
import cn.AssassinG.ScsyERP.common.core.biz.BaseBiz;

import java.util.Set;

public interface UserBiz extends BaseBiz<User> {

    //增删查改
    /**
     * @param userName 不能为空或Empty
     * @return null或者User
    1     * 抛出运行异常：参数不合法、DAO异常
     */
    User findUserByUname(String userName);

    /**
     * @param phone 必须是一个合法的手机号
     * @return null或者User
     * 抛出运行异常：参数不合法、DAO异常、手机号不唯一
     */
    User findUserByPhone(String phone);

    /**
     * @param phone 必须是一个合法的手机号
     * @return 一个String
     * 抛出运行异常：参数不合法、手机号未注册、DAO异常
     */
    //登录注册
    String getVcode(String phone);

    /**
     * @param userName
     * @param passWord
     * @return 是否登录成功
     * 抛出运行异常：参数不合法、用户名未注册、DAO异常
     */
    boolean login(String userName, String passWord);

    /**
     * @param phone 必须是一个合法的手机号
     * @param vcode
     * @param password
     * @return
     * 抛出运行异常：参数不合法、手机号未注册、验证码过期、DAO异常
     */
    void ChangePSW(String phone, String vcode, String password);

    /**
     * @param userId 用户登录信息主键
     * @param newUserName 新用户名
     * 抛出运行异常：用户主键为空、没有符合条件的用户、用户不合法、新用户名为空、新用户名被占用、DAO异常
     */
    void ChangeUserName(Long userId, String Vcode, String newUserName);

    /**
     * @param user 用户信息
     * @param newUserName 新用户名
     * 抛出运行异常：用户不合法、新用户名为空、新用户名被占用、DAO异常
     */
    void ChangeUserName(User user, String Vcode, String newUserName);

    /**
     * @param userId 用户登录信息主键
     * @param newPhone 新手机号
     * 抛出运行异常：用户主键为空、没有符合条件的用户、用户不合法、新手机号为空、新手机号被占用、DAO异常
     */
    void ChangePhone(Long userId, String Vcode, String newPhone);

    /**
     * @param user 用户信息
     * @param newPhone 新手机号
     * 抛出运行异常：用户不合法、新手机号为空、新手机号被占用、DAO异常
     */
    void ChangePhone(User user, String Vcode, String newPhone);

    //auth
    /**
     * @param userId 不能为空
     * @param roleId 不能为空
     * @return
     * 抛出运行异常：参数不合法、DAO异常
     */
    void addUserRole(Long userId, Long roleId);
    /**
     * @param userId 不能为空
     * @param roleId 不能为空
     * @return
     * 抛出运行异常：参数不合法、DAO异常
     */
    void removeUserRole(Long userId, Long roleId);
    /**
     * @param userId 不能为空
     * @param permissionId 不能为空
     * @return
     * 抛出运行异常：参数不合法、DAO异常
     */
    void addUserPermission(Long userId, Long permissionId);
    /**
     * @param userId 不能为空
     * @param permissionId 不能为空
     * @return
     * 抛出运行异常：参数不合法、DAO异常
     */
    void removeUserPermission(Long userId, Long permissionId);
    /**
     * @param userId
     * @return userId对应的用户拥有的角色集合
     * 抛出运行异常：参数不合法、DAO异常
     */
    Set<Role> findUserRoles(Long userId);
    /**
     * @param userId 不能为空
     * @return 权限集合
     * 抛出运行异常：参数不合法、DAO异常
     */
    Set<Permission> findUserPermissions(Long userId);

//    Set<Permission> findInheritRolePermissions(Long roleId);
}
