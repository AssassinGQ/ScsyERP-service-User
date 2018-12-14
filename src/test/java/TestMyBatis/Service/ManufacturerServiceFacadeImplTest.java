package TestMyBatis.Service;

import cn.AssassinG.ScsyERP.User.core.biz.ManufacturerBiz;
import cn.AssassinG.ScsyERP.User.core.biz.UserBiz;
import cn.AssassinG.ScsyERP.User.core.dao.ManufacturerDao;
import cn.AssassinG.ScsyERP.User.core.dao.UserDao;
import cn.AssassinG.ScsyERP.User.facade.entity.Manufacturer;
import cn.AssassinG.ScsyERP.User.facade.entity.User;
import cn.AssassinG.ScsyERP.User.facade.enums.UserType;
import cn.AssassinG.ScsyERP.User.facade.service.ManufacturerServiceFacade;
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
public class ManufacturerServiceFacadeImplTest {
    private static Logger logger = Logger.getLogger(ManufacturerServiceFacadeImplTest.class);
    @Autowired
    private UserBiz userBiz;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ManufacturerServiceFacade manufacturerService;
    @Autowired
    private ManufacturerBiz manufacturerBiz;
    @Autowired
    private ManufacturerDao manufacturerDao;

    private Long manufacturerId;
    private Long userId;

    @Before
    public void setUp() throws Exception {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setCorporation(1L);
        manufacturer.setName(StringUtils.getRandomStr(6));
        manufacturer.setAddress(StringUtils.getRandomStr(20));
        manufacturerId = manufacturerBiz.create(manufacturer);
        User user = new User();
        user.setUserName(StringUtils.getRandomStr(6));
        user.setPassWord("d123456");
        user.setPhone("188" + StringUtils.getRandomStr(8, StringUtils.StrType.NUMBER));
        user.setCorporation(1L);
        user.setUserType(UserType.getEnumByClassName(manufacturer.getClass().getName()));
        user.setUserInfo(manufacturerId);
        userId = userBiz.create(user);
    }

    @After
    public void tearDown() throws Exception {
        manufacturerDao.delete(manufacturerId);
        userDao.delete(userId);
    }

    @Test
    public void create() {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setCorporation(1L);
        manufacturer.setName(StringUtils.getRandomStr(6));
        Long id = manufacturerService.create(manufacturer);
        if(id == null){
            throw new RuntimeException("Create nothing");
        }else {
            logger.info("Created : " + manufacturerDao.getById(id));
        }
    }

    @Test
    public void update() {
        Manufacturer manufacturer = manufacturerDao.getById(manufacturerId);
        String new_name = StringUtils.getRandomStr(6);
        logger.info("Before Update: "+manufacturer);
        manufacturer.setName(new_name);
        manufacturerService.update(manufacturer);
        Manufacturer manufacturer_check = manufacturerDao.getById(manufacturerId);
        if(!manufacturer_check.getName().equals(new_name)){
            throw new RuntimeException("Update failed");
        }else{
            logger.info("Updated succeed");
        }
    }

    @Test
    public void deleteById() {
        manufacturerService.deleteById(manufacturerId);
        Manufacturer manufacturer_check = manufacturerDao.getById(manufacturerId);
        if(!manufacturer_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void delete() {
        Manufacturer manufacturer_test = manufacturerDao.getById(manufacturerId);
        manufacturerService.delete(manufacturer_test);
        Manufacturer manufacturer_check = manufacturerDao.getById(manufacturerId);
        if(!manufacturer_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void getById() {
        Manufacturer manufacturer = manufacturerService.getById(manufacturerId);
        if(manufacturer == null || manufacturer.getIfDeleted()){
            throw new RuntimeException("getById null");
        }else{
            if(manufacturer.getId().longValue() != manufacturerId.longValue()){
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
        paramMap.put("Id", manufacturerId);
        Manufacturer manufacturer = manufacturerService.getBy(paramMap);
        if(manufacturer.getId() != manufacturerId){
            throw new RuntimeException("getBy failed");
        }else{
            logger.info("getBy succeed");
        }
    }

    @Test
    public void listBy() {
        Manufacturer manufacturer = manufacturerDao.getById(manufacturerId);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", false);
        paramMap.put("Name", manufacturer.getName());
        List<Manufacturer> manufacturers = manufacturerService.listBy(paramMap);
        for (int i = 0; i < manufacturers.size(); i++)
            logger.info("Item" + i + ":" + manufacturers.get(i));
    }

    @Test
    public void listPage() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", false);
        PageParam pageParam = new PageParam(2, 2);
        PageBean<Manufacturer> pageBean = manufacturerService.listPage(pageParam, paramMap);
        logger.info(pageBean);
        List<Manufacturer> manufacturers = pageBean.getRecordList();
        for (int i = 0; i < manufacturers.size(); i++)
            logger.info("Item" + i + ":" + manufacturers.get(i));
    }

    @Test
    public void createWithUser() {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setCorporation(1L);
        manufacturer.setAddress(StringUtils.getRandomStr(20));
        User user = new User();
        user.setUserName(StringUtils.getRandomStr(8));
        user.setPassWord("d123456");
        user.setPhone("188" + StringUtils.getRandomStr(8, StringUtils.StrType.NUMBER));
        user.setCorporation(1L);
        manufacturerService.createWithUser(manufacturer, user);
        if(user.getUserType() != UserType.Manufacturer || user.getUserInfo().longValue() != manufacturer.getId().longValue()){
            throw new RuntimeException("createWithUser failed");
        }else {
            logger.info("createWithUser succeed : ManufacturerID:" + manufacturer.getId() + ", userID:" + user.getId());
        }
    }

    @Test
    public void updateByMap() {
        String new_name = StringUtils.getRandomStr(6);
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("name", new_name);
        manufacturerService.updateByMap(manufacturerId, paramMap);
        Manufacturer manufacturer_check = manufacturerDao.getById(manufacturerId);
        if(!manufacturer_check.getName().equals(new_name)){
            throw new RuntimeException("updateByMap failed");
        }else{
            logger.info("updateByMap succeed");
        }
    }

    @Test
    public void deleteByUserId() {
        manufacturerService.deleteByUserId(userId);
        User user_check = userDao.getById(userId);
        if(user_check == null || !user_check.getIfDeleted() || user_check.getUserType().getValue().intValue() != UserType.Manufacturer.getValue().intValue()){
            throw new RuntimeException("deleteByUserId failed");
        }
        Manufacturer manufacturer_check = manufacturerDao.getById(user_check.getUserInfo());
        if(manufacturer_check == null || !manufacturer_check.getIfDeleted()){
            throw new RuntimeException("deleteByUserId failed");
        }
        logger.info("deleteByUserId succeed");
    }
}