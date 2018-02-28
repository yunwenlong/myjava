/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50096
Source Host           : localhost:3306
Source Database       : boke

Target Server Type    : MYSQL
Target Server Version : 50096
File Encoding         : 65001

Date: 2018-02-28 22:13:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `blog_dictionary`
-- ----------------------------
DROP TABLE IF EXISTS `blog_dictionary`;
CREATE TABLE `blog_dictionary` (
  `id` varchar(64) NOT NULL,
  `code` varchar(64) default NULL COMMENT '类别ID',
  `category` varchar(32) default NULL COMMENT '分类',
  `status` varchar(2) default NULL COMMENT '状态 0-正常 1-停用',
  `addtime` datetime default NULL,
  `uptime` datetime default NULL,
  `name` varchar(32) default NULL COMMENT '类别名称',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of blog_dictionary
-- ----------------------------

-- ----------------------------
-- Table structure for `blog_info`
-- ----------------------------
DROP TABLE IF EXISTS `blog_info`;
CREATE TABLE `blog_info` (
  `id` varchar(64) NOT NULL,
  `title` varchar(128) default NULL COMMENT '标题',
  `content` text COMMENT '内容',
  `reply_num` int(6) default '0' COMMENT '回复次数',
  `browse_num` int(6) default '0' COMMENT '浏览量',
  `classify_id` varchar(64) default NULL COMMENT '分类ID',
  `key_word` varchar(128) default NULL COMMENT '关键字',
  `user_id` varchar(64) default NULL COMMENT '用户ID',
  `addtime` varchar(32) default NULL,
  `uptime` varchar(32) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of blog_info
-- ----------------------------

-- ----------------------------
-- Table structure for `blog_link`
-- ----------------------------
DROP TABLE IF EXISTS `blog_link`;
CREATE TABLE `blog_link` (
  `id` varchar(32) default NULL,
  `link_name` varchar(32) default NULL COMMENT '链接名称',
  `link_url` varchar(32) default NULL COMMENT '链接地址',
  `order` int(2) default '0' COMMENT '排序序号 从小到大排序'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of blog_link
-- ----------------------------

-- ----------------------------
-- Table structure for `blog_photo`
-- ----------------------------
DROP TABLE IF EXISTS `blog_photo`;
CREATE TABLE `blog_photo` (
  `id` varchar(64) NOT NULL,
  `user_id` varchar(64) default NULL COMMENT '用户ID',
  `pic` varchar(32) default NULL COMMENT '图片',
  `views` int(6) default '0' COMMENT '浏览量',
  `introduce` text COMMENT '介绍',
  `addtime` datetime default NULL,
  `uptime` datetime default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of blog_photo
-- ----------------------------

-- ----------------------------
-- Table structure for `blog_speak`
-- ----------------------------
DROP TABLE IF EXISTS `blog_speak`;
CREATE TABLE `blog_speak` (
  `id` varchar(64) NOT NULL,
  `user_id` varchar(64) default NULL COMMENT '用户ID',
  `blog_id` varchar(64) default NULL COMMENT '博客ID，为空时，为留言',
  `phone` varchar(16) default NULL COMMENT '手机号',
  `email` varchar(32) default NULL COMMENT '电子邮箱',
  `content` varchar(256) default NULL COMMENT '内容',
  `addtime` datetime default NULL COMMENT '添加时间',
  `status` varchar(2) default '0' COMMENT '审核状态  0 待审核 1 审核通过 2 审核未通过',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of blog_speak
-- ----------------------------
INSERT INTO `blog_speak` VALUES ('297e516b5e80cff8015e80d269e30001', '张胜男', null, '18736930319', '18736930319@126.com', '${message}', '2017-09-14 22:37:06', null);
INSERT INTO `blog_speak` VALUES ('297e516b5e80d874015e80d874440000', '张胜男', null, '18736930319', '18736930319@126.com', '哈哈哈哈或或或或或或或或', '2017-09-14 22:43:42', null);
INSERT INTO `blog_speak` VALUES ('297e516b5e8b6ba8015e8b6ba80d0000', '云文龙', null, '18736930319', '18736930319@126.com', '${message}', '2017-09-17 00:00:41', null);

-- ----------------------------
-- Table structure for `blog_user`
-- ----------------------------
DROP TABLE IF EXISTS `blog_user`;
CREATE TABLE `blog_user` (
  `id` varchar(64) NOT NULL,
  `nick_name` varchar(64) default NULL COMMENT '昵称',
  `pic` varchar(64) default NULL COMMENT '头像',
  `phone` varchar(11) default '' COMMENT '手机号',
  `real_name` varchar(32) default NULL COMMENT '真实姓名',
  `password` varchar(64) default NULL COMMENT '密码',
  `profession` varchar(32) default NULL COMMENT '职业',
  `sex` varchar(2) default '0' COMMENT '性别 0-隐藏 1-男 2-女',
  `birthday` varchar(32) default NULL COMMENT '出生日期',
  `city_id` varchar(64) default NULL COMMENT '城市',
  `content` text COMMENT '个人介绍',
  `addtime` datetime default NULL COMMENT '添加时间',
  `uptime` datetime default NULL COMMENT '修改时间',
  `ip` varchar(32) default NULL,
  `status` varchar(2) default NULL COMMENT '状态0-正常 1-禁用',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of blog_user
-- ----------------------------
