/*
 Navicat Premium Data Transfer

 Source Server         : javaweb
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3306
 Source Schema         : community

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 29/12/2022 15:55:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `username` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_czech_ci NOT NULL,
  `password` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_czech_ci NULL DEFAULT NULL,
  PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_czech_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('001', '001');

-- ----------------------------
-- Table structure for applyin
-- ----------------------------
DROP TABLE IF EXISTS `applyin`;
CREATE TABLE `applyin`  (
  `idx` int NOT NULL AUTO_INCREMENT,
  `id` int NULL DEFAULT NULL,
  `nowtime` datetime NULL DEFAULT NULL,
  `arrtime` date NULL DEFAULT NULL,
  `start` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_czech_ci NULL DEFAULT NULL,
  `end` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_czech_ci NULL DEFAULT NULL,
  `state` varchar(3) CHARACTER SET utf8mb3 COLLATE utf8mb3_czech_ci NULL DEFAULT '审核中',
  `remark` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_czech_ci NULL DEFAULT NULL,
  PRIMARY KEY (`idx`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 309 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_czech_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of applyin
-- ----------------------------
INSERT INTO `applyin` VALUES (1, 101, '2021-11-01 00:01:12', '2022-12-12', '山东省', '黑龙江省', '同意', '无');
INSERT INTO `applyin` VALUES (9, 101, '2022-12-15 01:17:32', '2022-12-15', '广东深圳', '幸福小区', '同意', '我是阴性');
INSERT INTO `applyin` VALUES (109, 102, '2022-12-15 03:13:05', '2022-12-18', '河南郑州', '威海市荣成市', '审核中', '无');
INSERT INTO `applyin` VALUES (209, 102, '2022-12-15 03:16:11', '2022-12-21', '山东济南', '黑龙江省双鸭山市', '审核中', '666');
INSERT INTO `applyin` VALUES (309, 109, '2022-12-15 03:27:13', '2022-12-25', '上海', '香港', '同意', '有钱任性');

-- ----------------------------
-- Table structure for applyout
-- ----------------------------
DROP TABLE IF EXISTS `applyout`;
CREATE TABLE `applyout`  (
  `idx` int NOT NULL AUTO_INCREMENT,
  `id` int NULL DEFAULT NULL,
  `nowtime` datetime NULL DEFAULT NULL,
  `leavetime` date NULL DEFAULT NULL,
  `start` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_czech_ci NULL DEFAULT NULL,
  `end` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_czech_ci NULL DEFAULT NULL,
  `state` varchar(3) CHARACTER SET utf8mb3 COLLATE utf8mb3_czech_ci NULL DEFAULT '审核中',
  `remark` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_czech_ci NULL DEFAULT NULL,
  PRIMARY KEY (`idx`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 309 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_czech_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of applyout
-- ----------------------------
INSERT INTO `applyout` VALUES (1, 101, '2021-11-01 00:01:12', '2022-12-29', '山东省', '黑龙江省', '同意', '无');
INSERT INTO `applyout` VALUES (9, 101, '2022-12-15 01:24:30', '2022-12-16', '哈理工荣成', '北京故宫', '审核中', '我是二币');
INSERT INTO `applyout` VALUES (109, 101, '2022-12-15 01:25:37', '2022-12-18', '山东威海', '黑龙江省哈尔滨市', '拒绝', '我是阳性');
INSERT INTO `applyout` VALUES (209, 102, '2022-12-15 03:17:06', '2022-12-30', '幸福小区', '山东青岛', '审核中', '开学了');
INSERT INTO `applyout` VALUES (309, 109, '2022-12-15 03:18:00', '2022-12-31', '黑盒', '百合', '审核中', '我是二币');

-- ----------------------------
-- Table structure for community
-- ----------------------------
DROP TABLE IF EXISTS `community`;
CREATE TABLE `community`  (
  `com_id` int NOT NULL AUTO_INCREMENT,
  `com_name` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_czech_ci NOT NULL,
  `manage_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`com_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 710 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_czech_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of community
-- ----------------------------
INSERT INTO `community` VALUES (500, '幸福小区', 201);
INSERT INTO `community` VALUES (501, '和谐小区', 202);
INSERT INTO `community` VALUES (502, '阳光小区', 203);

-- ----------------------------
-- Table structure for manager
-- ----------------------------
DROP TABLE IF EXISTS `manager`;
CREATE TABLE `manager`  (
  `manage_id` int NOT NULL AUTO_INCREMENT,
  `password` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_czech_ci NULL DEFAULT NULL,
  `name` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_czech_ci NOT NULL,
  `tel` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_czech_ci NULL DEFAULT NULL,
  PRIMARY KEY (`manage_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 309 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_czech_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of manager
-- ----------------------------
INSERT INTO `manager` VALUES (201, '123321', '李村长', '+8654321');
INSERT INTO `manager` VALUES (202, '123123', '王社长', '+8654321');
INSERT INTO `manager` VALUES (203, '888888', '安云天', '+8688888');
INSERT INTO `manager` VALUES (309, '123321', '小红', '120');

-- ----------------------------
-- Table structure for resident
-- ----------------------------
DROP TABLE IF EXISTS `resident`;
CREATE TABLE `resident`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `password` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_czech_ci NOT NULL,
  `com_id` int NULL DEFAULT NULL,
  `name` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_czech_ci NOT NULL,
  `sex` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_czech_ci NOT NULL,
  `tel` varchar(14) CHARACTER SET utf8mb3 COLLATE utf8mb3_czech_ci NULL DEFAULT NULL,
  `address` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_czech_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 109 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_czech_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of resident
-- ----------------------------
INSERT INTO `resident` VALUES (101, '123321', 500, '张三', '男', '+86123456789', '银河系太阳系地球');
INSERT INTO `resident` VALUES (102, '123123', 501, '小明', '男', '1523565', '黑龙江省双鸭山市');
INSERT INTO `resident` VALUES (109, '2222', 502, 'kkk', '男', '54321828', '山东省滨州市');

SET FOREIGN_KEY_CHECKS = 1;
