/*
Navicat MySQL Data Transfer

Source Server         : localhost_3434
Source Server Version : 50620
Source Host           : localhost:3434
Source Database       : kij

Target Server Type    : MYSQL
Target Server Version : 50620
File Encoding         : 65001

Date: 2016-04-27 11:40:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for group
-- ----------------------------
DROP TABLE IF EXISTS `group`;
CREATE TABLE `group` (
`id`  int(4) NOT NULL AUTO_INCREMENT ,
`nama_group`  varchar(30) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`username`  varchar(30) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`key`  text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci
AUTO_INCREMENT=6

;

-- ----------------------------
-- Records of group
-- ----------------------------
BEGIN;
INSERT INTO `group` VALUES ('1', 'group1', 'hanumuslem', null), ('2', 'group1', 'usaid', null), ('3', 'group2', 'hanumuslem', null), ('4', 'group2', 'usaid', null), ('5', 'group1', 'zaenal', null);
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
`id`  int(4) NOT NULL AUTO_INCREMENT ,
`username`  varchar(30) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`password`  varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`public_key`  text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci
AUTO_INCREMENT=7

;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES ('1', 'andi', 'ce0e5bf55e4f71749eade7a8b95c4e46', null), ('2', 'budi', '00dfc53ee86af02e742515cdcf075ed3', null), ('3', 'usaid', '70f9ffcc70c558ce7ff6240850cfa49b', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCdud35FyCBiwTFu8SVTjqapQ6+EIuqxkTpsnE3\r\nD4iL5QCtkdyHsbxnifwSFtaaOTTmyVvsTS6p1PmpfktjallGfWYYF2x/7o2626UUMtLe6i9wOP42\r\nJNUyGxAMRB0ra796d4knBT8G+rXjBQ37pdlfglvM6R2xZDya1XZyWBG1dQIDAQAB'), ('4', 'zaenal', '3885dac848498975dddca25e42a53cb5', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCKI/guY4oTdwJzCJtdj7MuWXIOxSFH1QKtf2Bf\r\nrEtyM2mDXgohlu57i5stiMs9TCNlIwf2Qz/rPDVLiojThA6xiepktZ6GLH87v6VSo5fWohVcsQlQ\r\ngFhVcQiI1QaOC7vNx2N3lHuLG+whMxGlRg9oJv4FnP6J0Eu5C/lmQ4lgrQIDAQAB'), ('5', 'hanumuslem', '89a9a878f28ce610e5e8d804d53de632', 'MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJKdmoGU+03wuzUnJ0mnqT6z1nr2\n4lcR+6qm9gsgCEaw/hgBjB44dja6vmmdX5xB170ywabTfk8mp4/KdVIgSZvEWQ/oQm/utjoQuKsx\nhKZFLFq77m/VirKKCacYqrGvjGSYvfprWEGwEvPiouGX35WJRcjOEcxyhH+zCvt9fW+TAgMBAAEC\ngYBe2/11TZDysohNMZHnw8RqvyxLzqROtWHg3blfJVB1fFpJ6eZgPyOs8VH1P0726FrPHKHScA/O\n4jGThOg3RJCaboMdYTUZtKUTGsEBc18YIaldtQF+6PYv+mMns8qE0VqrzYrZVRalzWWO0/5m4S4d\nZSAHlVsRjrglQKhY1icgEQJBAMuoWPZWRwHaKNih8anVyW/H9o/kJtYAE2ip7dmtAoNOoNs08uES\n1Z6T2lovyyIhysp+a2uCJLqS8uYokk10390CQQC4TC39UXyndeD+M3Y1zMNZ2EQQB/3OFpBlQI7I\n9Rt/XjPwZyRZ6UweLdJ8q0qHiKWYaIAFADlUwAHAw314b04vAkAHNZ5wmf1Jg3+Oq6zyZi8LVfjU\nfy8gxD/rpJhW7Fj6bP4kxGsUrKYDRxijuK1oN+oF4RPMan8CW9FddHI5a7nBAkBLGPewfpDt5/HL\ng4rKt2pu5NovMiJw5O7n41uLdZ1D1E4TyuOcI52ZupBHrsDK45qetQ5de07hQvAz7TTma2yPAkBs\ntnOexVSbl7900+mcNz9tpXvm0fAWwKIiDEHdaCgTGirGt5+Yoy0OvChOk83O9S3/tD5dRnOd5tKZ\nkdbk70iF'), ('6', 'jabir', '9d521df85be3e30b9163daece191e805', null);
COMMIT;

-- ----------------------------
-- Auto increment value for group
-- ----------------------------
ALTER TABLE `group` AUTO_INCREMENT=6;

-- ----------------------------
-- Auto increment value for user
-- ----------------------------
ALTER TABLE `user` AUTO_INCREMENT=7;
