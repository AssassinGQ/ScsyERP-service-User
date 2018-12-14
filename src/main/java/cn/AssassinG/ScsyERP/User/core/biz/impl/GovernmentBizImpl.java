package cn.AssassinG.ScsyERP.User.core.biz.impl;

import cn.AssassinG.ScsyERP.User.core.biz.GovernmentBiz;
import cn.AssassinG.ScsyERP.User.core.dao.GovernmentDao;
import cn.AssassinG.ScsyERP.User.facade.entity.Government;
import cn.AssassinG.ScsyERP.User.facade.entity.User;
import cn.AssassinG.ScsyERP.User.facade.enums.GovernmentDeptType;
import cn.AssassinG.ScsyERP.User.facade.enums.UserType;
import cn.AssassinG.ScsyERP.User.facade.exceptions.GovernmentBizException;
import cn.AssassinG.ScsyERP.common.core.dao.BaseDao;
import cn.AssassinG.ScsyERP.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Component("GovernmentBiz")
public class GovernmentBizImpl extends LoginableBizImpl<Government> implements GovernmentBiz {

    @Autowired
    private GovernmentDao governmentDao;

    @Override
    protected BaseDao<Government> getDao() {
        return this.governmentDao;
    }

    /**
     * 屏蔽createWithUser
     * @param entity
     * @param user
     * @return
     */
    @Override
    public Long createWithUser(Government entity, User user) {
        return -1L;
    }

    @Override
    @Transactional
    public Long create(String token, User user, Map<String, Object> paramMap){
        if(!token.equals("superadminabcd1234")) {
            throw new GovernmentBizException(GovernmentBizException.GOVERNMENTBIZ_NOPERMISSION, "权限码不正确:%s", token);
        }
        if(!StringUtils.isMobileNum(user.getPhone())){
            throw new GovernmentBizException(GovernmentBizException.GOVERNMENTBIZ_PARAMS_ILLEGAL, "请输入合法的手机号码:%s", user.getPhone());
        }
//        if(user.getUserType() == null ||
//                (user.getUserType().getValue().intValue() != UserType.Government.getValue().intValue())) {
//            throw new GovernmentBizException(GovernmentBizException.GOVERNMENTBIZ_PARAMS_ILLEGAL, "只能申请政府账号");
//        }
        GovernmentDeptType dept = (GovernmentDeptType) paramMap.get("dept");
        String governmentName = (String) paramMap.get("name");
        if(dept == null){
            throw new GovernmentBizException(GovernmentBizException.GOVERNMENTBIZ_PARAMS_ILLEGAL, "请指定政府部门");
        }
        User user_checkphone = userBiz.findUserByPhone(user.getPhone());
        if(user_checkphone != null){
            throw new GovernmentBizException(GovernmentBizException.GOVERNMENTBIZ_PARAMS_ILLEGAL, "手机号码已经被占用:%s", user.getPhone());
        }
        //创建基本信息
        if(governmentName == null || governmentName.isEmpty()){
            governmentName = "-1";
        }
        Government government = new Government(dept);
        government.setName(governmentName);
        government.setCorporation(-1L);
        long infoId = this.create(government);
        if(government.getName().equals("-1")){
            government.setName(Government.class.getSimpleName() + infoId);
            this.update(government);
        }
        //创建登录信息
        User user_insert = new User();
        if(user.getUserName() == null || user.getUserName().isEmpty())
            user_insert.setUserName("-1");
        else
            user_insert.setUserName(user.getUserName());
        if(user.getPassWord() == null || user.getPassWord().isEmpty())
            user_insert.setPassWord("123456");
        else
            user_insert.setPassWord(user.getPassWord());
        user_insert.setPhone(user.getPhone());
        user_insert.setUserType(UserType.Government);
        user_insert.setUserInfo(infoId);
        user_insert.setCorporation(-1L);
        long userId = userBiz.create(user_insert);
        if(user.getUserName() == null || user.getUserName().isEmpty()){
            user_insert.setUserName("username" + userId);
            userBiz.update(user_insert);
        }else{
            User user_checkun = userBiz.findUserByUname(user.getUserName());
            if(user_checkun != null){
                user_insert.setUserName("username" + userId);
                userBiz.update(user_insert);
            }
        }
        return userId;
    }

    /**
     * @param entityId
     * @param paramMap 政府基本信息字段(name)
     */
    @Override
    @Transactional
    public void updateByMap(Long entityId, Map<String, String> paramMap) {
        if(entityId == null){
            throw new GovernmentBizException(GovernmentBizException.GOVERNMENTBIZ_PARAMS_ILLEGAL, "政府基本信息主键不能为空");
        }
        Government government = this.getById(entityId);
        if(government == null || government.getIfDeleted()){
            throw new GovernmentBizException(GovernmentBizException.GOVERNMENTBIZ_PARAMS_ILLEGAL, "没有符合条件的政府基本信息，entityId: %d", entityId);
        }
        String name = paramMap.get("name");
        if(name != null && !name.isEmpty()) {
            government.setName(name);
            this.update(government);
        }
    }

    @Override
    @Transactional
    public void deleteByUserId(Long userId) {
        if(userId == null){
            throw new GovernmentBizException(GovernmentBizException.GOVERNMENTBIZ_PARAMS_ILLEGAL, "用户登录信息主键不能为空");
        }
        User user = userBiz.getById(userId);
        if(user == null || user.getIfDeleted() || user.getUserType().getValue().intValue() != UserType.Government.getValue().intValue()){
            throw new GovernmentBizException(GovernmentBizException.GOVERNMENTBIZ_NOSUIT_RESULT, "没有符合条件的用户登录信息，userId: %d", userId);
        }
        Government government = this.getById(user.getUserInfo());
        if(government == null || government.getIfDeleted()){
            throw new GovernmentBizException(GovernmentBizException.GOVERNMENTBIZ_NOSUIT_RESULT, "没有对应的政府基本信息，userId: %d", userId);
        }
        this.delete(government);
        userBiz.delete(user);
    }
}
