package TestMyBatis.Service;

import cn.AssassinG.ScsyERP.User.core.biz.UserBiz;
import cn.AssassinG.ScsyERP.User.core.dao.PermissionDao;
import cn.AssassinG.ScsyERP.User.core.dao.RoleDao;
import cn.AssassinG.ScsyERP.User.core.dao.UserDao;
import cn.AssassinG.ScsyERP.User.facade.entity.User;
import cn.AssassinG.ScsyERP.User.facade.enums.UserType;
import cn.AssassinG.ScsyERP.User.facade.service.UserServiceFacade;
import cn.AssassinG.ScsyERP.common.page.PageBean;
import cn.AssassinG.ScsyERP.common.page.PageParam;
import cn.AssassinG.ScsyERP.common.utils.StringUtils;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-context.xml"})
public class UserServiceFacadeImplTest {
    private static Logger logger = Logger.getLogger(UserServiceFacadeImplTest.class);

    //测试UserService
    @Autowired
    private UserServiceFacade userService;
    @Autowired
    private UserBiz userBiz;
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;

    private Long userId;

    @Before
    public void setUp() throws Exception {
        User user = new User();
        user.setUserName(StringUtils.getRandomStr(6));
        user.setPassWord("d123456");
        user.setPhone("188" + StringUtils.getRandomStr(8, StringUtils.StrType.NUMBER));
        user.setCorporation(1L);
        user.setUserType(UserType.Corporation);
        user.setUserInfo(-1L);
        userId = userBiz.create(user);
    }

    @After
    public void tearDown() throws Exception {
        userDao.delete(userId);
    }

    @Test
    public void create() {
        User user = new User();
        user.setUserName(StringUtils.getRandomStr(8));
        user.setPassWord("d123456");
        user.setPhone("188" + StringUtils.getRandomStr(8, StringUtils.StrType.NUMBER));
        user.setUserType(UserType.Admin);
        user.setUserInfo(1L);
        user.setCorporation(1L);
        userService.create(user);
        Long id = user.getId();
        if(id == null){
            throw new RuntimeException("Create nothing");
        }else {
            logger.info("Created : " + userDao.getById(id));
        }
    }

    @Test
    public void update() {
        User user = userDao.getById(userId);
        String new_name = StringUtils.getRandomStr(6);
        logger.info("Before Update: "+user);
        user.setUserName(new_name);
        userService.update(user);
        User user_check = userDao.getById(userId);
        if(!user_check.getUserName().equals(new_name)){
            throw new RuntimeException("Update failed");
        }else{
            logger.info("Updated succeed");
        }
    }

