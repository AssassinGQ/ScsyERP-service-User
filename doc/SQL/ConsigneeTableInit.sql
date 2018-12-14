drop table if exists t_consignee;

create table t_consignee (
  id            BIGINT(20)  NOT NULL AUTO_INCREMENT   COMMENT 'ID',
  corporation   BIGINT(20)  NOT NULL                    COMMENT '所属承运方ID',
  #   create_time DATETIME   NOT NULL DEFAULT now()  COMMENT '创建时间',
  #   update_time DATETIME   NOT NULL DEFAULT now()  COMMENT '最后修改时间',
  create_time   DATETIME    NOT NULL DEFAULT now()    COMMENT '创建时间',
  update_time   DATETIME    NOT NULL DEFAULT now()    COMMENT '最后修改时间',
  delete_time   DATETIME                                COMMENT '删除时间',
  is_deleted    BOOLEAN     NOT NULL DEFAULT false    COMMENT '数据是否已经删除',
  name          VARCHAR(30) NOT NULL                    COMMENT '名称',
  address       VARCHAR(100) NOT NULL                   COMMENT '地址',
  man_name      VARCHAR(20)                              COMMENT '联系人姓名',
  PRIMARY KEY (id)
);

alter table t_consignee comment '收货方信息表';

## 权限的初始化数据
insert into t_consignee(corporation, name, address, man_name) values (1, "张琦收货方", "湖北武汉", "张八收货员");