package TestMyBatis.Dao;

import cn.AssassinG.ScsyERP.User.core.dao.EscortDao;
import cn.AssassinG.ScsyERP.User.facade.entity.Escort;
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
public class TestEscort {
    private static Logger logger = Logger.getLogger(TestEscort.class);
    @Autowired
    private EscortDao escortDao;

    @Test
    public void testGetById() {
        Long escort_id = 1L;
        Escort escort = escortDao.getById(escort_id);
        if(escort == null || escort.getId() == null || escort.getId().longValue() != escort_id.longValue()){
            throw  new RuntimeException("getById failed");
        }else {
            logger.info("GetById success");
        }
    }

    @Test
    public void testInsert() {
        Escort escort = new Escort();
        escort.setName("asddf");
        escort.setCorporation(1L);
        escortDao.insert(escort);
        Long id = escort.getId();
        if(id == null){
            throw new RuntimeException("insert nothing");
        }else {
            logger.info("Inserted : " + escortDao.getById(id));
        }
    }

    @Test
    public void testBatchInsert() {
        Escort escort = new Escort();
        escort.setName("asddf3");
        escort.setCorporation(1L);
        Escort escort1 = new Escort();
        escort1.setName("asddf4");
        escort1.setCorporation(1L);
        List<Escort> escorts = new ArrayList<Escort>();
        escorts.add(escort);
        escorts.add(escort1);
        int result = escortDao.insert(escorts);
        if(result != escorts.size()){
            throw new RuntimeException("BatchInsert failed " + (escorts.size()-result) + "s, succeed " + result + "s");
        }else if(escort.getId() == null || escort1.getId() == null){
            throw new RuntimeException("BatchInsert failed");
        }else{
            logger.info("BatchInserted succeed");
        }
    }

    @Test
    public void testUpdate() {
        Escort escort = escortDao.getById(2);
        String new_name = StringUtils.getRandomStr(6);
        logger.info("Before Update: "+escort);
        escort.setName(new_name);
        escortDao.update(escort);
        Escort escort_check = escortDao.getById(2);
        if(!escort_check.getName().equals(new_name)){
            throw new RuntimeException("Update failed");
        }else{
            logger.info("Updated succeed");
        }
    }

    @Test
    public void testBatchUpdate() {
        Escort escort2 = escortDao.getById(2);
        Escort escort4 = escortDao.getById(4);
        String new_name_1 = StringUtils.getRandomStr(6);
        String new_name_2 = StringUtils.getRandomStr(6);
        escort2.setName(new_name_1);
        escort4.setName(new_name_2);
        List<Escort> escorts = new ArrayList<Escort>();
        escorts.add(escort2);
        escorts.add(escort4);
        escortDao.update(escorts);
        Escort escort2_check = escortDao.getById(2);
        Escort escort4_check = escortDao.getById(4);
        if(!escort2_check.getName().equals(new_name_1)){
            throw new RuntimeException("Escort2 updateByMap failed");
        }
        if(!escort4_check.getName().equals(new_name_2)){
            throw new RuntimeException("Escort4 updateByMap failed");
        }
        if(escort2_check.getName().equals(new_name_1) && escort4_check.getName().equals(new_name_2)){
            logger.info("BatchUpdated succeed");
        }
    }

    @Test
    public void testDeleteById() {
        Long delete_id = 2L;
        escortDao.delete(delete_id);
        Escort escort_check = escortDao.getById(delete_id);
        if(!escort_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void testDelete() {
        Long delete_id = 4L;
        Escort escort = escortDao.getById(delete_id);
        escortDao.delete(escort);
        Escort escort_check = escortDao.getById(delete_id);
        if(!escort_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void testListAll() {
        List<Escort> escorts = escortDao.listAll();
        for (int i = 0; i < escorts.size(); i++)
            logger.info("Item" + i + ":" + escorts.get(i));
    }

    @Test
    public void testGetBy() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", true);
        paramMap.put("Id", 1L);
        Escort escort = escortDao.getBy(paramMap);
        if(escort.getId().longValue() != 1L){
            throw new RuntimeException("GetBy failed");
        }else{
            logger.info("GetBy succeed");
        }
    }

    @Test
    public void testListBy() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("IsDeleted", false);
        paramMap.put("EscortName", "admi");
        List<Escort> escorts = escortDao.listBy(paramMap);
        for (int i = 0; i < escorts.size(); i++)
            logger.info("Item" + i + ":" + escorts.get(i));
    }

    @Test
    public void testListPage() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", false);
        PageParam pageParam = new PageParam(2, 2);
        PageBean<Escort> pageBean = escortDao.listPage(pageParam, paramMap);
        logger.info(pageBean);
        List<Escort> escorts = pageBean.getRecordList();
//        if(escorts.size() != 2){
//            throw new RuntimeException("ListPage failed");
//        }
        for (int i = 0; i < escorts.size(); i++)
            logger.info("Item" + i + ":" + escorts.get(i));
    }
}
