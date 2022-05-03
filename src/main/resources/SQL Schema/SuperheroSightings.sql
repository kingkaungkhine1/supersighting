DROP DATABASE IF EXISTS shSighting;
CREATE DATABASE shSighting;

USE shSighting;

CREATE TABLE `super` (
	super_id INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	alignment VARCHAR(150),
	description VARCHAR(150)
);

CREATE TABLE ability (
	ability_id INT PRIMARY KEY AUTO_INCREMENT,
	`name` VARCHAR(50) NOT NULL,
	description VARCHAR(150)
);

CREATE TABLE ability_super (
	super_id INT NOT NULL,
	ability_id INT NOT NULL,
    PRIMARY KEY (super_id, ability_id),
	CONSTRAINT fk_AbilitySuper_super_id
		FOREIGN KEY (super_id)
		REFERENCES super(super_id),
	CONSTRAINT fk_AbilitySuper_ability_id
		FOREIGN KEY (ability_id)
		REFERENCES ability(ability_id)
);

CREATE TABLE location (
	location_id INT PRIMARY KEY AUTO_INCREMENT,
	`name` VARCHAR(50) NOT NULL,
    description VARCHAR(50) NOT NULL,
    address VARCHAR(50) NOT NULL,
    latitude DECIMAL(7,4) NOT NULL,
    longitude DECIMAL(7,4) NOT NULL
);

CREATE TABLE organization (
	organization_id INT PRIMARY KEY AUTO_INCREMENT,
	`name` VARCHAR(50) NOT NULL,
	description VARCHAR(500),
	location_id INT,
    alignment VARCHAR(150),
	CONSTRAINT fk_Organization_Location
		FOREIGN KEY (location_id)
		REFERENCES location(location_id)
);

CREATE TABLE organization_super (
	super_id INT NOT NULL,
	organization_id INT NOT NULL,
    PRIMARY KEY (super_id, organization_id),
	CONSTRAINT fk_OrganizationSuper_super_id
		FOREIGN KEY (super_id)
		REFERENCES super(super_id),
	CONSTRAINT fk_OrganizationSuper_organization_id
		FOREIGN KEY (organization_id)
		REFERENCES organization(organization_id)
);

CREATE TABLE sighting (
	sighting_id INT PRIMARY KEY AUTO_INCREMENT,
	description VARCHAR(500),
	`date` DATE NOT NULL,
	location_id INT NOT NULL,
	CONSTRAINT fk_Sighting_Location
		FOREIGN KEY (location_id)
		REFERENCES location(location_id)
);

CREATE TABLE sighting_super (
	super_id INT NOT NULL,
	sighting_id INT NOT NULL,
    PRIMARY KEY (super_id, sighting_id),
	CONSTRAINT fk_SightingSuper_super_id
		FOREIGN KEY (super_id)
		REFERENCES `super`(super_id),
	CONSTRAINT fk_SightingSuper_sighting_id
		FOREIGN KEY (sighting_id)
		REFERENCES sighting(sighting_id)
)