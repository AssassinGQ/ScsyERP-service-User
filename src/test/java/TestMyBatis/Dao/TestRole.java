package TestMyBatis.Dao;

import cn.AssassinG.ScsyERP.User.core.dao.RoleDao;
import cn.AssassinG.ScsyERP.User.facade.entity.Role;
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
public class TestRole {
    private static Logger logger = Logger.getLogger(TestRole.class);
    @Autowired
    private RoleDao roleDao;

    @Test
    public void testGetById() {
        Long role_id = 1L;
        Role role = roleDao.getById(role_id);
        if(role == null || role.getId() == null || role.getId().longValue() != role_id.longValue()){
            throw  new RuntimeException("getById failed");
        }else {
            logger.info("GetById success");
        }
    }

    @Test
    public void testInsert() {
        Role role = new Role();
        role.setRoleName("asddf");
        role.setSuperRoleName("123");
        role.setCorporation(1L);
        roleDao.insert(role);
        Long id = role.getId();
        if(id == null){
            throw new RuntimeException("insert nothing");
        }else {
            logger.info("Inserted : " + roleDao.getById(id));
        }
    }

    @Test
    public void testBatchInsert() {
        Role role = new Role();
        role.setRoleName("asddf3");
        role.setSuperRoleName("1234");
        role.setCorporation(1L);
        Role role2 = new Role();
        role2.setRoleName("asddf4");
        role2.setSuperRoleName("12314");
        role2.setCorporation(1L);
        List<Role> roles = new ArrayList<Role>();
        roles.add(role);
        roles.add(role2);
        int result = roleDao.insert(roles);
        if(result != roles.size()){
            throw new RuntimeException("BatchInsert failed " + (roles.size()-result) + "s, succeed " + result + "s");
        }else if(role.getId() == null || role2.getId() == null){
            throw new RuntimeException("BatchInsert failed");
        }else{
            logger.info("BatchInserted succeed");
        }
    }

    @Test
    public void testUpdate() {
        Role role = roleDao.getById(2);
        String new_name = StringUtils.getRandomStr(6);
        logger.info("Before Update: "+role);
        role.setRoleName(new_name);
        roleDao.update(role);
        Role role_check = roleDao.getById(2);
        if(!role_check.getRoleName().equals(new_name)){
            throw new RuntimeException("Update failed");
        }else{
            logger.info("Updated succeed");
        }
    }

    @Test
    public void testBatchUpdate() {
        Role role2 = roleDao.getById(2);
        Role role4 = roleDao.getById(4);
        String new_name_1 = StringUtils.getRandomStr(6);
        String new_name_2 = StringUtils.getRandomStr(6);
        role2.setRoleName(new_name_1);
        role4.setRoleName(new_name_2);
        List<Role> roles = new ArrayList<Role>();
        roles.add(role2);
        roles.add(role4);
        roleDao.update(roles);
        Role role2_check = roleDao.getById(2);
        Role role4_check = roleDao.getById(4);
        if(!role2_check.getRoleName().equals(new_name_1)){
            throw new RuntimeException("Role2 updateByMap failed");
        }
        if(!role4_check.getRoleName().equals(new_name_2)){
            throw new RuntimeException("Role4 updateByMap failed");
        }
        if(role2_check.getRoleName().equals(new_name_1) && role4_check.getRoleName().equals(new_name_2)){
            logger.info("BatchUpdated succeed");
        }
    }

    @Test
    public void testDeleteById() {
        Long delete_id = 2L;
        roleDao.delete(delete_id);
        Role role_check = roleDao.getById(delete_id);
        if(!role_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void testDelete() {
        Long delete_id = 4L;
        Role role = roleDao.getById(delete_id);
        roleDao.delete(role);
        Role role_check = roleDao.getById(delete_id);
        if(!role_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void testListAll() {
        List<Role> roles = roleDao.listAll();
        for (int i = 0; i < roles.size(); i++)
            logger.info("Item" + i + ":" + roles.get(i));
    }

    @Test
    public void testGetBy() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("isDeleted", true);
        paramMap.put("Id", 1L);
        Role role = roleDao.getBy(paramMap);
        if(role.getId().longValue() != 1L){
            throw new RuntimeException("GetBy failed");
        }else{
            logger.info("GetBy succeed");
        }
    }

    @Test
    public void testListBy() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("IsDeleted", false);
        paramMap.put("RoleName", "admi");
        List<Role> roles = roleDao.listBy(paramMap);
        for (int i = 0; i < roles.size(); i++)
            logger.info("Item" + i + ":" + roles.get(i));
    }

    @Test
    public void testListPage() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("isDeleted", false);
        PageParam pageParam = new PageParam(2, 2);
        PageBean<Role> pageBean = roleDao.listPage(pageParam, paramMap);
        logger.info(pageBean);
        List<Role> roles = pageBean.getRecordList();
        if(roles.size() != 2){
            throw new RuntimeException("ListPage failed");
        }
        for (int i = 0; i < roles.size(); i++)
            logger.info("Item" + i + ":" + roles.get(i));
    }

    @Test
    public void testFindByUserId() {
        Long userid = 1L;
        logger.info("The user who's id = "+userid+" has roles : ");
        Set<Role> roles = roleDao.findByUserId(userid);
        for(Role role : roles)
            logger.info(role);
    }

    @Test
    public void testFindByUserName() {
        String username = "superadmin";
        logger.info("The user who's username= "+username+" has roles : ");
        Set<Role> roles = roleDao.findByUsername(username);
        for(Role role : roles)
            logger.info(role);
    }
}
