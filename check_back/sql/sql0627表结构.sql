/*
SQLyog Ultimate v12.5.1 (64 bit)
MySQL - 8.0.16 : Database - check_data
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`check_data` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `check_data`;

/*Table structure for table `t_class_design` */

DROP TABLE IF EXISTS `t_class_design`;

CREATE TABLE `t_class_design` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `class_id` int(11) DEFAULT NULL COMMENT '班级id',
  `design_id` int(11) DEFAULT NULL COMMENT '设计id',
  `tea_id` varchar(50) DEFAULT NULL COMMENT '教师id',
  `end_time` datetime DEFAULT NULL COMMENT '截止时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `t_class_tea` */

DROP TABLE IF EXISTS `t_class_tea`;

CREATE TABLE `t_class_tea` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '班级id',
  `class_name` varchar(100) DEFAULT NULL COMMENT '班级名',
  `tea_id` varchar(50) DEFAULT NULL COMMENT '老师id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `tea_id` (`tea_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `t_class_work` */

DROP TABLE IF EXISTS `t_class_work`;

CREATE TABLE `t_class_work` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `class_id` int(11) DEFAULT NULL COMMENT '班级id',
  `work_id` int(11) DEFAULT NULL COMMENT '作业id',
  `tea_id` varchar(50) DEFAULT NULL COMMENT '老师id',
  `end_time` datetime DEFAULT NULL COMMENT '该班级作业截止日期',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `class_id` (`class_id`),
  KEY `tea_id` (`tea_id`),
  KEY `work_id` (`work_id`)
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `t_design_result` */

DROP TABLE IF EXISTS `t_design_result`;

CREATE TABLE `t_design_result` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `design_id` int(11) DEFAULT NULL COMMENT '设计id',
  `design_first_id` int(11) DEFAULT NULL COMMENT '设计1id',
  `design_second_id` int(11) DEFAULT NULL COMMENT '设计2id',
  `work_result` varchar(10) DEFAULT NULL COMMENT '重复率',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `t_menus` */

DROP TABLE IF EXISTS `t_menus`;

CREATE TABLE `t_menus` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单id',
  `menu_rid` int(5) DEFAULT NULL COMMENT '角色id',
  `menu_url` varchar(64) DEFAULT NULL COMMENT '菜单url',
  `menu_path` varchar(64) DEFAULT NULL COMMENT '菜单路径',
  `menu_name` varchar(64) DEFAULT NULL COMMENT '菜单名字',
  `menu_component` varchar(64) DEFAULT NULL COMMENT '菜单组件',
  `menu_pid` int(11) DEFAULT NULL COMMENT '父id',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `t_role_url` */

DROP TABLE IF EXISTS `t_role_url`;

