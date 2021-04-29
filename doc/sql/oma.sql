/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50732
Source Host           : localhost:3306
Source Database       : oma

Target Server Type    : MYSQL
Target Server Version : 50732
File Encoding         : 65001

Date: 2021-04-29 13:44:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_acl
-- ----------------------------
DROP TABLE IF EXISTS `sys_acl`;
CREATE TABLE `sys_acl` (
  `id` varchar(32) NOT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `del` varchar(255) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `order_no` int(11) NOT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `updater` varchar(255) DEFAULT NULL,
  `principal_type` varchar(255) DEFAULT NULL,
  `principal_id` varchar(255) DEFAULT NULL,
  `resource_type` varchar(255) DEFAULT NULL,
  `resource_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_acl
-- ----------------------------
INSERT INTO `sys_acl` VALUES ('402881847917cea401791c1db9e50019', '2021-04-29 13:33:00.773000', 'admin', null, null, '0', '2021-04-29 13:33:00.773000', null, 'U', '402881847917cea4017917d3ee000000', 'M', '1');
INSERT INTO `sys_acl` VALUES ('402881847917cea401791c1db9f8001a', '2021-04-29 13:33:00.791000', 'admin', null, null, '0', '2021-04-29 13:33:00.791000', null, 'U', '402881847917cea4017917d3ee000000', 'M', '2');
INSERT INTO `sys_acl` VALUES ('402881847917cea401791c1dba07001b', '2021-04-29 13:33:00.807000', 'admin', null, null, '0', '2021-04-29 13:33:00.807000', null, 'U', '402881847917cea4017917d3ee000000', 'P', '1');
INSERT INTO `sys_acl` VALUES ('402881847917cea401791c1dba18001c', '2021-04-29 13:33:00.824000', 'admin', null, null, '0', '2021-04-29 13:33:00.824000', null, 'U', '402881847917cea4017917d3ee000000', 'P', '2');
INSERT INTO `sys_acl` VALUES ('402881847917cea401791c1e665c001d', '2021-04-29 13:33:44.924000', 'admin', null, null, '0', '2021-04-29 13:33:44.924000', null, 'R', '402881847917cea40179182e20150008', 'M', '1');
INSERT INTO `sys_acl` VALUES ('402881847917cea401791c1e666d001e', '2021-04-29 13:33:44.941000', 'admin', null, null, '0', '2021-04-29 13:33:44.941000', null, 'R', '402881847917cea40179182e20150008', 'M', '5');

-- ----------------------------
-- Table structure for sys_group
-- ----------------------------
DROP TABLE IF EXISTS `sys_group`;
CREATE TABLE `sys_group` (
  `id` varchar(32) NOT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `del` varchar(255) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `order_no` int(11) NOT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `updater` varchar(255) DEFAULT NULL,
  `group_code` varchar(255) DEFAULT NULL,
  `group_name` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `parent_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4miqlksu13h7y9nos119amknu` (`parent_id`),
  CONSTRAINT `FK4miqlksu13h7y9nos119amknu` FOREIGN KEY (`parent_id`) REFERENCES `sys_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_group
-- ----------------------------
INSERT INTO `sys_group` VALUES ('1', null, null, null, null, '1', null, null, 'root', '集团总部', '0', null);
INSERT INTO `sys_group` VALUES ('2', null, null, null, null, '2', null, null, 'all', '总务部', '0', '1');
INSERT INTO `sys_group` VALUES ('3', null, null, null, null, '3', null, null, 'hr', '人力部', '0', '1');
INSERT INTO `sys_group` VALUES ('4', null, null, null, null, '4', null, null, 'fico', '财务部', '0', '1');
INSERT INTO `sys_group` VALUES ('5', '2021-04-26 14:52:11.816000', 'admin', null, 'test', '5', '2021-04-26 14:52:11.816000', null, 'tech', '技术部', '1', '1');
INSERT INTO `sys_group` VALUES ('6', null, null, null, null, '6', null, null, 'sh', '上海分公司', '0', null);
INSERT INTO `sys_group` VALUES ('7', null, null, null, null, '7', null, null, 'market', '市场部', '0', '6');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` varchar(32) NOT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `del` varchar(255) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `order_no` int(11) NOT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `updater` varchar(255) DEFAULT NULL,
  `code` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  `component` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `icon_svg` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `target` varchar(255) DEFAULT NULL,
  `module_id` varchar(32) DEFAULT NULL,
  `parent_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_tq5314uwm3dsbq5mjd8mwdg2f` (`code`),
  KEY `FK4xbdv7cd5uupcix43h4n85y5g` (`module_id`),
  KEY `FK2jrf4gb0gjqi8882gxytpxnhe` (`parent_id`),
  CONSTRAINT `FK2jrf4gb0gjqi8882gxytpxnhe` FOREIGN KEY (`parent_id`) REFERENCES `sys_menu` (`id`),
  CONSTRAINT `FK4xbdv7cd5uupcix43h4n85y5g` FOREIGN KEY (`module_id`) REFERENCES `sys_module` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', null, null, null, null, '1', null, null, 'sys', '系统管理', '#', '1', 'far fa-circle', '', '1', '0', '1', '1', null);
INSERT INTO `sys_menu` VALUES ('2', null, null, null, null, '2', null, null, 'user', '用户管理', 'pages/user/index.html', '1', 'fas fa-user', '', '1', '0', '1', '1', '1');
INSERT INTO `sys_menu` VALUES ('3', null, null, null, null, '3', null, null, 'role', '角色管理', 'pages/role/index.html', '1', 'fas fa-user-tag', '', '1', '0', '1', '1', '1');
INSERT INTO `sys_menu` VALUES ('4', null, null, null, null, '4', null, null, 'menu', '菜单管理', 'pages/menu/index.html', '1', 'fas fa-tasks', '', '1', '0', '1', '1', '1');
INSERT INTO `sys_menu` VALUES ('5', null, null, null, null, '5', null, null, 'group', '组织管理', 'pages/group/index.html', '1', 'fas fa-users', '', '1', '0', '1', '1', '1');

-- ----------------------------
-- Table structure for sys_module
-- ----------------------------
DROP TABLE IF EXISTS `sys_module`;
CREATE TABLE `sys_module` (
  `id` varchar(32) NOT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `del` varchar(255) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `order_no` int(11) NOT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `updater` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_module
-- ----------------------------
INSERT INTO `sys_module` VALUES ('1', null, null, null, null, '1', null, null, 'sys', 'sys');

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` varchar(32) NOT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `del` varchar(255) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `order_no` int(11) NOT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `updater` varchar(255) DEFAULT NULL,
  `code` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  `menu_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_2vm98en2ouht0v15fvef2whp4` (`code`),
  KEY `FKdh0pa1l2c4t5xnewwsev2yyx2` (`menu_id`),
  CONSTRAINT `FKdh0pa1l2c4t5xnewwsev2yyx2` FOREIGN KEY (`menu_id`) REFERENCES `sys_menu` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1', null, null, null, null, '1', null, null, 'userList', '用户查询', 'user/list', '2');
INSERT INTO `sys_permission` VALUES ('2', null, null, null, null, '2', null, null, 'userSave', '用户修改', 'user/save', '2');
INSERT INTO `sys_permission` VALUES ('3', null, null, null, null, '3', null, null, 'userDelete', '用户删除', 'user/delete', '2');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(32) NOT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `del` varchar(255) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `order_no` int(11) NOT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `updater` varchar(255) DEFAULT NULL,
  `role_code` varchar(255) DEFAULT NULL,
  `role_name` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('402881847917cea40179182e20150008', '2021-04-28 19:12:26.640000', 'admin', null, '', '1', '2021-04-28 19:12:26.640000', null, 'test', 'test', '0');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(32) NOT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `del` varchar(255) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `order_no` int(11) NOT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `updater` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `end_date` datetime(6) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `pic` longtext,
  `sex` varchar(255) DEFAULT NULL,
  `start_date` datetime(6) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  `group_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_51bvuyvihefoh4kp5syh2jpi4` (`username`),
  KEY `FKl7i4833vinxhaeho5oynrsjtk` (`group_id`),
  CONSTRAINT `FKl7i4833vinxhaeho5oynrsjtk` FOREIGN KEY (`group_id`) REFERENCES `sys_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '2021-04-11 17:21:18.392000', 'admin', null, 'test', '1', '2021-04-12 19:23:04.745000', 'admin', 'onemysoft@163.com', null, '13912345678', '管理员', '$2a$10$YnQh6XDwpXzd4kilw8aIleR61GY6kCVDcF04ac5EPs3LRBkJGYyZK', null, '1', null, '0', null, 'admin', null);
INSERT INTO `sys_user` VALUES ('402881847917cea4017917d3ee000000', '2021-04-28 17:33:55.562000', 'admin', null, '123', '2', '2021-04-28 17:33:55.562000', null, '', null, '', 'test', '$2a$10$B5Tkotkqysx3n8gQRLs.TOdCeTFBS3YvB9A40xDMbjmB..nvoMOpG', null, '1', null, '0', null, 'guest', '4');

-- ----------------------------
-- Table structure for sys_user_roles
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_roles`;
CREATE TABLE `sys_user_roles` (
  `users_id` varchar(32) NOT NULL,
  `roles_id` varchar(32) NOT NULL,
  PRIMARY KEY (`users_id`,`roles_id`),
  KEY `FKdpvc6d7xqpqr43dfuk1s27cqh` (`roles_id`),
  CONSTRAINT `FKdpvc6d7xqpqr43dfuk1s27cqh` FOREIGN KEY (`roles_id`) REFERENCES `sys_role` (`id`),
  CONSTRAINT `FKl5a53kgwyql3twmb6wkyf1x7c` FOREIGN KEY (`users_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_roles
-- ----------------------------
