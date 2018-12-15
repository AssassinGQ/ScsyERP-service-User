package cn.AssassinG.ScsyERP.User.facade.service.impl;

import cn.AssassinG.ScsyERP.User.core.biz.ManufacturerBiz;
import cn.AssassinG.ScsyERP.User.core.biz.LoginableBiz;
import cn.AssassinG.ScsyERP.User.facade.entity.Manufacturer;
import cn.AssassinG.ScsyERP.User.facade.service.ManufacturerServiceFacade;
import cn.AssassinG.ScsyERP.common.exceptions.BizException;
import cn.AssassinG.ScsyERP.common.exceptions.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ManufacturerServiceFacade")
public class ManufacturerServiceFacadeImpl extends LoginableServiceImpl<Manufacturer> implements ManufacturerServiceFacade {
    @Autowired
    private ManufacturerBiz manufacturerBiz;
    @Override
    protected LoginableBiz<Manufacturer> getLoginableBiz() {
        return this.manufacturerBiz;
    }

    @Override
    public void addWorkshops(Long entityId, String jsonArrayStr) {
        try {
            this.manufacturerBiz.addWorkshops(entityId, jsonArrayStr);
        }catch(BizException | DaoException e){
            throw e;
        }
    }

    @Override
    public void removeWorkshop(Long entityId, Long workshopId) {
        this.manufacturerBiz.removeWorkshop(entityId, workshopId);
    }
}
