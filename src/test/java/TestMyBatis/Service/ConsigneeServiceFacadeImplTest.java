package TestMyBatis.Service;

import cn.AssassinG.ScsyERP.User.core.biz.ConsigneeBiz;
import cn.AssassinG.ScsyERP.User.core.biz.UserBiz;
import cn.AssassinG.ScsyERP.User.core.dao.ConsigneeDao;
import cn.AssassinG.ScsyERP.User.core.dao.UserDao;
import cn.AssassinG.ScsyERP.User.facade.entity.Consignee;
import cn.AssassinG.ScsyERP.User.facade.entity.User;
import cn.AssassinG.ScsyERP.User.facade.enums.UserType;
import cn.AssassinG.ScsyERP.User.facade.service.ConsigneeServiceFacade;
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
public class ConsigneeServiceFacadeImplTest {
    private static Logger logger = Logger.getLogger(ConsigneeServiceFacadeImplTest.class);
    @Autowired
    private UserBiz userBiz;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ConsigneeServiceFacade consigneeService;
    @Autowired
    private ConsigneeBiz consigneeBiz;
    @Autowired
    private ConsigneeDao consigneeDao;

    private Long consigneeId;
    private Long userId;

    @Before
    public void setUp() throws Exception {
        Consignee consignee = new Consignee();
        consignee.setCorporation(1L);
        consignee.setAddress(StringUtils.getRandomStr(20));
        consignee.setName(StringUtils.getRandomStr(6));
        consigneeId = consigneeBiz.create(consignee);
        User user = new User();
        user.setUserName(StringUtils.getRandomStr(6));
        user.setPassWord("d123456");
        user.setPhone("188" + StringUtils.getRandomStr(8, StringUtils.StrType.NUMBER));
        user.setCorporation(1L);
        user.setUserType(UserType.getEnumByClassName(consignee.getClass().getName()));
        user.setUserInfo(consigneeId);
        userId = userBiz.create(user);
    }

    @After
    public void tearDown() throws Exception {
        consigneeDao.delete(consigneeId);
        userDao.delete(userId);
    }

    @Test
    public void create() {
        Consignee consignee = new Consignee();
        consignee.setCorporation(1L);
        consignee.setName(StringUtils.getRandomStr(6));
        Long id = consigneeService.create(consignee);
        if(id == null){
            throw new RuntimeException("Create nothing");
        }else {
            logger.info("Created : " + consigneeDao.getById(id));
        }
    }

    @Test
    public void update() {
        Consignee consignee = consigneeDao.getById(consigneeId);
        String new_name = StringUtils.getRandomStr(6);
        logger.info("Before Update: "+consignee);
        consignee.setName(new_name);
        consigneeService.update(consignee);
        Consignee consignee_check = consigneeDao.getById(consigneeId);
        if(!consignee_check.getName().equals(new_name)){
            throw new RuntimeException("Update failed");
        }else{
            logger.info("Updated succeed");
        }
    }

    @Test
    public void deleteById() {
        consigneeService.deleteById(consigneeId);
        Consignee consignee_check = consigneeDao.getById(consigneeId);
        if(!consignee_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void delete() {
        Consignee consignee_test = consigneeDao.getById(consigneeId);
        consigneeService.delete(consignee_test);
        Consignee consignee_check = consigneeDao.getById(consigneeId);
        if(!consignee_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void getById() {
        Consignee consignee = consigneeService.getById(consigneeId);
        if(consignee == null || consignee.getIfDeleted()){
            throw new RuntimeException("getById null");
        }else{
            if(consignee.getId().longValue() != consigneeId.longValue()){
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
        paramMap.put("Id", consigneeId);
        Consignee consignee = consigneeService.getBy(paramMap);
        if(consignee.getId() != consigneeId){
            throw new RuntimeException("getBy failed");
        }else{
            logger.info("getBy succeed");
        }
    }

    @Test
    public void listBy() {
        Consignee consignee = consigneeDao.getById(consigneeId);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", false);
        paramMap.put("Name", consignee.getName());
        List<Consignee> consignees = consigneeService.listBy(paramMap);
        for (int i = 0; i < consignees.size(); i++)
            logger.info("Item" + i + ":" + consignees.get(i));
    }

    @Test
    public void listPage() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", false);
        PageParam pageParam = new PageParam(2, 2);
        PageBean<Consignee> pageBean = consigneeService.listPage(pageParam, paramMap);
        logger.info(pageBean);
        List<Consignee> consignees = pageBean.getRecordList();
        for (int i = 0; i < consignees.size(); i++)
            logger.info("Item" + i + ":" + consignees.get(i));
    }

    @Test
    public void createWithUser() {
        Consignee consignee = new Consignee();
        consignee.setCorporation(1L);
        consignee.setAddress(StringUtils.getRandomStr(20));
        User user = new User();
        user.setUserName(StringUtils.getRandomStr(8));
        user.setPassWord("d123456");
        user.setPhone("188" + StringUtils.getRandomStr(8, StringUtils.StrType.NUMBER));
        user.setCorporation(1L);
        consigneeService.createWithUser(consignee, user);
        if(user.getUserType() != UserType.Consignee || user.getUserInfo().longValue() != consignee.getId().longValue()){
            throw new RuntimeException("createWithUser failed");
        }else {
            logger.info("createWithUser succeed : ConsigneeID:" + consignee.getId() + ", userID:" + user.getId());
        }
    }

    @Test
    public void updateByMap() {
        String new_name = StringUtils.getRandomStr(6);
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("name", new_name);
        consigneeService.updateByMap(consigneeId, paramMap);
        Consignee consignee_check = consigneeDao.getById(consigneeId);
        if(!consignee_check.getName().equals(new_name)){
            throw new RuntimeException("updateByMap failed");
        }else{
            logger.info("updateByMap succeed");
        }
    }

    @Test
    public void deleteByUserId() {
        consigneeService.deleteByUserId(userId);
        User user_check = userDao.getById(userId);
        if(user_check == null || !user_check.getIfDeleted() || user_check.getUserType().getValue().intValue() != UserType.Consignee.getValue().intValue()){
            throw new RuntimeException("deleteByUserId failed");
        }
        Consignee consignee_check = consigneeDao.getById(user_check.getUserInfo());
        if(consignee_check == null || !consignee_check.getIfDeleted()){
            throw new RuntimeException("deleteByUserId failed");
        }
        logger.info("deleteByUserId succeed");
    }
}