/*
 Navicat Premium Data Transfer

 Source Server         : 阿里云RDS
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : sql.hll520.cn:3306
 Source Schema         : wtucloud_test

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 24/04/2020 19:41:53
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
  `READSUM` int(2) NOT NULL DEFAULT 0,
  PRIMARY KEY (`UID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

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
