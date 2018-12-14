package TestMyBatis.Dao;

import cn.AssassinG.ScsyERP.User.core.dao.DriverDao;
import cn.AssassinG.ScsyERP.User.facade.entity.Driver;
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
public class TestDriver {
    private static Logger logger = Logger.getLogger(TestDriver.class);
    @Autowired
    private DriverDao driverDao;

    @Test
    public void testGetById() {
        Long driver_id = 1L;
        Driver driver = driverDao.getById(driver_id);
        if(driver == null || driver.getId() == null || driver.getId().longValue() != driver_id.longValue()){
            throw  new RuntimeException("getById failed");
        }else {
            logger.info("GetById success");
        }
    }

    @Test
    public void testInsert() {
        Driver driver = new Driver();
        driver.setName("asddf");
        driver.setCorporation(1L);
        driverDao.insert(driver);
        Long id = driver.getId();
        if(id == null){
            throw new RuntimeException("insert nothing");
        }else {
            logger.info("Inserted : " + driverDao.getById(id));
        }
    }

    @Test
    public void testBatchInsert() {
        Driver driver = new Driver();
        driver.setName("asddf3");
        driver.setCorporation(1L);
        Driver driver1 = new Driver();
        driver1.setName("asddf4");
        driver1.setCorporation(1L);
        List<Driver> drivers = new ArrayList<Driver>();
        drivers.add(driver);
        drivers.add(driver1);
        int result = driverDao.insert(drivers);
        if(result != drivers.size()){
            throw new RuntimeException("BatchInsert failed " + (drivers.size()-result) + "s, succeed " + result + "s");
        }else if(driver.getId() == null || driver1.getId() == null){
            throw new RuntimeException("BatchInsert failed");
        }else{
            logger.info("BatchInserted succeed");
        }
    }

    @Test
    public void testUpdate() {
        Driver driver = driverDao.getById(2);
        String new_name = StringUtils.getRandomStr(6);
        logger.info("Before Update: "+driver);
        driver.setName(new_name);
        driverDao.update(driver);
        Driver driver_check = driverDao.getById(2);
        if(!driver_check.getName().equals(new_name)){
            throw new RuntimeException("Update failed");
        }else{
            logger.info("Updated succeed");
        }
    }

    @Test
    public void testBatchUpdate() {
        Driver driver2 = driverDao.getById(2);
        Driver driver4 = driverDao.getById(4);
        String new_name_1 = StringUtils.getRandomStr(6);
        String new_name_2 = StringUtils.getRandomStr(6);
        driver2.setName(new_name_1);
        driver4.setName(new_name_2);
        List<Driver> drivers = new ArrayList<Driver>();
        drivers.add(driver2);
        drivers.add(driver4);
        driverDao.update(drivers);
        Driver driver2_check = driverDao.getById(2);
        Driver driver4_check = driverDao.getById(4);
        if(!driver2_check.getName().equals(new_name_1)){
            throw new RuntimeException("Driver2 updateByMap failed");
        }
        if(!driver4_check.getName().equals(new_name_2)){
            throw new RuntimeException("Driver4 updateByMap failed");
        }
        if(driver2_check.getName().equals(new_name_1) && driver4_check.getName().equals(new_name_2)){
            logger.info("BatchUpdated succeed");
        }
    }

    @Test
    public void testDeleteById() {
        Long delete_id = 2L;
        driverDao.delete(delete_id);
        Driver driver_check = driverDao.getById(delete_id);
        if(!driver_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void testDelete() {
        Long delete_id = 4L;
        Driver driver = driverDao.getById(delete_id);
        driverDao.delete(driver);
        Driver driver_check = driverDao.getById(delete_id);
        if(!driver_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void testListAll() {
        List<Driver> drivers = driverDao.listAll();
        for (int i = 0; i < drivers.size(); i++)
            logger.info("Item" + i + ":" + drivers.get(i));
    }

    @Test
    public void testGetBy() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", true);
        paramMap.put("Id", 1L);
        Driver driver = driverDao.getBy(paramMap);
        if(driver.getId() != 1L){
            throw new RuntimeException("GetBy failed");
        }else{
            logger.info("GetBy succeed");
        }
    }

    @Test
    public void testListBy() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("IsDeleted", false);
        paramMap.put("DriverName", "admi");
        List<Driver> drivers = driverDao.listBy(paramMap);
        for (int i = 0; i < drivers.size(); i++)
            logger.info("Item" + i + ":" + drivers.get(i));
    }

    @Test
    public void testListPage() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", false);
        PageParam pageParam = new PageParam(2, 2);
        PageBean<Driver> pageBean = driverDao.listPage(pageParam, paramMap);
        logger.info(pageBean);
        List<Driver> drivers = pageBean.getRecordList();
//        if(drivers.size() != 2){
//            throw new RuntimeException("ListPage failed");
//        }
        for (int i = 0; i < drivers.size(); i++)
            logger.info("Item" + i + ":" + drivers.get(i));
    }
}
