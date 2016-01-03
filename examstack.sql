/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50617
Source Host           : localhost:3306
Source Database       : examstack

Target Server Type    : MYSQL
Target Server Version : 50617
File Encoding         : 65001

Date: 2015-12-11 23:47:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `et_comment`
-- ----------------------------
DROP TABLE IF EXISTS `et_comment`;
CREATE TABLE `et_comment` (
  `comment_id` int(10) NOT NULL AUTO_INCREMENT,
  `refer_id` int(10) NOT NULL,
  `comment_type` tinyint(1) NOT NULL DEFAULT '0',
  `index_id` int(10) NOT NULL,
  `user_id` int(10) NOT NULL,
  `content_msg` mediumtext NOT NULL,
  `quoto_id` int(10) NOT NULL DEFAULT '0',
  `re_id` int(10) NOT NULL DEFAULT '0',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`comment_id`),
  KEY `fk_u_id` (`user_id`),
  CONSTRAINT `et_comment_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `et_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of et_comment
-- ----------------------------

-- ----------------------------
-- Table structure for `et_department`
-- ----------------------------
DROP TABLE IF EXISTS `et_department`;
CREATE TABLE `et_department` (
  `dep_id` int(11) NOT NULL AUTO_INCREMENT,
  `dep_name` varchar(200) NOT NULL,
  `memo` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`dep_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of et_department
-- ----------------------------
INSERT INTO `et_department` VALUES ('1', '计划财务部', '计划财务部');

-- ----------------------------
-- Table structure for `et_exam`
-- ----------------------------
DROP TABLE IF EXISTS `et_exam`;
CREATE TABLE `et_exam` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `exam_name` varchar(100) NOT NULL,
  `exam_subscribe` varchar(500) DEFAULT NULL,
  `exam_type` tinyint(4) NOT NULL COMMENT '公有、私有、模拟考试',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `exam_paper_id` int(11) NOT NULL,
  `creator` int(11) DEFAULT NULL,
  `creator_id` varchar(50) DEFAULT NULL,
  `approved` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_exam_2pid` (`exam_paper_id`),
  CONSTRAINT `fk_exam_2pid` FOREIGN KEY (`exam_paper_id`) REFERENCES `et_exam_paper` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of et_exam
-- ----------------------------

-- ----------------------------
-- Table structure for `et_exam_2_paper`
-- ----------------------------
DROP TABLE IF EXISTS `et_exam_2_paper`;
CREATE TABLE `et_exam_2_paper` (
  `exam_id` int(11) NOT NULL,
  `paper_id` int(11) NOT NULL,
  UNIQUE KEY `idx_exam_epid` (`exam_id`,`paper_id`),
  KEY `fk_exam_pid` (`paper_id`),
  CONSTRAINT `fk_exam_eid` FOREIGN KEY (`exam_id`) REFERENCES `et_exam` (`id`),
  CONSTRAINT `fk_exam_pid` FOREIGN KEY (`paper_id`) REFERENCES `et_exam_paper` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of et_exam_2_paper
-- ----------------------------

-- ----------------------------
-- Table structure for `et_exam_paper`
-- ----------------------------
DROP TABLE IF EXISTS `et_exam_paper`;
CREATE TABLE `et_exam_paper` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `content` mediumtext,
  `duration` int(11) NOT NULL COMMENT '试卷考试时间',
  `total_point` int(11) DEFAULT '0',
  `pass_point` int(11) DEFAULT '0',
  `group_id` int(11) NOT NULL COMMENT '班组ID',
  `is_visible` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否所有用户可见，默认为0',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '试卷状态， 0未完成 -> 1已完成 -> 2已发布 -> 3通过审核 （已发布和通过审核的无法再修改）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `summary` varchar(100) DEFAULT NULL COMMENT '试卷介绍',
  `is_subjective` tinyint(1) DEFAULT NULL COMMENT '为1表示为包含主观题的试卷，需阅卷',
  `answer_sheet` mediumtext COMMENT '试卷答案，用答题卡的结构保存',
  `creator` varchar(40) DEFAULT NULL COMMENT '创建人的账号',
  `paper_type` varchar(40) NOT NULL DEFAULT '1' COMMENT '0 真题 1 模拟 2 专家',
  `field_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of et_exam_paper
-- ----------------------------

-- ----------------------------
-- Table structure for `et_field`
-- ----------------------------
DROP TABLE IF EXISTS `et_field`;
CREATE TABLE `et_field` (
  `field_id` int(5) NOT NULL AUTO_INCREMENT,
  `field_name` varchar(50) NOT NULL,
  `memo` varchar(100) DEFAULT NULL,
  `state` decimal(1,0) NOT NULL DEFAULT '1' COMMENT '1 正常 0 废弃',
  PRIMARY KEY (`field_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of et_field
-- ----------------------------
INSERT INTO `et_field` VALUES ('1', '2015年驾考C1', '2015年驾考C1', '0');

-- ----------------------------
-- Table structure for `et_group`
-- ----------------------------
DROP TABLE IF EXISTS `et_group`;
CREATE TABLE `et_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(40) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` int(11) NOT NULL,
  `is_default` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_group_uid` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of et_group
-- ----------------------------
INSERT INTO `et_group` VALUES ('1', '测试组', '2015-10-27 01:17:18', '1', '0');

-- ----------------------------
-- Table structure for `et_knowledge_point`
-- ----------------------------
DROP TABLE IF EXISTS `et_knowledge_point`;
CREATE TABLE `et_knowledge_point` (
  `point_id` int(5) NOT NULL AUTO_INCREMENT,
  `point_name` varchar(100) NOT NULL,
  `field_id` int(5) NOT NULL,
  `memo` varchar(100) DEFAULT NULL,
  `state` decimal(1,0) DEFAULT '1' COMMENT '1:正常 0：废弃',
  PRIMARY KEY (`point_id`),
  UNIQUE KEY `idx_point_name` (`point_name`) USING BTREE,
  KEY `fk_knowledge_field` (`field_id`),
  CONSTRAINT `et_knowledge_point_ibfk_1` FOREIGN KEY (`field_id`) REFERENCES `et_field` (`field_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of et_knowledge_point
-- ----------------------------
INSERT INTO `et_knowledge_point` VALUES ('1', '道路交通安全法律、法规和规章', '1', '', '0');
INSERT INTO `et_knowledge_point` VALUES ('2', '交通信号', '1', '', '0');
INSERT INTO `et_knowledge_point` VALUES ('3', '安全行车、文明驾驶基础知识', '1', '', '0');
INSERT INTO `et_knowledge_point` VALUES ('4', '机动车驾驶操作相关基础知识', '1', '', '0');

-- ----------------------------
-- Table structure for `et_menu_item`
-- ----------------------------
DROP TABLE IF EXISTS `et_menu_item`;
CREATE TABLE `et_menu_item` (
  `menu_id` varchar(50) NOT NULL,
  `menu_name` varchar(100) NOT NULL,
  `menu_href` varchar(200) NOT NULL,
  `menu_seq` int(5) NOT NULL,
  `authority` varchar(20) NOT NULL,
  `parent_id` varchar(50) NOT NULL,
  `icon` varchar(50) DEFAULT NULL,
  `visiable` tinyint(4) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of et_menu_item
-- ----------------------------
INSERT INTO `et_menu_item` VALUES ('question', '试题管理', 'admin/question/question-list', '1001', 'ROLE_ADMIN', '-1', 'fa-cloud', '1');
INSERT INTO `et_menu_item` VALUES ('question-list', '试题管理', 'admin/question/question-list', '100101', 'ROLE_ADMIN', 'question', 'fa-list', '1');
INSERT INTO `et_menu_item` VALUES ('question-add', '添加试题', 'admin/question/question-add', '100102', 'ROLE_ADMIN', 'question', 'fa-plus', '1');
INSERT INTO `et_menu_item` VALUES ('question-import', '导入试题', 'admin/question/question-import', '100103', 'ROLE_ADMIN', 'question', 'fa-cloud-upload', '1');
INSERT INTO `et_menu_item` VALUES ('exampaper', '试卷管理', 'admin/exampaper/exampaper-list/0', '1002', 'ROLE_ADMIN', '-1', 'fa-file-text-o', '1');
INSERT INTO `et_menu_item` VALUES ('exampaper-list', '试卷管理', 'admin/exampaper/exampaper-list/0', '100201', 'ROLE_ADMIN', 'exampaper', 'fa-list', '1');
INSERT INTO `et_menu_item` VALUES ('exampaper-add', '创建新试卷', 'admin/exampaper/exampaper-add', '100202', 'ROLE_ADMIN', 'exampaper', 'fa-leaf', '1');
INSERT INTO `et_menu_item` VALUES ('exampaper-edit', '修改试卷', '', '100203', 'ROLE_ADMIN', 'exampaper', 'fa-pencil', '0');
INSERT INTO `et_menu_item` VALUES ('exampaper-preview', '预览试卷', '', '100204', 'ROLE_ADMIN', 'exampaper', 'fa-eye', '0');
INSERT INTO `et_menu_item` VALUES ('exam', '考试管理', 'admin/exam/exam-list', '1003', 'ROLE_ADMIN', '-1', 'fa-trophy', '1');
INSERT INTO `et_menu_item` VALUES ('exam-list', '考试管理', 'admin/exam/exam-list', '100301', 'ROLE_ADMIN', 'exam', 'fa-list', '1');
INSERT INTO `et_menu_item` VALUES ('exam-add', '发布考试', 'admin/exam/exam-add', '100302', 'ROLE_ADMIN', 'exam', 'fa-plus-square-o', '1');
INSERT INTO `et_menu_item` VALUES ('exam-student-list', '学员名单', '', '100305', 'ROLE_ADMIN', 'exam', 'fa-sitemap', '0');
INSERT INTO `et_menu_item` VALUES ('student-answer-sheet', '学员试卷', '', '100306', 'ROLE_ADMIN', 'exam', 'fa-file-o', '0');
INSERT INTO `et_menu_item` VALUES ('mark-exampaper', '人工阅卷', '', '100307', 'ROLE_ADMIN', 'exam', 'fa-circle-o-notch', '0');
INSERT INTO `et_menu_item` VALUES ('user', '用户管理', 'admin/user/student-list', '1005', 'ROLE_ADMIN', '-1', 'fa-user', '1');
INSERT INTO `et_menu_item` VALUES ('student-list', '用户管理', 'admin/user/student-list', '100501', 'ROLE_ADMIN', 'user', 'fa-users', '1');
INSERT INTO `et_menu_item` VALUES ('student-examhistory', '考试历史', '', '100502', 'ROLE_ADMIN', 'user', 'fa-glass', '0');
INSERT INTO `et_menu_item` VALUES ('student-profile', '学员资料', '', '100503', 'ROLE_ADMIN', 'user', 'fa-flag-o', '0');
INSERT INTO `et_menu_item` VALUES ('teacher-list', '教师管理', 'admin/user/teacher-list', '100504', 'ROLE_ADMIN', 'user', 'fa-cubes', '1');
INSERT INTO `et_menu_item` VALUES ('teacher-profile', '教师资料', '', '100505', 'ROLE_ADMIN', 'user', null, '0');
INSERT INTO `et_menu_item` VALUES ('training', '培训', 'admin/training/training-list', '1004', 'ROLE_ADMIN', '-1', 'fa-laptop', '1');
INSERT INTO `et_menu_item` VALUES ('training-list', '培训管理', 'admin/training/training-list', '100401', 'ROLE_ADMIN', 'training', 'fa-list', '1');
INSERT INTO `et_menu_item` VALUES ('training-add', '添加培训', 'admin/training/training-add', '100402', 'ROLE_ADMIN', 'training', 'fa-plus', '1');
INSERT INTO `et_menu_item` VALUES ('student-practice-status', '学习进度', '', '100403', 'ROLE_ADMIN', 'training', 'fa-rocket', '0');
INSERT INTO `et_menu_item` VALUES ('student-training-history', '培训进度', '', '100404', 'ROLE_ADMIN', 'training', 'fa-star-half-full', '0');
INSERT INTO `et_menu_item` VALUES ('common', '通用数据', 'admin/common/tag-list', '1006', 'ROLE_ADMIN', '-1', 'fa-cubes', '1');
INSERT INTO `et_menu_item` VALUES ('tag-list', '标签管理', 'admin/common/tag-list', '100601', 'ROLE_ADMIN', 'common', 'fa-tags', '1');
INSERT INTO `et_menu_item` VALUES ('field-list', '专业题库', 'admin/common/field-list', '100602', 'ROLE_ADMIN', 'common', 'fa-anchor', '1');
INSERT INTO `et_menu_item` VALUES ('knowledge-list', '知识分类', 'admin/common/knowledge-list/0', '100603', 'ROLE_ADMIN', 'common', 'fa-flag', '1');
INSERT INTO `et_menu_item` VALUES ('question', '试题管理', 'teacher/question/question-list', '2001', 'ROLE_TEACHER', '-1', 'fa-cloud', '1');
INSERT INTO `et_menu_item` VALUES ('question-list', '试题管理', 'teacher/question/question-list', '200101', 'ROLE_TEACHER', 'question', 'fa-list', '1');
INSERT INTO `et_menu_item` VALUES ('question-add', '添加试题', 'teacher/question/question-add', '200102', 'ROLE_TEACHER', 'question', 'fa-plus', '1');
INSERT INTO `et_menu_item` VALUES ('question-import', '导入试题', 'teacher/question/question-import', '200103', 'ROLE_TEACHER', 'question', 'fa-cloud-upload', '1');
INSERT INTO `et_menu_item` VALUES ('exampaper', '试卷管理', 'teacher/exampaper/exampaper-list/0', '2002', 'ROLE_TEACHER', '-1', 'fa-file-text-o', '1');
INSERT INTO `et_menu_item` VALUES ('exampaper-list', '试卷管理', 'teacher/exampaper/exampaper-list/0', '200201', 'ROLE_TEACHER', 'exampaper', 'fa-list', '1');
INSERT INTO `et_menu_item` VALUES ('exampaper-add', '创建新试卷', 'teacher/exampaper/exampaper-add', '200202', 'ROLE_TEACHER', 'exampaper', 'fa-leaf', '1');
INSERT INTO `et_menu_item` VALUES ('exampaper-edit', '修改试卷', '', '200203', 'ROLE_TEACHER', 'exampaper', 'fa-pencil', '0');
INSERT INTO `et_menu_item` VALUES ('exampaper-preview', '预览试卷', '', '200204', 'ROLE_TEACHER', 'exampaper', 'fa-eye', '0');
INSERT INTO `et_menu_item` VALUES ('exam', '考试管理', 'teacher/exam/exam-list', '2003', 'ROLE_TEACHER', '-1', 'fa-trophy', '1');
INSERT INTO `et_menu_item` VALUES ('exam-list', '考试管理', 'teacher/exam/exam-list', '200301', 'ROLE_TEACHER', 'exam', 'fa-list', '1');
INSERT INTO `et_menu_item` VALUES ('exam-add', '发布考试', 'teacher/exam/exam-add', '200302', 'ROLE_TEACHER', 'exam', 'fa-plus-square-o', '1');
INSERT INTO `et_menu_item` VALUES ('exam-student-list', '学员名单', '', '200303', 'ROLE_TEACHER', 'exam', 'fa-sitemap', '0');
INSERT INTO `et_menu_item` VALUES ('student-answer-sheet', '学员试卷', '', '200304', 'ROLE_TEACHER', 'exam', 'fa-file-o', '0');
INSERT INTO `et_menu_item` VALUES ('mark-exampaper', '人工阅卷', '', '200305', 'ROLE_TEACHER', 'exam', 'fa-circle-o-notch', '0');
INSERT INTO `et_menu_item` VALUES ('user', '用户管理', 'teacher/user/student-list', '2005', 'ROLE_TEACHER', '-1', 'fa-user', '1');
INSERT INTO `et_menu_item` VALUES ('student-list', '用户管理', 'teacher/user/student-list', '200501', 'ROLE_TEACHER', 'user', 'fa-users', '1');
INSERT INTO `et_menu_item` VALUES ('student-examhistory', '考试历史', '', '200502', 'ROLE_TEACHER', 'user', 'fa-glass', '0');
INSERT INTO `et_menu_item` VALUES ('student-profile', '学员资料', '', '200503', 'ROLE_TEACHER', 'user', 'fa-flag-o', '0');
INSERT INTO `et_menu_item` VALUES ('training', '培训', 'teacher/training/training-list', '2004', 'ROLE_TEACHER', '-1', 'fa-laptop', '1');
INSERT INTO `et_menu_item` VALUES ('training-list', '培训管理', 'teacher/training/training-list', '200401', 'ROLE_TEACHER', 'training', 'fa-list', '1');
INSERT INTO `et_menu_item` VALUES ('training-add', '添加培训', 'teacher/training/training-add', '200402', 'ROLE_TEACHER', 'training', 'fa-plus', '1');
INSERT INTO `et_menu_item` VALUES ('student-practice-status', '学习进度', '', '200403', 'ROLE_TEACHER', 'training', 'fa-rocket', '0');
INSERT INTO `et_menu_item` VALUES ('student-training-history', '培训进度', '', '200404', 'ROLE_TEACHER', 'training', 'fa-star-half-full', '0');
INSERT INTO `et_menu_item` VALUES ('system', '系统设置', 'admin/system/admin-list', '1007', 'ROLE_ADMIN', '-1', 'fa-cog', '1');
INSERT INTO `et_menu_item` VALUES ('admin-list', '管理员列表', 'admin/system/admin-list', '100701', 'ROLE_ADMIN', 'system', 'fa-group', '1');
INSERT INTO `et_menu_item` VALUES ('news-list', '系统公告', 'admin/system/news-list', '100703', 'ROLE_ADMIN', 'system', 'fa-volume-up', '1');
INSERT INTO `et_menu_item` VALUES ('dep-list', '部门管理', 'admin/common/dep-list', '100604', 'ROLE_ADMIN', 'common', 'fa-leaf', '1');
INSERT INTO `et_menu_item` VALUES ('model-test-add', '发布模拟考试', 'admin/exam/model-test-add', '100304', 'ROLE_ADMIN', 'exam', 'fa-flag', '1');
INSERT INTO `et_menu_item` VALUES ('model-test-list', '模拟考试', 'admin/exam/model-test-list', '100303', 'ROLE_ADMIN', 'exam', 'fa-glass', '1');
INSERT INTO `et_menu_item` VALUES ('dashboard', '控制面板', 'admin/dashboard', '1000', 'ROLE_ADMIN', '-1', 'fa-dashboard', '1');
INSERT INTO `et_menu_item` VALUES ('', '', '', '0', 'ROLE_ADMIN', '', null, '1');

-- ----------------------------
-- Table structure for `et_news`
-- ----------------------------
DROP TABLE IF EXISTS `et_news`;
CREATE TABLE `et_news` (
  `news_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `content` varchar(2000) NOT NULL,
  `user_id` int(11) NOT NULL COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`news_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `et_news_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `et_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of et_news
-- ----------------------------
INSERT INTO `et_news` VALUES ('1', '在线考试系统正式发布', '在线考试系统正式发布', '1', '2015-10-28 01:46:37');

-- ----------------------------
-- Table structure for `et_practice_paper`
-- ----------------------------
DROP TABLE IF EXISTS `et_practice_paper`;
CREATE TABLE `et_practice_paper` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `name` varchar(40) NOT NULL,
  `content` mediumtext,
  `duration` int(11) NOT NULL COMMENT '试卷考试时间',
  `total_point` int(11) DEFAULT '0',
  `pass_point` int(11) DEFAULT '0',
  `group_id` int(11) NOT NULL COMMENT '班组ID',
  `is_visible` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否所有用户可见，默认为0',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '试卷状态， 0未完成 -> 1已完成 -> 2已发布 -> 3通过审核 （已发布和通过审核的无法再修改）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `summary` varchar(100) DEFAULT NULL COMMENT '试卷介绍',
  `is_subjective` tinyint(1) DEFAULT NULL COMMENT '为1表示为包含主观题的试卷，需阅卷',
  `answer_sheet` mediumtext COMMENT '试卷答案，用答题卡的结构保存',
  `creator` varchar(40) DEFAULT NULL COMMENT '创建人的账号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `group_id` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of et_practice_paper
-- ----------------------------

-- ----------------------------
-- Table structure for `et_question`
-- ----------------------------
DROP TABLE IF EXISTS `et_question`;
CREATE TABLE `et_question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `content` varchar(2000) NOT NULL,
  `question_type_id` int(11) NOT NULL COMMENT '题型',
  `duration` int(11) DEFAULT NULL COMMENT '试题考试时间',
  `points` int(11) DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL COMMENT '班组ID',
  `is_visible` tinyint(1) NOT NULL DEFAULT '0' COMMENT '试题可见性',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `creator` varchar(20) NOT NULL DEFAULT 'admin' COMMENT '创建者',
  `last_modify` timestamp NULL DEFAULT NULL,
  `answer` mediumtext NOT NULL,
  `expose_times` int(11) NOT NULL DEFAULT '2',
  `right_times` int(11) NOT NULL DEFAULT '1',
  `wrong_times` int(11) NOT NULL DEFAULT '1',
  `difficulty` int(5) NOT NULL DEFAULT '1',
  `analysis` mediumtext,
  `reference` varchar(1000) DEFAULT NULL,
  `examing_point` varchar(5000) DEFAULT NULL,
  `keyword` varchar(5000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `question_type_id` (`question_type_id`),
  KEY `et_question_ibfk_5` (`creator`),
  CONSTRAINT `et_question_ibfk_1` FOREIGN KEY (`question_type_id`) REFERENCES `et_question_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of et_question
-- ----------------------------
INSERT INTO `et_question` VALUES ('1', '驾驶机动车在道路上违', '{\"title\":\"驾驶机动车在道路上违反道路交通安全法的行为，属于什么行为？\",\"titleImg\":\"\",\"choiceList\":{\"A\":\"违法行为\",\"B\":\"违章行为\",\"C\":\"违规行为\",\"D\":\"过失行为\"},\"choiceImgList\":{}}', '1', null, '0', null, '0', '2015-12-11 22:11:22', 'admin', '0000-00-00 00:00:00', 'A', '2', '1', '1', '1', '“违反道路交通安全法”，违反法律法规即为违法行为。官方已无违章/违规的说法。', '', '', '');
INSERT INTO `et_question` VALUES ('2', '机动车驾驶人违法驾驶', '{\"title\":\"机动车驾驶人违法驾驶造成重大交通事故构成犯罪的，依法追究什么责任？\",\"titleImg\":\"\",\"choiceList\":{\"A\":\"直接责任\",\"B\":\"经济责任\",\"C\":\"民事责任\",\"D\":\"刑事责任\"},\"choiceImgList\":{}}', '1', null, '0', null, '0', '2015-12-11 23:03:54', 'admin', null, 'D', '2', '1', '1', '1', '《道路交通安全法》第一百零一条：违反道路交通安全法律、法规的规定，发生重大交通事故，构成犯罪的，依法追究刑事责任，并由公安机关交通管理部门吊销机动车驾驶证。', '', '', '');
INSERT INTO `et_question` VALUES ('3', '机动车驾驶人造成事故', '{\"title\":\"机动车驾驶人造成事故后逃逸构成犯罪的，吊销驾驶证且多长时间不得重新取得驾驶证？\",\"titleImg\":\"\",\"choiceList\":{\"A\":\"终生\",\"B\":\"10年内\",\"C\":\"5年内\",\"D\":\"20年内\"},\"choiceImgList\":{}}', '1', null, '0', null, '0', '2015-12-11 23:05:00', 'admin', null, 'A', '2', '1', '1', '1', '《道路交通安全法》第一百零一条：\n造成交通事故后逃逸的，由公安机关交通管理部门吊销机动车驾驶证，且终生不得重新取得机动车驾驶证。', '', '', '');
INSERT INTO `et_question` VALUES ('4', '驾驶机动车违反道路交', '{\"title\":\"驾驶机动车违反道路交通安全法律法规发生交通事故属于交通违章行为。\",\"titleImg\":\"\",\"choiceList\":{},\"choiceImgList\":{}}', '3', null, '0', null, '0', '2015-12-11 23:09:10', 'admin', null, 'F', '2', '1', '1', '1', '“违反道路交通安全法”，违反法律法规即为违法行为。现在官方已无违章/违规的说法。', '', '', '');
INSERT INTO `et_question` VALUES ('5', '驾驶机动车在道路上违', '{\"title\":\"驾驶机动车在道路上违反道路通行规定应当接受相应的处罚。\",\"titleImg\":\"\",\"choiceList\":{},\"choiceImgList\":{}}', '3', null, '0', null, '0', '2015-12-11 23:09:37', 'admin', null, 'T', '2', '1', '1', '1', '常识题，违反规定，就得接受相应的处罚。', '', '', '');
INSERT INTO `et_question` VALUES ('6', '对未取得驾驶证驾驶机', '{\"title\":\"对未取得驾驶证驾驶机动车的，追究其法律责任。\",\"titleImg\":\"\",\"choiceList\":{},\"choiceImgList\":{}}', '3', null, '0', null, '0', '2015-12-11 23:09:59', 'admin', null, 'T', '2', '1', '1', '1', '《道路交通安全法》第九十九条：\n未取得机动车驾驶证、机动车驾驶证被吊销或者机动车驾驶证被暂扣期间驾驶机动车的，由公安机关交通管理部门处二百元以上二千元以下罚款，可以并处十五日以下拘留。', '', '', '');
INSERT INTO `et_question` VALUES ('7', '对违法驾驶发生重大交', '{\"title\":\"对违法驾驶发生重大交通事故且构成犯罪的，不追究其刑事责任。\",\"titleImg\":\"\",\"choiceList\":{},\"choiceImgList\":{}}', '3', null, '0', null, '0', '2015-12-11 23:10:16', 'admin', null, 'F', '2', '1', '1', '1', '《道路交通安全法》第一百零一条：违反道路交通安全法律、法规的规定，发生重大交通事故，构成犯罪的，依法追究刑事责任，并由公安机关交通管理部门吊销机动车驾驶证。', '', '', '');
INSERT INTO `et_question` VALUES ('8', '造成交通事故后逃逸且', '{\"title\":\"造成交通事故后逃逸且构成犯罪的驾驶人，将吊销驾驶证且终生不得重新取得驾驶证\",\"titleImg\":\"\",\"choiceList\":{},\"choiceImgList\":{}}', '3', null, '0', null, '0', '2015-12-11 23:10:36', 'admin', null, 'T', '2', '1', '1', '1', '《道路交通安全法》第一百零一条：\n违反道路交通安全法律、法规的规定，发生重大交通事故，构成犯罪的，依法追究刑事责任，并由公安机关交通管理部门吊销机动车驾驶证。\n造成交通事故后逃逸的，由公安机关交通管理部门吊销机动车驾驶证，且终生不得重新取得机动车驾驶证。', '', '', '');
INSERT INTO `et_question` VALUES ('9', '驾驶机动车在道路上违', '{\"title\":\"驾驶机动车在道路上违反交通安全法规的行为属于违法行为。\",\"titleImg\":\"\",\"choiceList\":{},\"choiceImgList\":{}}', '3', null, '0', null, '0', '2015-12-11 23:10:54', 'admin', null, 'T', '2', '1', '1', '1', '“违反道路交通安全法”，违反法律法规即为违法行为。官方已无违章/违规的说法。', '', '', '');
INSERT INTO `et_question` VALUES ('10', '驾驶机动车应当随身携', '{\"title\":\"驾驶机动车应当随身携带哪种证件？\",\"titleImg\":\"\",\"choiceList\":{\"A\":\"驾驶证\",\"B\":\"工作证\",\"C\":\"身份证\",\"D\":\"职业资格证\"},\"choiceImgList\":{}}', '1', null, '0', null, '0', '2015-12-11 23:12:06', 'admin', null, 'A', '2', '1', '1', '1', '《道路交通安全法》第十一条：驾驶机动车上道路行驶，应当悬挂机动车号牌，放置检验合格标志、保险标志，并随车携带机动车行驶证。', '', '', '');
INSERT INTO `et_question` VALUES ('11', '未取得驾驶证的学员在', '{\"title\":\"未取得驾驶证的学员在道路上学习驾驶技能，下列哪种做法是正确的？\",\"titleImg\":\"\",\"choiceList\":{\"A\":\"使用所学车型的教练车由非教练员的驾驶人随车指导\",\"B\":\"使用所学车型的教练车单独驾驶学习\",\"C\":\"使用所学车型的教练车由教练员随车指导\",\"D\":\"使用私家车由教练员随车指导\"},\"choiceImgList\":{}}', '1', null, '0', null, '0', '2015-12-11 23:12:52', 'admin', null, 'C', '2', '1', '1', '1', '《公安部令第123号》规定：\n未取得驾驶证的学员在道路上学习驾驶技能，使用所学车型的教练车由教练员随车指导。', '', '', '');
INSERT INTO `et_question` VALUES ('12', '机动车驾驶人初次申领', '{\"title\":\"机动车驾驶人初次申领驾驶证后的实习期是多长时间？\",\"titleImg\":\"\",\"choiceList\":{\"A\":\"18个月\",\"B\":\"6个月\",\"C\":\"16个月\",\"D\":\"12个月\"},\"choiceImgList\":{}}', '1', null, '0', null, '0', '2015-12-11 23:13:35', 'admin', null, 'D', '2', '1', '1', '1', '《公安部令第123号》规定：\n机动车驾驶人初次申请机动车驾驶证和增加准驾车型后的12个月为实习期。在实习期内驾驶机动车的，应当在车身后部粘贴或者悬挂统一式样的实习标志。', '', '', '');
INSERT INTO `et_question` VALUES ('13', '在实习期内驾驶机动车', '{\"title\":\"在实习期内驾驶机动车的，应当在车身后部粘贴或者悬挂哪种标志？\",\"titleImg\":\"\",\"choiceList\":{\"A\":\"统一式样的实习标志\",\"B\":\"注意避让标志\",\"C\":\"注意车距标志\",\"D\":\"注意新手标志\"},\"choiceImgList\":{}}', '1', null, '0', null, '0', '2015-12-11 23:14:17', 'admin', null, 'A', '2', '1', '1', '1', '《公安部令第123号》规定：\n在实习期内驾驶机动车的，应当在车身后部粘贴或者悬挂统一式样的实习标志。', '', '', '');
INSERT INTO `et_question` VALUES ('14', '以欺骗、贿赂等不正当', '{\"title\":\"以欺骗、贿赂等不正当手段取得驾驶证被依法撤销驾驶许可的，多长时间不得重新申请驾驶许可？\",\"titleImg\":\"\",\"choiceList\":{\"A\":\"3年内\",\"B\":\"1年内\",\"C\":\"终身\",\"D\":\"5年内\"},\"choiceImgList\":{}}', '1', null, '0', null, '0', '2015-12-11 23:15:00', 'admin', null, 'A', '2', '1', '1', '1', '《公安部令第123号》规定：\n申请人以欺骗、贿赂等不正当手段取得机动车驾驶证的，公安机关交通管理部门收缴机动车驾驶证，撤销机动车驾驶许可；申请人在三年内不得再次申领机动车驾驶证。', '', '', '');
INSERT INTO `et_question` VALUES ('15', '驾驶人要按照驾驶证载', '{\"title\":\"驾驶人要按照驾驶证载明的准驾车型驾驶车辆。\",\"titleImg\":\"\",\"choiceList\":{},\"choiceImgList\":{}}', '3', null, '0', null, '0', '2015-12-11 23:15:35', 'admin', null, 'T', '2', '1', '1', '1', '《公安部令第123号》规定：\n驾驶与准驾车型不符的机动车的违法行为，一次记12分。', '', '', '');
INSERT INTO `et_question` VALUES ('16', '上路行驶的机动车未随', '{\"title\":\"上路行驶的机动车未随车携带身份证的，交通警察可依法扣留机动车。\",\"titleImg\":\"\",\"choiceList\":{},\"choiceImgList\":{}}', '3', null, '0', null, '0', '2015-12-11 23:15:54', 'admin', null, 'F', '2', '1', '1', '1', '《道路交通安全法》第九十五条：\n上道路行驶的机动车未悬挂机动车号牌，未放置检验合格标志、保险标志，或者未随车携带行驶证、驾驶证的，公安机关交通管理部门应当扣留机动车。', '', '', '');
INSERT INTO `et_question` VALUES ('17', '上路行驶的机动车未放', '{\"title\":\"上路行驶的机动车未放置检验合格标志的，交通警察可依法扣留机动车。\",\"titleImg\":\"\",\"choiceList\":{},\"choiceImgList\":{}}', '3', null, '0', null, '0', '2015-12-11 23:16:17', 'admin', null, 'T', '2', '1', '1', '1', '《道路交通安全法》第九十五条：\n上道路行驶的机动车未悬挂机动车号牌，未放置检验合格标志、保险标志，或者未随车携带行驶证、驾驶证的，公安机关交通管理部门应当扣留机动车。', '', '', '');
INSERT INTO `et_question` VALUES ('18', '伪造、变造机动车驾驶', '{\"title\":\"伪造、变造机动车驾驶证构成犯罪的将被依法追究刑事责任。\",\"titleImg\":\"\",\"choiceList\":{},\"choiceImgList\":{}}', '3', null, '0', null, '0', '2015-12-11 23:16:40', 'admin', null, 'T', '2', '1', '1', '1', '《道路交通安全法》第九十六条：\n伪造、变造或者使用伪造、变造的机动车登记证书、号牌、行驶证、驾驶证的，由公安机关交通管理部门予以收缴，扣留该机动车，处15日以下拘留，并处2000元以上5000元以下罚款；构成犯罪的，依法追究刑事责任。', '', '', '');
INSERT INTO `et_question` VALUES ('19', '机动车驾驶人在实习期', '{\"title\":\"机动车驾驶人在实习期内驾驶机动车不得牵引挂车。\",\"titleImg\":\"\",\"choiceList\":{},\"choiceImgList\":{}}', '3', null, '0', null, '0', '2015-12-11 23:16:56', 'admin', null, 'T', '2', '1', '1', '1', '《公安部令第123号》第六十五条：\n机动车驾驶人在实习期内不得驾驶公共汽车、营运客车或者执行任务的警车、消防车、救护车、工程救险车以及载有爆炸物品、易燃易爆化学物品、剧毒或者放射性等危险物品的机动车；驾驶的机动车不得牵引挂车。', '', '', '');
INSERT INTO `et_question` VALUES ('20', '驾驶拼装机动车上路行', '{\"title\":\"驾驶拼装机动车上路行驶的驾驶人，除按规定接受罚款外，还要受到哪种处理？\",\"titleImg\":\"\",\"choiceList\":{\"A\":\"处10日以下拘留\",\"B\":\"吊销驾驶证\",\"C\":\"追究刑事责任\",\"D\":\"暂扣驾驶证\"},\"choiceImgList\":{}}', '1', null, '0', null, '0', '2015-12-11 23:17:57', 'admin', null, 'B', '2', '1', '1', '1', '《道路交通安全法》第一百条：\n驾驶拼装的机动车或者已达到报废标准的机动车上道路行驶的，公安机关交通管理部门应当予以收缴，强制报废。\n对驾驶前款所列机动车上道路行驶的驾驶人，处200元以上2000元以下罚款，并吊销机动车驾驶证。', '', '', '');
INSERT INTO `et_question` VALUES ('21', '驾驶报废机动车上路行', '{\"title\":\"驾驶报废机动车上路行驶的驾驶人，除按规定罚款外，还要受到哪种处理？\",\"titleImg\":\"\",\"choiceList\":{\"A\":\"收缴驾驶证\",\"B\":\"强制恢复车况\",\"C\":\"吊销驾驶证\",\"D\":\"撤销驾驶许可\"},\"choiceImgList\":{}}', '1', null, '0', null, '0', '2015-12-11 23:18:39', 'admin', null, 'C', '2', '1', '1', '1', '《道路交通安全法》第一百条：\n驾驶拼装的机动车或者已达到报废标准的机动车上道路行驶的，公安机关交通管理部门应当予以收缴，强制报废。\n对驾驶前款所列机动车上道路行驶的驾驶人，处200元以上2000元以下罚款，并吊销机动车驾驶证。', '', '', '');
INSERT INTO `et_question` VALUES ('22', '对驾驶已达到报废标准', '{\"title\":\"对驾驶已达到报废标准的机动车上路行驶的驾驶人，会受到下列哪种处罚？\",\"titleImg\":\"\",\"choiceList\":{\"A\":\"处20以上200元以下罚款\",\"B\":\"处15日以下拘留\",\"C\":\"追究刑事责任\",\"D\":\"吊销机动车驾驶证\"},\"choiceImgList\":{}}', '1', null, '0', null, '0', '2015-12-11 23:19:49', 'admin', null, 'D', '2', '1', '1', '1', '《道路交通安全法》第一百条：\n驾驶拼装的机动车或者已达到报废标准的机动车上道路行驶的，公安机关交通管理部门应当予以收缴，强制报废。\n对驾驶前款所列机动车上道路行驶的驾驶人，处200元以上2000元以下罚款，并吊销机动车驾驶证。', '', '', '');
INSERT INTO `et_question` VALUES ('23', '对驾驶拼装机动车上路', '{\"title\":\"对驾驶拼装机动车上路行驶的驾驶人，会受到下列哪种处罚？\",\"titleImg\":\"\",\"choiceList\":{\"A\":\"吊销机动车行驶证\",\"B\":\"处15日以下拘留\",\"C\":\"处200以上2000元以下罚款\",\"D\":\"依法追究刑事责任\"},\"choiceImgList\":{}}', '1', null, '0', null, '0', '2015-12-11 23:20:30', 'admin', null, 'C', '2', '1', '1', '1', '《道路交通安全法》第一百条：\n驾驶拼装的机动车或者已达到报废标准的机动车上道路行驶的，公安机关交通管理部门应当予以收缴，强制报废。\n对驾驶前款所列机动车上道路行驶的驾驶人，处200元以上2000元以下罚款，并吊销机动车驾驶证。', '', '', '');
INSERT INTO `et_question` VALUES ('24', '驾驶机动车上路前应当', '{\"title\":\"驾驶机动车上路前应当检查车辆安全技术性能。\",\"titleImg\":\"\",\"choiceList\":{},\"choiceImgList\":{}}', '3', null, '0', null, '0', '2015-12-11 23:20:53', 'admin', null, 'T', '2', '1', '1', '1', '《道路交通安全法》第二十一条：\n驾驶人驾驶机动车上道路行驶前，应当对机动车的安全技术性能进行认真检查；不得驾驶安全设施不全或者机件不符合技术标准等具有安全隐患的机动车。', '', '', '');
INSERT INTO `et_question` VALUES ('25', '不得驾驶具有安全隐患', '{\"title\":\"不得驾驶具有安全隐患的机动车上道路行驶。\",\"titleImg\":\"\",\"choiceList\":{},\"choiceImgList\":{}}', '3', null, '0', null, '0', '2015-12-11 23:21:09', 'admin', null, 'T', '2', '1', '1', '1', '《道路交通安全法》第二十一条：\n驾驶人驾驶机动车上道路行驶前，应当对机动车的安全技术性能进行认真检查；不得驾驶安全设施不全或者机件不符合技术标准等具有安全隐患的机动车。', '', '', '');
INSERT INTO `et_question` VALUES ('26', '拼装的机动车只要认为', '{\"title\":\"拼装的机动车只要认为安全就可以上路行驶。\",\"titleImg\":\"\",\"choiceList\":{},\"choiceImgList\":{}}', '3', null, '0', null, '0', '2015-12-11 23:21:31', 'admin', null, 'F', '2', '1', '1', '1', '《道路交通安全法》第一百条：\n驾驶拼装的机动车或者已达到报废标准的机动车上道路行驶的，公安机关交通管理部门应当予以收缴，强制报废。\n对驾驶前款所列机动车上道路行驶的驾驶人，处200元以上2000元以下罚款，并吊销机动车驾驶证。', '', '', '');
INSERT INTO `et_question` VALUES ('27', '已经达到报废标准的机', '{\"title\":\"已经达到报废标准的机动车经大修后可以上路行驶。\",\"titleImg\":\"\",\"choiceList\":{},\"choiceImgList\":{}}', '3', null, '0', null, '0', '2015-12-11 23:21:46', 'admin', null, 'F', '2', '1', '1', '1', '《道路交通安全法》第一百条规定：\n驾驶拼装的机动车或者已达到报废标准的机动车上道路行驶的，公安机关交通管理部门应当予以收缴，强制报废。对驾驶前款所列机动车上道路行驶的驾驶人，处200元以上2000元以下罚款，并吊销机动车驾驶证。已经达到报废标准就得报废，再修也是达到报废标准的车子了。不能再上路行驶了。', '', '', '');
INSERT INTO `et_question` VALUES ('28', '下列哪种证件是驾驶机', '{\"title\":\"下列哪种证件是驾驶机动车上路行驶应当随车携带？\",\"titleImg\":\"\",\"choiceList\":{\"A\":\"机动车行驶证\",\"B\":\"机动车保险单\",\"C\":\"机动车登记证\",\"D\":\"出厂合格证明\"},\"choiceImgList\":{}}', '1', null, '0', null, '0', '2015-12-11 23:22:35', 'admin', null, 'A', '2', '1', '1', '1', '《道路交通安全法》第十一条：　驾驶机动车上道路行驶，应当悬挂机动车号牌，放置检验合格标志、保险标志，并随车携带机动车行驶证。', '', '', '');
INSERT INTO `et_question` VALUES ('29', '驾驶这种机动车上路行', '{\"title\":\"驾驶这种机动车上路行驶属于什么行为？\",\"titleImg\":\"files\\\\question\\\\admin\\\\1449847523491.jpg\",\"choiceList\":{\"A\":\"犯罪行为\",\"B\":\"违法行为\",\"C\":\"违规行为\",\"D\":\"违章行为\"},\"choiceImgList\":{}}', '1', null, '0', null, '0', '2015-12-11 23:26:04', 'admin', null, 'B', '2', '1', '1', '1', '《道路交通安全法》第十一条：\n机动车号牌应当按照规定悬挂并保持清晰、完整，不得故意遮挡、污损。\n违反《交通安全法》，所以是违法行为。', '', '', '');
INSERT INTO `et_question` VALUES ('30', '下列哪种标志是驾驶机', '{\"title\":\"下列哪种标志是驾驶机动车上路行驶应当在车上放置的标志？\",\"titleImg\":\"\",\"choiceList\":{\"A\":\"检验合格标志\",\"B\":\"产品合格标志\",\"C\":\"保持车距标志\",\"D\":\"提醒危险标志\"},\"choiceImgList\":{}}', '1', null, '0', null, '0', '2015-12-11 23:26:50', 'admin', null, 'A', '2', '1', '1', '1', '《道路交通安全法》第十一条：驾驶机动车上道路行驶，应当悬挂机动车号牌，放置检验合格标志、保险标志，并随车携带机动车行驶证。', '', '', '');
INSERT INTO `et_question` VALUES ('31', '驾驶人在下列哪种情况', '{\"title\":\"驾驶人在下列哪种情况下不能驾驶机动车？\",\"titleImg\":\"\",\"choiceList\":{\"A\":\"饮酒后\",\"B\":\"喝牛奶后\",\"C\":\"喝咖啡后\",\"D\":\"喝茶后\"},\"choiceImgList\":{}}', '1', null, '0', null, '0', '2015-12-11 23:27:30', 'admin', null, 'A', '2', '1', '1', '1', '常识性考题，酒后不能开车，酒后开车属违法行为。', '', '', '');
INSERT INTO `et_question` VALUES ('32', '这辆在道路上行驶的机', '{\"title\":\"这辆在道路上行驶的机动车有下列哪种违法行为？\",\"titleImg\":\"files\\\\question\\\\admin\\\\1449847713180.jpg\",\"choiceList\":{\"A\":\"占用非机动车道\",\"B\":\"未按规定悬挂号牌\",\"C\":\"逆向行驶\",\"D\":\"故意遮挡号牌\"},\"choiceImgList\":{}}', '1', null, '0', null, '0', '2015-12-11 23:28:35', 'admin', null, 'D', '2', '1', '1', '1', '很明显可以看出，机动车号牌被光碟挡住了，现在遮挡号牌的直接扣12分。', '', '', '');
INSERT INTO `et_question` VALUES ('33', '驾驶机动车上路行驶应', '{\"title\":\"驾驶机动车上路行驶应当按规定悬挂号牌。\",\"titleImg\":\"\",\"choiceList\":{},\"choiceImgList\":{}}', '3', null, '0', null, '0', '2015-12-11 23:29:00', 'admin', null, 'T', '2', '1', '1', '1', '《道路交通安全法》第十一条规定：\n驾驶机动车上道路行驶，应当悬挂机动车号牌，放置检验合格标志、保险标志，并随车携带机动车行驶证。', '', '', '');
INSERT INTO `et_question` VALUES ('34', '驾驶这种机动车上路行', '{\"title\":\"驾驶这种机动车上路行驶没有违法行为\",\"titleImg\":\"files\\\\question\\\\admin\\\\1449847811626.jpg\",\"choiceList\":{},\"choiceImgList\":{}}', '3', null, '0', null, '0', '2015-12-11 23:30:24', 'admin', null, 'F', '2', '1', '1', '1', '图中机动车号牌被光碟遮住了。故意遮挡、污损机动车号牌，是违反《道路交通安全违法》的行为，也就是违法行为。', '', '', '');
INSERT INTO `et_question` VALUES ('35', '服用国家管制的精神药', '{\"title\":\"服用国家管制的精神药品可以短途驾驶机动车。\",\"titleImg\":\"\",\"choiceList\":{},\"choiceImgList\":{}}', '3', null, '0', null, '0', '2015-12-11 23:30:40', 'admin', null, 'F', '2', '1', '1', '1', '《道路交通安全法》第二十二条：\n饮酒、服用国家管制的精神药品或者麻醉药品，或者患有妨碍安全驾驶机动车的疾病，或者过度疲劳影响安全驾驶的，不得驾驶机动车。', '', '', '');
INSERT INTO `et_question` VALUES ('36', '饮酒后只要不影响驾驶', '{\"title\":\"饮酒后只要不影响驾驶操作可以短距离驾驶机动车。\",\"titleImg\":\"\",\"choiceList\":{},\"choiceImgList\":{}}', '3', null, '0', null, '0', '2015-12-11 23:30:58', 'admin', null, 'F', '2', '1', '1', '1', '《道路交通安全法》第九十一条：\n饮酒后驾驶机动车的，处暂扣6个月机动车驾驶证，并处1000元以上2000元以下罚款。', '', '', '');
INSERT INTO `et_question` VALUES ('37', '公安机关交通管理部门', '{\"title\":\"公安机关交通管理部门对驾驶人的交通违法行为除依法给予行政处罚外，实行下列哪种制度？\",\"titleImg\":\"\",\"choiceList\":{\"A\":\"强制报废制度\",\"B\":\"累积记分制度\",\"C\":\"奖励里程制度\",\"D\":\"违法登记制度\"},\"choiceImgList\":{}}', '1', null, '0', null, '0', '2015-12-11 23:31:42', 'admin', null, 'B', '2', '1', '1', '1', '《道路交通安全法》第二十四条：公安机关交通管理部门对机动车驾驶人违反道路交通安全法律、法规的行为，除依法给予行政处罚外，实行累积记分制度。', '', '', '');
INSERT INTO `et_question` VALUES ('38', '道路交通安全违法行为', '{\"title\":\"道路交通安全违法行为累积记分的周期是多长时间？\",\"titleImg\":\"\",\"choiceList\":{\"A\":\"12个月\",\"B\":\"24个月\",\"C\":\"6个月\",\"D\":\"3个月\"},\"choiceImgList\":{}}', '1', null, '0', null, '0', '2015-12-11 23:32:27', 'admin', null, 'A', '2', '1', '1', '1', '《公安部令第123号》第五十五条：\n道路交通安全违法行为累积记分周期（即记分周期）为12个月，满分为12分，从机动车驾驶证初次领取之日起计算。', '', '', '');
INSERT INTO `et_question` VALUES ('39', '公安机关交通管理部门', '{\"title\":\"公安机关交通管理部门对累积记分达到规定分值的驾驶人怎样处理？\",\"titleImg\":\"\",\"choiceList\":{\"A\":\"进行法律法规教育，重新考试\",\"B\":\"依法追究刑事责任\",\"C\":\"终生禁驾\",\"D\":\"处15日以下拘留\"},\"choiceImgList\":{}}', '1', null, '0', null, '0', '2015-12-11 23:33:06', 'admin', null, 'A', '2', '1', '1', '1', '《道路交通安全法》第二十四条：公安机关交通管理部门对累积记分达到规定分值的机动车驾驶人，扣留机动车驾驶证，对其进行道路交通安全法律、法规教育，重新考。', '', '', '');
INSERT INTO `et_question` VALUES ('40', '驾驶人出现下列哪种情', '{\"title\":\"驾驶人出现下列哪种情况，不得驾驶机动车？\",\"titleImg\":\"\",\"choiceList\":{\"A\":\"记分达到10分\",\"B\":\"驾驶证丢失、损毁\",\"C\":\"驾驶证接近有效期\",\"D\":\"记分达到6分\"},\"choiceImgList\":{}}', '1', null, '0', null, '0', '2015-12-11 23:33:44', 'admin', null, 'B', '2', '1', '1', '1', '驾驶证丢失、损毁的应当向机动车驾驶证核发地车辆管理所申请补发、换证，在此期间不得驾驶机动车。', '', '', '');
INSERT INTO `et_question` VALUES ('41', '驾驶人在驾驶证丢失后', '{\"title\":\"驾驶人在驾驶证丢失后3个月内还可以驾驶机动车。\",\"titleImg\":\"\",\"choiceList\":{},\"choiceImgList\":{}}', '3', null, '0', null, '0', '2015-12-11 23:34:10', 'admin', null, 'F', '2', '1', '1', '1', '机动车驾驶证遗失的，机动车驾驶人应当向机动车驾驶证核发地车辆管理所申请补发。在此期间，不符合“随身携带机动车驾驶证”的规定，故不得驾驶机动车。', '', '', '');
INSERT INTO `et_question` VALUES ('42', '驾驶人持超过有效期的', '{\"title\":\"驾驶人持超过有效期的驾驶证可以在1年内驾驶机动车。\",\"titleImg\":\"\",\"choiceList\":{},\"choiceImgList\":{}}', '3', null, '0', null, '0', '2015-12-11 23:34:25', 'admin', null, 'F', '2', '1', '1', '1', '《实施条例》第二十八条：\n机动车驾驶人在机动车驾驶证丢失、损毁、超过有效期或者被依法扣留、暂扣期间以及记分达到12分的，不得驾驶机动车。', '', '', '');
INSERT INTO `et_question` VALUES ('43', '驾驶人的驾驶证损毁后', '{\"title\":\"驾驶人的驾驶证损毁后不得驾驶机动车。\",\"titleImg\":\"\",\"choiceList\":{},\"choiceImgList\":{}}', '3', null, '0', null, '0', '2015-12-11 23:34:52', 'admin', null, 'T', '2', '1', '1', '1', '《实施条例》第二十八条：\n机动车驾驶人在机动车驾驶证丢失、损毁、超过有效期或者被依法扣留、暂扣期间以及记分达到12分的，不得驾驶机动车。', '', '', '');
INSERT INTO `et_question` VALUES ('44', '记分满12分的驾驶人', '{\"title\":\"记分满12分的驾驶人拒不参加学习和考试的将被公告驾驶证停止使用。\",\"titleImg\":\"\",\"choiceList\":{},\"choiceImgList\":{}}', '3', null, '0', null, '0', '2015-12-11 23:35:07', 'admin', null, 'T', '2', '1', '1', '1', '《实施条例》第二十五条：\n机动车驾驶人记分达到12分，拒不参加公安机关交通管理部门通知的学习，也不接受考试的，由公安机关交通管理部门公告其机动车驾驶证停止使用。', '', '', '');
INSERT INTO `et_question` VALUES ('45', '驾驶人的机动车驾驶证', '{\"title\":\"驾驶人的机动车驾驶证被依法扣留、暂扣的情况下不得驾驶机动车。\",\"titleImg\":\"\",\"choiceList\":{},\"choiceImgList\":{}}', '3', null, '0', null, '0', '2015-12-11 23:35:23', 'admin', null, 'T', '2', '1', '1', '1', '《道路交通安全法实施条例》第二十八条：机动车驾驶人在机动车驾驶证丢失、损毁、超过有效期或者被依法扣留、暂扣期间以及记分达到12分的，不得驾驶机动车。', '', '', '');
INSERT INTO `et_question` VALUES ('47', '前方路口这种信号灯亮', '{\"title\":\"前方路口这种信号灯亮表示什么意思？\",\"titleImg\":\"files\\\\question\\\\admin\\\\1449848304636.jpg\",\"choiceList\":{\"A\":\"提醒注意\",\"B\":\"路口警示\",\"C\":\"禁止通行\",\"D\":\"准许通行\"},\"choiceImgList\":{}}', '1', null, '0', null, '0', '2015-12-11 23:38:42', 'admin', null, 'C', '2', '1', '1', '1', '红灯停。但此题不经推敲，像这种情况，右转弯的车子在确认安全后是可以通行的。', '', '', '');
INSERT INTO `et_question` VALUES ('49', '前方路口这种信号灯亮', '{\"title\":\"前方路口这种信号灯亮表示什么意思？\",\"titleImg\":\"files\\\\question\\\\admin\\\\1449848817103.jpg\",\"choiceList\":{\"A\":\"加速直行\",\"B\":\"禁止右转\",\"C\":\"加速左转\",\"D\":\"路口警示\"},\"choiceImgList\":{}}', '1', null, '0', null, '0', '2015-12-11 23:47:33', 'admin', null, 'D', '2', '1', '1', '1', '黄灯表示警示，所以是路口警示。', '', '', '');

-- ----------------------------
-- Table structure for `et_question_2_point`
-- ----------------------------
DROP TABLE IF EXISTS `et_question_2_point`;
CREATE TABLE `et_question_2_point` (
  `question_2_point_id` int(10) NOT NULL AUTO_INCREMENT,
  `question_id` int(10) DEFAULT NULL,
  `point_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`question_2_point_id`),
  KEY `fk_question_111` (`question_id`),
  KEY `fk_point_111` (`point_id`),
  CONSTRAINT `et_question_2_point_ibfk_1` FOREIGN KEY (`point_id`) REFERENCES `et_knowledge_point` (`point_id`) ON DELETE CASCADE,
  CONSTRAINT `et_question_2_point_ibfk_2` FOREIGN KEY (`question_id`) REFERENCES `et_question` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of et_question_2_point
-- ----------------------------
INSERT INTO `et_question_2_point` VALUES ('1', '1', '1');
INSERT INTO `et_question_2_point` VALUES ('2', '2', '1');
INSERT INTO `et_question_2_point` VALUES ('3', '3', '1');
INSERT INTO `et_question_2_point` VALUES ('4', '4', '1');
INSERT INTO `et_question_2_point` VALUES ('5', '5', '1');
INSERT INTO `et_question_2_point` VALUES ('6', '6', '1');
INSERT INTO `et_question_2_point` VALUES ('7', '7', '1');
INSERT INTO `et_question_2_point` VALUES ('8', '8', '1');
INSERT INTO `et_question_2_point` VALUES ('9', '9', '1');
INSERT INTO `et_question_2_point` VALUES ('10', '10', '1');
INSERT INTO `et_question_2_point` VALUES ('11', '11', '1');
INSERT INTO `et_question_2_point` VALUES ('12', '12', '1');
INSERT INTO `et_question_2_point` VALUES ('13', '13', '1');
INSERT INTO `et_question_2_point` VALUES ('14', '14', '1');
INSERT INTO `et_question_2_point` VALUES ('15', '15', '1');
INSERT INTO `et_question_2_point` VALUES ('16', '16', '1');
INSERT INTO `et_question_2_point` VALUES ('17', '17', '1');
INSERT INTO `et_question_2_point` VALUES ('18', '18', '1');
INSERT INTO `et_question_2_point` VALUES ('19', '19', '1');
INSERT INTO `et_question_2_point` VALUES ('20', '20', '1');
INSERT INTO `et_question_2_point` VALUES ('21', '21', '1');
INSERT INTO `et_question_2_point` VALUES ('22', '22', '1');
INSERT INTO `et_question_2_point` VALUES ('23', '23', '1');
INSERT INTO `et_question_2_point` VALUES ('24', '24', '1');
INSERT INTO `et_question_2_point` VALUES ('25', '25', '1');
INSERT INTO `et_question_2_point` VALUES ('26', '26', '1');
INSERT INTO `et_question_2_point` VALUES ('27', '27', '1');
INSERT INTO `et_question_2_point` VALUES ('28', '28', '1');
INSERT INTO `et_question_2_point` VALUES ('29', '29', '1');
INSERT INTO `et_question_2_point` VALUES ('30', '30', '1');
INSERT INTO `et_question_2_point` VALUES ('31', '31', '1');
INSERT INTO `et_question_2_point` VALUES ('32', '32', '1');
INSERT INTO `et_question_2_point` VALUES ('33', '33', '1');
INSERT INTO `et_question_2_point` VALUES ('34', '34', '1');
INSERT INTO `et_question_2_point` VALUES ('35', '35', '1');
INSERT INTO `et_question_2_point` VALUES ('36', '36', '1');
INSERT INTO `et_question_2_point` VALUES ('37', '37', '1');
INSERT INTO `et_question_2_point` VALUES ('38', '38', '1');
INSERT INTO `et_question_2_point` VALUES ('39', '39', '1');
INSERT INTO `et_question_2_point` VALUES ('40', '40', '1');
INSERT INTO `et_question_2_point` VALUES ('41', '41', '1');
INSERT INTO `et_question_2_point` VALUES ('42', '42', '1');
INSERT INTO `et_question_2_point` VALUES ('43', '43', '1');
INSERT INTO `et_question_2_point` VALUES ('44', '44', '1');
INSERT INTO `et_question_2_point` VALUES ('45', '45', '1');
INSERT INTO `et_question_2_point` VALUES ('47', '47', '1');
INSERT INTO `et_question_2_point` VALUES ('49', '49', '1');

-- ----------------------------
-- Table structure for `et_question_2_tag`
-- ----------------------------
DROP TABLE IF EXISTS `et_question_2_tag`;
CREATE TABLE `et_question_2_tag` (
  `question_tag_id` int(11) NOT NULL AUTO_INCREMENT,
  `question_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `creator` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`question_tag_id`),
  KEY `fk_question_tag_tid` (`tag_id`),
  KEY `fk_question_tag_qid` (`question_id`),
  CONSTRAINT `et_question_2_tag_ibfk_1` FOREIGN KEY (`question_id`) REFERENCES `et_question` (`id`) ON DELETE CASCADE,
  CONSTRAINT `et_question_2_tag_ibfk_2` FOREIGN KEY (`tag_id`) REFERENCES `et_tag` (`tag_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of et_question_2_tag
-- ----------------------------

-- ----------------------------
-- Table structure for `et_question_type`
-- ----------------------------
DROP TABLE IF EXISTS `et_question_type`;
CREATE TABLE `et_question_type` (
  `id` int(11) NOT NULL,
  `name` varchar(20) NOT NULL,
  `subjective` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of et_question_type
-- ----------------------------
INSERT INTO `et_question_type` VALUES ('1', '单选题', '0');
INSERT INTO `et_question_type` VALUES ('2', '多选题', '0');
INSERT INTO `et_question_type` VALUES ('3', '判断题', '0');
INSERT INTO `et_question_type` VALUES ('4', '填空题', '0');
INSERT INTO `et_question_type` VALUES ('5', '简答题', '1');
INSERT INTO `et_question_type` VALUES ('6', '论述题', '1');
INSERT INTO `et_question_type` VALUES ('7', '分析题', '1');

-- ----------------------------
-- Table structure for `et_reference`
-- ----------------------------
DROP TABLE IF EXISTS `et_reference`;
CREATE TABLE `et_reference` (
  `reference_id` int(5) NOT NULL,
  `reference_name` varchar(200) NOT NULL,
  `memo` varchar(200) DEFAULT NULL,
  `state` decimal(10,0) NOT NULL DEFAULT '1' COMMENT '1 正常 0 废弃',
  PRIMARY KEY (`reference_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of et_reference
-- ----------------------------

-- ----------------------------
-- Table structure for `et_role`
-- ----------------------------
DROP TABLE IF EXISTS `et_role`;
CREATE TABLE `et_role` (
  `id` int(11) NOT NULL,
  `authority` varchar(20) NOT NULL,
  `name` varchar(20) NOT NULL,
  `code` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of et_role
-- ----------------------------
INSERT INTO `et_role` VALUES ('1', 'ROLE_ADMIN', '超级管理员', 'admin');
INSERT INTO `et_role` VALUES ('2', 'ROLE_TEACHER', '教师', 'teacher');
INSERT INTO `et_role` VALUES ('3', 'ROLE_STUDENT', '学员', 'student');

-- ----------------------------
-- Table structure for `et_tag`
-- ----------------------------
DROP TABLE IF EXISTS `et_tag`;
CREATE TABLE `et_tag` (
  `tag_id` int(11) NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(100) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `creator` int(11) NOT NULL,
  `is_private` tinyint(1) NOT NULL DEFAULT '0',
  `memo` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`tag_id`),
  KEY `fk_tag_creator` (`creator`),
  CONSTRAINT `et_tag_ibfk_1` FOREIGN KEY (`creator`) REFERENCES `et_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of et_tag
-- ----------------------------
INSERT INTO `et_tag` VALUES ('3', '易错题', '2015-08-07 20:42:00', '1', '0', '易错题');
INSERT INTO `et_tag` VALUES ('4', '简单', '2015-08-16 17:46:42', '1', '0', '简单');
INSERT INTO `et_tag` VALUES ('6', '送分题', '2015-08-16 22:23:59', '1', '0', '送分题');

-- ----------------------------
-- Table structure for `et_training`
-- ----------------------------
DROP TABLE IF EXISTS `et_training`;
CREATE TABLE `et_training` (
  `training_id` int(11) NOT NULL AUTO_INCREMENT,
  `training_name` varchar(40) NOT NULL,
  `training_desc` mediumtext,
  `is_private` tinyint(1) NOT NULL DEFAULT '0',
  `field_id` int(11) NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator` int(11) DEFAULT NULL COMMENT '创建人',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0:未发布；1：发布；2：失效',
  `state_time` timestamp NULL DEFAULT NULL,
  `exp_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`training_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of et_training
-- ----------------------------

-- ----------------------------
-- Table structure for `et_training_section`
-- ----------------------------
DROP TABLE IF EXISTS `et_training_section`;
CREATE TABLE `et_training_section` (
  `section_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '1',
  `section_name` varchar(200) NOT NULL,
  `section_desc` mediumtext,
  `training_id` int(11) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_id` int(11) NOT NULL,
  `file_name` varchar(200) DEFAULT NULL,
  `file_path` varchar(200) DEFAULT NULL,
  `file_md5` varchar(200) DEFAULT NULL,
  `file_type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`section_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of et_training_section
-- ----------------------------

-- ----------------------------
-- Table structure for `et_user`
-- ----------------------------
DROP TABLE IF EXISTS `et_user`;
CREATE TABLE `et_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `user_name` varchar(50) NOT NULL COMMENT '账号',
  `true_name` varchar(50) NOT NULL COMMENT '真实姓名',
  `national_id` varchar(20) NOT NULL,
  `password` char(80) NOT NULL,
  `email` varchar(60) NOT NULL,
  `phone_num` varchar(20) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_by` int(11) DEFAULT NULL COMMENT '创建人',
  `enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '激活状态：0-未激活 1-激活',
  `field_id` int(10) NOT NULL,
  `last_login_time` timestamp NULL DEFAULT NULL,
  `login_time` timestamp NULL DEFAULT NULL,
  `department` varchar(100) DEFAULT NULL,
  `company` varchar(100) DEFAULT NULL COMMENT '1',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `idx_user_uid` (`user_name`),
  KEY `idx_user_national_id` (`national_id`),
  KEY `idx_user_email` (`email`),
  KEY `idx_user_phone` (`phone_num`)
) ENGINE=InnoDB AUTO_INCREMENT=4714 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of et_user
-- ----------------------------
INSERT INTO `et_user` VALUES ('1', 'admin', 'admin', '000000000000000000', '260acbffd3c30786febc29d7dd71a9880a811e77', '1111@111.com', '18908600000', '2015-09-29 14:38:23', '0', '1', '1', '2015-08-06 11:31:34', '2015-08-06 11:31:50', '', '');
INSERT INTO `et_user` VALUES ('2', 'student', '学员', '420000000000000000', '3f70af5072e23c9bf59dd1ac1c91f9f8fcc81478', '133@189.cn', '13333333333', '2015-12-11 21:32:07', '1', '1', '0', '2015-08-06 11:31:34', '2015-08-06 11:31:34', '', '');

-- ----------------------------
-- Table structure for `et_user_2_department`
-- ----------------------------
DROP TABLE IF EXISTS `et_user_2_department`;
CREATE TABLE `et_user_2_department` (
  `user_id` int(11) NOT NULL,
  `dep_id` int(11) NOT NULL,
  KEY `fk_ud_uid` (`user_id`),
  KEY `fk_ud_did` (`dep_id`),
  CONSTRAINT `fk_ud_did` FOREIGN KEY (`dep_id`) REFERENCES `et_department` (`dep_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_ud_uid` FOREIGN KEY (`user_id`) REFERENCES `et_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of et_user_2_department
-- ----------------------------
INSERT INTO `et_user_2_department` VALUES ('2', '1');

-- ----------------------------
-- Table structure for `et_user_2_group`
-- ----------------------------
DROP TABLE IF EXISTS `et_user_2_group`;
CREATE TABLE `et_user_2_group` (
  `group_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_admin` tinyint(4) DEFAULT '0',
  UNIQUE KEY `idx_user_guid` (`group_id`,`user_id`) USING BTREE,
  KEY `idx_user_gid` (`group_id`),
  KEY `idx_user_uid` (`user_id`),
  CONSTRAINT `fk_et_user_group_et_group_1` FOREIGN KEY (`group_id`) REFERENCES `et_group` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_et_user_group_et_user_1` FOREIGN KEY (`user_id`) REFERENCES `et_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of et_user_2_group
-- ----------------------------
INSERT INTO `et_user_2_group` VALUES ('1', '2', '2015-12-11 22:31:58', '0');

-- ----------------------------
-- Table structure for `et_user_2_role`
-- ----------------------------
DROP TABLE IF EXISTS `et_user_2_role`;
CREATE TABLE `et_user_2_role` (
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  KEY `user_id` (`user_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `et_r_user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `et_user` (`user_id`),
  CONSTRAINT `fk_user_rid` FOREIGN KEY (`role_id`) REFERENCES `et_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of et_user_2_role
-- ----------------------------
INSERT INTO `et_user_2_role` VALUES ('1', '1');
INSERT INTO `et_user_2_role` VALUES ('2', '3');

-- ----------------------------
-- Table structure for `et_user_exam_history`
-- ----------------------------
DROP TABLE IF EXISTS `et_user_exam_history`;
CREATE TABLE `et_user_exam_history` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `start_time` timestamp NULL DEFAULT NULL,
  `exam_id` int(10) NOT NULL,
  `exam_paper_id` int(10) NOT NULL,
  `enabled` tinyint(4) DEFAULT NULL,
  `point` int(5) DEFAULT '0',
  `seri_no` varchar(100) NOT NULL,
  `content` mediumtext,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `answer_sheet` mediumtext,
  `duration` int(10) NOT NULL,
  `point_get` float(10,1) NOT NULL DEFAULT '0.0',
  `submit_time` timestamp NULL DEFAULT NULL,
  `approved` tinyint(4) DEFAULT '0',
  `verify_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_exam_his_seri_no` (`seri_no`),
  UNIQUE KEY `idx_exam_pid` (`exam_id`,`exam_paper_id`,`user_id`) USING BTREE,
  KEY `fk_exam_his_uid` (`user_id`),
  KEY `fk_exam_paper_id` (`exam_paper_id`),
  CONSTRAINT `fk_exam_his_uid` FOREIGN KEY (`user_id`) REFERENCES `et_user` (`user_id`),
  CONSTRAINT `fk_exam_id` FOREIGN KEY (`exam_id`) REFERENCES `et_exam` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_exam_paper_id` FOREIGN KEY (`exam_paper_id`) REFERENCES `et_exam_paper` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of et_user_exam_history
-- ----------------------------

-- ----------------------------
-- Table structure for `et_user_question_history`
-- ----------------------------
DROP TABLE IF EXISTS `et_user_question_history`;
CREATE TABLE `et_user_question_history` (
  `question_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `question_type_id` int(11) NOT NULL,
  `is_right` tinyint(4) NOT NULL DEFAULT '1',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY `idx_hist_uqid` (`question_id`,`user_id`) USING BTREE,
  KEY `fk_hist_uid` (`user_id`),
  KEY `fk_hist_qid` (`question_id`),
  CONSTRAINT `fk_hist_qid` FOREIGN KEY (`question_id`) REFERENCES `et_question` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_hist_uid` FOREIGN KEY (`user_id`) REFERENCES `et_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of et_user_question_history
-- ----------------------------

-- ----------------------------
-- Table structure for `et_user_training_history`
-- ----------------------------
DROP TABLE IF EXISTS `et_user_training_history`;
CREATE TABLE `et_user_training_history` (
  `training_id` int(11) NOT NULL COMMENT '培训ID',
  `section_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `duration` float(11,4) NOT NULL DEFAULT '0.0000',
  `process` float(11,2) NOT NULL,
  `start_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `last_state_time` timestamp NULL DEFAULT NULL,
  `user_training_detail` mediumtext,
  PRIMARY KEY (`section_id`,`user_id`),
  UNIQUE KEY `et_r_user_training_history_uk_1` (`user_id`,`section_id`) USING BTREE,
  CONSTRAINT `fk_user_training_history_sid` FOREIGN KEY (`section_id`) REFERENCES `et_training_section` (`section_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_user_training_history_uid` FOREIGN KEY (`user_id`) REFERENCES `et_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户培训历史记录';

-- ----------------------------
-- Records of et_user_training_history
-- ----------------------------

-- ----------------------------
-- Table structure for `et_view_type`
-- ----------------------------
DROP TABLE IF EXISTS `et_view_type`;
CREATE TABLE `et_view_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='培训视图表现形式';

-- ----------------------------
-- Records of et_view_type
-- ----------------------------
INSERT INTO `et_view_type` VALUES ('1', 'PDF');
INSERT INTO `et_view_type` VALUES ('2', 'PPT');
INSERT INTO `et_view_type` VALUES ('3', 'WORD');
INSERT INTO `et_view_type` VALUES ('4', 'TXT');
INSERT INTO `et_view_type` VALUES ('5', 'SWF');
INSERT INTO `et_view_type` VALUES ('6', 'EXCEL');
INSERT INTO `et_view_type` VALUES ('7', 'MP4');
INSERT INTO `et_view_type` VALUES ('8', 'FLV');

-- ----------------------------
-- Table structure for `t_c3p0`
-- ----------------------------
DROP TABLE IF EXISTS `t_c3p0`;
CREATE TABLE `t_c3p0` (
  `a` char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_c3p0
-- ----------------------------
