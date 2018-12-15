package cn.AssassinG.ScsyERP.User.core.biz.impl;

import cn.AssassinG.ScsyERP.User.core.biz.PermissionBiz;
import cn.AssassinG.ScsyERP.User.core.dao.PermissionDao;
import cn.AssassinG.ScsyERP.User.facade.entity.Permission;
import cn.AssassinG.ScsyERP.common.core.biz.impl.BaseBizImpl;
import cn.AssassinG.ScsyERP.common.core.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("AdminBiz")
public class PermissionBizImpl extends BaseBizImpl<Permission> implements PermissionBiz {

    @Autowired
    private PermissionDao permissionDao;

    @Override
    protected BaseDao<Permission> getDao() {
        return this.permissionDao;
    }


    /**
     * @param entityId
     * @param paramMap 管理员基本信息字段()
     */
    @Override
    @Transactional
    public void updateByMap(Long entityId, Map<String, String> paramMap) {
//        if(entityId == null){
//            throw new AdminBizException(AdminBizException.ADMINBIZ_PARAMS_ILLEGAL, "承运方管理员基本信息主键不能为空");
//        }
//        Admin admin = this.getById(entityId);
//        if(admin == null || admin.getIfDeleted()){
//            throw new AdminBizException(AdminBizException.ADMINBIZ_NOSUIT_RESULT, "没有符合条件的承运方管理员基本信息，entityId: %d", entityId);
//        }
//        String name = paramMap.get("name");
//        if(name != null && !name.isEmpty()) {
//            admin.setName(name);
//            this.update(admin);
//        }
    }

    public List<Permission> findAllPermission() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("IfDeleted", false);
        return permissionDao.listBy(params);
    }
}
