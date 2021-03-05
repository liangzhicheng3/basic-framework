DROP TABLE IF EXISTS `test_department`;
CREATE TABLE `test_department` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `dept_name` varchar(255) DEFAULT NULL COMMENT '部门名称',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1052 DEFAULT CHARSET=utf8mb4 COMMENT='测试部门';


INSERT INTO `test_department` VALUES ('1', '研发部', sysdate(), sysdate());
INSERT INTO `test_department` VALUES ('2', '运营部', sysdate(), sysdate());
INSERT INTO `test_department` VALUES ('3', '客服部', sysdate(), sysdate());
INSERT INTO `test_department` VALUES ('4', '推广部', sysdate(), sysdate());

