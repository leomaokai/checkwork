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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `t_class_tea` */

insert  into `t_class_tea`(`id`,`class_name`,`tea_id`,`create_time`,`update_time`) values 
(10,'电信18-5班','04181410','2021-03-06 15:44:15','2021-03-06 15:44:15'),
(11,'测试一班','04181410','2021-03-06 23:41:39','2021-03-06 23:41:39'),
(12,'测试二班','04181410','2021-03-08 22:10:47','2021-03-08 22:10:47'),
(15,'测试三班','04181410','2021-03-10 22:04:08','2021-03-10 22:04:08');

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
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `t_class_work` */

insert  into `t_class_work`(`id`,`class_id`,`work_id`,`tea_id`,`end_time`,`create_time`,`update_time`) values 
(69,10,18,'04181410','2021-03-31 00:00:00','2021-03-10 22:38:17','2021-03-10 23:28:31'),
(70,11,18,'04181410','2021-03-17 22:38:14','2021-03-10 22:38:17','2021-03-10 22:38:17'),
(71,12,18,'04181410','2021-03-17 22:38:14','2021-03-10 22:38:17','2021-03-10 22:38:17'),
(72,15,18,'04181410','2021-03-17 22:38:14','2021-03-10 22:38:17','2021-03-10 22:38:17'),
(85,10,20,'04181410','2021-03-17 22:59:45','2021-03-10 22:59:48','2021-03-10 22:59:48'),
(86,11,20,'04181410','2021-03-17 22:59:45','2021-03-10 22:59:49','2021-03-10 22:59:49'),
(87,12,20,'04181410','2021-03-17 22:59:45','2021-03-10 22:59:49','2021-03-10 22:59:49'),
(88,15,20,'04181410','2021-03-17 22:59:45','2021-03-10 22:59:49','2021-03-10 22:59:49');

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
  `is_commit` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '未提交' COMMENT '未提交,已提交,超时提交',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=341 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `t_stu_work` */

