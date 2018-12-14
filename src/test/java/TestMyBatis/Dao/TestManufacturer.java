package TestMyBatis.Dao;

import cn.AssassinG.ScsyERP.User.core.dao.ManufacturerDao;
import cn.AssassinG.ScsyERP.User.facade.entity.Manufacturer;
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
public class TestManufacturer {
    private static Logger logger = Logger.getLogger(TestManufacturer.class);
    @Autowired
    private ManufacturerDao manufacturerDao;

    @Test
    public void testGetById() {
        Long manufacturer_id = 2L;
        Manufacturer manufacturer = manufacturerDao.getById(manufacturer_id);
        if(manufacturer == null || manufacturer.getId() == null || manufacturer.getId().longValue() != manufacturer_id.longValue()){
            throw  new RuntimeException("getById failed");
        }else {
            logger.info("GetById success");
        }
    }

    @Test
    public void testInsert() {
        Manufacturer manufacturer = new Manufacturer("浙江杭州");
        manufacturer.setName("asddf");
        manufacturer.setCorporation(1L);
        manufacturerDao.insert(manufacturer);
        Long id = manufacturer.getId();
        if(id == null){
            throw new RuntimeException("insert nothing");
        }else {
            logger.info("Inserted : " + manufacturerDao.getById(id));
        }
    }

    @Test
    public void testBatchInsert() {
        Manufacturer manufacturer = new Manufacturer("浙江嘉兴");
        manufacturer.setName("asddf3");
        manufacturer.setCorporation(1L);
        Manufacturer manufacturer1 = new Manufacturer("浙江绍兴");
        manufacturer1.setName("asddf4");
        manufacturer1.setCorporation(1L);
        List<Manufacturer> manufacturers = new ArrayList<Manufacturer>();
        manufacturers.add(manufacturer);
        manufacturers.add(manufacturer1);
        int result = manufacturerDao.insert(manufacturers);
        if(result != manufacturers.size()){
            throw new RuntimeException("BatchInsert failed " + (manufacturers.size()-result) + "s, succeed " + result + "s");
        }else if(manufacturer.getId() == null || manufacturer1.getId() == null){
            throw new RuntimeException("BatchInsert failed");
        }else{
            logger.info("BatchInserted succeed");
        }
    }

    @Test
    public void testUpdate() {
        Manufacturer manufacturer = manufacturerDao.getById(2);
        String new_name = StringUtils.getRandomStr(6);
        logger.info("Before Update: "+manufacturer);
        manufacturer.setName(new_name);
        manufacturerDao.update(manufacturer);
        Manufacturer manufacturer_check = manufacturerDao.getById(2);
        if(!manufacturer_check.getName().equals(new_name)){
            throw new RuntimeException("Update failed");
        }else{
            logger.info("Updated succeed");
        }
    }

    @Test
    public void testBatchUpdate() {
        Manufacturer manufacturer2 = manufacturerDao.getById(2);
        Manufacturer manufacturer4 = manufacturerDao.getById(4);
        String new_name_1 = StringUtils.getRandomStr(6);
        String new_name_2 = StringUtils.getRandomStr(6);
        manufacturer2.setName(new_name_1);
        manufacturer4.setName(new_name_2);
        List<Manufacturer> manufacturers = new ArrayList<Manufacturer>();
        manufacturers.add(manufacturer2);
        manufacturers.add(manufacturer4);
        manufacturerDao.update(manufacturers);
        Manufacturer manufacturer2_check = manufacturerDao.getById(2);
        Manufacturer manufacturer4_check = manufacturerDao.getById(4);
        if(!manufacturer2_check.getName().equals(new_name_1)){
            throw new RuntimeException("Manufacturer2 updateByMap failed");
        }
        if(!manufacturer4_check.getName().equals(new_name_2)){
            throw new RuntimeException("Manufacturer4 updateByMap failed");
        }
        if(manufacturer2_check.getName().equals(new_name_1) && manufacturer4_check.getName().equals(new_name_2)){
            logger.info("BatchUpdated succeed");
        }
    }

    @Test
    public void testDeleteById() {
        Long delete_id = 2L;
        manufacturerDao.delete(delete_id);
        Manufacturer manufacturer_check = manufacturerDao.getById(delete_id);
        if(!manufacturer_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void testDelete() {
        Long delete_id = 4L;
        Manufacturer manufacturer = manufacturerDao.getById(delete_id);
        manufacturerDao.delete(manufacturer);
        Manufacturer manufacturer_check = manufacturerDao.getById(delete_id);
        if(!manufacturer_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void testListAll() {
        List<Manufacturer> manufacturers = manufacturerDao.listAll();
        for (int i = 0; i < manufacturers.size(); i++)
            logger.info("Item" + i + ":" + manufacturers.get(i));
    }

    @Test
    public void testGetBy() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", false);
        paramMap.put("Id", 1L);
        Manufacturer manufacturer = manufacturerDao.getBy(paramMap);
        if(manufacturer.getId().longValue() != 1L){
            throw new RuntimeException("GetBy failed");
        }else{
            logger.info("GetBy succeed");
        }
    }

    @Test
    public void testListBy() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("IsDeleted", false);
        paramMap.put("ManufacturerName", "admi");
        List<Manufacturer> manufacturers = manufacturerDao.listBy(paramMap);
        for (int i = 0; i < manufacturers.size(); i++)
            logger.info("Item" + i + ":" + manufacturers.get(i));
    }

    @Test
    public void testListPage() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", false);
        PageParam pageParam = new PageParam(2, 2);
        PageBean<Manufacturer> pageBean = manufacturerDao.listPage(pageParam, paramMap);
        logger.info(pageBean);
        List<Manufacturer> manufacturers = pageBean.getRecordList();
//        if(manufacturers.size() != 2){
//            throw new RuntimeException("ListPage failed");
//        }
        for (int i = 0; i < manufacturers.size(); i++)
            logger.info("Item" + i + ":" + manufacturers.get(i));
    }
}
