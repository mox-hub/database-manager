/*
 Navicat Premium Data Transfer

 Source Server         : findplatform
 Source Server Type    : MySQL
 Source Server Version : 80013
 Source Host           : localhost:3306
 Source Schema         : graduate

 Target Server Type    : MySQL
 Target Server Version : 80013
 File Encoding         : 65001

 Date: 04/07/2022 18:56:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `admin_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `admin_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`admin_name`) USING BTREE,
  INDEX `admin_password`(`admin_password` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('admin', '123456');

-- ----------------------------
-- Table structure for company
-- ----------------------------
DROP TABLE IF EXISTS `company`;
CREATE TABLE `company`  (
  `company_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `company_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `contact_details` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `detailed_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`company_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of company
-- ----------------------------
INSERT INTO `company` VALUES ('0001', '阿里巴巴', 'www.1688.com', '浙江省/杭州市/市辖区', '杭州市余杭区文一西路969号');
INSERT INTO `company` VALUES ('222', '222', '222', '河北省/唐山市/路南区', '222');
INSERT INTO `company` VALUES ('333', '333', '333', '黑龙江省/齐齐哈尔市/龙沙区', '333');

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department`  (
  `department_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `department_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `department_info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `department_tel` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`department_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES ('001', '计算机与信息学院', '合肥工业大学计算机与信息学院', '12345678900');
INSERT INTO `department` VALUES ('002', '电气与自动化工程学院', '合肥工业大学电气与自动化工程学院', '12345678901');

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`  (
  `graduate_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `reg_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`reg_id`, `graduate_id`) USING BTREE,
  INDEX `employee_job_id`(`graduate_id` ASC) USING BTREE,
  CONSTRAINT `employee_graduate_id` FOREIGN KEY (`graduate_id`) REFERENCES `graduate` (`graduate_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `reg_id` FOREIGN KEY (`reg_id`) REFERENCES `registration` (`registration_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES ('2018218201', '10001234');
INSERT INTO `employee` VALUES ('2018218201', '10001235');
INSERT INTO `employee` VALUES ('2019218200', '10001236');
INSERT INTO `employee` VALUES ('2019218211', '10001234');
INSERT INTO `employee` VALUES ('2019218211', '10001235');
INSERT INTO `employee` VALUES ('2019218213', '10001234');
INSERT INTO `employee` VALUES ('2019218219', '10001236');
INSERT INTO `employee` VALUES ('2019218228', '10001235');
INSERT INTO `employee` VALUES ('2019218236', '10001234');

-- ----------------------------
-- Table structure for graduate
-- ----------------------------
DROP TABLE IF EXISTS `graduate`;
CREATE TABLE `graduate`  (
  `graduate_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `graduate_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `graduate_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `sex` enum('男','女') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` enum('就业','待业') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '待业',
  `grade` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `department_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `professional_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`graduate_id`, `department_id`, `professional_id`) USING BTREE,
  INDEX `depart_id`(`department_id` ASC) USING BTREE,
  INDEX `Professional_id`(`professional_id` ASC) USING BTREE,
  INDEX `graduate_id`(`graduate_id` ASC) USING BTREE,
  CONSTRAINT `department_id` FOREIGN KEY (`department_id`) REFERENCES `department` (`department_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `professional_id` FOREIGN KEY (`professional_id`) REFERENCES `professional` (`professional_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of graduate
-- ----------------------------
INSERT INTO `graduate` VALUES ('2018218201', '张三三', '2018218202', '男', '就业', '2016', '001', '001001');
INSERT INTO `graduate` VALUES ('2019218200', '李四', '2019218200', '女', '待业', '2019', '001', '001002');
INSERT INTO `graduate` VALUES ('2019218211', '张三', '2019218211', '男', '待业', '2019', '001', '001001');
INSERT INTO `graduate` VALUES ('2019218211', '秦嘉伦', '2019218211', '男', '就业', '2016', '001', '001002');
INSERT INTO `graduate` VALUES ('2019218212', '严致远', '2018218201', '男', '待业', '2011', '001', '001001');
INSERT INTO `graduate` VALUES ('2019218213', '何宇宁', '2019218200', '女', '就业', '2019', '001', '001001');
INSERT INTO `graduate` VALUES ('2019218215', '萧子韬', 'UuW7XJ77eG', '女', '待业', '2017', '001', '001002');
INSERT INTO `graduate` VALUES ('2019218216', '蒋璐', 'Czc5F2aqVt', '男', '就业', '2012', '001', '001001');
INSERT INTO `graduate` VALUES ('2019218217', '邵睿', '3v2PGaPDkI', '男', '就业', '2010', '001', '001002');
INSERT INTO `graduate` VALUES ('2019218218', '罗晓明', 'mRnOrpiT4i', '女', '待业', '2013', '001', '001001');
INSERT INTO `graduate` VALUES ('2019218219', '石璐', 'aBGszirBlU', '男', '就业', '2016', '001', '001002');
INSERT INTO `graduate` VALUES ('2019218220', '熊璐', '1fUQKlAhe2', '男', '待业', '2019', '001', '001002');
INSERT INTO `graduate` VALUES ('2019218221', '杨杰宏', 'CQXFOCCq2d', '女', '待业', '2019', '001', '001001');
INSERT INTO `graduate` VALUES ('2019218222', '程安琪', 'pQUUlTkybo', '女', '待业', '2015', '001', '001002');
INSERT INTO `graduate` VALUES ('2019218223', '潘致远', 'AmPedc8gmi', '男', '就业', '2013', '001', '001002');
INSERT INTO `graduate` VALUES ('2019218224', '吴子韬', 'o9EUZEJgkg', '女', '就业', '2014', '001', '001002');
INSERT INTO `graduate` VALUES ('2019218225', '谭致远', 'Z1R16PM8T9', '女', '就业', '2015', '001', '001001');
INSERT INTO `graduate` VALUES ('2019218226', '顾杰宏', '0X51CXEdTy', '女', '就业', '2017', '001', '001002');
INSERT INTO `graduate` VALUES ('2019218227', '邵睿', 'LP6QurH8Pl', '男', '待业', '2019', '001', '001002');
INSERT INTO `graduate` VALUES ('2019218228', '吴子韬', 'bQNAMqUKNo', '男', '就业', '2010', '001', '001002');
INSERT INTO `graduate` VALUES ('2019218229', '魏安琪', 'QNZHQjb1q9', '男', '就业', '2019', '001', '001002');
INSERT INTO `graduate` VALUES ('2019218230', '董杰宏', 'I4jBVnX8bv', '男', '待业', '2017', '001', '001002');
INSERT INTO `graduate` VALUES ('2019218231', '徐云熙', 'f7Bwms2qy3', '男', '待业', '2016', '001', '001002');
INSERT INTO `graduate` VALUES ('2019218232', '杜安琪', 'csVXurPHQ6', '男', '待业', '2010', '001', '001001');
INSERT INTO `graduate` VALUES ('2019218233', '顾嘉伦', '8tg2FBUb5a', '女', '待业', '2011', '001', '001001');
INSERT INTO `graduate` VALUES ('2019218234', '钟震南', 'NqqjBD511f', '女', '就业', '2011', '001', '001002');
INSERT INTO `graduate` VALUES ('2019218235', '沈杰宏', '0WYmIzbk7H', '女', '待业', '2012', '001', '001002');
INSERT INTO `graduate` VALUES ('2019218236', '程睿', 'y900xSyv5H', '男', '就业', '2018', '001', '001002');
INSERT INTO `graduate` VALUES ('2019218237', '任秀英', 'xb0SgbeFhw', '男', '待业', '2019', '001', '001001');
INSERT INTO `graduate` VALUES ('2019218238', '廖晓明', '3dRboXUhg0', '女', '待业', '2019', '001', '001001');
INSERT INTO `graduate` VALUES ('2019218239', '薛致远', 'ElslI7iJfF', '女', '待业', '2013', '001', '001001');
INSERT INTO `graduate` VALUES ('2019218240', '宋云熙', 'M9MhXi766m', '女', '就业', '2013', '001', '001001');
INSERT INTO `graduate` VALUES ('2019218241', '曹嘉伦', 'Fg8zC9sFdH', '男', '就业', '2012', '001', '001002');
INSERT INTO `graduate` VALUES ('2019218242', '钟杰宏', 'qpfBjgpwrf', '男', '就业', '2012', '001', '001002');
INSERT INTO `graduate` VALUES ('2019218243', '江晓明', 'AvrfqH2iJb', '女', '就业', '2013', '001', '001001');
INSERT INTO `graduate` VALUES ('2019218244', '郝嘉伦', 'IY5qsu3z0W', '女', '就业', '2018', '001', '001001');
INSERT INTO `graduate` VALUES ('2019218245', '张晓明', 'NoioU3suh8', '男', '待业', '2016', '001', '001001');
INSERT INTO `graduate` VALUES ('2019218246', '朱子异', 'W8pdV4EmHN', '男', '就业', '2017', '001', '001001');
INSERT INTO `graduate` VALUES ('2019218247', '汤詩涵', 'xIHUwJHduY', '男', '待业', '2015', '001', '001002');
INSERT INTO `graduate` VALUES ('2019218248', '尹致远', 'yL1sKpunrC', '女', '就业', '2013', '001', '001002');
INSERT INTO `graduate` VALUES ('2019218249', '赵睿', 'mwvahdkZQx', '男', '就业', '2010', '001', '001002');
INSERT INTO `graduate` VALUES ('2019218250', '陈睿', 'OLp4BZgkPk', '女', '待业', '2013', '001', '001002');
INSERT INTO `graduate` VALUES ('2019218251', '石嘉伦', 'FxYWoid7NR', '女', '待业', '2015', '001', '001001');
INSERT INTO `graduate` VALUES ('2019218252', '陶晓明', 'EtGDXSiCEN', '女', '就业', '2017', '001', '001002');
INSERT INTO `graduate` VALUES ('2019218253', '叶嘉伦', 'KxUqusgljZ', '男', '就业', '2014', '001', '001001');
INSERT INTO `graduate` VALUES ('2019218254', '王杰宏', '88sf27SDON', '男', '就业', '2012', '001', '001001');
INSERT INTO `graduate` VALUES ('2019218255', '梁杰宏', 'RfhCIAMBoo', '男', '待业', '2017', '001', '001002');
INSERT INTO `graduate` VALUES ('2019218256', '尹璐', 'd6cpDUxhR3', '男', '就业', '2014', '001', '001002');
INSERT INTO `graduate` VALUES ('2019218257', '孟宇宁', 'sevfZXjZZ9', '男', '待业', '2017', '001', '001001');
INSERT INTO `graduate` VALUES ('2019218258', '孔宇宁', 'SIwOpcKOEq', '女', '待业', '2016', '001', '001001');
INSERT INTO `graduate` VALUES ('2019218259', '胡詩涵', '0zigWyzhPJ', '女', '待业', '2013', '001', '001002');
INSERT INTO `graduate` VALUES ('2019218260', '谢睿', '20N3HIkXjv', '男', '就业', '2013', '001', '001002');
INSERT INTO `graduate` VALUES ('2019218261', '石秀英', 'Upe0dMJHut', '男', '待业', '2013', '001', '001001');
INSERT INTO `graduate` VALUES ('2019218262', '周杰宏', 'xu8ft161lS', '男', '就业', '2019', '001', '001001');
INSERT INTO `graduate` VALUES ('2019218263', '熊子韬', 'mjStj7NZEA', '女', '待业', '2013', '001', '001002');
INSERT INTO `graduate` VALUES ('2019218264', '毛璐', 'EPocPWHeAm', '女', '待业', '2011', '001', '001001');

-- ----------------------------
-- Table structure for job
-- ----------------------------
DROP TABLE IF EXISTS `job`;
CREATE TABLE `job`  (
  `job_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `job_introduction` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `job_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`job_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of job
-- ----------------------------
INSERT INTO `job` VALUES ('0001', '软件架构', '软件架构师');
INSERT INTO `job` VALUES ('0002', '后端工程', '后端工程师');
INSERT INTO `job` VALUES ('0003', '前端工程', '前端工程师');
INSERT INTO `job` VALUES ('0004', '测试工程', '测试工程师');
INSERT INTO `job` VALUES ('0005', '策划', '策划');

-- ----------------------------
-- Table structure for professional
-- ----------------------------
DROP TABLE IF EXISTS `professional`;
CREATE TABLE `professional`  (
  `professional_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `professional_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `department_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `professional_info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `professional_tel` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`professional_id`, `department_id`) USING BTREE,
  INDEX `Professional_id`(`professional_id` ASC) USING BTREE,
  INDEX `depart_id`(`department_id` ASC) USING BTREE,
  CONSTRAINT `depart_id` FOREIGN KEY (`department_id`) REFERENCES `department` (`department_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of professional
-- ----------------------------
INSERT INTO `professional` VALUES ('001001', '计算机科学与技术', '001', '计算机科学与技术专业我的家', '12345678901');
INSERT INTO `professional` VALUES ('001002', '电子信息科学与技术', '001', '电子信息科学与技术专业', '12345678902');
INSERT INTO `professional` VALUES ('001003', '物联网', '001', '物联网专业', '12345678903');
INSERT INTO `professional` VALUES ('002001', '自动化', '002', '自动化专业', '12345678904');
INSERT INTO `professional` VALUES ('002002', '机器人工程', '002', '机器人工程专业', '12345678905');
INSERT INTO `professional` VALUES ('002003', '智能电网信息工程', '002', '智能电网信息工程', '12345678906');

-- ----------------------------
-- Table structure for registration
-- ----------------------------
DROP TABLE IF EXISTS `registration`;
CREATE TABLE `registration`  (
  `registration_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `job_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `company_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `required_num` int(11) NULL DEFAULT NULL,
  `hires_num` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`registration_id`, `job_id`, `company_id`) USING BTREE,
  INDEX `job_id`(`job_id` ASC) USING BTREE,
  INDEX `company_id`(`company_id` ASC) USING BTREE,
  INDEX `registration_id`(`registration_id` ASC) USING BTREE,
  CONSTRAINT `company_id` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `job_id` FOREIGN KEY (`job_id`) REFERENCES `job` (`job_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of registration
-- ----------------------------
INSERT INTO `registration` VALUES ('10001234', '0001', '0001', 50, 7);
INSERT INTO `registration` VALUES ('10001235', '0002', '0001', 50, 3);
INSERT INTO `registration` VALUES ('10001236', '0004', '222', 20, 20);

-- ----------------------------
-- Procedure structure for 4
-- ----------------------------
DROP PROCEDURE IF EXISTS `4`;
delimiter ;;
CREATE PROCEDURE `4`(out biye int ,out jiuye int,out daiye int ,out jiuyedata float)
BEGIN 

   	select count(*) as biye from graduate;
		select count(*) as jiuye from graduate where status = '就业';
		select count(*) as daiye from graduate where status = '待业';
		select sum(case when status='就业' then 1 else 0 end)/count(*) as jiuyedata from graduate;

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for 5
-- ----------------------------
DROP PROCEDURE IF EXISTS `5`;
delimiter ;;
CREATE PROCEDURE `5`(in depart CHAR(20), out derate float(4,3))
BEGIN
	select (select COUNT(status)from graduate
where status='就业' and graduate.department_id=depart
 )/(select COUNT(graduate_id)from graduate) into derate;
select derate;

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for 6
-- ----------------------------
DROP PROCEDURE IF EXISTS `6`;
delimiter ;;
CREATE PROCEDURE `6`(in profess CHAR(20), out prrate float(4,3))
BEGIN
	
	select (select COUNT(status)from graduate
where status='就业' and graduate.professional_id=profess
 )/(select COUNT(graduate_id)from graduate) into prrate;

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for profrate
-- ----------------------------
DROP PROCEDURE IF EXISTS `profrate`;
delimiter ;;
CREATE PROCEDURE `profrate`()
BEGIN
	
	select (select COUNT(status)from graduate
	where status='就业' and graduate.professional_id=a.professional_id
	)/(select COUNT(graduate_id)from graduate),professional_id
	from graduate a group by professional_id;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table employee
-- ----------------------------
DROP TRIGGER IF EXISTS `保证聘用数量不大于需求数量`;
delimiter ;;
CREATE TRIGGER `保证聘用数量不大于需求数量` BEFORE INSERT ON `employee` FOR EACH ROW begin
DECLARE c int ; 
DECLARE d int  ;
SELECT hires_num INTO c FROM registration where registration_id = new.reg_id ;
SELECT required_num INTO d FROM registration where registration_id = new.reg_id ;
if(c>=d)
then signal sqlstate 'HY000' set message_text = '聘用数量不能大于需求数量' ;
end if;
end
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table employee
-- ----------------------------
DROP TRIGGER IF EXISTS `修改聘用数目`;
delimiter ;;
CREATE TRIGGER `修改聘用数目` AFTER INSERT ON `employee` FOR EACH ROW begin
UPDATE registration 
set hires_num =  hires_num +1  where registration_id = new.reg_id ;
UPDATE graduate
set  `status` = 1 where new.graduate_id = graduate_id;
end
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
