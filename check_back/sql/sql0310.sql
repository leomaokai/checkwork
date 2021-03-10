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

/*Table structure for table `t_class_tea` */

DROP TABLE IF EXISTS `t_class_tea`;

CREATE TABLE `t_class_tea` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '班级id',
  `class_name` varchar(100) DEFAULT NULL COMMENT '班级名',
  `tea_id` varchar(50) DEFAULT NULL COMMENT '老师id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `t_class_tea` */

insert  into `t_class_tea`(`id`,`class_name`,`tea_id`,`create_time`,`update_time`) values 
(10,'电信18-5班','04181410','2021-03-06 15:44:15','2021-03-06 15:44:15'),
(11,'测试一班','04181410','2021-03-06 23:41:39','2021-03-06 23:41:39'),
(12,'测试二班','04181410','2021-03-08 22:10:47','2021-03-08 22:10:47'),
(13,'测试三班','04181410','2021-03-10 13:33:30','2021-03-10 13:33:30');

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `t_class_work` */

insert  into `t_class_work`(`id`,`class_id`,`work_id`,`tea_id`,`end_time`,`create_time`,`update_time`) values 
(15,11,20,'04181410','2021-03-17 15:41:01','2021-03-10 15:41:14','2021-03-10 15:41:14'),
(36,10,18,'04181410','2021-03-17 18:13:27','2021-03-10 18:13:37','2021-03-10 18:13:37'),
(39,11,18,'04181410','2021-03-17 18:13:27','2021-03-10 18:13:37','2021-03-10 18:13:37'),
(42,12,18,'04181410','2021-03-17 18:13:27','2021-03-10 18:13:37','2021-03-10 18:13:37');

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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `t_menus` */

insert  into `t_menus`(`menu_id`,`menu_rid`,`menu_url`,`menu_path`,`menu_name`,`menu_component`,`menu_pid`) values 
(1,1,'/admin','/admin','管理员菜单','AdminHome',0),
(2,2,'/teacher','/teacher','教师菜单','TeacherHome',0),
(3,3,'/student','/student','学生菜单','StudentHome',0),
(4,1,'/admin/user','/admin','用户管理','AdminUser',1),
(6,2,'/teacher/class','/teacher','班级管理','TeacherClass',2),
(7,2,'/teacher/work','/teacher','作业管理','TeacherWork',2),
(8,3,'/student/work','/student','作业查询','StudentWork',3),
(9,3,'/student/info','/student','个人信息','StudentInfo',3);

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

/*Table structure for table `t_stu_work` */

DROP TABLE IF EXISTS `t_stu_work`;

