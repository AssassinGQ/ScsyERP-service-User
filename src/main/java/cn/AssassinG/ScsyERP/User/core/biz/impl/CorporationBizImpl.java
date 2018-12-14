package cn.AssassinG.ScsyERP.User.core.biz.impl;

import cn.AssassinG.ScsyERP.User.core.biz.CorporationBiz;
import cn.AssassinG.ScsyERP.User.core.dao.CorporationDao;
import cn.AssassinG.ScsyERP.User.facade.entity.Corporation;
import cn.AssassinG.ScsyERP.User.facade.entity.User;
import cn.AssassinG.ScsyERP.User.facade.enums.UserType;
import cn.AssassinG.ScsyERP.User.facade.exceptions.CorporationBizException;
import cn.AssassinG.ScsyERP.common.core.dao.BaseDao;
import cn.AssassinG.ScsyERP.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Component("CorporationBiz")
public class CorporationBizImpl extends LoginableBizImpl<Corporation> implements CorporationBiz {

    @Autowired
    private CorporationDao corporationDao;

    @Override
    protected BaseDao<Corporation> getDao() {
        return this.corporationDao;
    }

    /**
     * 屏蔽createWithUser
     * @param entity
     * @param user
     * @return
     */
    @Override
    public Long createWithUser(Corporation entity, User user) {
        return -1L;
    }

    @Override
    @Transactional
    public Long create(String token, User user, Map<String, Object> paramMap) {
        if(!token.equals("superadminabcd1234")) {
            throw new CorporationBizException(CorporationBizException.CORPORATIONBIZ_NOPERMISSION, "权限码不正确:%s", token);
        }
        if(!StringUtils.isMobileNum(user.getPhone())){
            throw new CorporationBizException(CorporationBizException.CORPORATIONBIZ_PARAMS_ILLEGAL, "请输入合法的手机号码:%s", user.getPhone());
        }
//        if(user.getUserType() == null ||
//                (user.getUserType().getValue().intValue() != UserType.Corporation.getValue().intValue())) {
//            throw new CorporationBizException(CorporationBizException.CORPORATIONBIZ_PARAMS_ILLEGAL, "只能申请承运方账号");
//        }
        User user_checkphone = userBiz.findUserByPhone(user.getPhone());
        if(user_checkphone != null){
            throw new CorporationBizException(CorporationBizException.CORPORATIONBIZ_PARAMS_ILLEGAL, "手机号码已经被占用:%s", user.getPhone());
        }
        //创建基本信息
        String corporationName = (String) paramMap.get("name");
        Corporation corporation = new Corporation();
        if(corporationName == null || corporationName.isEmpty())
            corporationName = "-1";
        corporation.setName(corporationName);
        long infoId = this.create(corporation);
        if(corporation.getName().equals("-1")){
            corporation.setName(Corporation.class.getSimpleName() + infoId);
            this.update(corporation);
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
        user_insert.setUserType(UserType.Corporation);
        user_insert.setUserInfo(infoId);
        user_insert.setCorporation(-1L);
        long userId = userBiz.create(user_insert);
        if(user_insert.getUserName().equals("-1")){
            user_insert.setUserName(User.class.getSimpleName() + userId);
            userBiz.update(user_insert);
        }
        return userId;
    }

    /**
     * @param entityId
     * @param paramMap 承运方基本信息字段(name)
     */
    @Override
    @Transactional
    public void updateByMap(Long entityId, Map<String, String> paramMap) {
        if(entityId == null){
            throw new CorporationBizException(CorporationBizException.CORPORATIONBIZ_PARAMS_ILLEGAL, "承运方基本信息主键不能为空");
        }
        Corporation corporation = this.getById(entityId);
        if(corporation == null || corporation.getIfDeleted()){
            throw new CorporationBizException(CorporationBizException.CORPORATIONBIZ_PARAMS_ILLEGAL, "没有符合条件的承运方基本信息，entityId: %d", entityId);
        }
        String name = paramMap.get("name");
        if(name != null && !name.isEmpty()) {
            corporation.setName(name);
            this.update(corporation);
        }
    }

    @Override
    @Transactional
    public void deleteByUserId(Long userId) {
        if(userId == null){
            throw new CorporationBizException(CorporationBizException.CORPORATIONBIZ_PARAMS_ILLEGAL, "用户登录信息主键不能为空");
        }
        User user = userBiz.getById(userId);
        if(user == null || user.getIfDeleted() || user.getUserType().getValue().intValue() != UserType.Corporation.getValue().intValue()){
            throw new CorporationBizException(CorporationBizException.CORPORATIONBIZ_NOSUIT_RESULT, "没有符合条件的用户登录信息，userId: %d", userId);
        }
        Corporation corporation = this.getById(user.getUserInfo());
        if(corporation == null || corporation.getIfDeleted()){
            throw new CorporationBizException(CorporationBizException.CORPORATIONBIZ_NOSUIT_RESULT, "没有对应的承运方基本信息，userId: %d", userId);
        }
        this.delete(corporation);
        userBiz.delete(user);
    }


}
