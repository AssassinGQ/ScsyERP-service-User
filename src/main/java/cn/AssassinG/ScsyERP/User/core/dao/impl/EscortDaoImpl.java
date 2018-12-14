package cn.AssassinG.ScsyERP.User.core.dao.impl;

import cn.AssassinG.ScsyERP.User.core.dao.EscortDao;
import cn.AssassinG.ScsyERP.User.facade.entity.Escort;
import cn.AssassinG.ScsyERP.common.core.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository(value="EscortDao")
public class EscortDaoImpl extends BaseDaoImpl<Escort> implements EscortDao {
}
