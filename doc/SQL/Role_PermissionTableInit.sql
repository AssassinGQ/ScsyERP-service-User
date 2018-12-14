drop table if exists t_role_permission;

create table t_role_permission (
  id            BIGINT(20)  NOT NULL AUTO_INCREMENT   COMMENT 'ID',
  corporation   BIGINT(20)  NOT NULL                    COMMENT '所属承运方ID',
  #   create_time DATETIME   NOT NULL DEFAULT now()  COMMENT '创建时间',
  #   update_time DATETIME   NOT NULL DEFAULT now()  COMMENT '最后修改时间',
  create_time   DATETIME    NOT NULL DEFAULT now()    COMMENT '创建时间',
  update_time   DATETIME    NOT NULL DEFAULT now()    COMMENT '最后修改时间',
  delete_time   DATETIME                                COMMENT '删除时间',
  is_deleted    BOOLEAN     NOT NULL DEFAULT false    COMMENT '数据是否已经删除',
  permission_id   BIGINT(20)  NOT NULL                  COMMENT '权限id',
  role_id         BIGINT(20)  NOT NULL                  COMMENT '角色id',
  PRIMARY KEY (id)
);

alter table t_role_permission comment '角色权限关联表';

## 用户角色关联信息初始化
insert into t_role_permission(corporation, permission_id, role_id) values (1, "1", "1");
insert into t_role_permission(corporation, permission_id, role_id) values (1, "3", "1");
insert into t_role_permission(corporation, permission_id, role_id) values (1, "1", "2");
insert into t_role_permission(corporation, permission_id, role_id) values (1, "2", "2");