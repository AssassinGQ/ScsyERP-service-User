package TestMyBatis.Dao;

import cn.AssassinG.ScsyERP.User.core.dao.AdminDao;
import cn.AssassinG.ScsyERP.User.facade.entity.Admin;
import cn.AssassinG.ScsyERP.User.facade.enums.AdminDeptType;
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
public class TestAdmin {
    private static Logger logger = Logger.getLogger(TestAdmin.class);
    @Autowired
    private AdminDao adminDao;

    @Test
    public void testGetById() {
        Long admin_id = 1L;
        Admin admin = adminDao.getById(admin_id);
        if(admin == null || admin.getId() == null || admin.getId().longValue() != admin_id.longValue()){
            throw  new RuntimeException("getById failed");
        }else {
            logger.info("GetById success");
        }
    }

    @Test
    public void testInsert() {
        Admin admin = new Admin(AdminDeptType.WarehouseAdmin);
        admin.setName("asddf");
        admin.setCorporation(1L);
        adminDao.insert(admin);
        Long id = admin.getId();
        if(id == null){
            throw new RuntimeException("insert nothing");
        }else {
            logger.info("Inserted : " + adminDao.getById(id));
        }
    }

    @Test
    public void testBatchInsert() {
        Admin admin = new Admin(AdminDeptType.ProjectAdmin);
        admin.setName("asddf3");
        admin.setCorporation(1L);
        Admin admin1 = new Admin(AdminDeptType.FinancialAdmin);
        admin1.setName("asddf4");
        admin1.setCorporation(1L);
        List<Admin> admins = new ArrayList<Admin>();
        admins.add(admin);
        admins.add(admin1);
        int result = adminDao.insert(admins);
        if(result != admins.size()){
            throw new RuntimeException("BatchInsert failed " + (admins.size()-result) + "s, succeed " + result + "s");
        }else if(admin.getId() == null || admin1.getId() == null){
            throw new RuntimeException("BatchInsert failed");
        }else{
            logger.info("BatchInserted succeed");
        }
    }

    @Test
    public void testUpdate() {
        Admin admin = adminDao.getById(2);
        String new_name = StringUtils.getRandomStr(6);
        logger.info("Before Update: "+admin);
        admin.setName(new_name);
        adminDao.update(admin);
        Admin admin_check = adminDao.getById(2);
        if(!admin_check.getName().equals(new_name)){
            throw new RuntimeException("Update failed");
        }else{
            logger.info("Updated succeed");
        }
    }

    @Test
    public void testBatchUpdate() {
        Admin admin2 = adminDao.getById(2);
        Admin admin4 = adminDao.getById(4);
        String new_name_1 = StringUtils.getRandomStr(6);
        String new_name_2 = StringUtils.getRandomStr(6);
        admin2.setName(new_name_1);
        admin4.setName(new_name_2);
        List<Admin> admins = new ArrayList<Admin>();
        admins.add(admin2);
        admins.add(admin4);
        adminDao.update(admins);
        Admin admin2_check = adminDao.getById(2);
        Admin admin4_check = adminDao.getById(4);
        if(!admin2_check.getName().equals(new_name_1)){
            throw new RuntimeException("Admin2 updateByMap failed");
        }
        if(!admin4_check.getName().equals(new_name_2)){
            throw new RuntimeException("Admin4 updateByMap failed");
        }
        if(admin2_check.getName().equals(new_name_1) && admin4_check.getName().equals(new_name_2)){
            logger.info("BatchUpdated succeed");
        }
    }

    @Test
    public void testDeleteById() {
        Long delete_id = 2L;
        adminDao.delete(delete_id);
        Admin admin_check = adminDao.getById(delete_id);
        if(!admin_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void testDelete() {
        Long delete_id = 4L;
        Admin admin = adminDao.getById(delete_id);
        adminDao.delete(admin);
        Admin admin_check = adminDao.getById(delete_id);
        if(!admin_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void testListAll() {
        List<Admin> admins = adminDao.listAll();
        for (int i = 0; i < admins.size(); i++)
            logger.info("Item" + i + ":" + admins.get(i));
    }

    @Test
    public void testGetBy() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", true);
        paramMap.put("Id", 1L);
        Admin admin = adminDao.getBy(paramMap);
        if(admin.getId() != 1L){
            throw new RuntimeException("GetBy failed");
        }else{
            logger.info("GetBy succeed");
        }
    }

    @Test
    public void testListBy() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("IsDeleted", false);
        paramMap.put("AdminName", "admi");
        List<Admin> admins = adminDao.listBy(paramMap);
        for (int i = 0; i < admins.size(); i++)
            logger.info("Item" + i + ":" + admins.get(i));
    }

    @Test
    public void testListPage() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", false);
        PageParam pageParam = new PageParam(2, 2);
        PageBean<Admin> pageBean = adminDao.listPage(pageParam, paramMap);
        logger.info(pageBean);
        List<Admin> admins = pageBean.getRecordList();
//        if(admins.size() != 2){
//            throw new RuntimeException("ListPage failed");
//        }
        for (int i = 0; i < admins.size(); i++)
            logger.info("Item" + i + ":" + admins.get(i));
    }
}
