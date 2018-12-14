drop table if exists t_permission;

create table t_permission (
  id            BIGINT(20)  NOT NULL AUTO_INCREMENT   COMMENT 'ID',
  corporation   BIGINT(20)  NOT NULL                    COMMENT '所属承运方ID',
  #   create_time DATETIME   NOT NULL DEFAULT now()  COMMENT '创建时间',
  #   update_time DATETIME   NOT NULL DEFAULT now()  COMMENT '最后修改时间',
  create_time   DATETIME    NOT NULL DEFAULT now()    COMMENT '创建时间',
  update_time   DATETIME    NOT NULL DEFAULT now()    COMMENT '最后修改时间',
  delete_time   DATETIME                                COMMENT '删除时间',
  is_deleted    BOOLEAN     NOT NULL DEFAULT false    COMMENT '数据是否已经删除',
  permission_name   VARCHAR(50) NOT NULL                COMMENT '权限名称',
  permission_desc   VARCHAR(50)                         COMMENT '权限描述',
  PRIMARY KEY (id)
);

alter table t_permission comment '权限信息表';

## 权限的初始化数据
insert into t_permission(corporation, permission_name, permission_desc) values (1, "ROLE_URL_HOME", "访问主页");
insert into t_permission(corporation, permission_name, permission_desc) values (1, "ROLE_URL_AUTHCONFIG", "访问权限配置页面");
insert into t_permission(corporation, permission_name, permission_desc) values (1, "RES_SER_AUTHREAD", "读取所有权限配置");
insert into t_permission(corporation, permission_name, permission_desc) values (1, "RES_SER_AUTHCONFIG", "修改所有权限配置");