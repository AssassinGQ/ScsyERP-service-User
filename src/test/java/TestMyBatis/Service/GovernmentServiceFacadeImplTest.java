package TestMyBatis.Service;

import cn.AssassinG.ScsyERP.User.core.biz.GovernmentBiz;
import cn.AssassinG.ScsyERP.User.core.biz.UserBiz;
import cn.AssassinG.ScsyERP.User.core.dao.GovernmentDao;
import cn.AssassinG.ScsyERP.User.core.dao.UserDao;
import cn.AssassinG.ScsyERP.User.facade.entity.Government;
import cn.AssassinG.ScsyERP.User.facade.entity.User;
import cn.AssassinG.ScsyERP.User.facade.enums.GovernmentDeptType;
import cn.AssassinG.ScsyERP.User.facade.enums.UserType;
import cn.AssassinG.ScsyERP.User.facade.service.GovernmentServiceFacade;
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
public class GovernmentServiceFacadeImplTest {
    private static Logger logger = Logger.getLogger(GovernmentServiceFacadeImplTest.class);
    @Autowired
    private UserBiz userBiz;
    @Autowired
    private UserDao userDao;
    @Autowired
    private GovernmentServiceFacade governmentService;
    @Autowired
    private GovernmentBiz governmentBiz;
    @Autowired
    private GovernmentDao governmentDao;

    private Long governmentId;
    private Long userId;

    @Before
    public void setUp() throws Exception {
        Government government = new Government();
        government.setCorporation(1L);
        government.setName(StringUtils.getRandomStr(6));
        government.setDept(GovernmentDeptType.JJB);
        governmentId = governmentBiz.create(government);
        User user = new User();
        user.setUserName(StringUtils.getRandomStr(6));
        user.setPassWord("d123456");
        user.setPhone("188" + StringUtils.getRandomStr(8, StringUtils.StrType.NUMBER));
        user.setCorporation(1L);
        user.setUserType(UserType.getEnumByClassName(government.getClass().getName()));
        user.setUserInfo(governmentId);
        userId = userBiz.create(user);
    }

    @After
    public void tearDown() throws Exception {
        governmentDao.delete(governmentId);
        userDao.delete(userId);
    }

    @Test
    public void create() {
        Government government = new Government();
        government.setCorporation(1L);
        government.setName(StringUtils.getRandomStr(6));
        Long id = governmentService.create(government);
        if(id == null){
            throw new RuntimeException("Create nothing");
        }else {
            logger.info("Created : " + governmentDao.getById(id));
        }
    }

    @Test
    public void update() {
        Government government = governmentDao.getById(governmentId);
        String new_name = StringUtils.getRandomStr(6);
        logger.info("Before Update: "+government);
        government.setName(new_name);
        governmentService.update(government);
        Government government_check = governmentDao.getById(governmentId);
        if(!government_check.getName().equals(new_name)){
            throw new RuntimeException("Update failed");
        }else{
            logger.info("Updated succeed");
        }
    }

    @Test
    public void deleteById() {
        governmentService.deleteById(governmentId);
        Government government_check = governmentDao.getById(governmentId);
        if(!government_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void delete() {
        Government government_test = governmentDao.getById(governmentId);
        governmentService.delete(government_test);
        Government government_check = governmentDao.getById(governmentId);
        if(!government_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void getById() {
        Government government = governmentService.getById(governmentId);
        if(government == null || government.getIfDeleted()){
            throw new RuntimeException("getById null");
        }else{
            if(government.getId().longValue() != governmentId.longValue()){
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
        paramMap.put("Id", governmentId);
        Government government = governmentService.getBy(paramMap);
        if(government.getId() != governmentId){
            throw new RuntimeException("getBy failed");
        }else{
            logger.info("getBy succeed");
        }
    }

    @Test
    public void listBy() {
        Government government = governmentDao.getById(governmentId);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", false);
        paramMap.put("Name", government.getName());
        List<Government> governments = governmentService.listBy(paramMap);
        for (int i = 0; i < governments.size(); i++)
            logger.info("Item" + i + ":" + governments.get(i));
    }

    @Test
    public void listPage() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", false);
        PageParam pageParam = new PageParam(2, 2);
        PageBean<Government> pageBean = governmentService.listPage(pageParam, paramMap);
        logger.info(pageBean);
        List<Government> governments = pageBean.getRecordList();
        for (int i = 0; i < governments.size(); i++)
            logger.info("Item" + i + ":" + governments.get(i));
    }

//    @Test
//    public void createWithUser() {
//        Government government = new Government();
//        government.setGovernment(1L);
//        User user = new User();
//        user.setUserName(StringUtils.getRandomStr(8));
//        user.setPassWord("d123456");
//        user.setPhone("188" + StringUtils.getRandomStr(8, StringUtils.StrType.NUMBER));
//        user.setGovernment(1L);
//        governmentService.createWithUser(government, user);
//        if(user.getUserType() != UserType.Government || user.getUserInfo().longValue() != government.getId().longValue()){
//            throw new RuntimeException("createWithUser failed");
//        }else {
//            logger.info("createWithUser succeed : GovernmentID:" + government.getId() + ", userID:" + user.getId());
//        }
//    }

    @Test
    public void updateByMap() {
        String new_name = StringUtils.getRandomStr(6);
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("name", new_name);
        governmentService.updateByMap(governmentId, paramMap);
        Government government_check = governmentDao.getById(governmentId);
        if(!government_check.getName().equals(new_name)){
            throw new RuntimeException("updateByMap failed");
        }else{
            logger.info("updateByMap succeed");
        }
    }

    @Test
    public void deleteByUserId() {
        governmentService.deleteByUserId(userId);
        User user_check = userDao.getById(userId);
        if(user_check == null || !user_check.getIfDeleted() || user_check.getUserType().getValue().intValue() != UserType.Government.getValue().intValue()){
            throw new RuntimeException("deleteByUserId failed");
        }
        Government government_check = governmentDao.getById(user_check.getUserInfo());
        if(government_check == null || !government_check.getIfDeleted()){
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
        paramMap.put("dept", GovernmentDeptType.AJB);
        paramMap.put("name", StringUtils.getRandomStr(8));
        Long user_id = governmentService.create(token, user, paramMap);
        User user_check = userDao.getById(user_id);
        if(user_check.getUserType() != UserType.Government || user_check.getUserInfo() == null){
            throw new RuntimeException("createWithUser failed");
        }else {
            logger.info("createWithUser succeed : userID:" + user.getId());
        }
    }
}