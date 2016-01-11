-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema RefugiApp
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `RefugiApp` ;

-- -----------------------------------------------------
-- Schema RefugiApp
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `RefugiApp` DEFAULT CHARACTER SET latin1 ;
USE `RefugiApp` ;

-- -----------------------------------------------------
-- Table `RefugiApp`.`Users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `RefugiApp`.`Users` ;

CREATE TABLE IF NOT EXISTS `RefugiApp`.`Users` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `RefugiApp`.`Content`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `RefugiApp`.`Content` ;

CREATE TABLE IF NOT EXISTS `RefugiApp`.`Content` (
  `idContent` INT(11) NOT NULL AUTO_INCREMENT,
  `user` INT(11) NOT NULL,
  `phrase` VARCHAR(45) NULL DEFAULT NULL,
  `audio` VARCHAR(200) NOT NULL,
  `Users_id` INT(11) NOT NULL,
  PRIMARY KEY (`idContent`),
  UNIQUE INDEX `idContent_UNIQUE` (`idContent` ASC),
  INDEX `fk_Content_Users1_idx` (`Users_id` ASC),
  CONSTRAINT `fk_Content_Users1`
    FOREIGN KEY (`Users_id`)
    REFERENCES `RefugiApp`.`Users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `RefugiApp`.`Enviroment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `RefugiApp`.`Enviroment` ;

CREATE TABLE IF NOT EXISTS `RefugiApp`.`Enviroment` (
  `idenviroments` INT(11) NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idenviroments`),
  UNIQUE INDEX `idenviroments_UNIQUE` (`idenviroments` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `RefugiApp`.`Phrase`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `RefugiApp`.`Phrase` ;

CREATE TABLE IF NOT EXISTS `RefugiApp`.`Phrase` (
  `idPhrase` INT(11) NOT NULL AUTO_INCREMENT,
  `type` INT(11) NOT NULL,
  `phrase_es` VARCHAR(120) NOT NULL,
  `phrase_ar` VARCHAR(120) NOT NULL,
  `audioFrase` VARCHAR(200) NULL DEFAULT NULL,
  `Enviroment_idenviroments` INT(11) NOT NULL,
  PRIMARY KEY (`idPhrase`),
  UNIQUE INDEX `idPhrase_UNIQUE` (`idPhrase` ASC),
  INDEX `fk_Phrase_Enviroment_idx` (`Enviroment_idenviroments` ASC),
  CONSTRAINT `fk_Phrase_Enviroment`
    FOREIGN KEY (`Enviroment_idenviroments`)
    REFERENCES `RefugiApp`.`Enviroment` (`idenviroments`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
