package cn.AssassinG.ScsyERP.User.facade.service.impl;

import cn.AssassinG.ScsyERP.User.core.biz.PermissionBiz;
import cn.AssassinG.ScsyERP.User.facade.entity.Permission;
import cn.AssassinG.ScsyERP.User.facade.service.PermissionServiceFacade;
import cn.AssassinG.ScsyERP.common.core.biz.BaseBiz;
import cn.AssassinG.ScsyERP.common.core.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("PermissionServiceFacade")
public class PermissionServiceFacadeImpl extends BaseServiceImpl<Permission> implements PermissionServiceFacade {

    @Autowired
    private PermissionBiz permissionBiz;

    @Override
    protected BaseBiz<Permission> getBiz() {
        return this.permissionBiz;
    }

    @Override
    public void updateByMap(Long entityId, Map<String, String> paramMap) {
        permissionBiz.updateByMap(entityId, paramMap);
    }

    public List<Permission> findAllPermission() {
        return permissionBiz.findAllPermission();
    }
}