CREATE TABLE `t_stu_work` (
  `id` int(50) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `work_id` int(20) DEFAULT NULL COMMENT '作业id',
  `class_id` int(11) DEFAULT NULL COMMENT '班级id',
  `work_name` varchar(200) DEFAULT NULL COMMENT '作业名',
  `stu_id` varchar(50) DEFAULT NULL COMMENT '学生id',
  `work_url` varchar(255) DEFAULT NULL COMMENT '作业url',
  `work_ext` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '作业类型',
  `is_commit` int(1) DEFAULT '0' COMMENT '0未提交,1提交,2超时提交',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=178 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `t_stu_work` */

insert  into `t_stu_work`(`id`,`work_id`,`class_id`,`work_name`,`stu_id`,`work_url`,`work_ext`,`is_commit`,`create_time`,`update_time`) values 
(62,20,11,NULL,'04181427',NULL,NULL,0,'2021-03-10 15:41:14','2021-03-10 15:41:14'),
(63,20,11,NULL,'04181428',NULL,NULL,0,'2021-03-10 15:41:14','2021-03-10 15:41:14'),
(64,20,11,NULL,'04181429',NULL,NULL,0,'2021-03-10 15:41:14','2021-03-10 15:41:14'),
(153,18,10,NULL,'04181420',NULL,NULL,0,'2021-03-10 18:13:37','2021-03-10 18:13:37'),
(154,18,10,NULL,'04181421',NULL,NULL,0,'2021-03-10 18:13:37','2021-03-10 18:13:37'),
(155,18,10,NULL,'04181422',NULL,NULL,0,'2021-03-10 18:13:37','2021-03-10 18:13:37'),
(156,18,10,NULL,'04181423',NULL,NULL,0,'2021-03-10 18:13:37','2021-03-10 18:13:37'),
(157,18,10,NULL,'04181424',NULL,NULL,0,'2021-03-10 18:13:37','2021-03-10 18:13:37'),
(158,18,10,NULL,'04181425',NULL,NULL,0,'2021-03-10 18:13:37','2021-03-10 18:13:37'),
(159,18,10,NULL,'04181426',NULL,NULL,0,'2021-03-10 18:13:37','2021-03-10 18:13:37'),
(166,18,11,NULL,'04181427',NULL,NULL,0,'2021-03-10 18:13:37','2021-03-10 18:13:37'),
(167,18,11,NULL,'04181428',NULL,NULL,0,'2021-03-10 18:13:37','2021-03-10 18:13:37'),
(168,18,11,NULL,'04181429',NULL,NULL,0,'2021-03-10 18:13:37','2021-03-10 18:13:37'),
(175,18,12,NULL,'04181430',NULL,NULL,0,'2021-03-10 18:13:37','2021-03-10 18:13:37'),
(176,18,12,NULL,'04181431',NULL,NULL,0,'2021-03-10 18:13:37','2021-03-10 18:13:37'),
(177,18,12,NULL,'04181432',NULL,NULL,0,'2021-03-10 18:13:37','2021-03-10 18:13:37');

/*Table structure for table `t_student` */

DROP TABLE IF EXISTS `t_student`;

CREATE TABLE `t_student` (
  `stu_id` varchar(50) NOT NULL COMMENT '学生id',
  `stu_name` varchar(50) DEFAULT NULL COMMENT '学生姓名',
  `stu_phone` char(11) DEFAULT NULL COMMENT '学生手机',
  `stu_qq` varchar(100) DEFAULT NULL COMMENT '学生qq',
  `stu_mail` varchar(100) DEFAULT NULL COMMENT '学生邮箱',
  `stu_class_id` int(10) DEFAULT NULL COMMENT '班级',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`stu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `t_student` */

insert  into `t_student`(`stu_id`,`stu_name`,`stu_phone`,`stu_qq`,`stu_mail`,`stu_class_id`,`create_time`,`update_time`) values 
('04181420',NULL,NULL,NULL,NULL,10,'2021-03-06 23:42:47','2021-03-06 23:42:47'),
('04181421',NULL,NULL,NULL,NULL,10,'2021-03-06 23:42:47','2021-03-06 23:42:47'),
('04181422',NULL,NULL,NULL,NULL,10,'2021-03-06 23:42:47','2021-03-06 23:42:47'),
('04181423',NULL,NULL,NULL,NULL,10,'2021-03-09 15:06:40','2021-03-09 15:06:40'),
('04181424',NULL,NULL,NULL,NULL,10,'2021-03-09 15:07:09','2021-03-09 15:07:09'),
('04181425',NULL,NULL,NULL,NULL,10,'2021-03-09 15:06:40','2021-03-09 15:06:40'),
('04181426',NULL,NULL,NULL,NULL,10,'2021-03-09 15:06:40','2021-03-09 15:06:40'),
('04181427',NULL,NULL,NULL,NULL,11,'2021-03-09 15:09:24','2021-03-09 15:09:24'),
('04181428',NULL,NULL,NULL,NULL,11,'2021-03-09 15:09:24','2021-03-09 15:09:24'),
('04181429',NULL,NULL,NULL,NULL,11,'2021-03-09 15:09:24','2021-03-09 15:09:24'),
('04181430',NULL,NULL,NULL,NULL,12,'2021-03-09 15:16:39','2021-03-09 15:16:39'),
('04181431',NULL,NULL,NULL,NULL,12,'2021-03-09 15:16:39','2021-03-09 15:16:39'),
('04181432',NULL,NULL,NULL,NULL,12,'2021-03-09 15:16:39','2021-03-09 15:16:39');

/*Table structure for table `t_tea_work` */

DROP TABLE IF EXISTS `t_tea_work`;

CREATE TABLE `t_tea_work` (
  `work_id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'work_id',
  `tea_id` varchar(50) DEFAULT NULL COMMENT '教师id',
  `work_title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '作业标题',
  `work_describe` varchar(100) DEFAULT NULL COMMENT '作业描述',
  `work_dir` varchar(200) DEFAULT NULL COMMENT '作业路径',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`work_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `t_tea_work` */

insert  into `t_tea_work`(`work_id`,`tea_id`,`work_title`,`work_describe`,`work_dir`,`create_time`,`update_time`) values 
(18,'04181410','测试作业一','数据结构第一章','/check_resource/04181410/测试作业一','2021-03-10 13:22:01','2021-03-10 18:13:36'),
(20,'04181410','测试作业二','数据结构第二章','/check_resource/04181410/测试作业二','2021-03-10 13:24:05','2021-03-10 15:41:14');

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
('{id}',NULL,NULL,NULL,NULL,'2021-03-08 16:16:03','2021-03-08 16:16:03'),
('04181410',NULL,NULL,NULL,NULL,'2021-03-04 20:16:27','2021-03-04 20:16:27'),
('04181411',NULL,NULL,NULL,NULL,'2021-03-08 15:36:33','2021-03-08 15:36:33'),
('04181412',NULL,NULL,NULL,NULL,'2021-03-08 16:39:28','2021-03-08 16:39:28'),
('04181450',NULL,NULL,NULL,NULL,'2021-03-08 16:46:53','2021-03-08 16:46:53'),
('04181451',NULL,NULL,NULL,NULL,'2021-03-08 16:46:53','2021-03-08 16:46:53'),
('04181452',NULL,NULL,NULL,NULL,'2021-03-08 16:46:53','2021-03-08 16:46:53'),
('04181453',NULL,NULL,NULL,NULL,'2021-03-08 16:46:54','2021-03-08 16:46:54'),
('04181454',NULL,NULL,NULL,NULL,'2021-03-08 16:46:54','2021-03-08 16:46:54'),
('04181455',NULL,NULL,NULL,NULL,'2021-03-08 16:46:54','2021-03-08 16:46:54'),
('undefined',NULL,NULL,NULL,NULL,'2021-03-08 16:21:55','2021-03-08 16:21:55');

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
('04181455','$2a$10$bnlcu8NRK/SyzT9dyI083uGz5CHNKqohG4RP6tHCjw8DKmPNWdSn2',2,'2021-03-08 16:46:54','2021-03-08 16:46:54');

/*Table structure for table `t_work_class` */

DROP TABLE IF EXISTS `t_work_class`;

CREATE TABLE `t_work_class` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `work_id` int(11) DEFAULT NULL COMMENT '作业id',
  `work_describe` varchar(100) DEFAULT NULL COMMENT '作业描述',
  `work_dir` varchar(200) DEFAULT NULL COMMENT '作业目录',
  `tea_id` varchar(50) DEFAULT NULL COMMENT '老师id',
  `class_id` int(11) DEFAULT NULL COMMENT '班级id',
  `end_time` datetime DEFAULT NULL COMMENT '截至时间',
  `total_number` int(10) DEFAULT '0' COMMENT '应提交人数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `t_work_class` */

/*Table structure for table `t_work_result` */

DROP TABLE IF EXISTS `t_work_result`;

CREATE TABLE `t_work_result` (
  `id` int(50) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `work_id` int(20) DEFAULT NULL COMMENT '作业id',
  `class_id` int(20) DEFAULT NULL COMMENT '作业id',
  `work_first_id` int(20) DEFAULT NULL COMMENT '作业1id',
  `work_second_id` int(20) DEFAULT NULL COMMENT '作业2id',
  `work_result` varchar(20) DEFAULT NULL COMMENT '重复率',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2140 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `t_work_result` */

insert  into `t_work_result`(`id`,`work_id`,`class_id`,`work_first_id`,`work_second_id`,`work_result`) values 
(229,20,11,62,63,NULL),
(230,20,11,62,64,NULL),
(231,20,11,63,64,NULL),
(2062,18,12,153,154,NULL),
(2063,18,12,153,155,NULL),
(2064,18,12,153,156,NULL),
(2065,18,12,153,157,NULL),
(2066,18,12,153,158,NULL),
(2067,18,12,153,159,NULL),
(2068,18,12,153,166,NULL),
(2069,18,12,153,167,NULL),
(2070,18,12,153,168,NULL),
(2071,18,12,153,175,NULL),
(2072,18,12,153,176,NULL),
(2073,18,12,153,177,NULL),
(2074,18,12,154,155,NULL),
(2075,18,12,154,156,NULL),
(2076,18,12,154,157,NULL),
(2077,18,12,154,158,NULL),
(2078,18,12,154,159,NULL),
(2079,18,12,154,166,NULL),
(2080,18,12,154,167,NULL),
(2081,18,12,154,168,NULL),
(2082,18,12,154,175,NULL),
(2083,18,12,154,176,NULL),
(2084,18,12,154,177,NULL),
(2085,18,12,155,156,NULL),
(2086,18,12,155,157,NULL),
(2087,18,12,155,158,NULL),
(2088,18,12,155,159,NULL),
(2089,18,12,155,166,NULL),
(2090,18,12,155,167,NULL),
(2091,18,12,155,168,NULL),
(2092,18,12,155,175,NULL),
(2093,18,12,155,176,NULL),
(2094,18,12,155,177,NULL),
(2095,18,12,156,157,NULL),
(2096,18,12,156,158,NULL),
(2097,18,12,156,159,NULL),
(2098,18,12,156,166,NULL),
(2099,18,12,156,167,NULL),
(2100,18,12,156,168,NULL),
(2101,18,12,156,175,NULL),
(2102,18,12,156,176,NULL),
(2103,18,12,156,177,NULL),
(2104,18,12,157,158,NULL),
(2105,18,12,157,159,NULL),
(2106,18,12,157,166,NULL),
(2107,18,12,157,167,NULL),
(2108,18,12,157,168,NULL),
(2109,18,12,157,175,NULL),
(2110,18,12,157,176,NULL),
(2111,18,12,157,177,NULL),
(2112,18,12,158,159,NULL),
(2113,18,12,158,166,NULL),
(2114,18,12,158,167,NULL),
(2115,18,12,158,168,NULL),
(2116,18,12,158,175,NULL),
(2117,18,12,158,176,NULL),
(2118,18,12,158,177,NULL),
(2119,18,12,159,166,NULL),
(2120,18,12,159,167,NULL),
(2121,18,12,159,168,NULL),
(2122,18,12,159,175,NULL),
(2123,18,12,159,176,NULL),
(2124,18,12,159,177,NULL),
(2125,18,12,166,167,NULL),
(2126,18,12,166,168,NULL),
(2127,18,12,166,175,NULL),
(2128,18,12,166,176,NULL),
(2129,18,12,166,177,NULL),
(2130,18,12,167,168,NULL),
(2131,18,12,167,175,NULL),
(2132,18,12,167,176,NULL),
(2133,18,12,167,177,NULL),
(2134,18,12,168,175,NULL),
(2135,18,12,168,176,NULL),
(2136,18,12,168,177,NULL),
(2137,18,12,175,176,NULL),
(2138,18,12,175,177,NULL),
(2139,18,12,176,177,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
