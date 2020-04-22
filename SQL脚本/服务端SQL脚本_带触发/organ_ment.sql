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
