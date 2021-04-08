/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : 127.0.0.1:3306
 Source Schema         : blog_mybatisplus

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : 65001

 Date: 08/04/2021 10:04:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章类别id',
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题',
  `author` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '作者',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
  `version` int(11) NULL DEFAULT 1 COMMENT '乐观锁',
  `deleted` int(11) NULL DEFAULT 0 COMMENT '逻辑删除',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES (1, '1', '测试00', 'suaxi', '测试数据', 1, 0, '2021-04-07 10:33:39', NULL);
INSERT INTO `article` VALUES (2, '3', '测试01', 'suaxi', '测试数据', 0, 1, '2021-04-07 13:34:29', NULL);
INSERT INTO `article` VALUES (3, '1', '测试02', 'suaxi', '测试数据', 1, 0, '2021-04-07 13:40:08', '2021-04-07 13:57:31');
INSERT INTO `article` VALUES (4, '3', '测试03', 'suaxi', '测试数据', 1, 0, '2021-04-07 13:58:41', NULL);
INSERT INTO `article` VALUES (5, '4', '测试04', 'suaxi', '测试数据', 1, 0, '2021-04-07 14:07:46', NULL);
INSERT INTO `article` VALUES (6, '3', '测试05', 'suaxi', '测试数据', 1, 0, '2021-04-07 15:19:37', '2021-04-07 15:47:22');
INSERT INTO `article` VALUES (7, '4', '测试06', 'suaxi', '测试数据', 1, 0, '2021-04-06 16:04:25', '2021-04-07 16:08:16');
INSERT INTO `article` VALUES (8, '3', '测试07', 'a', '测试数据', 1, 0, '2021-04-06 16:04:25', NULL);
INSERT INTO `article` VALUES (9, '1', '测试08', 'b', '测试数据', 1, 0, '2021-04-07 17:14:32', NULL);
INSERT INTO `article` VALUES (10, '2', '测试09', 'c', '测试数据', 1, 0, '2021-04-07 17:14:52', NULL);
INSERT INTO `article` VALUES (11, '3', '测试10', 'd', '测试数据', 1, 0, '2021-04-07 17:15:05', NULL);
INSERT INTO `article` VALUES (12, '4', '测试011', 'e', '测试数据', 1, 0, '2021-04-07 17:15:22', NULL);
INSERT INTO `article` VALUES (13, '1', '测试012', 'f', '测试数据', 1, 0, '2021-04-07 17:15:36', NULL);
INSERT INTO `article` VALUES (14, '1', '测试013', 'g', '测试数据', 1, 0, '2021-04-07 17:26:27', NULL);
INSERT INTO `article` VALUES (15, '2', '测试014', 'h', '测试数据', 1, 0, '2021-04-07 17:26:45', '2021-04-08 09:31:56');
INSERT INTO `article` VALUES (16, '1', '测试015', 'suaxi', '测试数据', 1, 0, '2021-04-08 09:29:35', NULL);

SET FOREIGN_KEY_CHECKS = 1;
