package cn.AssassinG.ScsyERP.User.core.biz;

import cn.AssassinG.ScsyERP.User.facade.entity.Corporation;
import cn.AssassinG.ScsyERP.User.facade.entity.User;

import java.util.Map;

public interface CorporationBiz extends LoginableBiz<Corporation> {
    /**
     * 生成承运方账号
     * @param token 权限码
     * @param user 上传的登录信息封装后的User
     * @param paramMap 基本信息中的字段信息(name)
     * @return 创建的承运方对应登录信息的主键
     * 抛出运行异常：权限码不正确、参数不合法、DAO异常
     */
    Long create(String token, User user, Map<String, Object> paramMap);
}
