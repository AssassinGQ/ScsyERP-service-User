package cn.AssassinG.ScsyERP.User.core.dao.impl;

import cn.AssassinG.ScsyERP.User.core.dao.RoleDao;
import cn.AssassinG.ScsyERP.User.facade.entity.Role;
import cn.AssassinG.ScsyERP.common.core.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository(value="RoleDao")
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao {
    public static final String SQL_FINDBYUSERID = "listByUserId";
    public static final String SQL_FINDBYUSERNAME = "listByUsername";

    /**
     * 查询主键为Id的用户拥有的所有角色，包括子孙角色
     * @param Id
     * @return
     */
    public Set<Role> findByUserId(long Id) {
        List<Role> roles = super.getSessionTemplate().selectList(super.getStatement(SQL_FINDBYUSERID), Id);
        Set<Role> ret = new HashSet<Role>();
        ret.addAll(roles);
        Queue<Role> queue = new LinkedList<Role>();
        ((LinkedList<Role>) queue).addAll(roles);
        while(queue.size() > 0){
            Role role = queue.poll();
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("SuperRoleName", role.getRoleName());
            List<Role> tmproles = super.getSessionTemplate().selectList(super.getStatement(SQL_LIST_BY), params);
            ret.addAll(tmproles);
            for(Role tmprole : tmproles){
                queue.offer(tmprole);
            }
        }
        return ret;
    }

    /**
     * 查询用户名为UserName的用户拥有的所有角色，包括子孙角色
     * @param UserName
     * @return
     */
    public Set<Role> findByUsername(String UserName) {
        List<Role> roles = super.getSessionTemplate().selectList(super.getStatement(SQL_FINDBYUSERNAME), UserName);
        Set<Role> ret = new HashSet<Role>();
        ret.addAll(roles);
        Queue<Role> queue = new LinkedList<Role>();
        ((LinkedList<Role>) queue).addAll(roles);
        while(queue.size() > 0){
            Role role = queue.poll();
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("SuperRoleName", role.getRoleName());
            List<Role> tmproles = super.getSessionTemplate().selectList(super.getStatement(SQL_LIST_BY), params);
            ret.addAll(tmproles);
            for(Role tmprole : tmproles){
                queue.offer(tmprole);
            }
        }
        return ret;
    }
}
