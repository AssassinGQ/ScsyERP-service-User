package TestMyBatis.Dao;

import cn.AssassinG.ScsyERP.User.core.dao.UserPermissionDao;
import cn.AssassinG.ScsyERP.User.facade.entity.User_Permission;
import cn.AssassinG.ScsyERP.User.facade.enums.UserPermissionType;
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
public class TestUserPermission {
    private static Logger logger = Logger.getLogger(TestUserPermission.class);
    @Autowired
    private UserPermissionDao userPermissionDao;

    @Test
    public void testGetById() {
        Long user_permission_id = 1L;
        User_Permission user_permission = userPermissionDao.getById(user_permission_id);
        if(user_permission == null || user_permission.getId() == null || user_permission.getId().longValue() != user_permission_id.longValue()){
            throw  new RuntimeException("getById failed");
        }else {
            logger.info("GetById success");
        }
    }

    @Test
    public void testInsert() {
        User_Permission user_permission = new User_Permission();
        user_permission.setUserId(1L);
        user_permission.setPermissionId(1L);
        user_permission.setType(UserPermissionType.Include);
        user_permission.setCorporation(1L);
        userPermissionDao.insert(user_permission);
        Long id = user_permission.getId();
        if(id == null){
            throw new RuntimeException("insert nothing");
        }else {
            logger.info("Inserted : " + userPermissionDao.getById(id));
        }
    }

    @Test
    public void testBatchInsert() {
        User_Permission user_permission = new User_Permission();
        user_permission.setUserId(2L);
        user_permission.setUserId(1L);
        user_permission.setCorporation(1L);
        User_Permission user_permission2 = new User_Permission();
        user_permission2.setUserId(4L);
        user_permission2.setUserId(1L);
        user_permission2.setCorporation(1L);
        List<User_Permission> user_permissions = new ArrayList<User_Permission>();
        user_permissions.add(user_permission);
        user_permissions.add(user_permission2);
        int result = userPermissionDao.insert(user_permissions);
        if(result != user_permissions.size()){
            throw new RuntimeException("BatchInsert failed " + (user_permissions.size()-result) + "s, succeed " + result + "s");
        }else if(user_permission.getId() == null || user_permission2.getId() == null){
            throw new RuntimeException("BatchInsert failed");
        }else{
            logger.info("BatchInserted succeed");
        }
    }

    @Test
    public void testUpdate() {
        User_Permission user_permission = userPermissionDao.getById(2);
        long newid = 299L;
        logger.info("Before Update: "+user_permission);
        user_permission.setUserId(newid);
        userPermissionDao.update(user_permission);
        User_Permission user_permission_check = userPermissionDao.getById(2);
        if(user_permission_check.getUserId() != newid){
            throw new RuntimeException("Update failed");
        }else{
            logger.info("Updated succeed");
        }
    }

    @Test
    public void testBatchUpdate() {
        User_Permission user_permission2 = userPermissionDao.getById(2);
        User_Permission user_permission4 = userPermissionDao.getById(4);
        long new_id_1 = 298L;
        long new_id_2 = 297L;
        user_permission2.setUserId(new_id_1);
        user_permission4.setUserId(new_id_2);
        List<User_Permission> user_permissions = new ArrayList<User_Permission>();
        user_permissions.add(user_permission2);
        user_permissions.add(user_permission4);
        userPermissionDao.update(user_permissions);
        User_Permission user_permission2_check = userPermissionDao.getById(2);
        User_Permission user_permission4_check = userPermissionDao.getById(4);
        if(user_permission2_check.getUserId().longValue() != new_id_1){
            throw new RuntimeException("User_Permission2 updateByMap failed");
        }
        if(user_permission4_check.getUserId().longValue() != new_id_2){
            throw new RuntimeException("User_Permission4 updateByMap failed");
        }
        if(user_permission2_check.getUserId().longValue() == new_id_1 && user_permission4_check.getUserId().longValue() == new_id_2){
            logger.info("BatchUpdated succeed");
        }
    }

    @Test
    public void testDeleteById() {
        Long delete_id = 2L;
        userPermissionDao.delete(delete_id);
        User_Permission user_permission_check = userPermissionDao.getById(delete_id);
        if(!user_permission_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void testDelete() {
        Long delete_id = 1L;
        User_Permission user_permission = userPermissionDao.getById(delete_id);
        userPermissionDao.delete(user_permission);
        User_Permission user_permission_check = userPermissionDao.getById(delete_id);
        if(!user_permission_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void testListAll() {
        List<User_Permission> user_permissions = userPermissionDao.listAll();
        for (int i = 0; i < user_permissions.size(); i++)
            logger.info("Item" + i + ":" + user_permissions.get(i));
    }

    @Test
    public void testGetBy() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", false);
        paramMap.put("Id", 1L);
        User_Permission user_permission = userPermissionDao.getBy(paramMap);
        if(user_permission.getId().longValue() != 1L){
            throw new RuntimeException("GetBy failed");
        }else{
            logger.info("GetBy succeed");
        }
    }

    @Test
    public void testListBy() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("IsDeleted", false);
        paramMap.put("User_PermissionName", "admi");
        List<User_Permission> user_permissions = userPermissionDao.listBy(paramMap);
        for (int i = 0; i < user_permissions.size(); i++)
            logger.info("Item" + i + ":" + user_permissions.get(i));
    }

    @Test
    public void testListPage() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("ifDeleted", false);
        PageParam pageParam = new PageParam(2, 2);
        PageBean<User_Permission> pageBean = userPermissionDao.listPage(pageParam, paramMap);
        logger.info(pageBean);
        List<User_Permission> user_permissions = pageBean.getRecordList();
        for (int i = 0; i < user_permissions.size(); i++)
            logger.info("Item" + i + ":" + user_permissions.get(i));
    }
}