drop table if exists t_user_permission;

create table t_user_permission (
  id            BIGINT(20)  NOT NULL AUTO_INCREMENT   COMMENT 'ID',
  corporation   BIGINT(20)  NOT NULL                    COMMENT '所属承运方ID',
  #   create_time DATETIME   NOT NULL DEFAULT now()  COMMENT '创建时间',
  #   update_time DATETIME   NOT NULL DEFAULT now()  COMMENT '最后修改时间',
  create_time   DATETIME    NOT NULL DEFAULT now()    COMMENT '创建时间',
  update_time   DATETIME    NOT NULL DEFAULT now()    COMMENT '最后修改时间',
  delete_time   DATETIME                                COMMENT '删除时间',
  is_deleted    BOOLEAN     NOT NULL DEFAULT false    COMMENT '数据是否已经删除',
  user_id       BIGINT(20)  NOT NULL                    COMMENT '用户id',
  permission_id         BIGINT(20)  NOT NULL            COMMENT '权限id',
  user_permission_type  VARCHAR(20) NOT NULL            COMMENT '关系类型',
  PRIMARY KEY (id)
);

alter table t_user_permission comment '用户权限关联表';

## 用户角色关联信息初始化
insert into t_user_permission(corporation, user_id, permission_id, user_permission_type) values (1, 1, 1, "屏蔽");
insert into t_user_permission(corporation, user_id, permission_id, user_permission_type) values (1, 2, 2, "额外");