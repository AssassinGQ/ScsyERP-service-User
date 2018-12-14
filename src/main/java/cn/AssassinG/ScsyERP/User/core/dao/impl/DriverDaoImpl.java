package cn.AssassinG.ScsyERP.User.core.dao.impl;

import cn.AssassinG.ScsyERP.User.core.dao.DriverDao;
import cn.AssassinG.ScsyERP.User.facade.entity.Driver;
import cn.AssassinG.ScsyERP.common.core.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository(value="DriverDao")
public class DriverDaoImpl extends BaseDaoImpl<Driver> implements DriverDao {
}
