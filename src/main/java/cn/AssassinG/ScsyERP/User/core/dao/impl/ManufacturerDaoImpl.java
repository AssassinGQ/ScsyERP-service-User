package cn.AssassinG.ScsyERP.User.core.dao.impl;

import cn.AssassinG.ScsyERP.User.core.dao.ManufacturerDao;
import cn.AssassinG.ScsyERP.User.facade.entity.Manufacturer;
import cn.AssassinG.ScsyERP.common.core.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository(value="ManufacturerDao")
public class ManufacturerDaoImpl extends BaseDaoImpl<Manufacturer> implements ManufacturerDao {
}
