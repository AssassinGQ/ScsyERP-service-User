package TestMyBatis.Dao;

import cn.AssassinG.ScsyERP.User.core.dao.CustomerDao;
import cn.AssassinG.ScsyERP.User.facade.entity.Customer;
import cn.AssassinG.ScsyERP.common.page.PageBean;
import cn.AssassinG.ScsyERP.common.page.PageParam;
import cn.AssassinG.ScsyERP.common.utils.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-context.xml"})
public class TestCustomer {
    private static Logger logger = Logger.getLogger(TestCustomer.class);
    @Autowired
    private CustomerDao customerDao;

    @Test
    public void testGetById() {
        Long customer_id = 1L;
        Customer customer = customerDao.getById(customer_id);
        if(customer == null || customer.getId() == null || customer.getId().longValue() != customer_id.longValue()){
            throw  new RuntimeException("getById failed");
        }else {
            logger.info("GetById success");
        }
    }

    @Test
    public void testInsert() {
        Customer customer = new Customer();
        customer.setName("asddf");
        customer.setCorporation(1L);
        customerDao.insert(customer);
        Long id = customer.getId();
        if(id == null){
            throw new RuntimeException("insert nothing");
        }else {
            logger.info("Inserted : " + customerDao.getById(id));
        }
    }

    @Test
    public void testBatchInsert() {
        Customer customer = new Customer();
        customer.setName("asddf3");
        customer.setCorporation(1L);
        Customer customer1 = new Customer();
        customer1.setName("asddf4");
        customer1.setCorporation(1L);
        List<Customer> customers = new ArrayList<Customer>();
        customers.add(customer);
        customers.add(customer1);
        int result = customerDao.insert(customers);
        if(result != customers.size()){
            throw new RuntimeException("BatchInsert failed " + (customers.size()-result) + "s, succeed " + result + "s");
        }else if(customer.getId() == null || customer1.getId() == null){
            throw new RuntimeException("BatchInsert failed");
        }else{
            logger.info("BatchInserted succeed");
        }
    }

    @Test
    public void testUpdate() {
        Customer customer = customerDao.getById(2);
        String new_name = StringUtils.getRandomStr(6);
        logger.info("Before Update: "+customer);
        customer.setName(new_name);
        customerDao.update(customer);
        Customer customer_check = customerDao.getById(2);
        if(!customer_check.getName().equals(new_name)){
            throw new RuntimeException("Update failed");
        }else{
            logger.info("Updated succeed");
        }
    }

    @Test
    public void testBatchUpdate() {
        Customer customer2 = customerDao.getById(2);
        Customer customer4 = customerDao.getById(4);
        String new_name_1 = StringUtils.getRandomStr(6);
        String new_name_2 = StringUtils.getRandomStr(6);
        customer2.setName(new_name_1);
        customer4.setName(new_name_2);
        List<Customer> customers = new ArrayList<Customer>();
        customers.add(customer2);
        customers.add(customer4);
        customerDao.update(customers);
        Customer customer2_check = customerDao.getById(2);
        Customer customer4_check = customerDao.getById(4);
        if(!customer2_check.getName().equals(new_name_1)){
            throw new RuntimeException("Customer2 updateByMap failed");
        }
        if(!customer4_check.getName().equals(new_name_2)){
            throw new RuntimeException("Customer4 updateByMap failed");
        }
        if(customer2_check.getName().equals(new_name_1) && customer4_check.getName().equals(new_name_2)){
            logger.info("BatchUpdated succeed");
        }
    }

    @Test
    public void testDeleteById() {
        Long delete_id = 2L;
        customerDao.delete(delete_id);
        Customer customer_check = customerDao.getById(delete_id);
        if(!customer_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void testDelete() {
        Long delete_id = 4L;
        Customer customer = customerDao.getById(delete_id);
        customerDao.delete(customer);
        Customer customer_check = customerDao.getById(delete_id);
        if(!customer_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void testListAll() {
        List<Customer> customers = customerDao.listAll();
        for (int i = 0; i < customers.size(); i++)
            logger.info("Item" + i + ":" + customers.get(i));
    }

    @Test
    public void testGetBy() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", true);
        paramMap.put("Id", 1L);
        Customer customer = customerDao.getBy(paramMap);
        if(customer.getId().longValue() != 1L){
            throw new RuntimeException("GetBy failed");
        }else{
            logger.info("GetBy succeed");
        }
    }

    @Test
    public void testListBy() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("IsDeleted", false);
        paramMap.put("CustomerName", "admi");
        List<Customer> customers = customerDao.listBy(paramMap);
        for (int i = 0; i < customers.size(); i++)
            logger.info("Item" + i + ":" + customers.get(i));
    }

    @Test
    public void testListPage() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", false);
        PageParam pageParam = new PageParam(2, 2);
        PageBean<Customer> pageBean = customerDao.listPage(pageParam, paramMap);
        logger.info(pageBean);
        List<Customer> customers = pageBean.getRecordList();
//        if(customers.size() != 2){
//            throw new RuntimeException("ListPage failed");
//        }
        for (int i = 0; i < customers.size(); i++)
            logger.info("Item" + i + ":" + customers.get(i));
    }
}
