#remote
#MmabCD123419930926
drop table if exists t_user;

create table t_user (
  id            BIGINT(20)  NOT NULL AUTO_INCREMENT    COMMENT 'ID',
  corporation   BIGINT(20)  NOT NULL                     COMMENT '所属承运方ID',
  #   create_time DATETIME   NOT NULL DEFAULT now()  COMMENT '创建时间',
  #   update_time DATETIME   NOT NULL DEFAULT now()  COMMENT '最后修改时间',
  create_time   DATETIME    NOT NULL DEFAULT now()      COMMENT '创建时间',
  update_time   DATETIME    NOT NULL DEFAULT now()      COMMENT '最后修改时间',
  delete_time   DATETIME                                  COMMENT '删除时间',
  is_deleted    BOOLEAN     NOT NULL DEFAULT false      COMMENT '数据是否已经删除',
  user_name     VARCHAR(30) NOT NULL                     COMMENT '登录用户名',
  _password     VARCHAR(30) NOT NULL                      COMMENT '登录密码',
  phone         VARCHAR(11) NOT NULL                      COMMENT '手机号',
  vcode         VARCHAR(6)                                 COMMENT '验证码',
  vcode_time    DATETIME                                   COMMENT '验证码有效期',
  if_registered BOOLEAN     NOT NULL  DEFAULT false     COMMENT '是否已经注册',
  user_type     VARCHAR(20) NOT NULL                     COMMENT '用户类型',
  user_info     BIGINT(20)  NOT NULL                      COMMENT '用户信息',
  PRIMARY KEY (id)
);

alter table t_user comment '用户信息表';

## 用户的初始化数据(密码123456)
insert into t_user (corporation, user_name, _password, if_registered, phone, user_type, user_info) values (1, "superadmin", "{noop}123456", true, 18888888888, "SuperAdmin", 1);
insert into t_user (corporation, user_name, _password, if_registered, phone, user_type, user_info) values (1, "corporation1", "{noop}123456", true, 18868187537, "Corporation", 1);
insert into t_user (corporation, user_name, _password, if_registered, phone, user_type, user_info) values (1, "corporation2", "{noop}123456", true, 18868187536, "Corporation", 2);
insert into t_user (corporation, user_name, _password, if_registered, phone, user_type, user_info) values (1, "government1", "{noop}123456", true, 18868187535, "Government", 1);
insert into t_user (corporation, user_name, _password, if_registered, phone, user_type, user_info) values (1, "government2", "{noop}123456", true, 18868187534, "Government", 2);
insert into t_user (corporation, user_name, _password, if_registered, phone, user_type, user_info) values (1, "government3", "{noop}123456", true, 18868187533, "Government", 3);
insert into t_user (corporation, user_name, _password, if_registered, phone, user_type, user_info) values (1, "government4", "{noop}123456", true, 18868187532, "Government", 4);
insert into t_user (corporation, user_name, _password, if_registered, phone, user_type, user_info) values (1, "government5", "{noop}123456", true, 18868187531, "Government", 5);
insert into t_user (corporation, user_name, _password, if_registered, phone, user_type, user_info) values (1, "admin1", "{noop}123456", true, 18868187530, "Admin", 1);
insert into t_user (corporation, user_name, _password, if_registered, phone, user_type, user_info) values (1, "admin2", "{noop}123456", true, 18868187529, "Admin", 2);
insert into t_user (corporation, user_name, _password, if_registered, phone, user_type, user_info) values (1, "admin3", "{noop}123456", true, 18868187528, "Admin", 3);
insert into t_user (corporation, user_name, _password, if_registered, phone, user_type, user_info) values (1, "customer1", "{noop}123456", true, 18868187527, "Customer", 1);
insert into t_user (corporation, user_name, _password, if_registered, phone, user_type, user_info) values (1, "manufacturer1", "{noop}123456", true, 18868187526, "Manufacturer", 1);
insert into t_user (corporation, user_name, _password, if_registered, phone, user_type, user_info) values (1, "consignee1", "{noop}123456", true, 18868187525, "Consignee", 1);
insert into t_user (corporation, user_name, _password, if_registered, phone, user_type, user_info) values (1, "driver1", "{noop}123456", true, 18868187524, "Driver", 1);
insert into t_user (corporation, user_name, _password, if_registered, phone, user_type, user_info) values (1, "escort1", "{noop}123456", true, 18868187523, "Escort", 1);
