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

 Date: 22/04/2020 17:20:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for organ
-- ----------------------------
DROP TABLE IF EXISTS `organ`;
CREATE TABLE `organ`  (
  `OID` int(5) UNSIGNED NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `CAMPUS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '阳光校区',
  `COLLEGE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '校级',
  `MENTSUM` int(8) NOT NULL DEFAULT 0,
  `PEOSUM` int(8) NOT NULL DEFAULT 0,
  `ADMIN` int(8) NULL DEFAULT NULL,
  PRIMARY KEY (`OID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
