DROP TABLE IF EXISTS `test_department_person`;
CREATE TABLE `test_department_person` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门id',
  `person_id` bigint(20) DEFAULT NULL COMMENT '人员id',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1052 DEFAULT CHARSET=utf8 COMMENT='测试部门人员';


INSERT INTO `test_department_person` VALUES ('991', '1', '386', sysdate(), sysdate());
INSERT INTO `test_department_person` VALUES ('992', '1', '387', sysdate(), sysdate());
INSERT INTO `test_department_person` VALUES ('993', '2', '388', sysdate(), sysdate());
INSERT INTO `test_department_person` VALUES ('994', '3', '389', sysdate(), sysdate());
INSERT INTO `test_department_person` VALUES ('995', '4', '390', sysdate(), sysdate());

