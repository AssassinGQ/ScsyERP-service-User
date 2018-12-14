package cn.AssassinG.ScsyERP.User.core.dao.impl;

import cn.AssassinG.ScsyERP.User.core.dao.GovernmentDao;
import cn.AssassinG.ScsyERP.User.facade.entity.Government;
import cn.AssassinG.ScsyERP.common.core.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository(value="GovernmentDao")
public class GovernmentImpl extends BaseDaoImpl<Government> implements GovernmentDao {
}
