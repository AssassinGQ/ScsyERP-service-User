package cn.AssassinG.ScsyERP.User.core.dao.impl;

import cn.AssassinG.ScsyERP.User.core.dao.ConsigneeDao;
import cn.AssassinG.ScsyERP.User.facade.entity.Consignee;
import cn.AssassinG.ScsyERP.common.core.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository(value="ConsigneeDao")
public class ConsigneeDaoImpl extends BaseDaoImpl<Consignee> implements ConsigneeDao {
}
