package TestMyBatis.Service;

import cn.AssassinG.ScsyERP.User.core.biz.CorporationBiz;
import cn.AssassinG.ScsyERP.User.core.biz.UserBiz;
import cn.AssassinG.ScsyERP.User.core.dao.CorporationDao;
import cn.AssassinG.ScsyERP.User.core.dao.UserDao;
import cn.AssassinG.ScsyERP.User.facade.entity.Corporation;
import cn.AssassinG.ScsyERP.User.facade.entity.User;
import cn.AssassinG.ScsyERP.User.facade.enums.UserType;
import cn.AssassinG.ScsyERP.User.facade.service.CorporationServiceFacade;
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
public class CorporationServiceFacadeImplTest {
    private static Logger logger = Logger.getLogger(CorporationServiceFacadeImplTest.class);
    @Autowired
    private UserBiz userBiz;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CorporationServiceFacade corporationService;
    @Autowired
    private CorporationBiz corporationBiz;
    @Autowired
    private CorporationDao corporationDao;

    private Long corporationId;
    private Long userId;

    @Before
    public void setUp() throws Exception {
        Corporation corporation = new Corporation();
        corporation.setCorporation(1L);
        corporation.setName(StringUtils.getRandomStr(6));
        corporationId = corporationBiz.create(corporation);
        User user = new User();
        user.setUserName(StringUtils.getRandomStr(6));
        user.setPassWord("d123456");
        user.setPhone("188" + StringUtils.getRandomStr(8, StringUtils.StrType.NUMBER));
        user.setCorporation(1L);
        user.setUserType(UserType.getEnumByClassName(corporation.getClass().getName()));
        user.setUserInfo(corporationId);
        userId = userBiz.create(user);
    }

    @After
    public void tearDown() throws Exception {
        corporationDao.delete(corporationId);
        userDao.delete(userId);
    }

    @Test
    public void create() {
        Corporation corporation = new Corporation();
        corporation.setCorporation(1L);
        corporation.setName(StringUtils.getRandomStr(6));
        Long id = corporationService.create(corporation);
        if(id == null){
            throw new RuntimeException("Create nothing");
        }else {
            logger.info("Created : " + corporationDao.getById(id));
        }
    }

    @Test
    public void update() {
        Corporation corporation = corporationDao.getById(corporationId);
        String new_name = StringUtils.getRandomStr(6);
        logger.info("Before Update: "+corporation);
        corporation.setName(new_name);
        corporationService.update(corporation);
        Corporation corporation_check = corporationDao.getById(corporationId);
        if(!corporation_check.getName().equals(new_name)){
            throw new RuntimeException("Update failed");
        }else{
            logger.info("Updated succeed");
        }
    }

    @Test
    public void deleteById() {
        corporationService.deleteById(corporationId);
        Corporation corporation_check = corporationDao.getById(corporationId);
        if(!corporation_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void delete() {
        Corporation corporation_test = corporationDao.getById(corporationId);
        corporationService.delete(corporation_test);
        Corporation corporation_check = corporationDao.getById(corporationId);
        if(!corporation_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void getById() {
        Corporation corporation = corporationService.getById(corporationId);
        if(corporation == null || corporation.getIfDeleted()){
            throw new RuntimeException("getById null");
        }else{
            if(corporation.getId().longValue() != corporationId.longValue()){
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
        paramMap.put("Id", corporationId);
        Corporation corporation = corporationService.getBy(paramMap);
        if(corporation.getId() != corporationId){
            throw new RuntimeException("getBy failed");
        }else{
            logger.info("getBy succeed");
        }
    }

    @Test
    public void listBy() {
        Corporation corporation = corporationDao.getById(corporationId);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", false);
        paramMap.put("Name", corporation.getName());
        List<Corporation> corporations = corporationService.listBy(paramMap);
        for (int i = 0; i < corporations.size(); i++)
            logger.info("Item" + i + ":" + corporations.get(i));
    }

    @Test
    public void listPage() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", false);
        PageParam pageParam = new PageParam(2, 2);
        PageBean<Corporation> pageBean = corporationService.listPage(pageParam, paramMap);
        logger.info(pageBean);
        List<Corporation> corporations = pageBean.getRecordList();
        for (int i = 0; i < corporations.size(); i++)
            logger.info("Item" + i + ":" + corporations.get(i));
    }

//    @Test
//    public void createWithUser() {
//        Corporation corporation = new Corporation();
//        corporation.setCorporation(1L);
//        User user = new User();
//        user.setUserName(StringUtils.getRandomStr(8));
//        user.setPassWord("d123456");
//        user.setPhone("188" + StringUtils.getRandomStr(8, StringUtils.StrType.NUMBER));
//        user.setCorporation(1L);
//        corporationService.createWithUser(corporation, user);
//        if(user.getUserType() != UserType.Corporation || user.getUserInfo().longValue() != corporation.getId().longValue()){
//            throw new RuntimeException("createWithUser failed");
//        }else {
//            logger.info("createWithUser succeed : CorporationID:" + corporation.getId() + ", userID:" + user.getId());
//        }
//    }

    @Test
    public void updateByMap() {
        String new_name = StringUtils.getRandomStr(6);
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("name", new_name);
        corporationService.updateByMap(corporationId, paramMap);
        Corporation corporation_check = corporationDao.getById(corporationId);
        if(!corporation_check.getName().equals(new_name)){
            throw new RuntimeException("updateByMap failed");
        }else{
            logger.info("updateByMap succeed");
        }
    }

    @Test
    public void deleteByUserId() {
        corporationService.deleteByUserId(userId);
        User user_check = userDao.getById(userId);
        if(user_check == null || !user_check.getIfDeleted() || user_check.getUserType().getValue().intValue() != UserType.Corporation.getValue().intValue()){
            throw new RuntimeException("deleteByUserId failed");
        }
        Corporation corporation_check = corporationDao.getById(user_check.getUserInfo());
        if(corporation_check == null || !corporation_check.getIfDeleted()){
            throw new RuntimeException("deleteByUserId failed");
        }
        logger.info("deleteByUserId succeed");
    }

    @Test
    public void createByMap() {
        String token = "superadminabcd1234";
        User user = new User();
        user.setUserName(StringUtils.getRandomStr(8));
        user.setPassWord("d123456");
        user.setPhone("188" + StringUtils.getRandomStr(8, StringUtils.StrType.NUMBER));
        user.setCorporation(1L);
        Map<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("name", StringUtils.getRandomStr(8));
        Long user_id = corporationService.create(token, user, paramMap);
        User user_check = userDao.getById(user_id);
        if(user_check.getUserType() != UserType.Corporation || user_check.getUserInfo() == null){
            throw new RuntimeException("createWithUser failed");
        }else {
            logger.info("createWithUser succeed : userID:" + user.getId());
        }
    }
}