package TestMyBatis.Dao;

import cn.AssassinG.ScsyERP.User.core.dao.PermissionDao;
import cn.AssassinG.ScsyERP.User.facade.entity.Permission;
import cn.AssassinG.ScsyERP.common.page.PageBean;
import cn.AssassinG.ScsyERP.common.page.PageParam;
import cn.AssassinG.ScsyERP.common.utils.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-context.xml"})
public class TestPermission {
    private static Logger logger = Logger.getLogger(TestPermission.class);

    @Autowired
    private PermissionDao permissionDao;

    @Test
    public void testGetById() {
        Long permission_id = 1L;
        Permission permission = permissionDao.getById(permission_id);
        if(permission == null || permission.getId() == null || permission.getId().longValue() != permission_id.longValue()){
            throw  new RuntimeException("getById failed");
        }else {
            logger.info("GetById success");
        }
    }

    @Test
    public void testInsert() {
        Permission permission = new Permission();
        permission.setPermissionName("asddf");
        permission.setCorporation(1L);
        permissionDao.insert(permission);
        Long id = permission.getId();
        if(id == null){
            throw new RuntimeException("insert nothing");
        }else {
            logger.info("Inserted : " + permissionDao.getById(id));
        }
    }

    @Test
    public void testBatchInsert() {
        Permission permission = new Permission();
        permission.setPermissionName("asddf3");
        permission.setCorporation(1L);
        Permission permission2 = new Permission();
        permission2.setPermissionName("asddf4");
        permission2.setCorporation(1L);
        List<Permission> permissions = new ArrayList<Permission>();
        permissions.add(permission);
        permissions.add(permission2);
        int result = permissionDao.insert(permissions);
        if(result != permissions.size()){
            throw new RuntimeException("BatchInsert failed " + (permissions.size()-result) + "s, succeed " + result + "s");
        }else if(permission.getId() == null || permission2.getId() == null){
            throw new RuntimeException("BatchInsert failed");
        }else{
            logger.info("BatchInserted succeed");
        }
    }

    @Test
    public void testUpdate() {
        Permission permission = permissionDao.getById(2);
        String new_name = StringUtils.getRandomStr(6);
        logger.info("Before Update: "+permission);
        permission.setPermissionName(new_name);
        permissionDao.update(permission);
        Permission permission_check = permissionDao.getById(2);
        if(!permission_check.getPermissionName().equals(new_name)){
            throw new RuntimeException("Update failed");
        }else{
            logger.info("Updated succeed");
        }
    }

    @Test
    public void testBatchUpdate() {
        Permission permission2 = permissionDao.getById(2);
        Permission permission4 = permissionDao.getById(4);
        String new_name_1 = StringUtils.getRandomStr(6);
        String new_name_2 = StringUtils.getRandomStr(6);
        permission2.setPermissionName(new_name_1);
        permission4.setPermissionName(new_name_2);
        List<Permission> permissions = new ArrayList<Permission>();
        permissions.add(permission2);
        permissions.add(permission4);
        permissionDao.update(permissions);
        Permission permission2_check = permissionDao.getById(2);
        Permission permission4_check = permissionDao.getById(4);
        if(!permission2_check.getPermissionName().equals(new_name_1)){
            throw new RuntimeException("Permission2 updateByMap failed");
        }
        if(!permission4_check.getPermissionName().equals(new_name_2)){
            throw new RuntimeException("Permission4 updateByMap failed");
        }
        if(permission2_check.getPermissionName().equals(new_name_1) && permission4_check.getPermissionName().equals(new_name_2)){
            logger.info("BatchUpdated succeed");
        }
    }

    @Test
    public void testDeleteById() {
        Long delete_id = 2L;
        permissionDao.delete(delete_id);
        Permission permission_check = permissionDao.getById(delete_id);
        if(!permission_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void testDelete() {
        Long delete_id = 4L;
        Permission permission = permissionDao.getById(delete_id);
        permissionDao.delete(permission);
        Permission permission_check = permissionDao.getById(delete_id);
        if(!permission_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void testListAll() {
        List<Permission> permissions = permissionDao.listAll();
        for (int i = 0; i < permissions.size(); i++)
            logger.info("Item" + i + ":" + permissions.get(i));
    }

    @Test
    public void testGetBy() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", true);
        paramMap.put("Id", 1L);
        Permission permission = permissionDao.getBy(paramMap);
        if(permission.getId().longValue() != 1L){
            throw new RuntimeException("GetBy failed");
        }else{
            logger.info("GetBy succeed");
        }
    }

    @Test
    public void testListBy() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("IfDeleted", false);
        paramMap.put("PermissionName", "admi");
        List<Permission> permissions = permissionDao.listBy(paramMap);
        for (int i = 0; i < permissions.size(); i++)
            logger.info("Item" + i + ":" + permissions.get(i));
    }

    @Test
    public void testListPage() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", false);
        PageParam pageParam = new PageParam(2, 2);
        PageBean<Permission> pageBean = permissionDao.listPage(pageParam, paramMap);
        logger.info(pageBean);
        List<Permission> permissions = pageBean.getRecordList();
        if(permissions.size() != 2){
            throw new RuntimeException("ListPage failed");
        }
        for (int i = 0; i < permissions.size(); i++)
            logger.info("Item" + i + ":" + permissions.get(i));
    }

    @Test
    public void testFindByRoleId() {
        Long roleid = 1L;
        logger.info("The role who's id = "+roleid+" has permissions: ");
        Set<Permission> permissions = permissionDao.findByRoleId(roleid);
        for(Permission permission : permissions)
            logger.info(permission);
    }

    @Test
    public void testFindByRoleName() {
        String rolename = "superadmin";
        logger.info("The role who's rolename = "+rolename+" has permissions: ");
        Set<Permission> permissions = permissionDao.findByRolename(rolename);
        for(Permission permission : permissions)
            logger.info(permission);
    }
}
