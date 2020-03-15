drop table if exists t_role;

create table t_role (
  id            BIGINT(20)  NOT NULL AUTO_INCREMENT   COMMENT 'ID',
  corporation   BIGINT(20)  NOT NULL                    COMMENT '所属承运方ID',
  #   create_time DATETIME   NOT NULL DEFAULT now()  COMMENT '创建时间',
  #   update_time DATETIME   NOT NULL DEFAULT now()  COMMENT '最后修改时间',
  create_time   DATETIME    NOT NULL DEFAULT now()    COMMENT '创建时间',
  update_time   DATETIME    NOT NULL DEFAULT now()    COMMENT '最后修改时间',
  delete_time   DATETIME                                COMMENT '删除时间',
  is_deleted    BOOLEAN     NOT NULL DEFAULT false    COMMENT '数据是否已经删除',
  role_name   VARCHAR(50)   NOT NULL                    COMMENT '角色名称',
  role_desc   VARCHAR(50)                               COMMENT '角色描述',
  superrole_name VARCHAR(50)  NOT NULL                  COMMENT '父角色名称',
  PRIMARY KEY (id)
);

alter table t_role comment '角色信息表';

## 角色的初始化数据
insert into t_role(corporation, role_name, role_desc, superrole_name) values (1, "superadmin", "superadmin", "admin0");##1

insert into t_role(corporation, role_name, role_desc, superrole_name) values (1, "corpration", "corpration", "superadmin");##2
insert into t_role(corporation, role_name, role_desc, superrole_name) values (1, "government", "government", "superadmin");##3

insert into t_role(corporation, role_name, role_desc, superrole_name) values (1, "corp_admin", "corp_admin", "corpration");##4
insert into t_role(corporation, role_name, role_desc, superrole_name) values (1, "corp_partner", "corp_partner", "corpration");##5
insert into t_role(corporation, role_name, role_desc, superrole_name) values (1, "corp_employee", "corp_employee", "corpration");##6

insert into t_role(corporation, role_name, role_desc, superrole_name) values (1, "admin_warehouse", "admin_warehouse", "corp_admin");##7
insert into t_role(corporation, role_name, role_desc, superrole_name) values (1, "admin_project", "admin_project", "corp_admin");##8
insert into t_role(corporation, role_name, role_desc, superrole_name) values (1, "admin_finance", "admin_finance", "corp_admin");##9
insert into t_role(corporation, role_name, role_desc, superrole_name) values (1, "partner_consignor", "partner_consignor", "corp_partner");##10
insert into t_role(corporation, role_name, role_desc, superrole_name) values (1, "partner_manufacturer", "partner_manufacturer", "corp_partner");##11
insert into t_role(corporation, role_name, role_desc, superrole_name) values (1, "partner_receiver", "partner_receiver", "corp_partner");##12
insert into t_role(corporation, role_name, role_desc, superrole_name) values (1, "employee_driver", "employee_driver", "corp_employee");##13
insert into t_role(corporation, role_name, role_desc, superrole_name) values (1, "employee_supervisor", "employee_supervisor", "corp_employee");##14
insert into t_role(corporation, role_name, role_desc, superrole_name) values (1, "employee_leftworker", "employee_leftworker", "corp_employee");##15
insert into t_role(corporation, role_name, role_desc, superrole_name) values (1, "employee_driveworker", "employee_driveworker", "corp_employee");##16