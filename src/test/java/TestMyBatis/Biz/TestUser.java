package TestMyBatis.Biz;


import cn.AssassinG.ScsyERP.User.core.biz.UserBiz;
import cn.AssassinG.ScsyERP.User.core.dao.PermissionDao;
import cn.AssassinG.ScsyERP.User.core.dao.RoleDao;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-context.xml"})
public class TestUser {
    private static Logger logger = Logger.getLogger(TestUser.class);

    @Autowired
    private UserBiz userBiz;
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;

    @Test
    public void testCreate() {
        User user = new User();
        user.setUserName("asddf");
        user.setPassWord("d123456");
        user.setPhone("18868182333");
        user.setUserType(UserType.Government);
        user.setUserInfo(1L);
        user.setCorporation(1L);
        userBiz.create(user);
        System.out.println(user);
        Long id = user.getId();
        if(id == null){
            throw new RuntimeException("Create nothing");
        }else {
            logger.info("Created : " + userDao.getById(id));
        }
    }

    @Test
    public void testUpdate() {
        User user = userDao.getById(2);
        String new_name = StringUtils.getRandomStr(6);
        logger.info("Before Update: "+user);
        user.setUserName(new_name);
        userBiz.update(user);
        User user_check = userDao.getById(2);
        if(!user_check.getUserName().equals(new_name)){
            throw new RuntimeException("Update failed");
        }else{
            logger.info("Updated succeed");
        }
    }

    @Test
    public void testDeleteById() {
        Long delete_id = 2L;
        userBiz.deleteById(delete_id);
        User user_check = userDao.getById(delete_id);
        if(!user_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void testGetBy() {
        User user = userBiz.getById(1L);
        if(user == null || user.getId() == null || user.getId() != 1L){
            throw new RuntimeException("GetBy failed");
        }else{
            logger.info("GetBy succeed");
        }
    }

    @Test
    public void testGetById() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("ifDeleted", true);
        paramMap.put("Id", 1L);
        User user = userBiz.getBy(paramMap);
        if(user.getId() != 1L){
            throw new RuntimeException("GetBy failed");
        }else{
            logger.info("GetBy succeed");
        }
    }

    @Test
    public void testListBy() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("IfDeleted", false);
        paramMap.put("UserName", "admi");
        List<User> users = userBiz.listBy(paramMap);
        for (int i = 0; i < users.size(); i++)
            logger.info("Item" + i + ":" + users.get(i));
    }

    @Test
    public void testListPage() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("ifDeleted", false);
        PageParam pageParam = new PageParam(2, 2);
        PageBean<User> pageBean = userBiz.listPage(pageParam, paramMap);
        logger.info(pageBean);
        List<User> users = pageBean.getRecordList();
        for (int i = 0; i < users.size(); i++)
            logger.info("Item" + i + ":" + users.get(i));
    }

    @Test
    public void testFindUserByUname() {
        String uname = "superadmin";
        User user = userBiz.findUserByUname(uname);
        if(user == null || user.getUserName() == null || !user.getUserName().equals(uname)){
            throw new RuntimeException("FindUserByUname failed");
        }else{
            logger.info("FindUserByUname succeed");
        }
    }

    @Test
    public void testFindUserByPhone() {
        String phone = "18868187538";
        User user = userBiz.findUserByPhone(phone);
        if(user == null || user.getPhone() == null || !user.getPhone().equals(phone)){
            throw new RuntimeException("findUserByPhone failed");
        }else{
            logger.info("findUserByPhone succeed");
        }
    }

}
