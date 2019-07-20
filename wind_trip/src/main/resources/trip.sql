-- 创建数据库
CREATE DATABASE trip;
USE trip;
-- 创建表
CREATE TABLE tab_category
(
  cid                  INT NOT NULL AUTO_INCREMENT,
  cname                VARCHAR(100) NOT NULL,
  PRIMARY KEY (cid),
  UNIQUE KEY AK_nq_categoryname (cname)
);

CREATE TABLE tab_favorite
(
  rid                  INT NOT NULL,
  DATE                 DATE NOT NULL,
  uid                  INT NOT NULL,
  PRIMARY KEY (rid, uid)
);

CREATE TABLE tab_route
(
  rid                  INT NOT NULL AUTO_INCREMENT,
  rname                VARCHAR(500) NOT NULL,
  price                DOUBLE NOT NULL,
  routeIntroduce       VARCHAR(1000),
  rflag                CHAR(1) NOT NULL,
  rdate                VARCHAR(19),
  isThemeTour          CHAR(1) NOT NULL,
  COUNT                INT DEFAULT 0,
  cid                  INT NOT NULL,
  rimage               VARCHAR(200),
  sid                  INT,
  sourceId             VARCHAR(50),
  PRIMARY KEY (rid),
  UNIQUE KEY AK_nq_sourceId (sourceId)
);

CREATE TABLE tab_route_img
(
  rgid                 INT NOT NULL AUTO_INCREMENT,
  rid                  INT NOT NULL,
  bigPic               VARCHAR(200) NOT NULL,
  smallPic             VARCHAR(200),
  PRIMARY KEY (rgid)
);

CREATE TABLE tab_seller
(
  sid                  INT NOT NULL AUTO_INCREMENT,
  sname                VARCHAR(200) NOT NULL,
  consphone            VARCHAR(20) NOT NULL,
  address              VARCHAR(200),
  PRIMARY KEY (sid),
  UNIQUE KEY AK_Key_2 (sname)
);

CREATE TABLE tab_user
(
  uid                  INT NOT NULL AUTO_INCREMENT,
  username             VARCHAR(100) NOT NULL,
  PASSWORD             VARCHAR(32) NOT NULL,
  NAME                 VARCHAR(100),
  birthday             DATE,
  sex                  CHAR(1),
  telephone            VARCHAR(11),
  email                VARCHAR(100),
  STATUS               CHAR(1) ,
  CODE					VARCHAR(50),

  PRIMARY KEY (uid),
  UNIQUE KEY AK_nq_username (username),
  UNIQUE KEY AK_nq_code (CODE)
);

ALTER TABLE tab_favorite ADD CONSTRAINT FK_route_favorite FOREIGN KEY (rid)
REFERENCES tab_route (rid) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE tab_favorite ADD CONSTRAINT FK_user_favorite FOREIGN KEY (uid)
REFERENCES tab_user (uid) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE tab_route ADD CONSTRAINT FK_category_route FOREIGN KEY (cid)
REFERENCES tab_category (cid) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE tab_route ADD CONSTRAINT FK_seller_route FOREIGN KEY (sid)
REFERENCES tab_seller (sid) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE tab_route_img ADD CONSTRAINT FK_route_routeimg FOREIGN KEY (rid)
REFERENCES tab_route (rid) ON DELETE RESTRICT ON UPDATE RESTRICT;
