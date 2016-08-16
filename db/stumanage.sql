/*
SQLyog Community v8.7 Beta3
MySQL - 5.6.16-log : Database - stumanage
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`stumanage` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `stumanage`;

/*Table structure for table `specialty` */

DROP TABLE IF EXISTS `specialty`;

CREATE TABLE `specialty` (
  `specialty_id` int(11) NOT NULL AUTO_INCREMENT,
  `specialty_name` char(20) DEFAULT NULL,
  PRIMARY KEY (`specialty_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `specialty` */

insert  into `specialty`(`specialty_id`,`specialty_name`) values (1,'计算机科学与技术'),(2,'信息管理与信息系统'),(3,'网络工程'),(4,'信息安全');

/*Table structure for table `student` */

DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
  `stu_id` int(11) NOT NULL AUTO_INCREMENT,
  `specialty_id` int(11) DEFAULT NULL,
  `name` char(10) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL COMMENT '男生是0，女生是1',
  `age` int(11) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `info` blob,
  PRIMARY KEY (`stu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `student` */

insert  into `student`(`stu_id`,`specialty_id`,`name`,`sex`,`age`,`birthday`,`info`) values (2,4,'小华',1,21,'1994-12-11',''),(3,3,'张三',1,26,'1990-01-01',''),(4,2,'李四',0,31,'1985-06-09',''),(7,3,'软件管家',1,16,'1999-09-22','腾讯呆脑管家'),(8,1,'返回',0,46,'1970-01-01',NULL),(9,4,'李阿华',1,11,'2005-02-15','软件工程师\n\n高级软件架构师\n\n信息安全工程师\n\n项目经理'),(10,1,'的双方各',0,46,'1970-01-01','是大法官是');

/*Table structure for table `stu_complete_info` */

DROP TABLE IF EXISTS `stu_complete_info`;

/*!50001 DROP VIEW IF EXISTS `stu_complete_info` */;
/*!50001 DROP TABLE IF EXISTS `stu_complete_info` */;

/*!50001 CREATE TABLE  `stu_complete_info`(
 `stu_id` int(11) ,
 `specialty_id` int(11) ,
 `specialty_name` char(20) ,
 `name` char(10) ,
 `sex` int(11) ,
 `age` int(11) ,
 `birthday` date ,
 `info` blob 
)*/;

/*View structure for view stu_complete_info */

/*!50001 DROP TABLE IF EXISTS `stu_complete_info` */;
/*!50001 DROP VIEW IF EXISTS `stu_complete_info` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `stu_complete_info` AS (select `stu`.`stu_id` AS `stu_id`,`stu`.`specialty_id` AS `specialty_id`,`spe`.`specialty_name` AS `specialty_name`,`stu`.`name` AS `name`,`stu`.`sex` AS `sex`,`stu`.`age` AS `age`,`stu`.`birthday` AS `birthday`,`stu`.`info` AS `info` from (`student` `stu` left join `specialty` `spe` on((`stu`.`specialty_id` = `spe`.`specialty_id`)))) */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
