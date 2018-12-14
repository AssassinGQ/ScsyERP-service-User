drop table if exists t_government;

create table t_government (
  id            BIGINT(20)  NOT NULL AUTO_INCREMENT   COMMENT 'ID',
  corporation   BIGINT(20)  NOT NULL                    COMMENT '所属承运方ID',
  #   create_time DATETIME   NOT NULL DEFAULT now()  COMMENT '创建时间',
  #   update_time DATETIME   NOT NULL DEFAULT now()  COMMENT '最后修改时间',
  create_time   DATETIME    NOT NULL DEFAULT now()    COMMENT '创建时间',
  update_time   DATETIME    NOT NULL DEFAULT now()    COMMENT '最后修改时间',
  delete_time   DATETIME                                COMMENT '删除时间',
  is_deleted    BOOLEAN     NOT NULL DEFAULT false    COMMENT '数据是否已经删除',
  name          VARCHAR(30) NOT NULL                    COMMENT '名称',
  dept          VARCHAR(30) NOT NULL                    COMMENT '部门',
  PRIMARY KEY (id)
);

alter table t_government comment '政府信息表';

## 权限的初始化数据
insert into t_government(corporation, name, dept) values (-1, "运管部", "TransportDept");
insert into t_government(corporation, name, dept) values (-1, "交警部", "TrafficPoliceDept");
insert into t_government(corporation, name, dept) values (-1, "环保部", "EnvironmentalDept");
insert into t_government(corporation, name, dept) values (-1, "消防部", "FireDept");
insert into t_government(corporation, name, dept) values (-1, "安监部", "SafetySupervisionDept");