package TestMyBatis.Dao;

import cn.AssassinG.ScsyERP.User.core.dao.GovernmentDao;
import cn.AssassinG.ScsyERP.User.facade.entity.Government;
import cn.AssassinG.ScsyERP.User.facade.enums.GovernmentDeptType;
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
public class TestGovernment {
    private static Logger logger = Logger.getLogger(TestGovernment.class);
    @Autowired
    private GovernmentDao governmentDao;

    @Test
    public void testGetById() {
        Long government_id = 1L;
        Government government = governmentDao.getById(government_id);
        if(government == null || government.getId() == null || government.getId().longValue() != government_id.longValue()){
            throw  new RuntimeException("getById failed");
        }else {
            logger.info("GetById success");
        }
    }

    @Test
    public void testInsert() {
        Government government = new Government(GovernmentDeptType.AJB);
        government.setName("asddf");
        government.setCorporation(-1L);
        governmentDao.insert(government);
        Long id = government.getId();
        if(id == null){
            throw new RuntimeException("insert nothing");
        }else {
            logger.info("Inserted : " + governmentDao.getById(id));
        }
    }

    @Test
    public void testBatchInsert() {
        Government government = new Government(GovernmentDeptType.HBB);
        government.setName("asddf3");
        government.setCorporation(-1L);
        Government government1 = new Government(GovernmentDeptType.JJB);
        government1.setName("asddf4");
        government1.setCorporation(-1L);
        List<Government> governments = new ArrayList<Government>();
        governments.add(government);
        governments.add(government1);
        int result = governmentDao.insert(governments);
        if(result != governments.size()){
            throw new RuntimeException("BatchInsert failed " + (governments.size()-result) + "s, succeed " + result + "s");
        }else if(government.getId() == null || government1.getId() == null){
            throw new RuntimeException("BatchInsert failed");
        }else{
            logger.info("BatchInserted succeed");
        }
    }

    @Test
    public void testUpdate() {
        Government government = governmentDao.getById(2);
        String new_name = StringUtils.getRandomStr(6);
        logger.info("Before Update: "+government);
        government.setName(new_name);
        governmentDao.update(government);
        Government government_check = governmentDao.getById(2);
        if(!government_check.getName().equals(new_name)){
            throw new RuntimeException("Update failed");
        }else{
            logger.info("Updated succeed");
        }
    }

    @Test
    public void testBatchUpdate() {
        Government government2 = governmentDao.getById(2);
        Government government4 = governmentDao.getById(4);
        String new_name_1 = StringUtils.getRandomStr(6);
        String new_name_2 = StringUtils.getRandomStr(6);
        government2.setName(new_name_1);
        government4.setName(new_name_2);
        List<Government> governments = new ArrayList<Government>();
        governments.add(government2);
        governments.add(government4);
        governmentDao.update(governments);
        Government government2_check = governmentDao.getById(2);
        Government government4_check = governmentDao.getById(4);
        if(!government2_check.getName().equals(new_name_1)){
            throw new RuntimeException("Government2 updateByMap failed");
        }
        if(!government4_check.getName().equals(new_name_2)){
            throw new RuntimeException("Government4 updateByMap failed");
        }
        if(government2_check.getName().equals(new_name_1) && government4_check.getName().equals(new_name_2)){
            logger.info("BatchUpdated succeed");
        }
    }

    @Test
    public void testDeleteById() {
        Long delete_id = 2L;
        governmentDao.delete(delete_id);
        Government government_check = governmentDao.getById(delete_id);
        if(!government_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void testDelete() {
        Long delete_id = 4L;
        Government government = governmentDao.getById(delete_id);
        governmentDao.delete(government);
        Government government_check = governmentDao.getById(delete_id);
        if(!government_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void testListAll() {
        List<Government> governments = governmentDao.listAll();
        for (int i = 0; i < governments.size(); i++)
            logger.info("Item" + i + ":" + governments.get(i));
    }

    @Test
    public void testGetBy() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", true);
        paramMap.put("Id", 1L);
        Government government = governmentDao.getBy(paramMap);
        if(government.getId() != 1L){
            throw new RuntimeException("GetBy failed");
        }else{
            logger.info("GetBy succeed");
        }
    }

    @Test
    public void testListBy() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("IsDeleted", false);
        paramMap.put("GovernmentName", "admi");
        List<Government> governments = governmentDao.listBy(paramMap);
        for (int i = 0; i < governments.size(); i++)
            logger.info("Item" + i + ":" + governments.get(i));
    }

    @Test
    public void testListPage() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", false);
        PageParam pageParam = new PageParam(2, 2);
        PageBean<Government> pageBean = governmentDao.listPage(pageParam, paramMap);
        logger.info(pageBean);
        List<Government> governments = pageBean.getRecordList();
//        if(governments.size() != 2){
//            throw new RuntimeException("ListPage failed");
//        }
        for (int i = 0; i < governments.size(); i++)
            logger.info("Item" + i + ":" + governments.get(i));
    }
}
