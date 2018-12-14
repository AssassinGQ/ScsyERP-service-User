package TestMyBatis.Dao;

import cn.AssassinG.ScsyERP.User.core.dao.ConsigneeDao;
import cn.AssassinG.ScsyERP.User.facade.entity.Consignee;
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
public class TestConsignee {
    private static Logger logger = Logger.getLogger(TestConsignee.class);
    @Autowired
    private ConsigneeDao consigneeDao;

    @Test
    public void testGetById() {
        Long consignee_id = 1L;
        Consignee consignee = consigneeDao.getById(consignee_id);
        if(consignee == null || consignee.getId() == null || consignee.getId().longValue() != consignee_id.longValue()){
            throw  new RuntimeException("getById failed");
        }else {
            logger.info("GetById success");
        }
    }

    @Test
    public void testInsert() {
        Consignee consignee = new Consignee("四川成都");
        consignee.setName("asddf");
        consignee.setCorporation(1L);
        consigneeDao.insert(consignee);
        Long id = consignee.getId();
        if(id == null){
            throw new RuntimeException("insert nothing");
        }else {
            logger.info("Inserted : " + consigneeDao.getById(id));
        }
    }

    @Test
    public void testBatchInsert() {
        Consignee consignee = new Consignee("四川自贡");
        consignee.setName("asddf3");
        consignee.setCorporation(1L);
        Consignee consignee1 = new Consignee("四川遂宁");
        consignee1.setName("asddf4");
        consignee1.setCorporation(1L);
        List<Consignee> consignees = new ArrayList<Consignee>();
        consignees.add(consignee);
        consignees.add(consignee1);
        int result = consigneeDao.insert(consignees);
        if(result != consignees.size()){
            throw new RuntimeException("BatchInsert failed " + (consignees.size()-result) + "s, succeed " + result + "s");
        }else if(consignee.getId() == null || consignee1.getId() == null){
            throw new RuntimeException("BatchInsert failed");
        }else{
            logger.info("BatchInserted succeed");
        }
    }

    @Test
    public void testUpdate() {
        Consignee consignee = consigneeDao.getById(2);
        String new_name = StringUtils.getRandomStr(6);
        logger.info("Before Update: "+consignee);
        consignee.setName(new_name);
        consigneeDao.update(consignee);
        Consignee consignee_check = consigneeDao.getById(2);
        if(!consignee_check.getName().equals(new_name)){
            throw new RuntimeException("Update failed");
        }else{
            logger.info("Updated succeed");
        }
    }

    @Test
    public void testBatchUpdate() {
        Consignee consignee2 = consigneeDao.getById(2);
        Consignee consignee4 = consigneeDao.getById(4);
        String new_name_1 = StringUtils.getRandomStr(6);
        String new_name_2 = StringUtils.getRandomStr(6);
        consignee2.setName(new_name_1);
        consignee4.setName(new_name_2);
        List<Consignee> consignees = new ArrayList<Consignee>();
        consignees.add(consignee2);
        consignees.add(consignee4);
        consigneeDao.update(consignees);
        Consignee consignee2_check = consigneeDao.getById(2);
        Consignee consignee4_check = consigneeDao.getById(4);
        if(!consignee2_check.getName().equals(new_name_1)){
            throw new RuntimeException("Consignee2 updateByMap failed");
        }
        if(!consignee4_check.getName().equals(new_name_2)){
            throw new RuntimeException("Consignee4 updateByMap failed");
        }
        if(consignee2_check.getName().equals(new_name_1) && consignee4_check.getName().equals(new_name_2)){
            logger.info("BatchUpdated succeed");
        }
    }

    @Test
    public void testDeleteById() {
        Long delete_id = 2L;
        consigneeDao.delete(delete_id);
        Consignee consignee_check = consigneeDao.getById(delete_id);
        if(!consignee_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void testDelete() {
        Long delete_id = 4L;
        Consignee consignee = consigneeDao.getById(delete_id);
        consigneeDao.delete(consignee);
        Consignee consignee_check = consigneeDao.getById(delete_id);
        if(!consignee_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void testListAll() {
        List<Consignee> consignees = consigneeDao.listAll();
        for (int i = 0; i < consignees.size(); i++)
            logger.info("Item" + i + ":" + consignees.get(i));
    }

    @Test
    public void testGetBy() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", true);
        paramMap.put("Id", 1L);
        Consignee consignee = consigneeDao.getBy(paramMap);
        if(consignee.getId().longValue() != 1L){
            throw new RuntimeException("GetBy failed");
        }else{
            logger.info("GetBy succeed");
        }
    }

    @Test
    public void testListBy() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("IsDeleted", false);
        paramMap.put("ConsigneeName", "admi");
        List<Consignee> consignees = consigneeDao.listBy(paramMap);
        for (int i = 0; i < consignees.size(); i++)
            logger.info("Item" + i + ":" + consignees.get(i));
    }

    @Test
    public void testListPage() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", false);
        PageParam pageParam = new PageParam(2, 2);
        PageBean<Consignee> pageBean = consigneeDao.listPage(pageParam, paramMap);
        logger.info(pageBean);
        List<Consignee> consignees = pageBean.getRecordList();
//        if(consignees.size() != 2){
//            throw new RuntimeException("ListPage failed");
//        }
        for (int i = 0; i < consignees.size(); i++)
            logger.info("Item" + i + ":" + consignees.get(i));
    }
}
