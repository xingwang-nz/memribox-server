SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `memribox` ;
CREATE SCHEMA IF NOT EXISTS `memribox` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
USE `memribox` ;

-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user` ;

CREATE TABLE IF NOT EXISTS `user` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(64) NOT NULL,
  `first_name` VARCHAR(64) NOT NULL,
  `last_name` VARCHAR(64) NOT NULL,
  `email` VARCHAR(127) NULL,
  `password` VARCHAR(64) NOT NULL,
  `created_time` DATETIME NOT NULL,
  `gender` VARCHAR(1) NOT NULL,
  `birthday` DATE NULL,
  `native_tongue` VARCHAR(128) NULL,
  `home_country` VARCHAR(64) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `user_name_UNIQUE` (`user_name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `story`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `story` ;

CREATE TABLE IF NOT EXISTS `story` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` INT UNSIGNED NOT NULL,
  `title` VARCHAR(512) NOT NULL,
  `summary` VARCHAR(1024) NOT NULL,
  `time_line` DATE NOT NULL,
  `created_time` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_story_user_idx` (`user_id` ASC),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  CONSTRAINT `story_user_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `story_data`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `story_data` ;

CREATE TABLE IF NOT EXISTS `story_data` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `story_id` INT UNSIGNED NOT NULL,
  `type` VARCHAR(16) NOT NULL,
  `created_time` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `story_data_story_fk_idx` (`story_id` ASC),
  CONSTRAINT `story_data_story_fk`
    FOREIGN KEY (`story_id`)
    REFERENCES `story` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `story_text`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `story_text` ;

CREATE TABLE IF NOT EXISTS `story_text` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `text` TEXT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `legacy_data_id_fk_idx` (`id` ASC),
  CONSTRAINT `story_text_story_data_fk`
    FOREIGN KEY (`id`)
    REFERENCES `story_data` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `story_file`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `story_file` ;

CREATE TABLE IF NOT EXISTS `story_file` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `file_name` VARCHAR(127) NOT NULL,
  `stored_file_name` VARCHAR(127) NULL,
  `file_type` VARCHAR(16) NULL,
  `meta_info` VARCHAR(512) NULL,
  PRIMARY KEY (`id`),
  INDEX `legacy_binary_data-fk_idx` (`id` ASC),
  CONSTRAINT `story_file_story__data_fk`
    FOREIGN KEY (`id`)
    REFERENCES `story_data` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `phone`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `phone` ;

CREATE TABLE IF NOT EXISTS `phone` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` INT UNSIGNED NOT NULL,
  `type` VARCHAR(16) NOT NULL,
  `number` VARCHAR(32) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `phone_user_fk_idx` (`user_id` ASC),
  CONSTRAINT `phone_user_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `role` ;

CREATE TABLE IF NOT EXISTS `role` (
  `id` INT UNSIGNED NOT NULL,
  `code` VARCHAR(64) NOT NULL,
  `description` VARCHAR(127) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `code_UNIQUE` (`code` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `user_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user_role` ;

CREATE TABLE IF NOT EXISTS `user_role` (
  `user_id` INT UNSIGNED NULL,
  `role_id` INT UNSIGNED NULL,
  INDEX `fk_user_role_user_id_idx` (`user_id` ASC),
  INDEX `fk_user_role_role_id_idx` (`role_id` ASC),
  CONSTRAINT `fk_user_role_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_role_role_id`
    FOREIGN KEY (`role_id`)
    REFERENCES `role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
