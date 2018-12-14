package TestMyBatis.Service;

import cn.AssassinG.ScsyERP.User.core.biz.DriverBiz;
import cn.AssassinG.ScsyERP.User.core.biz.UserBiz;
import cn.AssassinG.ScsyERP.User.core.dao.DriverDao;
import cn.AssassinG.ScsyERP.User.core.dao.UserDao;
import cn.AssassinG.ScsyERP.User.facade.entity.Driver;
import cn.AssassinG.ScsyERP.User.facade.entity.User;
import cn.AssassinG.ScsyERP.User.facade.enums.UserType;
import cn.AssassinG.ScsyERP.User.facade.service.DriverServiceFacade;
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
public class DriverServiceFacadeImplTest {
    private static Logger logger = Logger.getLogger(DriverServiceFacadeImplTest.class);
    @Autowired
    private UserBiz userBiz;
    @Autowired
    private UserDao userDao;
    @Autowired
    private DriverServiceFacade driverService;
    @Autowired
    private DriverBiz driverBiz;
    @Autowired
    private DriverDao driverDao;

    private Long driverId;
    private Long userId;

    @Before
    public void setUp() throws Exception {
        Driver driver = new Driver();
        driver.setCorporation(1L);
        driver.setName(StringUtils.getRandomStr(6));
        driverId = driverBiz.create(driver);
        User user = new User();
        user.setUserName(StringUtils.getRandomStr(6));
        user.setPassWord("d123456");
        user.setPhone("188" + StringUtils.getRandomStr(8, StringUtils.StrType.NUMBER));
        user.setCorporation(1L);
        user.setUserType(UserType.getEnumByClassName(driver.getClass().getName()));
        user.setUserInfo(driverId);
        userId = userBiz.create(user);
    }

    @After
    public void tearDown() throws Exception {
        driverDao.delete(driverId);
        userDao.delete(userId);
    }

    @Test
    public void create() {
        Driver driver = new Driver();
        driver.setCorporation(1L);
        driver.setName(StringUtils.getRandomStr(6));
        Long id = driverService.create(driver);
        if(id == null){
            throw new RuntimeException("Create nothing");
        }else {
            logger.info("Created : " + driverDao.getById(id));
        }
    }

    @Test
    public void update() {
        Driver driver = driverDao.getById(driverId);
        String new_name = StringUtils.getRandomStr(6);
        logger.info("Before Update: "+driver);
        driver.setName(new_name);
        driverService.update(driver);
        Driver driver_check = driverDao.getById(driverId);
        if(!driver_check.getName().equals(new_name)){
            throw new RuntimeException("Update failed");
        }else{
            logger.info("Updated succeed");
        }
    }

    @Test
    public void deleteById() {
        driverService.deleteById(driverId);
        Driver driver_check = driverDao.getById(driverId);
        if(!driver_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void delete() {
        Driver driver_test = driverDao.getById(driverId);
        driverService.delete(driver_test);
        Driver driver_check = driverDao.getById(driverId);
        if(!driver_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void getById() {
        Driver driver = driverService.getById(driverId);
        if(driver == null || driver.getIfDeleted()){
            throw new RuntimeException("getById null");
        }else{
            if(driver.getId().longValue() != driverId.longValue()){
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
        paramMap.put("Id", driverId);
        Driver driver = driverService.getBy(paramMap);
        if(driver.getId() != driverId){
            throw new RuntimeException("getBy failed");
        }else{
            logger.info("getBy succeed");
        }
    }

    @Test
    public void listBy() {
        Driver driver = driverDao.getById(driverId);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", false);
        paramMap.put("Name", driver.getName());
        List<Driver> drivers = driverService.listBy(paramMap);
        for (int i = 0; i < drivers.size(); i++)
            logger.info("Item" + i + ":" + drivers.get(i));
    }

    @Test
    public void listPage() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", false);
        PageParam pageParam = new PageParam(2, 2);
        PageBean<Driver> pageBean = driverService.listPage(pageParam, paramMap);
        logger.info(pageBean);
        List<Driver> drivers = pageBean.getRecordList();
        for (int i = 0; i < drivers.size(); i++)
            logger.info("Item" + i + ":" + drivers.get(i));
    }

    @Test
    public void createWithUser() {
        Driver driver = new Driver();
        driver.setCorporation(1L);
        User user = new User();
        user.setUserName(StringUtils.getRandomStr(8));
        user.setPassWord("d123456");
        user.setPhone("188" + StringUtils.getRandomStr(8, StringUtils.StrType.NUMBER));
        user.setCorporation(1L);
        driverService.createWithUser(driver, user);
        if(user.getUserType() != UserType.Driver || user.getUserInfo().longValue() != driver.getId().longValue()){
            throw new RuntimeException("createWithUser failed");
        }else {
            logger.info("createWithUser succeed : DriverID:" + driver.getId() + ", userID:" + user.getId());
        }
    }

    @Test
    public void updateByMap() {
        String new_name = StringUtils.getRandomStr(6);
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("name", new_name);
        driverService.updateByMap(driverId, paramMap);
        Driver driver_check = driverDao.getById(driverId);
        if(!driver_check.getName().equals(new_name)){
            throw new RuntimeException("updateByMap failed");
        }else{
            logger.info("updateByMap succeed");
        }
    }

    @Test
    public void deleteByUserId() {
        driverService.deleteByUserId(userId);
        User user_check = userDao.getById(userId);
        if(user_check == null || !user_check.getIfDeleted() || user_check.getUserType().getValue().intValue() != UserType.Driver.getValue().intValue()){
            throw new RuntimeException("deleteByUserId failed");
        }
        Driver driver_check = driverDao.getById(user_check.getUserInfo());
        if(driver_check == null || !driver_check.getIfDeleted()){
            throw new RuntimeException("deleteByUserId failed");
        }
        logger.info("deleteByUserId succeed");
    }
}