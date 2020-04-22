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

 Date: 22/04/2020 16:01:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for course_null
-- ----------------------------
DROP TABLE IF EXISTS `course_null`;
CREATE TABLE `course_null`  (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `UID` int(8) NOT NULL,
  `SID` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '未设置',
  `WEEK` int(3) NOT NULL DEFAULT 1 COMMENT '星期几',
  `AM1_2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0-0',
  `AM3_5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0-0',
  `PM1_2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0-0',
  `PM3_4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0-0',
  `NIGHT` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0-0',
  PRIMARY KEY (`_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Triggers structure for table course_null
-- ----------------------------
DROP TRIGGER IF EXISTS `addNull`;
delimiter ;;
CREATE TRIGGER `addNull` AFTER INSERT ON `course_null` FOR EACH ROW BEGIN
UPDATE user_info SET user_info.ISKCB=201 WHERE user_info.UID=new.UID;
UPDATE user_info SET user_info.KCBTIME=(select now()) WHERE user_info.uid=new.uid;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table course_null
-- ----------------------------
DROP TRIGGER IF EXISTS `upNull`;
delimiter ;;
CREATE TRIGGER `upNull` AFTER UPDATE ON `course_null` FOR EACH ROW BEGIN
UPDATE user_info SET user_info.ISKCB=201 WHERE user_info.UID=new.UID;
UPDATE user_info SET user_info.KCBTIME=(select now()) WHERE user_info.uid=new.uid;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table course_null
-- ----------------------------
DROP TRIGGER IF EXISTS `delNull`;
delimiter ;;
CREATE TRIGGER `delNull` AFTER DELETE ON `course_null` FOR EACH ROW BEGIN
UPDATE user_info SET user_info.ISKCB=101 WHERE user_info.UID=old.UID;
UPDATE user_info SET user_info.KCBTIME=(select now()) WHERE user_info.uid=old.uid;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