insert  into `t_stu_work`(`id`,`work_id`,`class_id`,`work_name`,`stu_id`,`work_url`,`work_ext`,`is_commit`,`create_time`,`update_time`) values 
(276,18,10,'未提交','04181420',NULL,NULL,'未提交','2021-03-10 22:38:17','2021-03-10 22:38:17'),
(277,18,10,'未提交','04181421',NULL,NULL,'未提交','2021-03-10 22:38:17','2021-03-10 22:38:17'),
(278,18,10,'未提交','04181422',NULL,NULL,'未提交','2021-03-10 22:38:17','2021-03-10 22:38:17'),
(279,18,10,'未提交','04181423',NULL,NULL,'未提交','2021-03-10 22:38:17','2021-03-10 22:38:17'),
(280,18,10,'未提交','04181424',NULL,NULL,'未提交','2021-03-10 22:38:17','2021-03-10 22:38:17'),
(281,18,10,'未提交','04181425',NULL,NULL,'未提交','2021-03-10 22:38:17','2021-03-10 22:38:17'),
(282,18,10,'未提交','04181426',NULL,NULL,'未提交','2021-03-10 22:38:17','2021-03-10 22:38:17'),
(283,18,11,'未提交','04181427',NULL,NULL,'未提交','2021-03-10 22:38:17','2021-03-10 22:38:17'),
(284,18,11,'未提交','04181428',NULL,NULL,'未提交','2021-03-10 22:38:17','2021-03-10 22:38:17'),
(285,18,11,'未提交','04181429',NULL,NULL,'未提交','2021-03-10 22:38:17','2021-03-10 22:38:17'),
(286,18,12,'未提交','04181430',NULL,NULL,'未提交','2021-03-10 22:38:17','2021-03-10 22:38:17'),
(287,18,12,'未提交','04181431',NULL,NULL,'未提交','2021-03-10 22:38:17','2021-03-10 22:38:17'),
(288,18,12,'未提交','04181432',NULL,NULL,'未提交','2021-03-10 22:38:17','2021-03-10 22:38:17'),
(328,20,10,'未提交','04181420',NULL,NULL,'未提交','2021-03-10 22:59:48','2021-03-10 22:59:48'),
(329,20,10,'未提交','04181421',NULL,NULL,'未提交','2021-03-10 22:59:48','2021-03-10 22:59:48'),
(330,20,10,'未提交','04181422',NULL,NULL,'未提交','2021-03-10 22:59:48','2021-03-10 22:59:48'),
(331,20,10,'未提交','04181423',NULL,NULL,'未提交','2021-03-10 22:59:48','2021-03-10 22:59:48'),
(332,20,10,'未提交','04181424',NULL,NULL,'未提交','2021-03-10 22:59:48','2021-03-10 22:59:48'),
(333,20,10,'未提交','04181425',NULL,NULL,'未提交','2021-03-10 22:59:48','2021-03-10 22:59:48'),
(334,20,10,'未提交','04181426',NULL,NULL,'未提交','2021-03-10 22:59:48','2021-03-10 22:59:48'),
(335,20,11,'未提交','04181427',NULL,NULL,'未提交','2021-03-10 22:59:49','2021-03-10 22:59:49'),
(336,20,11,'未提交','04181428',NULL,NULL,'未提交','2021-03-10 22:59:49','2021-03-10 22:59:49'),
(337,20,11,'未提交','04181429',NULL,NULL,'未提交','2021-03-10 22:59:49','2021-03-10 22:59:49'),
(338,20,12,'未提交','04181430',NULL,NULL,'未提交','2021-03-10 22:59:49','2021-03-10 22:59:49'),
(339,20,12,'未提交','04181431',NULL,NULL,'未提交','2021-03-10 22:59:49','2021-03-10 22:59:49'),
(340,20,12,'未提交','04181432',NULL,NULL,'未提交','2021-03-10 22:59:49','2021-03-10 22:59:49');

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
(18,'04181410','测试作业一','数据结构第一章','/check_resource/04181410/测试作业一','2021-03-10 13:22:01','2021-03-10 22:38:16'),
(20,'04181410','测试作业二','数据结构第二章','/check_resource/04181410/测试作业二','2021-03-10 13:24:05','2021-03-10 22:59:48');

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
('04181454',NULL,NULL,NULL,NULL,'2021-03-08 16:46:54','2021-03-08 16:46:54'),
('04181455',NULL,NULL,NULL,NULL,'2021-03-08 16:46:54','2021-03-08 16:46:54');

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
  `work_first_id` int(20) DEFAULT NULL COMMENT '作业1id',
  `work_second_id` int(20) DEFAULT NULL COMMENT '作业2id',
  `work_result` varchar(20) DEFAULT NULL COMMENT '重复率',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5560 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `t_work_result` */

