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

 Date: 22/04/2020 16:00:33
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
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
