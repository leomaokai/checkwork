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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `t_class_tea` */

insert  into `t_class_tea`(`id`,`class_name`,`tea_id`,`create_time`,`update_time`) values 
(1,'电信18-5班','04181410',NULL,NULL),
(2,'电信18-6班','04181410',NULL,NULL),
(3,'电信18-7班','04181411',NULL,NULL),
(4,'电信18-8班','04181411',NULL,NULL);

/*Table structure for table `t_role_url` */

DROP TABLE IF EXISTS `t_role_url`;

CREATE TABLE `t_role_url` (
  `id` int(5) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_name` enum('管理员','老师','学生') DEFAULT NULL COMMENT '角色',
  `role_url` varchar(100) DEFAULT NULL COMMENT 'url',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `t_role_url` */

insert  into `t_role_url`(`id`,`role_name`,`role_url`) values 
(1,'管理员','/admin/**'),
(2,'老师','/teacher/**'),
(3,'学生','/student/**');

/*Table structure for table `t_stu_class` */

DROP TABLE IF EXISTS `t_stu_class`;

CREATE TABLE `t_stu_class` (
  `stu_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学号',
  `class_id` int(11) DEFAULT NULL COMMENT '班级id',
  PRIMARY KEY (`stu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `t_stu_class` */

insert  into `t_stu_class`(`stu_id`,`class_id`) values 
('04181412',1),
('04181413',1),
('04181414',1),
('04181415',1),
('04181416',1),
('04181417',2),
('04181418',2),
('04181419',2),
('04181420',2),
('04181421',2),
('04181422',3),
('04181423',3),
('04181424',3),
('04181425',3),
('04181426',4),
('04181427',4),
('04181428',4),
('04181429',4),
('04181430',4),
('04181431',4);

/*Table structure for table `t_stu_work` */

DROP TABLE IF EXISTS `t_stu_work`;

CREATE TABLE `t_stu_work` (
  `id` int(50) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `work_id` int(20) DEFAULT NULL COMMENT '作业id',
  `work_name` varchar(200) DEFAULT NULL COMMENT '作业名',
  `stu_id` varchar(50) DEFAULT NULL COMMENT '学生id',
  `work_url` varchar(255) DEFAULT NULL COMMENT '作业url',
  `is_commit` int(1) DEFAULT '0' COMMENT '0未提交,1提交',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `t_stu_work` */

insert  into `t_stu_work`(`id`,`work_id`,`work_name`,`stu_id`,`work_url`,`is_commit`,`create_time`,`update_time`) values 
(1,1,NULL,'04181412',NULL,0,NULL,NULL),
(2,1,NULL,NULL,NULL,0,NULL,NULL);

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `user_id` varchar(50) NOT NULL COMMENT '用户id',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户姓名',
  `user_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户密码',
  `user_phone` char(11) DEFAULT NULL COMMENT '用户手机',
  `user_role_id` int(3) DEFAULT NULL COMMENT '3学生,2老师,1管理员',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `t_user` */

insert  into `t_user`(`user_id`,`user_name`,`user_password`,`user_phone`,`user_role_id`,`create_time`,`update_time`) values 
('04180000','admin','$2a$10$BTleV5eaOVyY3YrGWZSgo.2WlY.aqcucdezs8EVbrZs1VaWWDiZcm','19552258770',1,NULL,NULL),
('04181410','tea1',NULL,NULL,2,NULL,NULL),
('04181411','tea2',NULL,NULL,2,NULL,NULL),
('04181412','stu1',NULL,NULL,3,NULL,NULL),
('04181413','stu2',NULL,NULL,3,NULL,NULL),
('04181414','stu3',NULL,NULL,3,NULL,NULL),
('04181415','stu4',NULL,NULL,3,NULL,NULL),
('04181416','stu5',NULL,NULL,3,NULL,NULL),
('04181417','stu6',NULL,NULL,3,NULL,NULL),
('04181418','stu7',NULL,NULL,3,NULL,NULL),
('04181419','stu8',NULL,NULL,3,NULL,NULL),
('04181420','stu9',NULL,NULL,3,NULL,NULL),
('04181421','stu10',NULL,NULL,3,NULL,NULL),
('04181422','stu11',NULL,NULL,3,NULL,NULL),
('04181423','stu12',NULL,NULL,3,NULL,NULL),
('04181424','stu13',NULL,NULL,3,NULL,NULL),
('04181425','stu14',NULL,NULL,3,NULL,NULL),
('04181426','stu15',NULL,NULL,3,NULL,NULL),
('04181427','stu16',NULL,NULL,3,NULL,NULL),
('04181428','stu17',NULL,NULL,3,NULL,NULL),
('04181429','stu18',NULL,NULL,3,NULL,NULL),
('04181430','stu19',NULL,NULL,3,NULL,NULL),
('04181431','stu20',NULL,NULL,3,NULL,NULL);

/*Table structure for table `t_work_result` */

DROP TABLE IF EXISTS `t_work_result`;

CREATE TABLE `t_work_result` (
  `id` int(50) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `work_first_id` int(20) DEFAULT NULL COMMENT '作业1id',
  `work_second_id` int(20) DEFAULT NULL COMMENT '作业2id',
  `work_result` varchar(20) DEFAULT NULL COMMENT '重复率',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `t_work_result` */

/*Table structure for table `t_work_tea` */

DROP TABLE IF EXISTS `t_work_tea`;

CREATE TABLE `t_work_tea` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '作业id',
  `work_describe` varchar(100) DEFAULT NULL COMMENT '作业描述',
  `class_id` int(11) DEFAULT NULL COMMENT '班级id',
  `tea_id` varchar(50) DEFAULT NULL COMMENT '老师id',
  `end_time` datetime DEFAULT NULL COMMENT '截至时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `t_work_tea` */

insert  into `t_work_tea`(`id`,`work_describe`,`class_id`,`tea_id`,`end_time`,`create_time`,`update_time`) values 
(1,'第一次作业',1,'04181410',NULL,NULL,NULL),
(2,'第一次作业',2,'04181410',NULL,NULL,NULL),
(3,'第一次作业',3,'04181411',NULL,NULL,NULL),
(4,'第一次作业',4,'04181411',NULL,NULL,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
