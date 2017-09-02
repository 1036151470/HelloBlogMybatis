/*
Navicat MySQL Data Transfer

Source Server         : forliu
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : forliu

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-09-02 14:30:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for blogcontext
-- ----------------------------
DROP TABLE IF EXISTS `blogcontext`;
CREATE TABLE `blogcontext` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `changetime` varchar(255) DEFAULT NULL,
  `context` text,
  `createdtime` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;
