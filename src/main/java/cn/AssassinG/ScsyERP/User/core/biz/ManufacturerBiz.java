package cn.AssassinG.ScsyERP.User.core.biz;

import cn.AssassinG.ScsyERP.User.facade.entity.Manufacturer;

public interface ManufacturerBiz extends LoginableBiz<Manufacturer> {
    void addWorkshops(Long entityId, String jsonArrayStr);
    void removeWorkshop(Long entityId, Long workshopId);
}
