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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `t_class_tea` */

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
  `work_name` varchar(200) DEFAULT NULL COMMENT '作业名',
  `stu_id` varchar(50) DEFAULT NULL COMMENT '学生id',
  `work_url` varchar(255) DEFAULT NULL COMMENT '作业url',
  `work_ext` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '作业类型',
  `is_commit` int(1) DEFAULT '0' COMMENT '0未提交,1提交',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `t_stu_work` */

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

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户密码',
  `user_role_id` int(3) DEFAULT NULL COMMENT '3学生,2老师,1管理员',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `t_user` */

insert  into `t_user`(`username`,`password`,`user_role_id`,`create_time`,`update_time`) values 
('04180000','$2a$10$9tRjQ6PVKyL.OyN/oDtF9urtERTaXK5dI49V3aeEB4myXzNXLvO.6',1,NULL,'2021-03-03 22:33:38');

/*Table structure for table `t_work_class` */

DROP TABLE IF EXISTS `t_work_class`;

CREATE TABLE `t_work_class` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '作业id',
  `work_describe` varchar(100) DEFAULT NULL COMMENT '作业描述',
  `work_dir` varchar(200) DEFAULT NULL COMMENT '作业目录',
  `class_id` int(11) DEFAULT NULL COMMENT '班级id',
  `end_time` datetime DEFAULT NULL COMMENT '截至时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `t_work_result` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
