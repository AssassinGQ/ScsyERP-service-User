package TestMyBatis.Dao;

import cn.AssassinG.ScsyERP.User.core.dao.UserDao;
import cn.AssassinG.ScsyERP.User.facade.entity.User;
import cn.AssassinG.ScsyERP.User.facade.enums.UserType;
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
public class TestUser {
    private static Logger logger = Logger.getLogger(TestUser.class);
    @Autowired
    private UserDao userDao;

    @Test
    public void testGetById() {
        Long user_id = 1L;
        User user = userDao.getById(user_id);
        if(user == null || user.getId() == null || user.getId().longValue() != user_id.longValue()){
            throw  new RuntimeException("getById failed");
        }else {
            logger.info("GetById success");
        }
    }

    @Test
    public void testInsert() {
        User user = new User();
        user.setUserName("asddf");
        user.setPassWord("d123456");
        user.setPhone("156");
        user.setUserType(UserType.Corporation);
        user.setUserInfo(1L);
        user.setCorporation(1L);
        userDao.insert(user);
        Long id = user.getId();
        if(id == null){
            throw new RuntimeException("insert nothing");
        }else {
            logger.info("Inserted : " + userDao.getById(id));
        }
    }

    @Test
    public void testBatchInsert() {
        User user = new User();
        user.setUserName("asddf3");
        user.setPassWord("12312");
        user.setPhone("156");
        user.setUserType(UserType.Corporation);
        user.setUserInfo(1L);
        user.setCorporation(1L);
        User user2 = new User();
        user2.setUserName("asddf4");
        user2.setPassWord("12124");
        user2.setPhone("156");
        user2.setUserType(UserType.Corporation);
        user2.setUserInfo(1L);
        user2.setCorporation(1L);
        List<User> users = new ArrayList<User>();
        users.add(user);
        users.add(user2);
        int result = userDao.insert(users);
        if(result != users.size()){
            throw new RuntimeException("BatchInsert failed " + (users.size()-result) + "s, succeed " + result + "s");
        }else if(user.getId() == null || user2.getId() == null){
            throw new RuntimeException("BatchInsert failed");
        }else{
            logger.info("BatchInserted succeed");
        }
    }

    @Test
    public void testUpdate() {
        User user = userDao.getById(2);
        String new_name = StringUtils.getRandomStr(6);
        logger.info("Before Update: "+user);
        user.setUserName(new_name);
        userDao.update(user);
        User user_check = userDao.getById(2);
        if(!user_check.getUserName().equals(new_name)){
            throw new RuntimeException("Update failed");
        }else{
            logger.info("Updated succeed");
        }
    }

    @Test
    public void testBatchUpdate() {
        User user2 = userDao.getById(2);
        User user4 = userDao.getById(4);
        String new_name_1 = StringUtils.getRandomStr(6);
        String new_name_2 = StringUtils.getRandomStr(6);
        user2.setUserName(new_name_1);
        user4.setUserName(new_name_2);
        List<User> users = new ArrayList<User>();
        users.add(user2);
        users.add(user4);
        userDao.update(users);
        User user2_check = userDao.getById(2);
        User user4_check = userDao.getById(4);
        if(!user2_check.getUserName().equals(new_name_1)){
            throw new RuntimeException("User2 updateByMap failed");
        }
        if(!user4_check.getUserName().equals(new_name_2)){
            throw new RuntimeException("User4 updateByMap failed");
        }
        if(user2_check.getUserName().equals(new_name_1) && user4_check.getUserName().equals(new_name_2)){
            logger.info("BatchUpdated succeed");
        }
    }

    @Test
    public void testDeleteById() {
        Long delete_id = 2L;
        userDao.delete(delete_id);
        User user_check = userDao.getById(delete_id);
        if(!user_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void testDelete() {
        Long delete_id = 4L;
        User user = userDao.getById(delete_id);
        userDao.delete(user);
        User user_check = userDao.getById(delete_id);
        if(!user_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void testListAll() {
        List<User> users = userDao.listAll();
        for (int i = 0; i < users.size(); i++)
            logger.info("Item" + i + ":" + users.get(i));
    }

    @Test
    public void testGetBy() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", true);
        paramMap.put("Id", 1L);
        User user = userDao.getBy(paramMap);
        if(user.getId() != 1L){
            throw new RuntimeException("GetBy failed");
        }else{
            logger.info("GetBy succeed");
        }
    }

    @Test
    public void testListBy() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("IsDeleted", false);
        paramMap.put("UserName", "admi");
        List<User> users = userDao.listBy(paramMap);
        for (int i = 0; i < users.size(); i++)
            logger.info("Item" + i + ":" + users.get(i));
    }

    @Test
    public void testListPage() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", false);
        PageParam pageParam = new PageParam(2, 2);
        PageBean<User> pageBean = userDao.listPage(pageParam, paramMap);
        logger.info(pageBean);
        List<User> users = pageBean.getRecordList();
//        if(users.size() != 2){
//            throw new RuntimeException("ListPage failed");
//        }
        for (int i = 0; i < users.size(); i++)
            logger.info("Item" + i + ":" + users.get(i));
    }

    @Test
    public void testFindByUsername() {
        String username = "superadmin";
        logger.info("The user who's username = "+username+" : "+ userDao.findByUserName(username));
    }

    @Test
    public void testId() {
        User user = new User();
        user.setUserName("asddf");
        user.setPassWord("d123456");
        user.setPhone("156");
        user.setUserType(UserType.Corporation);
        user.setUserInfo(1L);
        user.setCorporation(1L);
        userDao.insert(user);
        logger.info("user.id = " + user.getId());
        user.setPassWord("1231231");
        userDao.update(user);
    }

//    @Test
//    public void testFindRoleById() {
//        UserWithRole userWithRole = userDao.findRoleById(1);
//        logger.info(userWithRole);
//    }
}
