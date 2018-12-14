package cn.AssassinG.ScsyERP.User.core.biz.impl;

import cn.AssassinG.ScsyERP.User.core.biz.AdminBiz;
import cn.AssassinG.ScsyERP.User.core.dao.AdminDao;
import cn.AssassinG.ScsyERP.User.facade.entity.Admin;
import cn.AssassinG.ScsyERP.User.facade.entity.User;
import cn.AssassinG.ScsyERP.User.facade.enums.UserType;
import cn.AssassinG.ScsyERP.User.facade.exceptions.AdminBizException;
import cn.AssassinG.ScsyERP.common.core.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Component("AdminBiz")
public class AdminBizImpl extends LoginableBizImpl<Admin> implements AdminBiz {

    @Autowired
    private AdminDao adminDao;

    @Override
    protected BaseDao<Admin> getDao() {
        return this.adminDao;
    }


    /**
     * @param entityId
     * @param paramMap 管理员基本信息字段(name)
     */
    @Override
    @Transactional
    public void updateByMap(Long entityId, Map<String, String> paramMap) {
        if(entityId == null){
            throw new AdminBizException(AdminBizException.ADMINBIZ_PARAMS_ILLEGAL, "承运方管理员基本信息主键不能为空");
        }
        Admin admin = this.getById(entityId);
        if(admin == null || admin.getIfDeleted()){
            throw new AdminBizException(AdminBizException.ADMINBIZ_NOSUIT_RESULT, "没有符合条件的承运方管理员基本信息，entityId: %d", entityId);
        }
        String name = paramMap.get("name");
        if(name != null && !name.isEmpty()) {
            admin.setName(name);
            this.update(admin);
        }
    }

    @Override
    @Transactional
    public void deleteByUserId(Long userId) {
        if(userId == null){
            throw new AdminBizException(AdminBizException.ADMINBIZ_PARAMS_ILLEGAL, "用户登录信息主键不能为空");
        }
        User user = userBiz.getById(userId);
        if(user == null || user.getIfDeleted() || user.getUserType().getValue().intValue() != UserType.Admin.getValue().intValue()){
            throw new AdminBizException(AdminBizException.ADMINBIZ_NOSUIT_RESULT, "没有符合条件的用户登录信息，userId: %d", userId);
        }
        Admin admin = this.getById(user.getUserInfo());
        if(admin == null || admin.getIfDeleted()){
            throw new AdminBizException(AdminBizException.ADMINBIZ_NOSUIT_RESULT, "没有对应的承运方管理员基本信息，userId: %d", userId);
        }
        this.delete(admin);
        userBiz.delete(user);
    }
}
