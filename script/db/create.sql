drop table if exists auth_account_role;
drop table if exists auth_role_permission;
drop table if exists auth_account;
drop table if exists auth_permission;
drop table if exists auth_role;

drop table if exists tos_advert_info;
drop table if exists tos_call_record;
drop table if exists tos_dinner_table;
drop table if exists tos_dishes;
drop table if exists tos_dishes_category;
drop table if exists tos_help_document;
drop table if exists tos_order;
drop table if exists tos_order_detail;
drop table if exists tos_order_rating;

/*==============================================================*/
/* Table: auth_account                                          */
/*==============================================================*/
create table auth_account
(
   id                   int unsigned not null auto_increment,
   account              varchar(30) not null comment '帐号',
   password             varchar(64) not null comment '密码',
   enabled              bit default 1 comment '是否可用',
   locked               bit default 0 comment '是否锁定',
   create_time          datetime default CURRENT_TIMESTAMP,
   primary key (id)
);

/*==============================================================*/
/* Table: auth_account_role                                     */
/*==============================================================*/
create table auth_account_role
(
   id                   int unsigned not null auto_increment,
   account_id           int unsigned not null comment '帐号ID',
   role_id              int unsigned not null comment '角色ID',
   primary key (id)
);

/*==============================================================*/
/* Table: auth_permission                                       */
/*==============================================================*/
create table auth_permission
(
   id                   int unsigned not null auto_increment,
   pid                  int unsigned not null,
   name                 varchar(255) not null,
   link                 varchar(512) not null,
   primary key (id)
);

/*==============================================================*/
/* Table: auth_role                                             */
/*==============================================================*/
create table auth_role
(
   id                   int unsigned not null auto_increment,
   name                 varchar(150) not null comment '角色名',
   description          varchar(255) comment '描述',
   primary key (id)
);

/*==============================================================*/
/* Table: auth_role_permission                                  */
/*==============================================================*/
create table auth_role_permission
(
   id                   int unsigned not null auto_increment,
   role_id              int unsigned not null comment '角色ID',
   permission_id        int unsigned not null comment '权限ID',
   primary key (id)
);

/*==============================================================*/
/* Table: tos_advert_info                                       */
/*==============================================================*/
create table tos_advert_info
(
   id                   int unsigned not null auto_increment,
   type                 int not null comment '广告分类主要包括图片还是视频',
   link                 varchar(512) not null comment '广告地址',
   sort                 int default 0 comment '排序',
   create_time          datetime default CURRENT_TIMESTAMP comment '创建时间',
   primary key (id)
);

/*==============================================================*/
/* Table: tos_call_record                                       */
/*==============================================================*/
create table tos_call_record
(
   id                   int unsigned not null auto_increment,
   table_no             varchar(8) not null comment '枱号',
   call_code            varchar(255) not null comment '呼叫类型',
   content              varchar(1024) comment '呼叫内容',
   create_time          datetime default CURRENT_TIMESTAMP comment '创建时间',
   primary key (id)
);

/*==============================================================*/
/* Table: tos_dinner_table                                      */
/*==============================================================*/
create table tos_dinner_table
(
   id                   int unsigned not null auto_increment,
   table_no             varchar(8) not null comment '枱号',
   primary key (id)
);

/*==============================================================*/
/* Table: tos_dishes                                            */
/*==============================================================*/
create table tos_dishes
(
   id                   int unsigned not null auto_increment,
   category_id          int unsigned not null comment '分类ID',
   code                 varchar(32) not null comment '代码',
   name                 varchar(255) not null comment '菜品名',
   description          varchar(512) comment '描述',
   image                varchar(512) comment '图片',
   price                float(10,2) not null comment '价格',
   discount_price       float(10,2) comment '优惠价',
   state                bit default 1 comment '上架状态',
   sort                 int default 0 comment '排序',
   create_time          datetime default CURRENT_TIMESTAMP comment '创建时间',
   primary key (id)
);

/*==============================================================*/
/* Table: tos_dishes_category                                   */
/*==============================================================*/
create table tos_dishes_category
(
   id                   int unsigned not null auto_increment,
   name                 varchar(64) not null comment '分类名',
   description          varchar(512) comment '描述',
   image                varchar(512) comment '图片',
   sort                 int default 0 comment '排序',
   create_time          datetime default CURRENT_TIMESTAMP comment '创建时间',
   primary key (id)
);

/*==============================================================*/
/* Table: tos_help_document                                     */
/*==============================================================*/
create table tos_help_document
(
   id                   int unsigned not null auto_increment,
   title                varchar(255) not null comment '标题',
   content              text not null comment '内容',
   sort                 int default 0 comment '排序',
   create_time          datetime default CURRENT_TIMESTAMP,
   primary key (id)
);

/*==============================================================*/
/* Table: tos_order                                             */
/*==============================================================*/
create table tos_order
(
   id                   int unsigned not null auto_increment,
   order_no             varchar(128) not null comment '订单号',
   order_date           datetime not null default CURRENT_TIMESTAMP comment '订单时间',
   table_no             varchar(8) not null comment '枱号',
   price                float(10,2) not null comment '订单价钱',
   order_state          int not null comment '代表出单状态和付款状态',
   need_invoice         bit default 0 comment '是否开发票',
   invoice_title        varchar(512) comment '发票抬头',
   primary key (id)
);

/*==============================================================*/
/* Table: tos_order_detail                                      */
/*==============================================================*/
create table tos_order_detail
(
   id                   int unsigned not null auto_increment,
   order_no             varchar(128) not null comment '订单号',
   dishes_code          varchar(32) not null comment '菜品代码',
   dishes_name          varchar(255) not null comment '菜品名',
   price                float(10,2) not null comment '菜品价格',
   amount               int not null default 1 comment '菜品数量',
   state                int not null default 0 comment '是否上菜',
   primary key (id)
);

/*==============================================================*/
/* Table: tos_order_rating                                      */
/*==============================================================*/
create table tos_order_rating
(
   id                   int unsigned not null auto_increment,
   order_no             varchar(128) comment '订单号',
   taste                int comment '口味',
   speed                int comment '速度',
   service              int comment '服务',
   environment          int comment '环境',
   opinion              varchar(1024) comment '意见',
   contacts             varchar(255) comment '联系人',
   mobile               varchar(32) comment '手机',
   primary key (id)
);

alter table auth_account_role add constraint FK_account_role foreign key (account_id)
      references auth_account (id) on delete restrict on update restrict;

alter table auth_account_role add constraint FK_role_account foreign key (role_id)
      references auth_role (id) on delete restrict on update restrict;

alter table auth_role_permission add constraint FK_permission_role foreign key (permission_id)
      references auth_permission (id) on delete restrict on update restrict;

alter table auth_role_permission add constraint FK_role_permission foreign key (role_id)
      references auth_role (id) on delete restrict on update restrict;

alter table tos_dishes add constraint FK_dishes_catetory foreign key (category_id)
      references tos_dishes_category (id) on delete restrict on update restrict;

