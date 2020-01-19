# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 39.108.252.77 (MySQL 5.7.28-log)
# Database: mall_shop
# Generation Time: 2020-01-19 06:34:20 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table tenant
# ------------------------------------------------------------

CREATE TABLE `tenant` (
  `id` bigint(20) NOT NULL,
  `username` varchar(60) NOT NULL DEFAULT '',
  `password` varchar(60) DEFAULT '',
  `nick_name` varchar(100) DEFAULT NULL COMMENT '昵称',
  `avatar` text,
  `kf_weixin` varchar(20) DEFAULT NULL COMMENT '客服微信号',
  `kf_weixin_qr` text COMMENT '客服微信二维码',
  `kf_phone` varchar(20) DEFAULT NULL COMMENT '客服电话',
  `credit` decimal(10,2) DEFAULT '0.00' COMMENT '余额',
  `charge` decimal(5,2) NOT NULL DEFAULT '0.10' COMMENT '充值费率',
  `is_auto_transfer` int(1) DEFAULT '2' COMMENT '是否自动打款1.是 2.否',
  `open_wxpay` int(1) DEFAULT '1' COMMENT '开启微信打款 1.是 2.否',
  `open_alipay` int(1) DEFAULT '1' COMMENT '开启支付宝打款 1.是 2.否',
  `open_sms` int(1) DEFAULT '2' COMMENT '开启短信登录 1.是 2.否',
  `sms_per` decimal(4,2) DEFAULT NULL COMMENT '短信单价',
  `sms_count` int(11) DEFAULT '100' COMMENT '短信数量',
  `storage_space_per` decimal(10,2) DEFAULT NULL COMMENT '存储空间单价',
  `storage_space_count` int(11) DEFAULT '100' COMMENT '存储空间大小',
  `enable` int(1) DEFAULT '1' COMMENT '1.启用  2.禁用',
  `enable_one_account` bit(1) DEFAULT b'0' COMMENT '是否开启只有1个账号登录',
  `expiration_time` datetime DEFAULT NULL COMMENT '到期时间',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_username` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='单元';

LOCK TABLES `tenant` WRITE;
/*!40000 ALTER TABLE `tenant` DISABLE KEYS */;

INSERT INTO `tenant` (`id`, `username`, `password`, `nick_name`, `avatar`, `kf_weixin`, `kf_weixin_qr`, `kf_phone`, `credit`, `charge`, `is_auto_transfer`, `open_wxpay`, `open_alipay`, `open_sms`, `sms_per`, `sms_count`, `storage_space_per`, `storage_space_count`, `enable`, `enable_one_account`, `expiration_time`, `create_time`, `update_time`)
VALUES
	(1211177466152165378,'csp123','$2a$10$fnxxU/wmaXQ3jff2uUBHb.tQ8gNolk.zcryLgolTAIfvy94HM7jwi','chenshaopp',NULL,NULL,NULL,NULL,0.00,0.10,2,1,1,2,NULL,100,NULL,100,1,b'0',NULL,'2019-12-29 14:49:45',NULL);

/*!40000 ALTER TABLE `tenant` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table tenant_admin_login_log
# ------------------------------------------------------------

CREATE TABLE `tenant_admin_login_log` (
  `id` bigint(20) NOT NULL,
  `tenant_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `ip` varchar(64) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `user_agent` varchar(100) DEFAULT NULL COMMENT '浏览器登录类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='后台用户登录日志表';



# Dump of table tenant_admin_permission_relation
# ------------------------------------------------------------

CREATE TABLE `tenant_admin_permission_relation` (
  `id` bigint(20) NOT NULL,
  `tenant_id` bigint(20) DEFAULT NULL,
  `permission_id` bigint(20) DEFAULT NULL,
  `type` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台用户和权限关系表(除角色中定义的权限以外的加减权限)';

LOCK TABLES `tenant_admin_permission_relation` WRITE;
/*!40000 ALTER TABLE `tenant_admin_permission_relation` DISABLE KEYS */;

INSERT INTO `tenant_admin_permission_relation` (`id`, `tenant_id`, `permission_id`, `type`)
VALUES
	(1212317884081115136,1211177466152165378,1212307963880194049,1);

/*!40000 ALTER TABLE `tenant_admin_permission_relation` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table tenant_admin_role_relation
# ------------------------------------------------------------

CREATE TABLE `tenant_admin_role_relation` (
  `id` bigint(20) NOT NULL,
  `tenant_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='后台用户和角色关系表';

LOCK TABLES `tenant_admin_role_relation` WRITE;
/*!40000 ALTER TABLE `tenant_admin_role_relation` DISABLE KEYS */;

INSERT INTO `tenant_admin_role_relation` (`id`, `tenant_id`, `role_id`)
VALUES
	(1212318734459473920,1211177466152165378,1212315980235382785);

/*!40000 ALTER TABLE `tenant_admin_role_relation` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table tenant_permission
# ------------------------------------------------------------

CREATE TABLE `tenant_permission` (
  `id` bigint(20) NOT NULL,
  `pid` bigint(20) DEFAULT NULL COMMENT '父级权限id',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '单元ID',
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `value` varchar(200) DEFAULT NULL COMMENT '权限值',
  `icon` varchar(500) DEFAULT NULL COMMENT '图标',
  `type` int(1) DEFAULT NULL COMMENT '权限类型：1->目录；2->菜单；3->按钮（接口绑定权限）',
  `uri` varchar(200) DEFAULT NULL COMMENT '前端资源路径',
  `status` int(1) DEFAULT NULL COMMENT '启用状态；0->禁用；1->启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='后台用户权限表';

LOCK TABLES `tenant_permission` WRITE;
/*!40000 ALTER TABLE `tenant_permission` DISABLE KEYS */;

INSERT INTO `tenant_permission` (`id`, `pid`, `tenant_id`, `name`, `value`, `icon`, `type`, `uri`, `status`, `create_time`, `sort`, `update_time`)
VALUES
	(1212307963880194049,0,1211177466152165378,'qwe','string','qwe',1,'string',0,'2020-01-01 17:41:57',0,'2020-01-01 17:41:57');

/*!40000 ALTER TABLE `tenant_permission` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table tenant_role
# ------------------------------------------------------------

CREATE TABLE `tenant_role` (
  `id` bigint(20) NOT NULL,
  `tenant_id` bigint(20) NOT NULL,
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `admin_count` int(11) DEFAULT NULL COMMENT '后台用户数量',
  `status` int(1) DEFAULT '1' COMMENT '启用状态：0->禁用；1->启用',
  `sort` int(11) DEFAULT '0',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='后台用户角色表';

LOCK TABLES `tenant_role` WRITE;
/*!40000 ALTER TABLE `tenant_role` DISABLE KEYS */;

INSERT INTO `tenant_role` (`id`, `tenant_id`, `name`, `description`, `admin_count`, `status`, `sort`, `create_time`, `update_time`)
VALUES
	(1212315980235382785,1211177466152165378,'string','string',0,1,0,'2020-01-01 18:13:48','2020-01-01 18:13:48');

/*!40000 ALTER TABLE `tenant_role` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table tenant_role_permission_relation
# ------------------------------------------------------------

CREATE TABLE `tenant_role_permission_relation` (
  `id` bigint(20) NOT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  `permission_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='后台用户角色和权限关系表';



# Dump of table tenant_user_role
# ------------------------------------------------------------

CREATE TABLE `tenant_user_role` (
  `id` int(11) NOT NULL,
  `tenant_id` varchar(20) NOT NULL COMMENT '用户id',
  `role_id` varchar(20) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
