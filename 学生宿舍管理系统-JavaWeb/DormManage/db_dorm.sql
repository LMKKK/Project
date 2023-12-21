/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 6.0.11-alpha-community : Database - db_dorm
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_dorm` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `db_dorm`;

/*Table structure for table `t_admin` */

DROP TABLE IF EXISTS `t_admin`;

CREATE TABLE `t_admin` (
  `adminId` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `tel` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`adminId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `t_admin` */

insert  into `t_admin`(`adminId`,`userName`,`password`,`name`,`sex`,`tel`) values (1,'admin','111','Lero','男','123');

/*Table structure for table `t_dorm` */

DROP TABLE IF EXISTS `t_dorm`;

CREATE TABLE `t_dorm` (
  `dormId` int(11) NOT NULL AUTO_INCREMENT,
  `dormBuildId` int(11) DEFAULT NULL,
  `dormName` varchar(20) DEFAULT NULL,
  `dormType` varchar(20) DEFAULT NULL,
  `dormNumber` int(11) DEFAULT NULL,
  `dormTel` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`dormId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `t_dorm` */

insert  into `t_dorm`(`dormId`,`dormBuildId`,`dormName`,`dormType`,`dormNumber`,`dormTel`) values (1,1,'220','男',6,'110');

/*Table structure for table `t_dormbuild` */

DROP TABLE IF EXISTS `t_dormbuild`;

CREATE TABLE `t_dormbuild` (
  `dormBuildId` int(11) NOT NULL AUTO_INCREMENT,
  `dormBuildName` varchar(20) DEFAULT NULL,
  `dormBuildDetail` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`dormBuildId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `t_dormbuild` */

insert  into `t_dormbuild`(`dormBuildId`,`dormBuildName`,`dormBuildDetail`) values (1,'1栋','这是一栋。。。'),(4,'2栋','这是2栋'),(5,'3栋',''),(6,'4栋',''),(7,'5栋',''),(8,'6栋','');

/*Table structure for table `t_dormmanager` */

DROP TABLE IF EXISTS `t_dormmanager`;

CREATE TABLE `t_dormmanager` (
  `dormManId` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `dormBuildId` int(11) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `sex` varchar(20) DEFAULT NULL,
  `tel` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`dormManId`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `t_dormmanager` */

insert  into `t_dormmanager`(`dormManId`,`userName`,`password`,`dormBuildId`,`name`,`sex`,`tel`) values (2,'manager2','123',4,'小张','男','123'),(3,'manager3','123',1,'小李','女','123'),(4,'manager4','123',5,'小陈','男','123'),(5,'manager5','123',NULL,'小宋','男','123'),(7,'manager6','123',NULL,'呵呵 ','女','123'),(8,'manager1','123',6,'小白','男','123'),(9,'manager7','123',7,'哈哈','女','123');

/*Table structure for table `t_record` */

DROP TABLE IF EXISTS `t_record`;

CREATE TABLE `t_record` (
  `recordId` int(11) NOT NULL AUTO_INCREMENT,
  `studentNumber` varchar(20) DEFAULT NULL,
  `studentName` varchar(30) DEFAULT NULL,
  `dormBuildId` int(11) DEFAULT NULL,
  `dormName` varchar(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `detail` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`recordId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `t_record` */

insert  into `t_record`(`recordId`,`studentNumber`,`studentName`,`dormBuildId`,`dormName`,`date`,`detail`) values (1,'002','李四',4,'120','2014-01-01','123'),(3,'007','测试1',1,'221','2014-08-11','123'),(4,'005','赵起',4,'220','2014-08-12','...'),(5,'006','王珂珂',4,'111','2014-08-12','00'),(6,'004','李进',6,'220','2014-08-12','....'),(7,'004','李进',6,'220','2014-08-12','22');

/*Table structure for table `t_student` */

DROP TABLE IF EXISTS `t_student`;

CREATE TABLE `t_student` (
  `studentId` int(11) NOT NULL AUTO_INCREMENT,
  `stuNum` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `dormBuildId` int(11) DEFAULT NULL,
  `dormName` varchar(11) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `tel` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`studentId`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

/*Data for the table `t_student` */

insert  into `t_student`(`studentId`,`stuNum`,`password`,`name`,`dormBuildId`,`dormName`,`sex`,`tel`) values (2,'002','123','李四',4,'120','男','32'),(3,'003','123','王五',5,'201','男','2'),(4,'004','123','李进',6,'220','女','1'),(5,'005','123','赵起',4,'220','女','123'),(6,'006','123','王珂珂',4,'111','女','111'),(9,'007','123','测试1',1,'221','男','123'),(28,'001','123','测试1',1,'111','男','123'),(29,'008','123','测试3',6,'123','男','123'),(30,'009','123','测试4',5,'123','男','123'),(31,'010','123','小强',4,'222','男','111');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
