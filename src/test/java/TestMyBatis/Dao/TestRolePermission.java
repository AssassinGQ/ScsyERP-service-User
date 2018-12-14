package TestMyBatis.Dao;

import cn.AssassinG.ScsyERP.User.core.dao.RolePermissionDao;
import cn.AssassinG.ScsyERP.User.facade.entity.Role_Permission;
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
public class TestRolePermission {
    private static Logger logger = Logger.getLogger(TestRolePermission.class);
    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Test
    public void testGetById() {
        Long role_premission_id = 1L;
        Role_Permission role_premission = rolePermissionDao.getById(role_premission_id);
        if(role_premission == null || role_premission.getId() == null || role_premission.getId().longValue() != role_premission_id.longValue()){
            throw  new RuntimeException("getById failed");
        }else {
            logger.info("GetById success");
        }
    }

    @Test
    public void testInsert() {
        Role_Permission role_premission = new Role_Permission();
        role_premission.setPermissionId(1L);
        role_premission.setRoleId(1L);
        role_premission.setCorporation(1L);
        rolePermissionDao.insert(role_premission);
        Long id = role_premission.getId();
        if(id == null){
            throw new RuntimeException("insert nothing");
        }else {
            logger.info("Inserted : " + rolePermissionDao.getById(id));
        }
    }

    @Test
    public void testBatchInsert() {
        Role_Permission role_premission = new Role_Permission();
        role_premission.setPermissionId(2L);
        role_premission.setRoleId(1L);
        role_premission.setCorporation(1L);
        Role_Permission role_premission2 = new Role_Permission();
        role_premission2.setPermissionId(4L);
        role_premission2.setCorporation(1L);
        role_premission2.setRoleId(1L);
        List<Role_Permission> role_premissions = new ArrayList<Role_Permission>();
        role_premissions.add(role_premission);
        role_premissions.add(role_premission2);
        int result = rolePermissionDao.insert(role_premissions);
        if(result != role_premissions.size()){
            throw new RuntimeException("BatchInsert failed " + (role_premissions.size()-result) + "s, succeed " + result + "s");
        }else if(role_premission.getId() == null || role_premission2.getId() == null){
            throw new RuntimeException("BatchInsert failed");
        }else{
            logger.info("BatchInserted succeed");
        }
    }

    @Test
    public void testUpdate() {
        Role_Permission role_premission = rolePermissionDao.getById(2);
        long newid = 299L;
        logger.info("Before Update: "+role_premission);
        role_premission.setPermissionId(newid);
        rolePermissionDao.update(role_premission);
        Role_Permission role_premission_check = rolePermissionDao.getById(2);
        if(role_premission_check.getPermissionId().longValue() != newid){
            throw new RuntimeException("Update failed");
        }else{
            logger.info("Updated succeed");
        }
    }

    @Test
    public void testBatchUpdate() {
        Role_Permission role_premission2 = rolePermissionDao.getById(2);
        Role_Permission role_premission4 = rolePermissionDao.getById(4);
        long new_id_1 = 298L;
        long new_id_2 = 297L;
        role_premission2.setPermissionId(new_id_1);
        role_premission4.setPermissionId(new_id_2);
        List<Role_Permission> role_premissions = new ArrayList<Role_Permission>();
        role_premissions.add(role_premission2);
        role_premissions.add(role_premission4);
        rolePermissionDao.update(role_premissions);
        Role_Permission role_premission2_check = rolePermissionDao.getById(2);
        Role_Permission role_premission4_check = rolePermissionDao.getById(4);
        if(role_premission2_check.getPermissionId().longValue() != new_id_1){
            throw new RuntimeException("Role_Permission2 updateByMap failed");
        }
        if(role_premission4_check.getPermissionId().longValue() != new_id_2){
            throw new RuntimeException("Role_Permission4 updateByMap failed");
        }
        if(role_premission2_check.getPermissionId().longValue() == new_id_1 && role_premission4_check.getPermissionId().longValue() == new_id_2){
            logger.info("BatchUpdated succeed");
        }
    }

    @Test
    public void testDeleteById() {
        Long delete_id = 2L;
        rolePermissionDao.delete(delete_id);
        Role_Permission role_premission_check = rolePermissionDao.getById(delete_id);
        if(!role_premission_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void testDelete() {
        Long delete_id = 4L;
        Role_Permission role_premission = rolePermissionDao.getById(delete_id);
        rolePermissionDao.delete(role_premission);
        Role_Permission role_premission_check = rolePermissionDao.getById(delete_id);
        if(!role_premission_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void testListAll() {
        List<Role_Permission> role_premissions = rolePermissionDao.listAll();
        for (int i = 0; i < role_premissions.size(); i++)
            logger.info("Item" + i + ":" + role_premissions.get(i));
    }

    @Test
    public void testGetBy() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", true);
        paramMap.put("Id", 1L);
        Role_Permission role_premission = rolePermissionDao.getBy(paramMap);
        if(role_premission.getId().longValue() != 1L){
            throw new RuntimeException("GetBy failed");
        }else{
            logger.info("GetBy succeed");
        }
    }

    @Test
    public void testListBy() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("IsDeleted", false);
        paramMap.put("Role_PermissionName", "admi");
        List<Role_Permission> role_premissions = rolePermissionDao.listBy(paramMap);
        for (int i = 0; i < role_premissions.size(); i++)
            logger.info("Item" + i + ":" + role_premissions.get(i));
    }

    @Test
    public void testListPage() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", false);
        PageParam pageParam = new PageParam(2, 2);
        PageBean<Role_Permission> pageBean = rolePermissionDao.listPage(pageParam, paramMap);
        logger.info(pageBean);
        List<Role_Permission> role_premissions = pageBean.getRecordList();
        if(role_premissions.size() != 2){
            throw new RuntimeException("ListPage failed");
        }
        for (int i = 0; i < role_premissions.size(); i++)
            logger.info("Item" + i + ":" + role_premissions.get(i));
    }
}
