/*
 Navicat MySQL Data Transfer

 Source Server         : 本地Mysql
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : localhost
 Source Database       : game

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : utf-8

 Date: 06/08/2019 13:30:43 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `activity_info`
-- ----------------------------
DROP TABLE IF EXISTS `activity_info`;
CREATE TABLE `activity_info` (
  `activity_id` varchar(255) NOT NULL,
  `activity_name` varchar(255) DEFAULT NULL,
  `activity_describe` varchar(255) DEFAULT NULL,
  `activity_icon` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`activity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `botany_base`
-- ----------------------------
DROP TABLE IF EXISTS `botany_base`;
CREATE TABLE `botany_base` (
  `botany_id` varchar(255) NOT NULL,
  `botany_name` varchar(255) DEFAULT NULL,
  `botany_icon` varchar(255) DEFAULT NULL,
  `botany_num` int(11) DEFAULT NULL,
  `botany_description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`botany_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `botany_base`
-- ----------------------------
BEGIN;
INSERT INTO `botany_base` VALUES ('1559887731124394872', '美人蕉', 'https://s2.ax1x.com/2019/06/07/V0iYlV.png', '96', '亚热带和热带常用的观花植物。喜温暖和充足的阳光，不耐寒。对土壤要求不严，在疏松肥沃、排水良好的沙土壤中生长最佳，也适应于肥沃粘质土壤生长。'), ('1559895146331340768', '五彩多肉', 'https://s2.ax1x.com/2019/06/07/V0iEWt.png', '199', '多肉是绿植领养的老朋友啦！它们生命顽强，外形可爱，深受大家欢迎。多肉植物都喜欢生长在半阴的环境中。照料它们时，要注意不能暴晒在阳光下，一周浇水1～2次即可。'), ('1559895414899956393', '佛甲草', 'https://s2.ax1x.com/2019/06/07/V0i4kd.png', '49', '佛甲草去年一推出就广受好评，因为它真的很好养。一株小小的枝蔓就长出了一大盆，小编都惊呆了！关键是它的颜值还高，碧绿的小叶子宛若翡翠，你心动了吗？'), ('1559895555708617407', '太阳花', 'https://s2.ax1x.com/2019/06/07/V0ivkj.png', '98', '露地播种, 当气温20℃以上时种子萌发, 播后10天左右发芽。覆土宜薄, 不盖土亦能生长。');
COMMIT;

-- ----------------------------
--  Table structure for `botany_info`
-- ----------------------------
DROP TABLE IF EXISTS `botany_info`;
CREATE TABLE `botany_info` (
  `adopt_id` varchar(255) NOT NULL,
  `adopt_user` varchar(255) DEFAULT NULL,
  `adopt_botany` varchar(255) DEFAULT NULL,
  `adopt_create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`adopt_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `help_info`
-- ----------------------------
DROP TABLE IF EXISTS `help_info`;
CREATE TABLE `help_info` (
  `help_id` varchar(255) NOT NULL,
  `help_title` varchar(255) DEFAULT NULL,
  `help_describe` varchar(255) DEFAULT NULL,
  `help_icon` varchar(255) DEFAULT NULL,
  `help_type` int(11) DEFAULT NULL COMMENT '1、寻求帮扶  2、提供帮扶',
  PRIMARY KEY (`help_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `msg_queue`
-- ----------------------------
DROP TABLE IF EXISTS `msg_queue`;
CREATE TABLE `msg_queue` (
  `msg_id` varchar(255) NOT NULL,
  `msg_msg` varchar(255) DEFAULT NULL COMMENT '消息内容',
  `msg_from` varchar(50) DEFAULT NULL COMMENT '谁发的？userId',
  `msg_to` varchar(50) DEFAULT NULL COMMENT '发给谁？userId',
  `msg_next` varchar(255) DEFAULT NULL COMMENT '对话上一条消息Id',
  `msg_read` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`msg_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `order_detail`
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail` (
  `detail_id` varchar(32) NOT NULL,
  `order_id` varchar(32) NOT NULL,
  `product_id` varchar(32) NOT NULL,
  `product_num` int(11) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`detail_id`),
  KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `order_detail`
-- ----------------------------
BEGIN;
INSERT INTO `order_detail` VALUES ('1559971202258270368', '1559971202249369960', '1559286382724', '1', '2019-06-08 13:20:02', '2019-06-08 13:20:02'), ('1559971202268188306', '1559971202249369960', '1559286845345', '1', '2019-06-08 13:20:02', '2019-06-08 13:20:02');
COMMIT;

-- ----------------------------
--  Table structure for `order_master`
-- ----------------------------
DROP TABLE IF EXISTS `order_master`;
CREATE TABLE `order_master` (
  `order_id` varchar(50) NOT NULL,
  `buyer_id` varchar(32) NOT NULL COMMENT '买家Id',
  `order_amount` decimal(8,2) NOT NULL COMMENT '订单总金额',
  `order_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '订单状态, 默认为新下单',
  `pay_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '支付状态, 默认未支付',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `order_master`
-- ----------------------------
BEGIN;
INSERT INTO `order_master` VALUES ('1559971202249369960', 'ahojcn', '4.50', '0', '1', '2019-06-08 13:20:02', '2019-06-08 13:20:52');
COMMIT;

-- ----------------------------
--  Table structure for `pick_info`
-- ----------------------------
DROP TABLE IF EXISTS `pick_info`;
CREATE TABLE `pick_info` (
  `pick_id` varchar(255) NOT NULL,
  `pick_user` varchar(255) DEFAULT NULL,
  `pick_product` varchar(255) DEFAULT NULL,
  `pick_time` timestamp NULL DEFAULT NULL,
  `pick_check` int(11) DEFAULT NULL COMMENT '审核是否通过',
  PRIMARY KEY (`pick_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `practice_base`
-- ----------------------------
DROP TABLE IF EXISTS `practice_base`;
CREATE TABLE `practice_base` (
  `base_id` varchar(255) NOT NULL,
  `base_name` varchar(255) DEFAULT NULL,
  `base_address` varchar(255) DEFAULT NULL,
  `base_description` varchar(255) DEFAULT NULL,
  `base_join` int(11) DEFAULT NULL,
  `base_maxpeople` int(11) DEFAULT NULL,
  `base_start` timestamp NULL DEFAULT NULL,
  `base_end` timestamp NULL DEFAULT NULL,
  `base_icon` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`base_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `practice_base`
-- ----------------------------
BEGIN;
INSERT INTO `practice_base` VALUES ('1559837078331742182', '西安创新实践基地', '陕西省西安市', '创新实践基地是由西安某大学学生工作处实施和运作的以学生为中心，培养和提高学生创新实践能力为主要目的专门场所。', '14', '200', '2019-06-05 14:25:23', '2019-06-05 20:25:23', 'https://s2.ax1x.com/2019/06/06/VdXRk8.png'), ('1559893870401785606', '增加实践基地信息', '增加实践基地信息', '增加实践基地信息增加实践基地信息增加实践基地信息增加实践基地信息增加实践基地信息', '5', '5', '2019-06-07 02:50:49', '2019-06-07 02:50:49', 'https://s2.ax1x.com/2019/06/07/V0ZrfH.png');
COMMIT;

-- ----------------------------
--  Table structure for `practice_info`
-- ----------------------------
DROP TABLE IF EXISTS `practice_info`;
CREATE TABLE `practice_info` (
  `practice_id` varchar(255) NOT NULL,
  `practice_user` varchar(255) DEFAULT NULL,
  `practice_base` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`practice_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `practice_info`
-- ----------------------------
BEGIN;
INSERT INTO `practice_info` VALUES ('1559971386501982650', 'ahojcn', '1559837078331742182');
COMMIT;

-- ----------------------------
--  Table structure for `product_category`
-- ----------------------------
DROP TABLE IF EXISTS `product_category`;
CREATE TABLE `product_category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(64) NOT NULL COMMENT '类目名字',
  `category_type` int(11) NOT NULL COMMENT '类目编号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `click_num` int(11) DEFAULT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `product_category`
-- ----------------------------
BEGIN;
INSERT INTO `product_category` VALUES ('1', '蔬菜', '0', '2019-05-23 09:02:49', '2019-06-08 13:25:51', '6'), ('2', '水果', '1', '2019-05-23 09:02:49', '2019-06-08 13:26:52', '5'), ('3', '花草', '2', '2019-05-25 11:24:46', '2019-06-08 13:26:23', '10'), ('4', '家禽', '3', '2019-05-25 11:17:28', '2019-06-08 13:26:25', '13');
COMMIT;

-- ----------------------------
--  Table structure for `product_info`
-- ----------------------------
DROP TABLE IF EXISTS `product_info`;
CREATE TABLE `product_info` (
  `product_id` varchar(32) NOT NULL,
  `product_name` varchar(64) NOT NULL COMMENT '商品名称',
  `product_price` decimal(8,2) NOT NULL COMMENT '单价',
  `product_stock` int(11) NOT NULL COMMENT '库存',
  `product_description` varchar(512) DEFAULT NULL COMMENT '简介',
  `product_icon` varchar(100) DEFAULT NULL COMMENT '小图',
  `category_type` int(11) NOT NULL COMMENT '类目编号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `product_grade` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `product_info`
-- ----------------------------
BEGIN;
INSERT INTO `product_info` VALUES ('1558575338519', '康乃馨', '3.50', '500', '叶片线状披针形，顶端长渐尖，基部稍成短鞘，中脉明显，上面下凹，下面稍凸起。花常单生枝端有香气，粉红、紫红或白色。', 'https://s2.ax1x.com/2019/05/31/VlwDcn.png', '2', '2019-05-23 09:35:38', '2019-06-03 17:08:24', '4'), ('1558575338525', '风信子', '3.00', '2000', '原产地中海沿岸及小亚细亚一带，是研究发现的会开花的植物中最香的一个品种。喜阳光充足和比较湿润的生长环境，要求排水良好和肥沃的沙壤土等。', 'https://s2.ax1x.com/2019/05/31/Vlw439.png', '2', '2019-05-23 09:35:38', '2019-06-03 17:08:26', '4'), ('1558575338537', '农家乌鸡', '120.00', '100', '放在油里面煎一下，然后加我在里面做出来的味道才会特别的好，可以放一些土豆，也可以在里面多加上一些金针菇，做出来的味道也是非常的好吃哦。', 'https://s2.ax1x.com/2019/05/31/VlwqAO.png', '3', '2019-05-23 09:35:38', '2019-06-03 17:08:30', '2'), ('1559286281552', '西红柿', '2.50', '1000', '是茄科番茄属中以成熟多汁浆果为产品的草木植物。我国栽培的番茄是从国外引种的，果实多为红色，样子像柿子，故俗称西红柿', 'https://s2.ax1x.com/2019/06/08/VBRC1f.png', '0', '2019-05-31 15:04:41', '2019-06-08 13:14:05', '3'), ('1559286382724', '海南香蕉', '3.00', '500', '芭蕉科芭蕉属植物，又指其果实，热带地区广泛种植。', 'https://s2.ax1x.com/2019/06/08/VB6b6g.png', '1', '2019-05-31 15:06:22', '2019-06-08 13:07:32', '4'), ('1559286845345', '醇香鸭梨', '1.50', '100', '通常品种是一种落叶乔木或灌木，极少数品种为常绿，属于被子植物门双子叶植物纲蔷薇科苹果亚科。', 'https://s2.ax1x.com/2019/06/08/VB6wwR.png', '1', '2019-05-31 15:14:05', '2019-06-08 12:56:14', '4'), ('1559286970343', '家养野猪', '350.00', '50', '野猪肉是指肉经腌制后再经过烘烤（或日光下曝晒）的过程所制成的加工品。腊肉的防腐能力强。', 'https://s2.ax1x.com/2019/05/31/VlDm7V.png', '3', '2019-05-31 15:16:10', '2019-06-03 17:08:55', '4'), ('1559287485289', '淡水鸭子', '60.00', '50', '喙扁而宽，後趾无蹼。高漂水上，飞行迅速，大声扑跳然后转为平飞。密集成群。分布全世界', 'https://s2.ax1x.com/2019/05/31/VlDA6s.png', '3', '2019-05-31 15:24:45', '2019-06-03 17:08:57', '5'), ('1559695661149', '紫藤萝', '2.00', '1024', '优良的观花藤木植物，缠绕茎，羽状复叶，小叶长椭圆形。总状花序，春季开花，花淡紫色，具有优美的姿态和迷人的风采。', 'https://s2.ax1x.com/2019/05/31/VldOln.png', '2', '2019-06-05 08:47:41', '2019-06-05 08:47:41', '4'), ('1559969582265', '水晶葡萄', '4.50', '100', '现摘云南深山无籽夏黑葡萄3斤装特级果 非黑加仑提子，新鲜水果，特级果', 'https://s2.ax1x.com/2019/06/08/VBy2aq.png', '1', '2019-06-08 12:54:11', '2019-06-08 13:00:26', '3'), ('1559970251789', '金钻凤梨', '20.00', '2000', '坏果包赔台湾金钻凤梨精品2个装，净重约5斤 无眼菠萝手撕凤梨 非泰国香水菠萝 新鲜水果 2个装', 'https://s2.ax1x.com/2019/06/08/VBcR3T.png', '1', '2019-06-08 13:05:59', '2019-06-08 13:08:07', '5'), ('1559970686466', '胡萝卜', '1.50', '2000', '西红柿', 'https://i.loli.net/2019/06/08/5cfb437a9748496811.png', '0', '2019-06-08 13:11:26', '2019-06-08 13:11:26', '5'), ('1559970976123', '白萝卜', '4.40', '500', '白萝卜', 'https://s2.ax1x.com/2019/06/08/VBRL80.png', '0', '2019-06-08 13:16:33', '2019-06-08 13:16:39', '4');
COMMIT;

-- ----------------------------
--  Table structure for `shopping_cart`
-- ----------------------------
DROP TABLE IF EXISTS `shopping_cart`;
CREATE TABLE `shopping_cart` (
  `shopping_id` varchar(255) NOT NULL,
  `user_id` varchar(255) NOT NULL COMMENT '用户Id',
  `product_id` varchar(255) NOT NULL COMMENT '商品Id',
  `product_num` int(11) NOT NULL DEFAULT '1' COMMENT '商品数量'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `shopping_cart`
-- ----------------------------
BEGIN;
INSERT INTO `shopping_cart` VALUES ('1559971347300', 'ahojcn', '1559286382724', '1'), ('1559971350896', 'ahojcn', '1559286845345', '1');
COMMIT;

-- ----------------------------
--  Table structure for `user_info`
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `user_id` varchar(255) NOT NULL,
  `user_password` varchar(255) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `user_address` varchar(255) NOT NULL,
  `user_phone` varchar(255) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `user_icon` varchar(255) DEFAULT NULL,
  `user_isman` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `user_info`
-- ----------------------------
BEGIN;
INSERT INTO `user_info` VALUES ('00000', '123456', '管理员', '管理员地址', '15291418231', '2019-05-25 11:24:46', '2019-05-31 15:36:08', 'https://s2.ax1x.com/2019/05/31/VQxJZn.png', '0'), ('00001', '123456', '客户1', '西安工程大学', '13772812803', '2019-05-31 13:38:29', '2019-05-31 15:35:52', 'https://s2.ax1x.com/2019/05/31/VQxJZn.png', '1'), ('00002', '123456', '客户2', '西安工程大学', '13772812804', '2019-05-16 15:33:34', '2019-05-31 15:34:47', 'https://s2.ax1x.com/2019/05/31/VQxJZn.png', '1'), ('ahojcn', '1Liujingliang', 'ahojcn', '陕西省神木市', '13891267906', '2019-06-04 15:39:03', '2019-06-04 15:39:03', 'https://i.loli.net/2019/06/04/5cf61ffe93f4069938.png', '1');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
