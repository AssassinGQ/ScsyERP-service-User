package cn.AssassinG.ScsyERP.User.core.biz.impl;

import cn.AssassinG.ScsyERP.User.core.biz.EscortBiz;
import cn.AssassinG.ScsyERP.User.core.dao.EscortDao;
import cn.AssassinG.ScsyERP.User.facade.entity.Escort;
import cn.AssassinG.ScsyERP.User.facade.entity.User;
import cn.AssassinG.ScsyERP.User.facade.enums.UserType;
import cn.AssassinG.ScsyERP.User.facade.exceptions.EscortBizException;
import cn.AssassinG.ScsyERP.common.core.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Component("EscortBiz")
public class EscortBizImpl extends LoginableBizImpl<Escort> implements EscortBiz {

    @Autowired
    private EscortDao escortDao;

    @Override
    protected BaseDao<Escort> getDao() {
        return this.escortDao;
    }


    /**
     * @param entityId
     * @param paramMap 押运员基本信息字段(name)
     */
    @Override
    @Transactional
    public void updateByMap(Long entityId, Map<String, String> paramMap) {
        if(entityId == null){
            throw new EscortBizException(EscortBizException.ESCORTBIZ_PARAMS_ILLEGAL, "押运员基本信息主键不能为空");
        }
        Escort escort = this.getById(entityId);
        if(escort == null || escort.getIfDeleted()){
            throw new EscortBizException(EscortBizException.ESCORTBIZ_NOSUIT_RESULT, "没有符合条件的押运员基本信息，entityId: %d", entityId);
        }
        String name = paramMap.get("name");
        if(name != null && !name.isEmpty()) {
            escort.setName(name);
            this.update(escort);
        }
    }

    @Override
    @Transactional
    public void deleteByUserId(Long userId) {
        if(userId == null){
            throw new EscortBizException(EscortBizException.ESCORTBIZ_PARAMS_ILLEGAL, "用户登录信息主键不能为空");
        }
        User user = userBiz.getById(userId);
        if(user == null || user.getIfDeleted() || user.getUserType().getValue().intValue() != UserType.Escort.getValue().intValue()){
            throw new EscortBizException(EscortBizException.ESCORTBIZ_NOSUIT_RESULT, "没有符合条件的用户登录信息，userId: %d", userId);
        }
        Escort escort = this.getById(user.getUserInfo());
        if(escort == null || escort.getIfDeleted()){
            throw new EscortBizException(EscortBizException.ESCORTBIZ_NOSUIT_RESULT, "没有对应的押运员基本信息，userId: %d", userId);
        }
        this.delete(escort);
        userBiz.delete(user);
    }

}