    @Test
    public void deleteUserById() {
        userService.deleteById(userId);
        User user_check = userDao.getById(userId);
        if(!user_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void delete() {
        User user_test = userDao.getById(userId);
        userService.delete(user_test);
        User user_check = userDao.getById(userId);
        if(!user_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void getById() {
        User user = userService.getById(userId);
        if(user == null || user.getIfDeleted()){
            throw new RuntimeException("GetUserById null");
        }else{
            if(user.getId().longValue() != userId.longValue()){
                throw new RuntimeException("GetUserById failed");
            }else{
                logger.info("GetUserById succeed");
            }
        }
    }

    @Test
    public void getBy() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", false);
        paramMap.put("Id", userId);
        User user = userService.getBy(paramMap);
        if(user.getId().longValue() != userId.longValue()){
            throw new RuntimeException("GetUserBy failed");
        }else{
            logger.info("GetUserBy succeed");
        }
    }

    @Test
    public void listBy() {
        User user = userDao.getById(userId);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IsDeleted", false);
//        paramMap.put("UserName", user.getUserName());
        paramMap.put("UserType", UserType.Admin);
        List<User> users = userService.listBy(paramMap);
        for (int i = 0; i < users.size(); i++)
            logger.info("Item" + i + ":" + users.get(i));
    }

    @Test
    public void listPage() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", false);
        paramMap.put("UserType", UserType.Admin);
        PageParam pageParam = new PageParam(2, 2);
        PageBean<User> pageBean = userService.listPage(pageParam, paramMap);
        logger.info(pageBean);
        List<User> users = pageBean.getRecordList();
//        if(users.size() != 2){
//            throw new RuntimeException("ListPage failed");
//        }
        for (int i = 0; i < users.size(); i++)
            logger.info("Item" + i + ":" + users.get(i));
    }

    @Test
    public void findUserByUname() {
        User user = userDao.getById(userId);
        userService.findUserByUname(user.getUserName());
        logger.info("FindUserByUname succeed");
    }

    @Test
    public void findUserByPhone() {
        User user_pre = userDao.getById(userId);
        User user = userService.findUserByPhone(user_pre.getPhone());
        if(user == null || !user.getPhone().equals(user_pre.getPhone())){
            throw new RuntimeException("FindUserByPhone failed");
        }else {
            logger.info("FindUserByPhone succeed");
        }
    }

    @Test
    public void getVcode() {
        User user_pre = userDao.getById(userId);
        String vcode = userService.getVcode(user_pre.getPhone());
        if(vcode.length() == 6){
            logger.info("getVcode succeed:"+vcode);
        }else{
            throw new RuntimeException("getVcode failed");
        }
    }

    @Test
    public void login() {
        User user_pre = userDao.getById(userId);
        String uname = user_pre.getUserName();
        String password = user_pre.getPassWord();
        userService.login(uname, password);
    }

    @Test
    public void changePSW() {
        User user_pre = userDao.getById(userId);
        String phone = user_pre.getPhone();
        String vcode = userService.getVcode(phone);
        String newPassword = StringUtils.getRandomStr(6);
        userService.ChangePSW(phone, vcode, newPassword);
        User user_check = userService.findUserByPhone(phone);
        if( user_check!= null && user_check.getPassWord().equals(newPassword)){
            logger.info("changePSW succeed");
        }else{
            throw new RuntimeException("changePSW failed");
        }
    }

    @Test
    public void changeUserName() {
        String newUserName = StringUtils.getRandomStr(8);
        userService.ChangeUserName(userId, "todo", newUserName);//todo
        User user_check = userService.getById(userId);
        if( user_check!= null && user_check.getUserName().equals(newUserName)){
            logger.info("changeUserName succeed");
        }else{
            throw new RuntimeException("changeUserName failed");
        }
    }

    @Test
    public void changePhone() {
        String newPhone = "188" + StringUtils.getRandomStr(8, StringUtils.StrType.NUMBER);
        userService.ChangePhone(userId, "", newPhone);//todo
        User user_check = userService.getById(userId);
        if( user_check!= null && user_check.getPhone().equals(newPhone)){
            logger.info("changePhone succeed");
        }else{
            throw new RuntimeException("changePhone failed");
        }
    }

    @Test
    public void findUserRoles() {
        userService.findUserRoles(1L);
    }

    @Test
    public void findAllRoles() {
        userService.findAllRoles();
    }

    @Test
    public void findRoleByRoleName() {
        userService.findRoleByRoleName("asd");
    }

    @Test
    public void findUserPermissions() {
        userService.findUserPermissions(1L);
    }

    @Test
    public void findRolePermissions() {
        userService.findRolePermissions(1L);
    }

    @Test
    public void findFatherRolePermissions() {
        userService.findFatherRolePermissions(1L);
    }

    @Test
    public void findAllPermission() {
        userService.findAllPermission();
    }

    @Test
    public void addPermissionToRole() {
        userService.addPermissionToRole(1L, 1L);
    }

    @Test
    public void removePermissionFromRole() {
        userService.removePermissionFromRole(1L, 1L);
    }

    @Test
    public void addUserRole() {
        userService.addUserRole(1L, 1L);
    }

    @Test
    public void removeUserRole() {
        userService.removeUserRole(1L, 1L);
    }
}