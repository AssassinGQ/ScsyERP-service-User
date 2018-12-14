#remote
#MmabCD123419930926
drop table if exists t_test;

# createWithUser table t_test (
#   Id            BIGINT(20)  NOT NULL  AUTO_INCREMENT  COMMENT 'ID',
#   create_time   DATETIME    NOT NULL  DEFAULT now()   COMMENT '创建时间',
#   update_time   DATETIME    NOT NULL  DEFAULT now()   COMMENT '最后修改时间',
#   delete_time   DATETIME                                COMMENT '删除时间',
#   is_deleted    BOOLEAN     NOT NULL  DEFAULT false   COMMENT '数据是否已经删除',
#   _ip            VARCHAR(20)                             COMMENT '最新IP值',
#   PRIMARY KEY (Id)
#);

create table t_test (
  id            BIGINT(20)  NOT NULL  AUTO_INCREMENT                  COMMENT 'ID',
  corporation   BIGINT(20) NOT NULL COMMENT '所属承运方ID',
  create_time   TIMESTAMP   NOT NULL  DEFAULT '2018-06-10 17:34:02'   COMMENT '创建时间',
  update_time   TIMESTAMP   NOT NULL  DEFAULT CURRENT_TIMESTAMP       COMMENT '最后修改时间',
  delete_time   TIMESTAMP                                               COMMENT '删除时间',
  is_deleted    BOOLEAN     NOT NULL  DEFAULT false                   COMMENT '数据是否已经删除',
  _ip            VARCHAR(20)                                            COMMENT '最新IP值',
  PRIMARY KEY (id)
);

alter table t_test comment '测试表';