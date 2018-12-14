package TestMyBatis.Dao;

import cn.AssassinG.ScsyERP.User.core.dao.CorporationDao;
import cn.AssassinG.ScsyERP.User.facade.entity.Corporation;
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
public class TestCorporation {
    private static Logger logger = Logger.getLogger(TestCorporation.class);
    @Autowired
    private CorporationDao corporationDao;

    @Test
    public void testGetById() {
        Long corporation_id = 1L;
        Corporation corporation = corporationDao.getById(corporation_id);
        if(corporation == null || corporation.getId() == null || corporation.getId().longValue() != corporation_id.longValue()){
            throw  new RuntimeException("getById failed");
        }else {
            logger.info("GetById success");
        }
    }

    @Test
    public void testInsert() {
        Corporation corporation = new Corporation();
        corporation.setName("asddf");
        corporation.setCorporation(-1L);
        corporationDao.insert(corporation);
        Long id = corporation.getId();
        if(id == null){
            throw new RuntimeException("insert nothing");
        }else {
            logger.info("Inserted : " + corporationDao.getById(id));
        }
    }

    @Test
    public void testBatchInsert() {
        Corporation corporation = new Corporation();
        corporation.setName("asddf3");
        corporation.setCorporation(-1L);
        Corporation corporation1 = new Corporation();
        corporation1.setName("asddf4");
        corporation1.setCorporation(-1L);
        List<Corporation> corporations = new ArrayList<Corporation>();
        corporations.add(corporation);
        corporations.add(corporation1);
        int result = corporationDao.insert(corporations);
        if(result != corporations.size()){
            throw new RuntimeException("BatchInsert failed " + (corporations.size()-result) + "s, succeed " + result + "s");
        }else if(corporation.getId() == null || corporation1.getId() == null){
            throw new RuntimeException("BatchInsert failed");
        }else{
            logger.info("BatchInserted succeed");
        }
    }

    @Test
    public void testUpdate() {
        Corporation corporation = corporationDao.getById(2);
        String new_name = StringUtils.getRandomStr(6);
        logger.info("Before Update: "+corporation);
        corporation.setName(new_name);
        corporationDao.update(corporation);
        Corporation corporation_check = corporationDao.getById(2);
        if(!corporation_check.getName().equals(new_name)){
            throw new RuntimeException("Update failed");
        }else{
            logger.info("Updated succeed");
        }
    }

    @Test
    public void testBatchUpdate() {
        Corporation corporation2 = corporationDao.getById(2);
        Corporation corporation4 = corporationDao.getById(4);
        String new_name_1 = StringUtils.getRandomStr(6);
        String new_name_2 = StringUtils.getRandomStr(6);
        corporation2.setName(new_name_1);
        corporation4.setName(new_name_2);
        List<Corporation> corporations = new ArrayList<Corporation>();
        corporations.add(corporation2);
        corporations.add(corporation4);
        corporationDao.update(corporations);
        Corporation corporation2_check = corporationDao.getById(2);
        Corporation corporation4_check = corporationDao.getById(4);
        if(!corporation2_check.getName().equals(new_name_1)){
            throw new RuntimeException("Corporation2 updateByMap failed");
        }
        if(!corporation4_check.getName().equals(new_name_2)){
            throw new RuntimeException("Corporation4 updateByMap failed");
        }
        if(corporation2_check.getName().equals(new_name_1) && corporation4_check.getName().equals(new_name_2)){
            logger.info("BatchUpdated succeed");
        }
    }

    @Test
    public void testDeleteById() {
        Long delete_id = 2L;
        corporationDao.delete(delete_id);
        Corporation corporation_check = corporationDao.getById(delete_id);
        if(!corporation_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void testDelete() {
        Long delete_id = 4L;
        Corporation corporation = corporationDao.getById(delete_id);
        corporationDao.delete(corporation);
        Corporation corporation_check = corporationDao.getById(delete_id);
        if(!corporation_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void testListAll() {
        List<Corporation> corporations = corporationDao.listAll();
        for (int i = 0; i < corporations.size(); i++)
            logger.info("Item" + i + ":" + corporations.get(i));
    }

    @Test
    public void testGetBy() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", true);
        paramMap.put("Id", 1L);
        Corporation corporation = corporationDao.getBy(paramMap);
        if(corporation.getId().longValue() != 1L){
            throw new RuntimeException("GetBy failed");
        }else{
            logger.info("GetBy succeed");
        }
    }

    @Test
    public void testListBy() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("IsDeleted", false);
        paramMap.put("CorporationName", "admi");
        List<Corporation> corporations = corporationDao.listBy(paramMap);
        for (int i = 0; i < corporations.size(); i++)
            logger.info("Item" + i + ":" + corporations.get(i));
    }

    @Test
    public void testListPage() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", false);
        PageParam pageParam = new PageParam(2, 2);
        PageBean<Corporation> pageBean = corporationDao.listPage(pageParam, paramMap);
        logger.info(pageBean);
        List<Corporation> corporations = pageBean.getRecordList();
//        if(corporations.size() != 2){
//            throw new RuntimeException("ListPage failed");
//        }
        for (int i = 0; i < corporations.size(); i++)
            logger.info("Item" + i + ":" + corporations.get(i));
    }
}