CREATE TABLE `t_role_url` (
  `id` int(5) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_name` enum('管理员','教师','学生') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '角色',
  `role_url` varchar(100) DEFAULT NULL COMMENT 'url',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `t_stu_design` */

DROP TABLE IF EXISTS `t_stu_design`;

CREATE TABLE `t_stu_design` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `group_id` int(11) DEFAULT NULL COMMENT '组id',
  `design_id` int(11) DEFAULT NULL COMMENT '设计id',
  `code_name` varchar(200) DEFAULT '未提交' COMMENT '源代码作业名',
  `code_path` varchar(255) DEFAULT NULL COMMENT '源代码保存路径',
  `code_ext` varchar(20) DEFAULT NULL COMMENT '源代码类型',
  `pdf_name` varchar(200) DEFAULT '未提交' COMMENT 'pdf名',
  `pdf_path` varchar(255) DEFAULT NULL COMMENT 'pdf保存路径',
  `is_commit` varchar(20) DEFAULT '未提交' COMMENT '未提交,已提交,超时提交',
  `is_checked` int(1) DEFAULT NULL COMMENT '0正常,1查重过高',
  `tea_score` int(3) DEFAULT NULL COMMENT '得分',
  `tea_opinion` varchar(100) DEFAULT NULL COMMENT '点评',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `t_stu_group` */

DROP TABLE IF EXISTS `t_stu_group`;

CREATE TABLE `t_stu_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `design_id` int(11) DEFAULT NULL COMMENT '设计id',
  `class_id` int(11) DEFAULT NULL COMMENT '班级id',
  `stu_id1` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '组员1',
  `stu_id2` varchar(50) DEFAULT NULL COMMENT '组员2',
  `stu_id3` varchar(50) DEFAULT NULL COMMENT '组员3',
  `stu_id4` varchar(50) DEFAULT NULL COMMENT '组员4',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `t_stu_work` */

DROP TABLE IF EXISTS `t_stu_work`;

CREATE TABLE `t_stu_work` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `work_id` int(11) DEFAULT NULL COMMENT '作业id',
  `class_id` int(11) DEFAULT NULL COMMENT '班级id',
  `stu_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '学生id',
  `work_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '未提交' COMMENT '源代码作业名',
  `work_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '作业url',
  `work_ext` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '作业类型',
  `pdf_name` varchar(200) DEFAULT '未提交' COMMENT '作业PDF名',
  `pdf_path` varchar(255) DEFAULT NULL COMMENT 'pdf作业路径',
  `is_commit` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '未提交' COMMENT '未提交,已提交,超时提交',
  `is_checked` int(1) DEFAULT '0' COMMENT '0正常,1查重过高',
  `tea_score` int(3) DEFAULT '-1' COMMENT '得分',
  `tea_opinion` varchar(100) DEFAULT NULL COMMENT '点评',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `work_id` (`work_id`),
  KEY `class_id` (`class_id`),
  KEY `stu_id` (`stu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=420 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `t_student` */

DROP TABLE IF EXISTS `t_student`;

CREATE TABLE `t_student` (
  `stu_id` varchar(50) NOT NULL COMMENT '学生id',
  `stu_name` varchar(50) DEFAULT NULL COMMENT '学生姓名',
  `stu_phone` char(11) DEFAULT NULL COMMENT '学生手机',
  `stu_qq` varchar(100) DEFAULT NULL COMMENT '学生qq',
  `stu_mail` varchar(100) DEFAULT NULL COMMENT '学生邮箱',
  `stu_class_id` int(11) DEFAULT NULL COMMENT '班级',
  `is_group` int(1) DEFAULT '0' COMMENT '0未组队,1组队',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`stu_id`),
  KEY `class_id` (`stu_class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `t_tea_design` */

DROP TABLE IF EXISTS `t_tea_design`;

CREATE TABLE `t_tea_design` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `design_title` varchar(100) DEFAULT NULL COMMENT '设计标题',
  `design_url` varchar(200) DEFAULT NULL COMMENT '设计内容',
  `design_dir` varchar(200) DEFAULT NULL COMMENT '提交路径',
  `tea_id` varchar(50) DEFAULT NULL COMMENT '教师id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `t_tea_work` */

DROP TABLE IF EXISTS `t_tea_work`;

CREATE TABLE `t_tea_work` (
  `work_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'work_id',
  `tea_id` varchar(50) DEFAULT NULL COMMENT '教师id',
  `work_title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '作业标题',
  `work_describe` varchar(100) DEFAULT NULL COMMENT '作业描述',
  `work_dir` varchar(200) DEFAULT NULL COMMENT '作业路径',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`work_id`),
  KEY `tea_id` (`tea_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `t_teacher` */

DROP TABLE IF EXISTS `t_teacher`;

CREATE TABLE `t_teacher` (
  `tea_id` varchar(50) NOT NULL COMMENT '老师id',
  `tea_name` varchar(50) DEFAULT NULL COMMENT '老师姓名',
  `tea_phone` char(11) DEFAULT NULL COMMENT '老师手机',
  `tea_qq` varchar(100) DEFAULT NULL COMMENT '老师qq',
  `tea_mail` varchar(100) DEFAULT NULL COMMENT '老师邮箱',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`tea_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户密码',
  `user_role_id` int(3) DEFAULT NULL COMMENT '3学生,2老师,1管理员',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`username`),
  KEY `ROLE_ID` (`user_role_id`) COMMENT '角色id普通索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `t_work_result` */

DROP TABLE IF EXISTS `t_work_result`;

CREATE TABLE `t_work_result` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `work_id` int(11) DEFAULT NULL COMMENT '作业id',
  `work_first_id` int(11) DEFAULT NULL COMMENT '作业1id',
  `work_second_id` int(11) DEFAULT NULL COMMENT '作业2id',
  `work_result` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '-1' COMMENT '重复率',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5969 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
