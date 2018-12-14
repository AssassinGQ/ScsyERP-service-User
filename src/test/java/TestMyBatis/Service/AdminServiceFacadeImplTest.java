package TestMyBatis.Service;

import cn.AssassinG.ScsyERP.User.core.biz.AdminBiz;
import cn.AssassinG.ScsyERP.User.core.biz.UserBiz;
import cn.AssassinG.ScsyERP.User.core.dao.AdminDao;
import cn.AssassinG.ScsyERP.User.core.dao.UserDao;
import cn.AssassinG.ScsyERP.User.facade.entity.Admin;
import cn.AssassinG.ScsyERP.User.facade.entity.User;
import cn.AssassinG.ScsyERP.User.facade.enums.AdminDeptType;
import cn.AssassinG.ScsyERP.User.facade.enums.UserType;
import cn.AssassinG.ScsyERP.User.facade.service.AdminServiceFacade;
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
public class AdminServiceFacadeImplTest {
    private static Logger logger = Logger.getLogger(AdminServiceFacadeImplTest.class);
    @Autowired
    private UserBiz userBiz;
    @Autowired
    private UserDao userDao;
    @Autowired
    private AdminServiceFacade adminService;
    @Autowired
    private AdminBiz adminBiz;
    @Autowired
    private AdminDao adminDao;

    private Long adminId;
    private Long userId;

    @Before
    public void setUp() throws Exception {
        Admin admin = new Admin();
        admin.setCorporation(1L);
        admin.setName(StringUtils.getRandomStr(6));
        admin.setDept(AdminDeptType.FinancialAdmin);
        adminId = adminBiz.create(admin);
        User user = new User();
        user.setUserName(StringUtils.getRandomStr(6));
        user.setPassWord("d123456");
        user.setPhone("188" + StringUtils.getRandomStr(8, StringUtils.StrType.NUMBER));
        user.setCorporation(1L);
        user.setUserType(UserType.getEnumByClassName(admin.getClass().getName()));
        user.setUserInfo(adminId);
        userId = userBiz.create(user);
    }

    @After
    public void tearDown() throws Exception {
        adminDao.delete(adminId);
        userDao.delete(userId);
    }

    @Test
    public void create() {
        Admin admin = new Admin();
        admin.setCorporation(1L);
        admin.setDept(AdminDeptType.FinancialAdmin);
        admin.setName(StringUtils.getRandomStr(8));
        Long id = adminService.create(admin);
        if(id == null){
            throw new RuntimeException("Create nothing");
        }else {
            logger.info("Created : " + adminDao.getById(id));
        }
    }

    @Test
    public void update() {
        Admin admin = adminDao.getById(adminId);
        String new_name = StringUtils.getRandomStr(6);
        logger.info("Before Update: "+admin);
        admin.setName(new_name);
        adminService.update(admin);
        Admin admin_check = adminDao.getById(adminId);
        if(!admin_check.getName().equals(new_name)){
            throw new RuntimeException("Update failed");
        }else{
            logger.info("Updated succeed");
        }
    }

    @Test
    public void deleteById() {
        adminService.deleteById(adminId);
        Admin admin_check = adminDao.getById(adminId);
        if(!admin_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void delete() {
        Admin admin_test = adminDao.getById(adminId);
        adminService.delete(admin_test);
        Admin admin_check = adminDao.getById(adminId);
        if(!admin_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void getById() {
        Admin admin = adminService.getById(adminId);
        if(admin == null || admin.getIfDeleted()){
            throw new RuntimeException("getById null");
        }else{
            if(admin.getId().longValue() != adminId.longValue()){
                throw new RuntimeException("getById failed");
            }else{
                logger.info("getById succeed");
            }
        }
    }

    @Test
    public void getBy() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", false);
        paramMap.put("Id", adminId);
        Admin admin = adminService.getBy(paramMap);
        if(admin.getId() != adminId){
            throw new RuntimeException("getBy failed");
        }else{
            logger.info("getBy succeed");
        }
    }

    @Test
    public void listBy() {
        Admin admin = adminDao.getById(adminId);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", false);
        paramMap.put("Name", admin.getName());
        List<Admin> admins = adminService.listBy(paramMap);
        for (int i = 0; i < admins.size(); i++)
            logger.info("Item" + i + ":" + admins.get(i));
    }

    @Test
    public void listPage() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", false);
        PageParam pageParam = new PageParam(2, 2);
        PageBean<Admin> pageBean = adminService.listPage(pageParam, paramMap);
        logger.info(pageBean);
        List<Admin> admins = pageBean.getRecordList();
        for (int i = 0; i < admins.size(); i++)
            logger.info("Item" + i + ":" + admins.get(i));
    }

    @Test
    public void createWithUser() {
        Admin admin = new Admin();
        admin.setCorporation(1L);
        admin.setDept(AdminDeptType.FinancialAdmin);
        User user = new User();
        user.setUserName(StringUtils.getRandomStr(8));
        user.setPassWord("d123456");
        user.setPhone("188" + StringUtils.getRandomStr(8, StringUtils.StrType.NUMBER));
        user.setCorporation(1L);
        adminService.createWithUser(admin, user);
        if(user.getUserType() != UserType.Admin || user.getUserInfo().longValue() != admin.getId().longValue()){
            throw new RuntimeException("createWithUser failed");
        }else {
            logger.info("createWithUser succeed : AdminID:" + admin.getId() + ", userID:" + user.getId());
        }
    }

    @Test
    public void updateByMap() {
        String new_name = StringUtils.getRandomStr(6);
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("name", new_name);
        adminService.updateByMap(adminId, paramMap);
        Admin admin_check = adminDao.getById(adminId);
        if(!admin_check.getName().equals(new_name)){
            throw new RuntimeException("updateByMap failed");
        }else{
            logger.info("updateByMap succeed");
        }
    }

    @Test
    public void deleteByUserId() {
        adminService.deleteByUserId(userId);
        User user_check = userDao.getById(userId);
        if(user_check == null || !user_check.getIfDeleted() || user_check.getUserType().getValue().intValue() != UserType.Admin.getValue().intValue()){
            throw new RuntimeException("deleteByUserId failed");
        }
        Admin admin_check = adminDao.getById(user_check.getUserInfo());
        if(admin_check == null || !admin_check.getIfDeleted()){
            throw new RuntimeException("deleteByUserId failed");
        }
        logger.info("deleteByUserId succeed");
    }
}