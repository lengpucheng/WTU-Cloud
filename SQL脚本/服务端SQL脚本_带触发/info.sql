/*
 Navicat Premium Data Transfer

 Source Server         : mysql1
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : 127.0.0.1:3306
 Source Schema         : app_test_web

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 22/04/2020 16:00:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for info
-- ----------------------------
DROP TABLE IF EXISTS `info`;
CREATE TABLE `info`  (
  `_ID` int(11) NOT NULL AUTO_INCREMENT,
  `POST_ID` int(8) NOT NULL,
  `GIT_ID` int(8) NOT NULL,
  `INFOCLASS` int(5) NULL DEFAULT NULL,
  `VAR` varchar(1999) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `TIME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
