package cn.AssassinG.ScsyERP.User.core.biz;

import cn.AssassinG.ScsyERP.User.facade.entity.Permission;
import cn.AssassinG.ScsyERP.common.core.biz.BaseBiz;

import java.util.List;

public interface PermissionBiz extends BaseBiz<Permission> {
    /**
     * @return 返回所有权限信息的列表
     * 抛出运行异常：DAO异常
     */
    List<Permission> findAllPermission();
}
