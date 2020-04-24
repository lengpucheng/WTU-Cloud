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

 Date: 22/04/2020 16:01:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for app_system
-- ----------------------------
DROP TABLE IF EXISTS `app_system`;
CREATE TABLE `app_system`  (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `ID` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `VAR` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;



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

 Date: 22/04/2020 19:02:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `UID` int(8) UNSIGNED NOT NULL AUTO_INCREMENT,
  `UNAME` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `SID` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学号',
  `PASSWORD` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `SAFETY` int(4) NOT NULL DEFAULT 0 COMMENT '权限',
  `PASSINFO` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密保',
  `PASSKEY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '答案',
  `REGTIME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '注册时间',
  `LOGINTIME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '最后一次登录',
  `LOGIN` int(2) NOT NULL DEFAULT 0 COMMENT '状态1在线，0未登录',
  `READ` int(2) NOT NULL DEFAULT 0,
  PRIMARY KEY (`UID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Triggers structure for table user
-- ----------------------------
DROP TRIGGER IF EXISTS `addUSER`;
delimiter ;;
CREATE TRIGGER `addUSER` AFTER INSERT ON `user` FOR EACH ROW BEGIN
INSERT INTO user_info(UID,NAME) VALUES(new.UID,new.uname);
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table user
-- ----------------------------
DROP TRIGGER IF EXISTS `delUser`;
delimiter ;;
CREATE TRIGGER `delUser` AFTER DELETE ON `user` FOR EACH ROW BEGIN
DELETE FROM user_info WHERE user_info.uid=old.uid;
DELETE FROM organ_peo WHERE organ_peo.uid=old.uid;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
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

 Date: 22/04/2020 16:40:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `UID` int(8) NOT NULL,
  `SID` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `IMG` varchar(999) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '101',
  `NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '未设置',
  `GENDER` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '男',
  `BIRTHDAY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '1999-01-01',
  `PHONE` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '+86-0',
  `QQ` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '未设置',
  `EMAIL` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '未设置',
  `CAMPUS` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '阳光校区',
  `COLLEG` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '保密',
  `CLAS` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '保密' COMMENT '班级',
  `HOUSE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `MAINORG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '要显组织',
  `MAINMENT` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '主显部门',
  `MAINPOSITION` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '主显职务',
  `ISKCB` int(4) NOT NULL DEFAULT 101 COMMENT '是否上传空课表',
  `KCBTIME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '未上传' COMMENT '空课表上传时间',
  `IDENTITY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '政治面貌',
  `ADDRESS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地址',
  `IDCARD` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '身份证',
  `BANKCARD` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '工行卡',
  PRIMARY KEY (`UID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Triggers structure for table user_info
-- ----------------------------
DROP TRIGGER IF EXISTS `upSid`;
delimiter ;;
CREATE TRIGGER `upSid` AFTER UPDATE ON `user_info` FOR EACH ROW BEGIN
UPDATE user SET user.SID=new.SID  WHERE user.UID=new.UID;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
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

 Date: 22/04/2020 17:20:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for organ_ment
-- ----------------------------
DROP TABLE IF EXISTS `organ_ment`;
CREATE TABLE `organ_ment`  (
  `MID` int(6) UNSIGNED NOT NULL AUTO_INCREMENT,
  `OID` int(5) NOT NULL,
  `MENTNAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `PEOSUM` int(8) NOT NULL DEFAULT 0,
  `INVITCODE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`MID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Triggers structure for table organ_ment
-- ----------------------------
DROP TRIGGER IF EXISTS `addMent`;
delimiter ;;
CREATE TRIGGER `addMent` AFTER INSERT ON `organ_ment` FOR EACH ROW BEGIN
UPDATE organ SET organ.MENTSUM=(SELECT COUNT(*) FROM organ_ment WHERE organ_ment.OID=new.OID) WHERE organ.OID=new.OID;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table organ_ment
-- ----------------------------
DROP TRIGGER IF EXISTS `delMent`;
delimiter ;;
CREATE TRIGGER `delMent` AFTER DELETE ON `organ_ment` FOR EACH ROW BEGIN
UPDATE organ SET organ.MENTSUM=(SELECT COUNT(*) FROM organ_ment WHERE organ_ment.OID=old.OID) WHERE organ.OID=old.Oid;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
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

 Date: 22/04/2020 19:02:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for organ_peo
-- ----------------------------
DROP TABLE IF EXISTS `organ_peo`;
CREATE TABLE `organ_peo`  (
  `OID` int(5) NOT NULL,
  `MID` int(6) NOT NULL,
  `UID` int(8) NOT NULL,
  `SAFETY` int(5) NOT NULL DEFAULT 101,
  `NUMBER` int(11) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Triggers structure for table organ_peo
-- ----------------------------
DROP TRIGGER IF EXISTS `addPeo`;
delimiter ;;
CREATE TRIGGER `addPeo` AFTER INSERT ON `organ_peo` FOR EACH ROW BEGIN
UPDATE organ SET organ.PEOSUM=(SELECT COUNT(*) FROM organ_peo WHERE organ_peo.OID=new.oid ) WHERE organ.OID=new.OID;
UPDATE organ_ment SET organ_ment.PEOSUM=(SELECT COUNT(*) FROM organ_peo WHERE organ_peo.MID=new.MID) WHERE organ_ment.MID=new.MID;
UPDATE user_info SET user_info.MAINORG=(SELECT organ.NAME FROM organ WHERE OID=new.oid) WHERE user_info.UID=new.uid;
UPDATE user_info SET user_info.MAINMENT=(SELECT organ_ment.MENTNAME FROM organ_ment WHERE MID=new.MID) WHERE user_info.UID=new.uid;
UPDATE user SET user.SAFETY=new.SAFETY WHERE user.UID=new.uid;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table organ_peo
-- ----------------------------
DROP TRIGGER IF EXISTS `upPeo`;
delimiter ;;
CREATE TRIGGER `upPeo` AFTER UPDATE ON `organ_peo` FOR EACH ROW BEGIN
UPDATE organ SET organ.PEOSUM=(SELECT COUNT(*) FROM organ_peo WHERE organ_peo.OID=new.oid ) WHERE organ.OID=new.OID;
UPDATE organ_ment SET organ_ment.PEOSUM=(SELECT COUNT(*) FROM organ_peo WHERE organ_peo.MID=new.MID) WHERE organ_ment.MID=new.MID;
UPDATE user_info SET user_info.MAINORG=(SELECT organ.NAME FROM organ WHERE OID=new.oid) WHERE user_info.UID=new.uid;
UPDATE user_info SET user_info.MAINMENT=(SELECT organ_ment.MENTNAME FROM organ_ment WHERE MID=new.MID) WHERE user_info.UID=new.uid;
UPDATE user SET user.SAFETY=new.SAFETY WHERE user.UID=new.uid;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table organ_peo
-- ----------------------------
DROP TRIGGER IF EXISTS `delPeo`;
delimiter ;;
CREATE TRIGGER `delPeo` AFTER DELETE ON `organ_peo` FOR EACH ROW BEGIN
UPDATE organ SET organ.PEOSUM=(SELECT COUNT(*) FROM organ_peo WHERE organ_peo.OID=old.oid ) WHERE organ.OID=old.OID;
UPDATE organ_ment SET organ_ment.PEOSUM=(SELECT COUNT(*) FROM organ_peo WHERE organ_peo.MID=old.MID) WHERE organ_ment.MID=old.MID;
UPDATE user_info SET user_info.MAINORG='无' WHERE user_info.UID=old.uid;
UPDATE user_info SET user_info.MAINMENT='无' WHERE user_info.UID=old.uid;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
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
