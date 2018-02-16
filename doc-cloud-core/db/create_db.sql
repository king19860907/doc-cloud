SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `doc_cloud` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `doc_cloud` ;

-- -----------------------------------------------------
-- Table `doc_cloud`.`dc_user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `doc_cloud`.`dc_user` (
  `user_id` INT(20) NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(100) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `create_time` TIMESTAMP NOT NULL DEFAULT now(),
  `update_time` TIMESTAMP NOT NULL DEFAULT now(),
  `password_salt` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `user_name_UNIQUE` (`user_name` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `doc_cloud`.`dc_session`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `doc_cloud`.`dc_session` (
  `id` VARCHAR(50) NOT NULL,
  `session` LONGTEXT NOT NULL,
  `create_time` TIMESTAMP NOT NULL DEFAULT now(),
  `update_time` TIMESTAMP NOT NULL DEFAULT now(),
  `user_id` INT(20) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_dc_session_dc_user_idx` (`user_id` ASC),
  CONSTRAINT `fk_dc_session_dc_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `doc_cloud`.`dc_user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `doc_cloud`.`dc_repository`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `doc_cloud`.`dc_repository` (
  `id` INT(20) NOT NULL AUTO_INCREMENT COMMENT '主键id\n',
  `user_id` INT(20) NOT NULL,
  `name` VARCHAR(50) NOT NULL COMMENT '仓库名称',
  `display_name` VARCHAR(100) NOT NULL COMMENT '显示名称',
  `description` VARCHAR(500) NULL COMMENT '仓库描述',
  `private` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否为私有仓库',
  `create_time` TIMESTAMP NOT NULL DEFAULT now() COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT now() COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `fk_dc_repository_dc_user1_idx` (`user_id` ASC),
  UNIQUE INDEX `user_id_name_unique_idx` (`user_id` ASC, `name` ASC),
  CONSTRAINT `fk_dc_repository_dc_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `doc_cloud`.`dc_user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
