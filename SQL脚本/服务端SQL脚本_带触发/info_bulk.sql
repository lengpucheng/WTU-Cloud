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

 Date: 22/04/2020 16:00:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for info_bulk
-- ----------------------------
DROP TABLE IF EXISTS `info_bulk`;
CREATE TABLE `info_bulk`  (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `BULKCLASS` int(5) NOT NULL DEFAULT 101 COMMENT '群发类型',
  `VAR` varchar(999) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ISURL` int(5) NOT NULL DEFAULT 101 COMMENT '是否有链接',
  `URL` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'http://wtu.edu.cn',
  `SENDTIME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `ENDTIME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
