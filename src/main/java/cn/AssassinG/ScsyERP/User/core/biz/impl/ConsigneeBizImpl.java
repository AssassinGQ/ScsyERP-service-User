package cn.AssassinG.ScsyERP.User.core.biz.impl;

import cn.AssassinG.ScsyERP.User.core.biz.ConsigneeBiz;
import cn.AssassinG.ScsyERP.User.core.dao.ConsigneeDao;
import cn.AssassinG.ScsyERP.User.facade.entity.Consignee;
import cn.AssassinG.ScsyERP.User.facade.entity.User;
import cn.AssassinG.ScsyERP.User.facade.enums.UserType;
import cn.AssassinG.ScsyERP.User.facade.exceptions.ConsigneeBizException;
import cn.AssassinG.ScsyERP.common.core.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Component("ConsigneeBiz")
public class ConsigneeBizImpl extends LoginableBizImpl<Consignee> implements ConsigneeBiz {

    @Autowired
    private ConsigneeDao consigneeDao;

    @Override
    protected BaseDao<Consignee> getDao() {
        return this.consigneeDao;
    }


    /**
     * @param entityId
     * @param paramMap 收货单位基本信息字段(name,address,manName)
     */
    @Override
    @Transactional
    public void updateByMap(Long entityId, Map<String, String> paramMap) {
        if(entityId == null){
            throw new ConsigneeBizException(ConsigneeBizException.CONSIGNEEBIZ_PARAMS_ILLEGAL, "收货单位基本信息主键不能为空");
        }
        Consignee consignee = this.getById(entityId);
        if(consignee == null || consignee.getIfDeleted()){
            throw new ConsigneeBizException(ConsigneeBizException.CONSIGNEEBIZ_NOSUIT_RESULT, "没有符合条件的收货单位基本信息，entityId: %d", entityId);
        }
        String name = paramMap.get("name");
        String address = paramMap.get("address");
        String manName = paramMap.get("manName");
        boolean flag = false;
        if(name != null && !name.isEmpty()) {
            consignee.setName(name);
            flag = true;
        }
        if(address != null && !address.isEmpty()) {
            consignee.setAddress(address);
            flag = true;
        }
        if(manName != null && !manName.isEmpty()) {
            consignee.setManName(manName);
            flag = true;
        }
        if(flag){
            this.update(consignee);
        }
    }

    @Override
    @Transactional
    public void deleteByUserId(Long userId) {
        if(userId == null){
            throw new ConsigneeBizException(ConsigneeBizException.CONSIGNEEBIZ_PARAMS_ILLEGAL, "用户登录信息主键不能为空");
        }
        User user = userBiz.getById(userId);
        if(user == null || user.getIfDeleted() || user.getUserType().getValue().intValue() != UserType.Consignee.getValue().intValue()){
            throw new ConsigneeBizException(ConsigneeBizException.CONSIGNEEBIZ_NOSUIT_RESULT, "没有符合条件的用户登录信息，userId: %d", userId);
        }
        Consignee consignee = this.getById(user.getUserInfo());
        if(consignee == null || consignee.getIfDeleted()){
            throw new ConsigneeBizException(ConsigneeBizException.CONSIGNEEBIZ_NOSUIT_RESULT, "没有对应的收货单位基本信息，userId: %d", userId);
        }
        this.delete(consignee);
        userBiz.delete(user);
    }

}
