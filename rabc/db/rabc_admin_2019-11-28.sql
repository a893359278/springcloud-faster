# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.26)
# Database: rabc_admin
# Generation Time: 2019-11-28 13:43:00 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table admin_user
# ------------------------------------------------------------

CREATE TABLE `admin_user` (
  `id` bigint(20) NOT NULL COMMENT '用户id',
  `username` varchar(100) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '用户密码密文',
  `name` varchar(200) DEFAULT NULL COMMENT '用户姓名',
  `salt` varchar(200) DEFAULT NULL COMMENT '密码盐',
  `mobile` varchar(200) DEFAULT NULL COMMENT '用户手机',
  `description` varchar(500) DEFAULT NULL COMMENT '简介',
  `deleted` tinyint(4) NOT NULL DEFAULT '2' COMMENT '是否已删除；1：已删除，2：未删除',
  `enabled` tinyint(1) DEFAULT NULL COMMENT '是否有效用户',
  `account_non_expired` tinyint(1) DEFAULT NULL COMMENT '账号是否未过期',
  `credentials_non_expired` tinyint(1) DEFAULT NULL COMMENT '密码是否未过期',
  `account_non_locked` tinyint(1) DEFAULT NULL COMMENT '是否未锁定',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_adminUser_username` (`username`) USING BTREE,
  UNIQUE KEY `ux_adminUser_mobile` (`mobile`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

LOCK TABLES `admin_user` WRITE;
/*!40000 ALTER TABLE `admin_user` DISABLE KEYS */;

INSERT INTO `admin_user` (`id`, `username`, `password`, `name`, `salt`, `mobile`, `description`, `deleted`, `enabled`, `account_non_expired`, `credentials_non_expired`, `account_non_locked`, `created_time`, `updated_time`)
VALUES
	(1193445540674965506,'chenshaoping','e10adc3949ba59abbe56e057f20f883e','HAHAH','ceup75sI','18250073990',NULL,2,1,1,1,1,'2019-11-10 16:29:24','2019-11-10 16:29:24'),
	(1193446884286734338,'asdqweqweewq','qweqwewq','HAHA1212H','ICnlvA2d','18250073991',NULL,2,1,1,1,1,'2019-11-10 16:34:45','2019-11-10 17:15:52');

/*!40000 ALTER TABLE `admin_user` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table menu
# ------------------------------------------------------------

CREATE TABLE `menu` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `parent_id` bigint(20) NOT NULL COMMENT '父菜单id',
  `href` varchar(200) DEFAULT NULL COMMENT '菜单路径',
  `icon` varchar(200) DEFAULT NULL COMMENT '菜单图标',
  `name` varchar(200) DEFAULT NULL COMMENT '菜单名称',
  `sort_order` int(11) NOT NULL DEFAULT '1' COMMENT '排序，越小，越靠前',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `order_num` int(11) DEFAULT NULL COMMENT '创建时间',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';



# Dump of table resource
# ------------------------------------------------------------

CREATE TABLE `resource` (
  `id` bigint(20) NOT NULL COMMENT '资源id',
  `name` varchar(200) NOT NULL COMMENT '资源名称',
  `url` varchar(200) NOT NULL COMMENT '资源url',
  `method` varchar(20) NOT NULL COMMENT '资源方法',
  `version` int(4) NOT NULL DEFAULT '1' COMMENT '资源版本',
  `description` varchar(500) DEFAULT NULL COMMENT '简介',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资源表';



# Dump of table role_resource_relation
# ------------------------------------------------------------

CREATE TABLE `role_resource_relation` (
  `resource_id` bigint(20) NOT NULL COMMENT '资源id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`resource_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色和资源关系表';



# Dump of table role
# ------------------------------------------------------------

CREATE TABLE `role` (
  `id` bigint(20) NOT NULL COMMENT '角色id',
  `code` varchar(100) NOT NULL COMMENT '角色code',
  `name` varchar(200) DEFAULT NULL COMMENT '角色名称',
  `description` varchar(500) DEFAULT NULL COMMENT '简介',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';



# Dump of table user_resource_relation
# ------------------------------------------------------------

CREATE TABLE `user_resource_relation` (
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `resources_id` bigint(20) NOT NULL COMMENT '资源id',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`user_id`,`resources_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户和资源关系表';



# Dump of table user_role_relation
# ------------------------------------------------------------

CREATE TABLE `user_role_relation` (
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户和角色关系表';




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
