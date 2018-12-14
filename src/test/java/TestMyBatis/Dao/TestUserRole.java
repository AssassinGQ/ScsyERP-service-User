package TestMyBatis.Dao;

import cn.AssassinG.ScsyERP.User.core.dao.UserRoleDao;
import cn.AssassinG.ScsyERP.User.facade.entity.User_Role;
import cn.AssassinG.ScsyERP.common.page.PageBean;
import cn.AssassinG.ScsyERP.common.page.PageParam;
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
public class TestUserRole {
    private static Logger logger = Logger.getLogger(TestUserRole.class);
    @Autowired
    private UserRoleDao user_roleDao;

    @Test
    public void testGetById() {
        Long user_role_id = 1L;
        User_Role user_role = user_roleDao.getById(user_role_id);
        if(user_role == null || user_role.getId() == null || user_role.getId().longValue() != user_role_id.longValue()){
            throw  new RuntimeException("getById failed");
        }else {
            logger.info("GetById success");
        }
    }

    @Test
    public void testInsert() {
        User_Role user_role = new User_Role();
        user_role.setRoleId(1L);
        user_role.setUserId(1L);
        user_role.setCorporation(1L);
        user_roleDao.insert(user_role);
        Long id = user_role.getId();
        if(id == null){
            throw new RuntimeException("insert nothing");
        }else {
            logger.info("Inserted : " + user_roleDao.getById(id));
        }
    }

    @Test
    public void testBatchInsert() {
        User_Role user_role = new User_Role();
        user_role.setRoleId(2L);
        user_role.setUserId(1L);
        user_role.setCorporation(1L);
        User_Role user_role2 = new User_Role();
        user_role2.setRoleId(4L);
        user_role2.setUserId(1L);
        user_role2.setCorporation(1L);
        List<User_Role> user_roles = new ArrayList<User_Role>();
        user_roles.add(user_role);
        user_roles.add(user_role2);
        int result = user_roleDao.insert(user_roles);
        if(result != user_roles.size()){
            throw new RuntimeException("BatchInsert failed " + (user_roles.size()-result) + "s, succeed " + result + "s");
        }else if(user_role.getId() == null || user_role2.getId() == null){
            throw new RuntimeException("BatchInsert failed");
        }else{
            logger.info("BatchInserted succeed");
        }
    }

    @Test
    public void testUpdate() {
        User_Role user_role = user_roleDao.getById(2);
        long newid = 299L;
        logger.info("Before Update: "+user_role);
        user_role.setRoleId(newid);
        user_roleDao.update(user_role);
        User_Role user_role_check = user_roleDao.getById(2);
        if(user_role_check.getRoleId().longValue() != newid){
            throw new RuntimeException("Update failed");
        }else{
            logger.info("Updated succeed");
        }
    }

    @Test
    public void testBatchUpdate() {
        User_Role user_role2 = user_roleDao.getById(2);
        User_Role user_role4 = user_roleDao.getById(4);
        long new_id_1 = 298L;
        long new_id_2 = 297L;
        user_role2.setRoleId(new_id_1);
        user_role4.setRoleId(new_id_2);
        List<User_Role> user_roles = new ArrayList<User_Role>();
        user_roles.add(user_role2);
        user_roles.add(user_role4);
        user_roleDao.update(user_roles);
        User_Role user_role2_check = user_roleDao.getById(2);
        User_Role user_role4_check = user_roleDao.getById(4);
        if(user_role2_check.getRoleId().longValue() != new_id_1){
            throw new RuntimeException("User_Role2 updateByMap failed");
        }
        if(user_role4_check.getRoleId().longValue() != new_id_2){
            throw new RuntimeException("User_Role4 updateByMap failed");
        }
        if(user_role2_check.getRoleId().longValue() == new_id_1 && user_role4_check.getRoleId().longValue() == new_id_2){
            logger.info("BatchUpdated succeed");
        }
    }

    @Test
    public void testDeleteById() {
        Long delete_id = 2L;
        user_roleDao.delete(delete_id);
        User_Role user_role_check = user_roleDao.getById(delete_id);
        if(!user_role_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void testDelete() {
        Long delete_id = 4L;
        User_Role user_role = user_roleDao.getById(delete_id);
        user_roleDao.delete(user_role);
        User_Role user_role_check = user_roleDao.getById(delete_id);
        if(!user_role_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void testListAll() {
        List<User_Role> user_roles = user_roleDao.listAll();
        for (int i = 0; i < user_roles.size(); i++)
            logger.info("Item" + i + ":" + user_roles.get(i));
    }

    @Test
    public void testGetBy() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("ifDeleted", true);
        paramMap.put("Id", 1L);
        User_Role user_role = user_roleDao.getBy(paramMap);
        if(user_role.getId().longValue() != 1L){
            throw new RuntimeException("GetBy failed");
        }else{
            logger.info("GetBy succeed");
        }
    }

    @Test
    public void testListBy() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("IsDeleted", false);
        paramMap.put("User_RoleName", "admi");
        List<User_Role> user_roles = user_roleDao.listBy(paramMap);
        for (int i = 0; i < user_roles.size(); i++)
            logger.info("Item" + i + ":" + user_roles.get(i));
    }

    @Test
    public void testListPage() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("ifDeleted", false);
        PageParam pageParam = new PageParam(2, 2);
        PageBean<User_Role> pageBean = user_roleDao.listPage(pageParam, paramMap);
        logger.info(pageBean);
        List<User_Role> user_roles = pageBean.getRecordList();
        for (int i = 0; i < user_roles.size(); i++)
            logger.info("Item" + i + ":" + user_roles.get(i));
    }
}