insert  into `t_work_result`(`id`,`work_id`,`work_first_id`,`work_second_id`,`work_result`) values 
(4324,18,276,277,NULL),
(4325,18,276,278,NULL),
(4326,18,276,279,NULL),
(4327,18,276,280,NULL),
(4328,18,276,281,NULL),
(4329,18,276,282,NULL),
(4330,18,276,283,NULL),
(4331,18,276,284,NULL),
(4332,18,276,285,NULL),
(4333,18,276,286,NULL),
(4334,18,276,287,NULL),
(4335,18,276,288,NULL),
(4336,18,277,278,NULL),
(4337,18,277,279,NULL),
(4338,18,277,280,NULL),
(4339,18,277,281,NULL),
(4340,18,277,282,NULL),
(4341,18,277,283,NULL),
(4342,18,277,284,NULL),
(4343,18,277,285,NULL),
(4344,18,277,286,NULL),
(4345,18,277,287,NULL),
(4346,18,277,288,NULL),
(4347,18,278,279,NULL),
(4348,18,278,280,NULL),
(4349,18,278,281,NULL),
(4350,18,278,282,NULL),
(4351,18,278,283,NULL),
(4352,18,278,284,NULL),
(4353,18,278,285,NULL),
(4354,18,278,286,NULL),
(4355,18,278,287,NULL),
(4356,18,278,288,NULL),
(4357,18,279,280,NULL),
(4358,18,279,281,NULL),
(4359,18,279,282,NULL),
(4360,18,279,283,NULL),
(4361,18,279,284,NULL),
(4362,18,279,285,NULL),
(4363,18,279,286,NULL),
(4364,18,279,287,NULL),
(4365,18,279,288,NULL),
(4366,18,280,281,NULL),
(4367,18,280,282,NULL),
(4368,18,280,283,NULL),
(4369,18,280,284,NULL),
(4370,18,280,285,NULL),
(4371,18,280,286,NULL),
(4372,18,280,287,NULL),
(4373,18,280,288,NULL),
(4374,18,281,282,NULL),
(4375,18,281,283,NULL),
(4376,18,281,284,NULL),
(4377,18,281,285,NULL),
(4378,18,281,286,NULL),
(4379,18,281,287,NULL),
(4380,18,281,288,NULL),
(4381,18,282,283,NULL),
(4382,18,282,284,NULL),
(4383,18,282,285,NULL),
(4384,18,282,286,NULL),
(4385,18,282,287,NULL),
(4386,18,282,288,NULL),
(4387,18,283,284,NULL),
(4388,18,283,285,NULL),
(4389,18,283,286,NULL),
(4390,18,283,287,NULL),
(4391,18,283,288,NULL),
(4392,18,284,285,NULL),
(4393,18,284,286,NULL),
(4394,18,284,287,NULL),
(4395,18,284,288,NULL),
(4396,18,285,286,NULL),
(4397,18,285,287,NULL),
(4398,18,285,288,NULL),
(4399,18,286,287,NULL),
(4400,18,286,288,NULL),
(4401,18,287,288,NULL),
(5482,20,328,329,NULL),
(5483,20,328,330,NULL),
(5484,20,328,331,NULL),
(5485,20,328,332,NULL),
(5486,20,328,333,NULL),
(5487,20,328,334,NULL),
(5488,20,328,335,NULL),
(5489,20,328,336,NULL),
(5490,20,328,337,NULL),
(5491,20,328,338,NULL),
(5492,20,328,339,NULL),
(5493,20,328,340,NULL),
(5494,20,329,330,NULL),
(5495,20,329,331,NULL),
(5496,20,329,332,NULL),
(5497,20,329,333,NULL),
(5498,20,329,334,NULL),
(5499,20,329,335,NULL),
(5500,20,329,336,NULL),
(5501,20,329,337,NULL),
(5502,20,329,338,NULL),
(5503,20,329,339,NULL),
(5504,20,329,340,NULL),
(5505,20,330,331,NULL),
(5506,20,330,332,NULL),
(5507,20,330,333,NULL),
(5508,20,330,334,NULL),
(5509,20,330,335,NULL),
(5510,20,330,336,NULL),
(5511,20,330,337,NULL),
(5512,20,330,338,NULL),
(5513,20,330,339,NULL),
(5514,20,330,340,NULL),
(5515,20,331,332,NULL),
(5516,20,331,333,NULL),
(5517,20,331,334,NULL),
(5518,20,331,335,NULL),
(5519,20,331,336,NULL),
(5520,20,331,337,NULL),
(5521,20,331,338,NULL),
(5522,20,331,339,NULL),
(5523,20,331,340,NULL),
(5524,20,332,333,NULL),
(5525,20,332,334,NULL),
(5526,20,332,335,NULL),
(5527,20,332,336,NULL),
(5528,20,332,337,NULL),
(5529,20,332,338,NULL),
(5530,20,332,339,NULL),
(5531,20,332,340,NULL),
(5532,20,333,334,NULL),
(5533,20,333,335,NULL),
(5534,20,333,336,NULL),
(5535,20,333,337,NULL),
(5536,20,333,338,NULL),
(5537,20,333,339,NULL),
(5538,20,333,340,NULL),
(5539,20,334,335,NULL),
(5540,20,334,336,NULL),
(5541,20,334,337,NULL),
(5542,20,334,338,NULL),
(5543,20,334,339,NULL),
(5544,20,334,340,NULL),
(5545,20,335,336,NULL),
(5546,20,335,337,NULL),
(5547,20,335,338,NULL),
(5548,20,335,339,NULL),
(5549,20,335,340,NULL),
(5550,20,336,337,NULL),
(5551,20,336,338,NULL),
(5552,20,336,339,NULL),
(5553,20,336,340,NULL),
(5554,20,337,338,NULL),
(5555,20,337,339,NULL),
(5556,20,337,340,NULL),
(5557,20,338,339,NULL),
(5558,20,338,340,NULL),
(5559,20,339,340,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
