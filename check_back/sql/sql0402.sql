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

/*Data for the table `t_class_design` */

insert  into `t_class_design`(`id`,`class_id`,`design_id`,`tea_id`,`end_time`,`create_time`,`update_time`) values 
(1,10,6,'04181410','2021-04-09 18:02:50','2021-04-02 18:02:53','2021-04-02 18:02:53'),
(2,10,5,'04181410','2021-04-09 18:02:50','2021-04-02 18:02:53','2021-04-02 18:02:53'),
(3,10,4,'04181410','2021-04-09 18:02:50','2021-04-02 18:02:53','2021-04-02 18:02:53'),
(4,11,6,'04181410','2021-04-09 18:02:50','2021-04-02 18:02:53','2021-04-02 18:02:53'),
(5,11,5,'04181410','2021-04-09 18:02:50','2021-04-02 18:02:53','2021-04-02 18:02:53'),
(6,11,4,'04181410','2021-04-09 18:02:50','2021-04-02 18:02:53','2021-04-02 18:02:53'),
(7,12,6,'04181410','2021-04-09 18:02:50','2021-04-02 18:02:53','2021-04-02 18:02:53'),
(8,12,5,'04181410','2021-04-09 18:02:50','2021-04-02 18:02:53','2021-04-02 18:02:53'),
(9,12,4,'04181410','2021-04-09 18:02:50','2021-04-02 18:02:53','2021-04-02 18:02:53');

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

/*Data for the table `t_class_tea` */

insert  into `t_class_tea`(`id`,`class_name`,`tea_id`,`create_time`,`update_time`) values 
(10,'电信18-5班','04181410','2021-03-06 15:44:15','2021-03-06 15:44:15'),
(11,'测试一班','04181410','2021-03-06 23:41:39','2021-03-06 23:41:39'),
(12,'测试二班','04181410','2021-03-08 22:10:47','2021-03-08 22:10:47'),
(16,'自动化18-1班','04181411','2021-03-11 22:33:46','2021-03-11 22:33:46'),
(17,'自动化18-2班','04181411','2021-03-11 22:33:56','2021-03-11 22:33:56');

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

/*Data for the table `t_class_work` */

insert  into `t_class_work`(`id`,`class_id`,`work_id`,`tea_id`,`end_time`,`create_time`,`update_time`) values 
(103,10,18,'04181410','2021-03-19 11:33:34','2021-03-12 11:33:36','2021-03-12 11:33:36'),
(104,11,18,'04181410','2021-03-19 11:33:34','2021-03-12 11:33:36','2021-03-12 11:33:36'),
(105,12,18,'04181410','2021-03-19 11:33:34','2021-03-12 11:33:36','2021-03-12 11:33:36');

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

/*Data for the table `t_design_result` */

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

/*Data for the table `t_menus` */

insert  into `t_menus`(`menu_id`,`menu_rid`,`menu_url`,`menu_path`,`menu_name`,`menu_component`,`menu_pid`) values 
(1,1,'/admin','/admin','管理员菜单','AdminHome',0),
(2,2,'/teacher','/teacher','教师菜单','TeacherHome',0),
(3,3,'/student','/student','学生菜单','StudentHome',0),
(4,1,'/admin/user','/admin','用户管理','AdminUser',1),
(6,2,'/teacher/class','/teacher','班级管理','TeacherClass',2),
(7,2,'/teacher/work','/teacher','作业管理','TeacherWork',2),
(8,3,'/student/work','/student','作业查询','StudentWork',3),
(9,3,'/student/design','/student','课程设计','StudentDesign',3),
(10,2,'/teacher/design','/teacher','课程设计管理','TeacherDesign',2),
(11,3,'/student/info','/student','个人信息','StudentInfo',3);

/*Table structure for table `t_role_url` */

DROP TABLE IF EXISTS `t_role_url`;

