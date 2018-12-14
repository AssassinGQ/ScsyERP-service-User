package cn.AssassinG.ScsyERP.User.core.biz.impl;

import cn.AssassinG.ScsyERP.User.core.biz.DriverBiz;
import cn.AssassinG.ScsyERP.User.core.dao.DriverDao;
import cn.AssassinG.ScsyERP.User.facade.entity.Driver;
import cn.AssassinG.ScsyERP.User.facade.entity.User;
import cn.AssassinG.ScsyERP.User.facade.enums.UserType;
import cn.AssassinG.ScsyERP.User.facade.exceptions.DriverBizException;
import cn.AssassinG.ScsyERP.common.core.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Component("DriverBiz")
public class DriverBizImpl extends LoginableBizImpl<Driver> implements DriverBiz {

    @Autowired
    private DriverDao driverDao;

    @Override
    protected BaseDao<Driver> getDao() {
        return this.driverDao;
    }


    /**
     * @param entityId
     * @param paramMap 驾驶员基本信息字段(name,driverLicense,capableCar,iDCardPhoto)
     */
    @Override
    @Transactional
    public void updateByMap(Long entityId, Map<String, String> paramMap) {
        if(entityId == null){
            throw new DriverBizException(DriverBizException.DRIVERBIZ_PARAMS_ILLEGAL, "驾驶员基本信息主键不能为空");
        }
        Driver driver = this.getById(entityId);
        if(driver == null || driver.getIfDeleted()){
            throw new DriverBizException(DriverBizException.DRIVERBIZ_NOSUIT_RESULT, "没有符合条件的驾驶员基本信息，entityId: %d", entityId);
        }
        try{
            String name = paramMap.get("name");
            String driverLicense = paramMap.get("driverLicense");
            String capableCar = paramMap.get("capableCar");
            Long iDCardPhoto = paramMap.get("iDCardPhoto") == null ? null : Long.valueOf(paramMap.get("iDCardPhoto"));
            boolean flag = false;
            if(name != null && !name.isEmpty()) {
                driver.setName(name);
                flag = true;
            }
            if(driverLicense != null && !driverLicense.isEmpty()){
                driver.setDriverLicense(driverLicense);
                flag = true;
            }
            if(capableCar != null && !capableCar.isEmpty()){
                driver.setCapableCar(capableCar);
                flag = true;
            }
            if(iDCardPhoto != null && iDCardPhoto > 0){
                driver.setIDCardPhoto(iDCardPhoto);
                flag = true;
            }
            if(flag)
                this.update(driver);
        }catch(NumberFormatException e){
            throw new DriverBizException(DriverBizException.DRIVERBIZ_NOSUIT_RESULT, "参数格式错误："+e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteByUserId(Long userId) {
        if(userId == null){
            throw new DriverBizException(DriverBizException.DRIVERBIZ_PARAMS_ILLEGAL, "用户登录信息主键不能为空");
        }
        User user = userBiz.getById(userId);
        if(user == null || user.getIfDeleted() || user.getUserType().getValue().intValue() != UserType.Driver.getValue().intValue()){
            throw new DriverBizException(DriverBizException.DRIVERBIZ_NOSUIT_RESULT, "没有符合条件的用户登录信息，userId: %d", userId);
        }
        Driver driver = this.getById(user.getUserInfo());
        if(driver == null || driver.getIfDeleted()){
            throw new DriverBizException(DriverBizException.DRIVERBIZ_NOSUIT_RESULT, "没有对应的驾驶员基本信息，userId: %d", userId);
        }
        this.delete(driver);
        userBiz.delete(user);
    }

}
