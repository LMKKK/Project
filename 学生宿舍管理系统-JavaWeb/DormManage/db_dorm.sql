/*
 Navicat Premium Data Transfer

 Source Server         : remote-db
 Source Server Type    : MySQL
 Source Server Version : 80027 (8.0.27)
 Source Host           : 124.222.203.242:3306
 Source Schema         : db_dorm

 Target Server Type    : MySQL
 Target Server Version : 80027 (8.0.27)
 File Encoding         : 65001

 Date: 28/12/2023 17:18:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_admin
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin` (
  `adminId` int NOT NULL AUTO_INCREMENT,
  `userName` varchar(20) DEFAULT NULL,
  `password` varchar(32) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `tel` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`adminId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of t_admin
-- ----------------------------
BEGIN;
INSERT INTO `t_admin` (`adminId`, `userName`, `password`, `name`, `sex`, `tel`) VALUES (1, 'admin', '698d51a19d8a121ce581499d7b701668', '马保国', '男', '1558888978');
COMMIT;

-- ----------------------------
-- Table structure for t_count
-- ----------------------------
DROP TABLE IF EXISTS `t_count`;
CREATE TABLE `t_count` (
  `countId` int NOT NULL AUTO_INCREMENT,
  `stuNum` varchar(255) DEFAULT NULL,
  `stuName` varchar(255) DEFAULT NULL,
  `date` varchar(255) DEFAULT NULL,
  `dormBuildId` int DEFAULT NULL,
  `dormBuildName` varchar(255) DEFAULT NULL,
  `dormName` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `detail` varchar(255) DEFAULT NULL,
  `startDate` varchar(255) DEFAULT NULL,
  `endDate` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`countId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of t_count
-- ----------------------------
BEGIN;
INSERT INTO `t_count` (`countId`, `stuNum`, `stuName`, `date`, `dormBuildId`, `dormBuildName`, `dormName`, `state`, `detail`, `startDate`, `endDate`) VALUES (1, '123456', 'John Doe', '2023-12-25', 1, 'Building A', 'Room 101', 'Checked In', 'No issues', '2023-12-25', '2023-12-26');
INSERT INTO `t_count` (`countId`, `stuNum`, `stuName`, `date`, `dormBuildId`, `dormBuildName`, `dormName`, `state`, `detail`, `startDate`, `endDate`) VALUES (2, '987654', 'Jane Smith', '2023-12-26', 2, 'Building B', 'Room 202', 'Checked Out', 'Cleaned room', '2023-12-25', '2023-12-26');
INSERT INTO `t_count` (`countId`, `stuNum`, `stuName`, `date`, `dormBuildId`, `dormBuildName`, `dormName`, `state`, `detail`, `startDate`, `endDate`) VALUES (3, '456789', 'Bob Johnson', '2023-12-26', 1, 'Building A', 'Room 103', 'Checked In', 'Maintenance requested', '2023-12-26', '2023-12-27');
INSERT INTO `t_count` (`countId`, `stuNum`, `stuName`, `date`, `dormBuildId`, `dormBuildName`, `dormName`, `state`, `detail`, `startDate`, `endDate`) VALUES (4, '203009', '徐靖博', '2023-12-26 23:42:02', 7, NULL, NULL, '出', '回家', NULL, NULL);
INSERT INTO `t_count` (`countId`, `stuNum`, `stuName`, `date`, `dormBuildId`, `dormBuildName`, `dormName`, `state`, `detail`, `startDate`, `endDate`) VALUES (5, '', '王师傅', '2023-12-26 23:42:49', 7, NULL, NULL, '入', '修理网线', NULL, NULL);
INSERT INTO `t_count` (`countId`, `stuNum`, `stuName`, `date`, `dormBuildId`, `dormBuildName`, `dormName`, `state`, `detail`, `startDate`, `endDate`) VALUES (6, '010', '张三', '2023-12-28 13:55:41', 7, NULL, NULL, '出', '回家', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_dorm
-- ----------------------------
DROP TABLE IF EXISTS `t_dorm`;
CREATE TABLE `t_dorm` (
  `dormId` int NOT NULL AUTO_INCREMENT,
  `dormBuildId` int DEFAULT NULL,
  `dormName` varchar(20) DEFAULT NULL,
  `dormType` varchar(20) DEFAULT NULL,
  `dormNumber` int DEFAULT NULL,
  `dormTel` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`dormId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of t_dorm
-- ----------------------------
BEGIN;
INSERT INTO `t_dorm` (`dormId`, `dormBuildId`, `dormName`, `dormType`, `dormNumber`, `dormTel`) VALUES (1, 1, '220', '男', 6, '110');
COMMIT;

-- ----------------------------
-- Table structure for t_dormBuild
-- ----------------------------
DROP TABLE IF EXISTS `t_dormBuild`;
CREATE TABLE `t_dormBuild` (
  `dormBuildId` int NOT NULL AUTO_INCREMENT,
  `dormBuildName` varchar(20) DEFAULT NULL,
  `dormBuildDetail` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`dormBuildId`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of t_dormBuild
-- ----------------------------
BEGIN;
INSERT INTO `t_dormBuild` (`dormBuildId`, `dormBuildName`, `dormBuildDetail`) VALUES (1, '1栋', '这是一栋。。。');
INSERT INTO `t_dormBuild` (`dormBuildId`, `dormBuildName`, `dormBuildDetail`) VALUES (4, '2栋', '这是2栋');
INSERT INTO `t_dormBuild` (`dormBuildId`, `dormBuildName`, `dormBuildDetail`) VALUES (5, '3栋', '青山绿水环抱，宛如世外桃源，让您感受到大自然的清新与宁静');
INSERT INTO `t_dormBuild` (`dormBuildId`, `dormBuildName`, `dormBuildDetail`) VALUES (6, '4栋', '卧龙凤雏发源地');
INSERT INTO `t_dormBuild` (`dormBuildId`, `dormBuildName`, `dormBuildDetail`) VALUES (7, '5栋', '哈理工最好的公寓楼，没有之一');
INSERT INTO `t_dormBuild` (`dormBuildId`, `dormBuildName`, `dormBuildDetail`) VALUES (8, '6栋', '热血校园，有胆你就来');
INSERT INTO `t_dormBuild` (`dormBuildId`, `dormBuildName`, `dormBuildDetail`) VALUES (9, '12公寓', '斯是陋室，惟吾德馨');
COMMIT;

-- ----------------------------
-- Table structure for t_dormManager
-- ----------------------------
DROP TABLE IF EXISTS `t_dormManager`;
CREATE TABLE `t_dormManager` (
  `dormManId` int NOT NULL AUTO_INCREMENT,
  `userName` varchar(20) DEFAULT NULL,
  `password` varchar(32) NOT NULL,
  `dormBuildId` int DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `sex` varchar(20) DEFAULT NULL,
  `tel` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`dormManId`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of t_dormManager
-- ----------------------------
BEGIN;
INSERT INTO `t_dormManager` (`dormManId`, `userName`, `password`, `dormBuildId`, `name`, `sex`, `tel`) VALUES (2, 'manager2', '123', 4, '小张', '男', '123');
INSERT INTO `t_dormManager` (`dormManId`, `userName`, `password`, `dormBuildId`, `name`, `sex`, `tel`) VALUES (3, 'manager3', '123', 1, '小李', '女', '123');
INSERT INTO `t_dormManager` (`dormManId`, `userName`, `password`, `dormBuildId`, `name`, `sex`, `tel`) VALUES (4, 'manager4', '123', 5, '小陈', '男', '123');
INSERT INTO `t_dormManager` (`dormManId`, `userName`, `password`, `dormBuildId`, `name`, `sex`, `tel`) VALUES (5, 'manager5', '123', 9, '小宋', '男', '123');
INSERT INTO `t_dormManager` (`dormManId`, `userName`, `password`, `dormBuildId`, `name`, `sex`, `tel`) VALUES (7, 'manager6', '123', 9, '呵呵 ', '女', '123');
INSERT INTO `t_dormManager` (`dormManId`, `userName`, `password`, `dormBuildId`, `name`, `sex`, `tel`) VALUES (9, 'manager7', '123', 7, '哈哈', '女', '123');
INSERT INTO `t_dormManager` (`dormManId`, `userName`, `password`, `dormBuildId`, `name`, `sex`, `tel`) VALUES (10, 'zhang', '698d51a19d8a121ce581499d7b701668', 7, '张阿姨', '女', '12833554');
COMMIT;

-- ----------------------------
-- Table structure for t_exception
-- ----------------------------
DROP TABLE IF EXISTS `t_exception`;
CREATE TABLE `t_exception` (
  `excpId` int NOT NULL AUTO_INCREMENT,
  `stuNum` varchar(255) DEFAULT NULL,
  `dormBuildId` int DEFAULT NULL,
  `dormBuildName` varchar(255) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `date` varchar(255) DEFAULT NULL,
  `startDate` varchar(255) DEFAULT NULL,
  `endDate` varchar(255) DEFAULT NULL,
  `detail` varchar(255) DEFAULT NULL,
  `imgUrl` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`excpId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of t_exception
-- ----------------------------
BEGIN;
INSERT INTO `t_exception` (`excpId`, `stuNum`, `dormBuildId`, `dormBuildName`, `tel`, `date`, `startDate`, `endDate`, `detail`, `imgUrl`, `state`) VALUES (1, '123456', 1, 'Building A', '123-456-7890', '2023-12-25', '2023-12-25', '2023-12-26', 'Issue with plumbing', 'example.com/image1.jpg', 'Pending');
INSERT INTO `t_exception` (`excpId`, `stuNum`, `dormBuildId`, `dormBuildName`, `tel`, `date`, `startDate`, `endDate`, `detail`, `imgUrl`, `state`) VALUES (2, '987654', 2, 'Building B', '987-654-3210', '2023-12-26', '2023-12-25', '2023-12-26', 'Maintenance request', 'example.com/image2.jpg', 'In Progress');
INSERT INTO `t_exception` (`excpId`, `stuNum`, `dormBuildId`, `dormBuildName`, `tel`, `date`, `startDate`, `endDate`, `detail`, `imgUrl`, `state`) VALUES (3, '456789', 1, 'Building A', '456-789-0123', '2023-12-26', '2023-12-26', '2023-12-27', 'Electrical issue', 'example.com/image3.jpg', 'Resolved');
INSERT INTO `t_exception` (`excpId`, `stuNum`, `dormBuildId`, `dormBuildName`, `tel`, `date`, `startDate`, `endDate`, `detail`, `imgUrl`, `state`) VALUES (5, '2030090509', 7, NULL, '18254321828', '2023-12-27', NULL, NULL, '门锁坏了', '3cbaa7e3-9d39-49cb-8c66-aecaeae1a9a3_QQ图片20220901222811.jpg', '已完成');
COMMIT;

-- ----------------------------
-- Table structure for t_record
-- ----------------------------
DROP TABLE IF EXISTS `t_record`;
CREATE TABLE `t_record` (
  `recordId` int NOT NULL AUTO_INCREMENT,
  `studentNumber` varchar(20) DEFAULT NULL,
  `studentName` varchar(30) DEFAULT NULL,
  `dormBuildId` int DEFAULT NULL,
  `dormName` varchar(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `detail` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`recordId`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of t_record
-- ----------------------------
BEGIN;
INSERT INTO `t_record` (`recordId`, `studentNumber`, `studentName`, `dormBuildId`, `dormName`, `date`, `detail`) VALUES (1, '002', '李四', 4, '120', '2014-01-01', '123');
INSERT INTO `t_record` (`recordId`, `studentNumber`, `studentName`, `dormBuildId`, `dormName`, `date`, `detail`) VALUES (3, '007', '测试1', 1, '221', '2014-08-11', '123');
INSERT INTO `t_record` (`recordId`, `studentNumber`, `studentName`, `dormBuildId`, `dormName`, `date`, `detail`) VALUES (5, '006', '王珂珂', 4, '111', '2014-08-12', '00');
INSERT INTO `t_record` (`recordId`, `studentNumber`, `studentName`, `dormBuildId`, `dormName`, `date`, `detail`) VALUES (13, '010', '张三', 7, '128', '2023-12-27', '宿舍煮火锅');
COMMIT;

-- ----------------------------
-- Table structure for t_student
-- ----------------------------
DROP TABLE IF EXISTS `t_student`;
CREATE TABLE `t_student` (
  `studentId` int NOT NULL AUTO_INCREMENT,
  `stuNum` varchar(20) DEFAULT NULL,
  `password` varchar(32) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `dormBuildId` int DEFAULT NULL,
  `dormName` varchar(11) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `tel` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`studentId`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of t_student
-- ----------------------------
BEGIN;
INSERT INTO `t_student` (`studentId`, `stuNum`, `password`, `name`, `dormBuildId`, `dormName`, `sex`, `tel`) VALUES (2, '002', '123', '李四', 4, '120', '男', '32');
INSERT INTO `t_student` (`studentId`, `stuNum`, `password`, `name`, `dormBuildId`, `dormName`, `sex`, `tel`) VALUES (4, '004', '123', '李进', 6, '220', '女', '1');
INSERT INTO `t_student` (`studentId`, `stuNum`, `password`, `name`, `dormBuildId`, `dormName`, `sex`, `tel`) VALUES (5, '005', '123', '赵起', 4, '220', '女', '123');
INSERT INTO `t_student` (`studentId`, `stuNum`, `password`, `name`, `dormBuildId`, `dormName`, `sex`, `tel`) VALUES (6, '006', '123', '王珂珂', 4, '111', '女', '111');
INSERT INTO `t_student` (`studentId`, `stuNum`, `password`, `name`, `dormBuildId`, `dormName`, `sex`, `tel`) VALUES (31, '010', '202cb962ac59075b964b07152d234b70', '张三', 7, '128', '男', '110119120');
INSERT INTO `t_student` (`studentId`, `stuNum`, `password`, `name`, `dormBuildId`, `dormName`, `sex`, `tel`) VALUES (33, '22398489', '4297f44b13955235245b2497399d7a93', '猪猪侠', 6, '128', '男', '+8612654');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
