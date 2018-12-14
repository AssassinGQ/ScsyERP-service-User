package cn.AssassinG.ScsyERP.User.core.biz;

import cn.AssassinG.ScsyERP.User.facade.entity.Government;
import cn.AssassinG.ScsyERP.User.facade.entity.User;

import java.util.Map;

public interface GovernmentBiz extends LoginableBiz<Government> {
    /**
     * 生成政府账号
     * @param token 权限码
     * @param user 上传的登录信息封装后的User
     * @param paramMap 基本信息中的字段信息(name,dept)
     * @return
     * 抛出运行异常：权限码不正确、参数不合法、DAO异常
     */
    Long create(String token, User user, Map<String, Object> paramMap);
}