CREATE TABLE `t_role_url` (
  `id` int(5) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_name` enum('管理员','教师','学生') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '角色',
  `role_url` varchar(100) DEFAULT NULL COMMENT 'url',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `t_role_url` */

insert  into `t_role_url`(`id`,`role_name`,`role_url`) values 
(1,'管理员','/admin/**'),
(2,'教师','/teacher/**'),
(3,'学生','/student/**');

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `t_stu_design` */

insert  into `t_stu_design`(`id`,`group_id`,`design_id`,`code_name`,`code_path`,`code_ext`,`pdf_name`,`pdf_path`,`is_commit`,`is_checked`,`tea_score`,`tea_opinion`,`create_time`,`update_time`) values 
(1,1,6,'未提交',NULL,NULL,'未提交',NULL,'未提交',NULL,NULL,NULL,'2021-04-02 19:41:45','2021-04-02 19:41:45');

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `t_stu_group` */

insert  into `t_stu_group`(`id`,`design_id`,`class_id`,`stu_id1`,`stu_id2`,`stu_id3`,`stu_id4`,`create_time`,`update_time`) values 
(1,6,10,'04181420','04181421','04181422','04181423','2021-04-02 19:41:45','2021-04-02 19:41:45');

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

/*Data for the table `t_stu_work` */

insert  into `t_stu_work`(`id`,`work_id`,`class_id`,`stu_id`,`work_name`,`work_url`,`work_ext`,`pdf_name`,`pdf_path`,`is_commit`,`is_checked`,`tea_score`,`tea_opinion`,`create_time`,`update_time`) values 
(407,18,10,'04181420','电信18-5班-04181420-leomaokai.java','/check_resource/04181410/测试作业一/code/电信18-5班-04181420-leomaokai.java','java','电信18-5班-04181420-leomaokai.pdf','/check_resource/04181410/测试作业一/pdf/电信18-5班-04181420-leomaokai.pdf','超时提交',0,NULL,NULL,'2021-03-12 11:33:36','2021-04-02 15:42:38'),
(408,18,10,'04181421','电信18-5班-04181421-kkkk2.cpp','/check_resource/04181410/测试作业一/code/电信18-5班-04181421-kkkk2.cpp','cpp','电信18-5班-04181421-kkkk2.pdf','/check_resource/04181410/测试作业一/pdf/电信18-5班-04181421-kkkk2.pdf','按时提交',0,NULL,NULL,'2021-03-12 11:33:36','2021-03-13 20:47:49'),
(409,18,10,'04181422','电信18-5班-04181422-kai3.cpp','/check_resource/04181410/测试作业一/code/电信18-5班-04181422-kai3.cpp','cpp','电信18-5班-04181422-kai3.pdf','/check_resource/04181410/测试作业一/pdf/电信18-5班-04181422-kai3.pdf','按时提交',0,NULL,NULL,'2021-03-12 11:33:36','2021-03-13 20:47:49'),
(410,18,10,'04181423','未提交',NULL,NULL,'未提交',NULL,'未提交',1,NULL,NULL,'2021-03-12 11:33:36','2021-03-13 20:27:33'),
(411,18,10,'04181424','未提交',NULL,NULL,'未提交',NULL,'未提交',0,NULL,NULL,'2021-03-12 11:33:36','2021-03-12 11:33:36'),
(412,18,10,'04181425','未提交',NULL,NULL,'未提交',NULL,'未提交',0,NULL,NULL,'2021-03-12 11:33:36','2021-03-12 11:33:36'),
(413,18,10,'04181426','未提交',NULL,NULL,'未提交',NULL,'未提交',0,NULL,NULL,'2021-03-12 11:33:36','2021-03-12 11:33:36'),
(414,18,11,'04181427','测试一班-04181427-27.cpp','/check_resource/04181410/测试作业一/code/测试一班-04181427-27.cpp','cpp','未提交','','未提交PDF',0,NULL,NULL,'2021-03-12 11:33:36','2021-03-12 14:54:26'),
(415,18,11,'04181428','测试一班-04181428-28.cpp','/check_resource/04181410/测试作业一/code/测试一班-04181428-28.cpp','cpp','未提交','','未提交PDF',0,NULL,NULL,'2021-03-12 11:33:36','2021-03-12 15:04:35'),
(416,18,11,'04181429','测试一班-04181429-29.cpp','/check_resource/04181410/测试作业一/code/测试一班-04181429-29.cpp','cpp','未提交',NULL,'未提交PDF',0,NULL,NULL,'2021-03-12 11:33:36','2021-03-12 15:41:34'),
(417,18,12,'04181430','未提交',NULL,NULL,'未提交',NULL,'未提交',0,NULL,NULL,'2021-03-12 11:33:36','2021-03-12 11:33:36'),
(418,18,12,'04181431','未提交',NULL,NULL,'未提交',NULL,'未提交',0,NULL,NULL,'2021-03-12 11:33:36','2021-03-12 11:33:36'),
(419,18,12,'04181432','未提交',NULL,NULL,'未提交',NULL,'未提交',0,NULL,NULL,'2021-03-12 11:33:36','2021-03-12 11:33:36');

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

/*Data for the table `t_student` */

insert  into `t_student`(`stu_id`,`stu_name`,`stu_phone`,`stu_qq`,`stu_mail`,`stu_class_id`,`is_group`,`create_time`,`update_time`) values 
('04181420','leomaokai',NULL,NULL,NULL,10,1,'2021-03-06 23:42:47','2021-03-11 22:20:48'),
('04181421','kkkk2',NULL,NULL,NULL,10,1,'2021-03-06 23:42:47','2021-03-11 22:26:37'),
('04181422','kai3',NULL,NULL,NULL,10,1,'2021-03-06 23:42:47','2021-03-11 22:28:43'),
('04181423','kai5',NULL,NULL,NULL,10,1,'2021-03-09 15:06:40','2021-03-11 22:31:30'),
('04181424',NULL,NULL,NULL,NULL,10,0,'2021-03-09 15:07:09','2021-03-09 15:07:09'),
('04181425',NULL,NULL,NULL,NULL,10,0,'2021-03-09 15:06:40','2021-03-09 15:06:40'),
('04181426',NULL,NULL,NULL,NULL,10,0,'2021-03-09 15:06:40','2021-03-09 15:06:40'),
('04181427','27',NULL,NULL,NULL,11,0,'2021-03-09 15:09:24','2021-03-12 14:36:24'),
('04181428','28',NULL,NULL,NULL,11,0,'2021-03-09 15:09:24','2021-03-12 14:56:35'),
('04181429','29',NULL,NULL,NULL,11,0,'2021-03-09 15:09:24','2021-03-12 15:40:58'),
('04181430',NULL,NULL,NULL,NULL,12,0,'2021-03-09 15:16:39','2021-03-09 15:16:39'),
('04181431',NULL,NULL,NULL,NULL,12,0,'2021-03-09 15:16:39','2021-03-09 15:16:39'),
('04181432',NULL,NULL,NULL,NULL,12,0,'2021-03-09 15:16:39','2021-03-09 15:16:39'),
('04181481','81',NULL,NULL,NULL,16,0,'2021-03-11 22:34:46','2021-03-11 22:47:31'),
('04181482','82',NULL,NULL,NULL,16,0,'2021-03-11 22:34:46','2021-03-11 22:48:49'),
('04181483','83',NULL,NULL,NULL,16,0,'2021-03-11 22:34:46','2021-03-11 23:18:37'),
('04181484',NULL,NULL,NULL,NULL,16,0,'2021-03-11 22:34:46','2021-03-11 22:34:46'),
('04181485',NULL,NULL,NULL,NULL,16,0,'2021-03-11 22:34:46','2021-03-11 22:34:46'),
('04181491','91',NULL,NULL,NULL,17,0,'2021-03-11 22:36:48','2021-03-11 23:37:57'),
('04181492','92',NULL,NULL,NULL,17,0,'2021-03-11 22:36:48','2021-03-11 23:36:29'),
('04181493',NULL,NULL,NULL,NULL,17,0,'2021-03-11 22:36:48','2021-03-11 22:36:48'),
('04181494',NULL,NULL,NULL,NULL,17,0,'2021-03-11 22:36:48','2021-03-11 22:36:48'),
('04181495',NULL,NULL,NULL,NULL,17,0,'2021-03-11 22:36:48','2021-03-11 22:36:48');

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

/*Data for the table `t_tea_design` */

insert  into `t_tea_design`(`id`,`design_title`,`design_url`,`design_dir`,`tea_id`,`create_time`,`update_time`) values 
(4,'wmk','/check_design/04181410/wmk.pdf','/check_design_resource/04181410/wmk','04181410','2021-04-02 16:06:00','2021-04-02 16:06:00'),
(5,'wmk2','/check_design/04181410/wmk2.pdf','/check_design_resource/04181410/wmk2','04181410','2021-04-02 16:07:01','2021-04-02 16:07:01'),
(6,'leo3','/check_design/04181410/leo3.pdf','/check_design_resource/04181410/leo3','04181410','2021-04-02 16:07:34','2021-04-02 16:07:34'),
(7,'leo5','/check_design/04181410/leo5.pdf','/check_design_resource/04181410/leo5','04181410','2021-04-02 16:10:58','2021-04-02 16:10:58'),
(8,'leomaokai','/check_design/04181410/leomaokai.pdf','/check_design_resource/04181410/leomaokai','04181410','2021-04-02 17:09:22','2021-04-02 17:09:22'),
(9,'hhhhhhh','/check_design/04181410/hhhhhhh.pdf','/check_design_resource/04181410/hhhhhhh','04181410','2021-04-02 17:11:59','2021-04-02 17:11:59'),
(10,'lllllllllllllll','/check_design/04181410/lllllllllllllll.pdf','/check_design_resource/04181410/lllllllllllllll','04181410','2021-04-02 17:13:10','2021-04-02 17:13:10'),
(11,'ooooooooooooooooo','/check_design/04181410/ooooooooooooooooo.pdf','/check_design_resource/04181410/ooooooooooooooooo','04181410','2021-04-02 17:18:13','2021-04-02 17:18:13'),
(12,'ppppppppppppp','/check_design/04181410/ppppppppppppp.pdf','/check_design_resource/04181410/ppppppppppppp','04181410','2021-04-02 17:19:10','2021-04-02 17:19:10');

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

/*Data for the table `t_tea_work` */

insert  into `t_tea_work`(`work_id`,`tea_id`,`work_title`,`work_describe`,`work_dir`,`create_time`,`update_time`) values 
(18,'04181410','测试作业一','数据结构第一章','/check_resource/04181410/测试作业一','2021-03-10 13:22:01','2021-03-12 11:33:36'),
(20,'04181410','测试作业二','数据结构第二章','/check_resource/04181410/测试作业二','2021-03-10 13:24:05','2021-03-10 22:59:48'),
(21,'04181411','计算机网络作业一','null','/check_resource/04181411/计算机网络作业一','2021-03-11 22:37:22','2021-03-11 22:37:48'),
(22,'04181411','计算机网络作业二','null','/check_resource/04181411/计算机网络作业二','2021-03-11 22:37:42','2021-03-11 22:37:58');

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

/*Data for the table `t_teacher` */

insert  into `t_teacher`(`tea_id`,`tea_name`,`tea_phone`,`tea_qq`,`tea_mail`,`create_time`,`update_time`) values 
('04181410',NULL,NULL,NULL,NULL,'2021-03-04 20:16:27','2021-03-04 20:16:27'),
('04181411',NULL,NULL,NULL,NULL,'2021-03-08 15:36:33','2021-03-08 15:36:33'),
('04181412',NULL,NULL,NULL,NULL,'2021-03-08 16:39:28','2021-03-08 16:39:28'),
('04181450',NULL,NULL,NULL,NULL,'2021-03-08 16:46:53','2021-03-08 16:46:53'),
('04181451',NULL,NULL,NULL,NULL,'2021-03-08 16:46:53','2021-03-08 16:46:53'),
('04181452',NULL,NULL,NULL,NULL,'2021-03-08 16:46:53','2021-03-08 16:46:53'),
('04181453',NULL,NULL,NULL,NULL,'2021-03-08 16:46:54','2021-03-08 16:46:54'),
('04181454',NULL,NULL,NULL,NULL,'2021-03-08 16:46:54','2021-03-08 16:46:54');

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

/*Data for the table `t_user` */

insert  into `t_user`(`username`,`password`,`user_role_id`,`create_time`,`update_time`) values 
('04180000','$2a$10$R8mbigPfUXWikD5MhV/fu.8yC8QfVdD/rCE5jvvXo7sAWTwCs7uFS',1,NULL,'2021-03-09 13:52:22'),
('04181410','$2a$10$Zuhb7xhDSUsMnOAcpV.P/u1uhU9YqfUYonGxz/19jEq50ie.TvjCK',2,'2021-03-04 20:16:27','2021-03-08 17:23:17'),
('04181411','$2a$10$Z8LHzhrYn5mG1lgVfP52lu6xYXEP7NlQc5ABxBs6Eoq.lBd6H9EXa',2,'2021-03-08 15:36:33','2021-03-08 17:23:18'),
('04181412','$2a$10$uV5wNI3zvMCbjqqiMTwAWe2OXza8050qltsUKthwUFoCsXvENtWyW',2,'2021-03-08 16:39:28','2021-03-08 16:39:28'),
('04181420','$2a$10$jEkH3oMYYzT0uI4yOMAjT.dqZJJO5uHTkw.VtZRduWL3bNk07JKa2',3,'2021-03-06 23:42:47','2021-03-06 23:42:47'),
('04181421','$2a$10$DL0hGTPrZLB.Z3jE5yYojeDuinLXYpXBwp.NWTGvc.AS4ZUR1oHG.',3,'2021-03-06 23:42:47','2021-03-06 23:42:47'),
('04181422','$2a$10$jCNRo.To3BBHejWrJ2MwG.21STmoG0Q2wra2QpYb9ovlbbV3XUEjS',3,'2021-03-06 23:42:47','2021-03-06 23:42:47'),
('04181423','$2a$10$wUYBCBJ2K/YZLQNb2cSRKehdxcs47cCiIsArgpCNxOOCbWGYK20QS',3,'2021-03-09 15:06:39','2021-03-09 15:06:39'),
('04181424','$2a$10$Hp.Jnym1CQd4CbyHOZVB9O44VLrd7Noyy1S/83V.QufKadu5Amkva',3,'2021-03-09 15:07:09','2021-03-09 15:07:09'),
('04181425','$2a$10$oUp5ur1/IKNbcv6V6v/nTeSy625Ll9fWVkUcaUinVLI03NTnJELOC',3,'2021-03-09 15:06:40','2021-03-09 15:06:40'),
('04181426','$2a$10$FqxXsF4qGpg7V2pzmu9icOI99EEc/Lhg0R25J4tAB6yE0QV1hJxMO',3,'2021-03-09 15:06:40','2021-03-09 15:06:40'),
('04181427','$2a$10$crob8RLp4iV31TyUR5maMeEFR1Sjs4u89lI4oCxsKLwm9lmlDKU.a',3,'2021-03-09 15:09:24','2021-03-09 15:09:24'),
('04181428','$2a$10$5mem6udgURx6kA1NDsZf7e6nIwUI7Gs58ffTK1c6a/yOEk.rRuLS2',3,'2021-03-09 15:09:24','2021-03-09 15:09:24'),
('04181429','$2a$10$fb9vXE5YdaOli6dOVvQbCOXenpZv2EEbzSbHdFKJbh/wVknmcrmVm',3,'2021-03-09 15:09:24','2021-03-09 15:09:24'),
('04181430','$2a$10$lSsGiioSvl51AQwYtM737Okbdn6Pya1NAPWkD7OgRXMY2ZGxQLuCK',3,'2021-03-09 15:16:39','2021-03-09 15:16:39'),
('04181431','$2a$10$Yo3kvUWtdm.Pwh6/dfJr9e0VTnYDjq0gFBUeDivpc.xofTWKm700i',3,'2021-03-09 15:16:39','2021-03-09 15:16:39'),
('04181432','$2a$10$ZhY7OR7Ylx6x87uiAZP8.e7UQP7LJ8SitZ9wMBYVv54oUWytOFYcy',3,'2021-03-09 15:16:39','2021-03-09 15:16:39'),
('04181450','$2a$10$MIhSdjwAUHZN4DqRS/2Ud.fANW0JMcx318ofJOxQeeA9QugbLH62W',2,'2021-03-08 16:46:53','2021-03-08 16:46:53'),
('04181451','$2a$10$hT1QEn8DAjKBeHFG1lL2sOYdk71U1Uu9i5aSk3vIsLd.Dl0lJagAS',2,'2021-03-08 16:46:53','2021-03-08 16:46:53'),
('04181452','$2a$10$FXL5mz/pzhWFiMSExp.qtOv/9u7T15TlHKea79kAgArIzFP0WRFuC',2,'2021-03-08 16:46:53','2021-03-08 16:46:53'),
('04181453','$2a$10$IngNZDmCjJ47O2t3O6HzyuLW7GPcXx1Po6OfYv0pr3EuFgQFFfUc6',2,'2021-03-08 16:46:54','2021-03-08 16:46:54'),
('04181454','$2a$10$1izTfoj5pY0KDk4qqgCEQuRoNGNupWClFzJmBpDqvVF//M7VXjYx6',2,'2021-03-08 16:46:54','2021-03-08 16:46:54'),
('04181481','$2a$10$gc6/H1O1sxx2rquq9AiIUez2QWfPH3pj9veqULDq9FpLIeH3FtpUe',3,'2021-03-11 22:34:45','2021-03-11 22:34:45'),
('04181482','$2a$10$TjnKbMHWWJsczl4UtN7hEuWI1lE/.I1aFAanR4iFYlgIIScyccG16',3,'2021-03-11 22:34:46','2021-03-11 22:34:46'),
('04181483','$2a$10$zv6td0AOMsVZCbKJ.bQ8MuEEVN4xZROjRiYFPUvig1p2ZzAP6QUyC',3,'2021-03-11 22:34:46','2021-03-11 23:23:18'),
('04181484','$2a$10$70FTnyoxfAHD7NrhyCgZoOmz81ZJZe7aq7MLhacubrR9q3dw3j9RK',3,'2021-03-11 22:34:46','2021-03-11 22:34:46'),
('04181485','$2a$10$SASD/0YB6peWJEOe32uUAOFOXBlCHAgb8aUcYHwOa1iGmRfo9DzSS',3,'2021-03-11 22:34:46','2021-03-11 22:34:46'),
('04181491','$2a$10$21Q.cEgz8HVMzuD.X1vJxu7Hs.BUcAm5sL/PR3vD.BFTKbqDpL9Mu',3,'2021-03-11 22:36:48','2021-03-11 22:36:48'),
('04181492','$2a$10$HjUphcqG3u2aBJO/vk2vSO8cC2eBBq7ogwK.w95SB/MEbihjWw.eq',3,'2021-03-11 22:36:48','2021-03-11 22:36:48'),
('04181493','$2a$10$w5zLKmbDSSeOxEeP6/yK6.YCGkiWgW8DoCS57/WydqS2XxdnKp//6',3,'2021-03-11 22:36:48','2021-03-11 22:36:48'),
('04181494','$2a$10$u44BKLaleuiAS2eyaKHvreYs6ltSmx5jKx5rpwv84bJeD5H0LEFla',3,'2021-03-11 22:36:48','2021-03-11 22:36:48'),
('04181495','$2a$10$ehxPuxrTzmqj5zRN0SQ2/eB8.fedErljJ5q6Ay2Xi7kauyym4qSba',3,'2021-03-11 22:36:48','2021-03-11 22:36:48');

/*Table structure for table `t_work_result` */

DROP TABLE IF EXISTS `t_work_result`;

CREATE TABLE `t_work_result` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `work_id` int(11) DEFAULT NULL COMMENT '作业id',
  `work_first_id` int(11) DEFAULT NULL COMMENT '作业1id',
  `work_second_id` int(11) DEFAULT NULL COMMENT '作业2id',
  `work_result` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '-1' COMMENT '重复率',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5964 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `t_work_result` */

insert  into `t_work_result`(`id`,`work_id`,`work_first_id`,`work_second_id`,`work_result`) values 
(5856,18,409,408,'0'),
(5864,18,414,408,'0'),
(5865,18,414,409,'0'),
(5877,18,415,408,'0'),
(5878,18,415,409,'0'),
(5879,18,415,414,'0'),
(5881,18,416,408,'64.2'),
(5882,18,416,409,'0'),
(5883,18,416,414,'0'),
(5884,18,416,415,'0');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
