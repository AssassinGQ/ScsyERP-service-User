package TestMyBatis.Service;

import cn.AssassinG.ScsyERP.User.core.biz.CustomerBiz;
import cn.AssassinG.ScsyERP.User.core.biz.UserBiz;
import cn.AssassinG.ScsyERP.User.core.dao.CustomerDao;
import cn.AssassinG.ScsyERP.User.core.dao.UserDao;
import cn.AssassinG.ScsyERP.User.facade.entity.Customer;
import cn.AssassinG.ScsyERP.User.facade.entity.User;
import cn.AssassinG.ScsyERP.User.facade.enums.UserType;
import cn.AssassinG.ScsyERP.User.facade.service.CustomerServiceFacade;
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
public class EscortServiceFacadeImplTest {
    private static Logger logger = Logger.getLogger(EscortServiceFacadeImplTest.class);
    @Autowired
    private UserBiz userBiz;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CustomerServiceFacade customerService;
    @Autowired
    private CustomerBiz customerBiz;
    @Autowired
    private CustomerDao customerDao;

    private Long customerId;
    private Long userId;

    @Before
    public void setUp() throws Exception {
        Customer customer = new Customer();
        customer.setCorporation(1L);
        customer.setName(StringUtils.getRandomStr(6));
        customerId = customerBiz.create(customer);
        User user = new User();
        user.setUserName(StringUtils.getRandomStr(6));
        user.setPassWord("d123456");
        user.setPhone("188" + StringUtils.getRandomStr(8, StringUtils.StrType.NUMBER));
        user.setCorporation(1L);
        user.setUserType(UserType.getEnumByClassName(customer.getClass().getName()));
        user.setUserInfo(customerId);
        userId = userBiz.create(user);
    }

    @After
    public void tearDown() throws Exception {
        customerDao.delete(customerId);
        userDao.delete(userId);
    }

    @Test
    public void create() {
        Customer customer = new Customer();
        customer.setCorporation(1L);
        customer.setName(StringUtils.getRandomStr(6));
        Long id = customerService.create(customer);
        if(id == null){
            throw new RuntimeException("Create nothing");
        }else {
            logger.info("Created : " + customerDao.getById(id));
        }
    }

    @Test
    public void update() {
        Customer customer = customerDao.getById(customerId);
        String new_name = StringUtils.getRandomStr(6);
        logger.info("Before Update: "+customer);
        customer.setName(new_name);
        customerService.update(customer);
        Customer customer_check = customerDao.getById(customerId);
        if(!customer_check.getName().equals(new_name)){
            throw new RuntimeException("Update failed");
        }else{
            logger.info("Updated succeed");
        }
    }

    @Test
    public void deleteById() {
        customerService.deleteById(customerId);
        Customer customer_check = customerDao.getById(customerId);
        if(!customer_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void delete() {
        Customer customer_test = customerDao.getById(customerId);
        customerService.delete(customer_test);
        Customer customer_check = customerDao.getById(customerId);
        if(!customer_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void getById() {
        Customer customer = customerService.getById(customerId);
        if(customer == null || customer.getIfDeleted()){
            throw new RuntimeException("getById null");
        }else{
            if(customer.getId().longValue() != customerId.longValue()){
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
        paramMap.put("Id", customerId);
        Customer customer = customerService.getBy(paramMap);
        if(customer.getId() != customerId){
            throw new RuntimeException("getBy failed");
        }else{
            logger.info("getBy succeed");
        }
    }

    @Test
    public void listBy() {
        Customer customer = customerDao.getById(customerId);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", false);
        paramMap.put("Name", customer.getName());
        List<Customer> customers = customerService.listBy(paramMap);
        for (int i = 0; i < customers.size(); i++)
            logger.info("Item" + i + ":" + customers.get(i));
    }

    @Test
    public void listPage() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", false);
        PageParam pageParam = new PageParam(2, 2);
        PageBean<Customer> pageBean = customerService.listPage(pageParam, paramMap);
        logger.info(pageBean);
        List<Customer> customers = pageBean.getRecordList();
        for (int i = 0; i < customers.size(); i++)
            logger.info("Item" + i + ":" + customers.get(i));
    }

    @Test
    public void createWithUser() {
        Customer customer = new Customer();
        customer.setCorporation(1L);
        User user = new User();
        user.setUserName(StringUtils.getRandomStr(8));
        user.setPassWord("d123456");
        user.setPhone("188" + StringUtils.getRandomStr(8, StringUtils.StrType.NUMBER));
        user.setCorporation(1L);
        customerService.createWithUser(customer, user);
        if(user.getUserType() != UserType.Customer || user.getUserInfo().longValue() != customer.getId().longValue()){
            throw new RuntimeException("createWithUser failed");
        }else {
            logger.info("createWithUser succeed : CustomerID:" + customer.getId() + ", userID:" + user.getId());
        }
    }

    @Test
    public void updateByMap() {
        String new_name = StringUtils.getRandomStr(6);
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("name", new_name);
        customerService.updateByMap(customerId, paramMap);
        Customer customer_check = customerDao.getById(customerId);
        if(!customer_check.getName().equals(new_name)){
            throw new RuntimeException("updateByMap failed");
        }else{
            logger.info("updateByMap succeed");
        }
    }

    @Test
    public void deleteByUserId() {
        customerService.deleteByUserId(userId);
        User user_check = userDao.getById(userId);
        if(user_check == null || !user_check.getIfDeleted() || user_check.getUserType().getValue().intValue() != UserType.Customer.getValue().intValue()){
            throw new RuntimeException("deleteByUserId failed");
        }
        Customer customer_check = customerDao.getById(user_check.getUserInfo());
        if(customer_check == null || !customer_check.getIfDeleted()){
            throw new RuntimeException("deleteByUserId failed");
        }
        logger.info("deleteByUserId succeed");
    }
}