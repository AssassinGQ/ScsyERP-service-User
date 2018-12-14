package cn.AssassinG.ScsyERP.User.core.dao.impl;

import cn.AssassinG.ScsyERP.User.core.dao.CustomerDao;
import cn.AssassinG.ScsyERP.User.facade.entity.Customer;
import cn.AssassinG.ScsyERP.common.core.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository(value="CustomerDao")
public class CustomerDaoImpl extends BaseDaoImpl<Customer> implements CustomerDao {
}
