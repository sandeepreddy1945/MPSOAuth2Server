--------------- H2 ---------------

drop table if exists oauth_client_details;
create table oauth_client_details (
  client_id VARCHAR(255) PRIMARY KEY,
  resource_ids VARCHAR(255),
  client_secret VARCHAR(255),
  scope VARCHAR(255),
  authorized_grant_types VARCHAR(255),
  web_server_redirect_uri VARCHAR(255),
  authorities VARCHAR(255),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(255)
);

create table if not exists oauth_client_token (
  token_id VARCHAR(255),
  token LONGVARBINARY,
  authentication_id VARCHAR(255) PRIMARY KEY,
  user_name VARCHAR(255),
  client_id VARCHAR(255)
);

create table if not exists oauth_access_token (
  token_id VARCHAR(255),
  token LONGVARBINARY,
  authentication_id VARCHAR(255) PRIMARY KEY,
  user_name VARCHAR(255),
  client_id VARCHAR(255),
  authentication LONGVARBINARY,
  refresh_token VARCHAR(255)
);

create table if not exists oauth_refresh_token (
  token_id VARCHAR(255),
  token LONGVARBINARY,
  authentication LONGVARBINARY
);

create table if not exists oauth_code (
  code VARCHAR(255), authentication LONGVARBINARY
);

create table if not exists oauth_approvals (
	userId VARCHAR(255),
	clientId VARCHAR(255),
	scope VARCHAR(255),
	status VARCHAR(10),
	expiresAt TIMESTAMP,
	lastModifiedAt TIMESTAMP
);

create table if not exists ClientDetails (
  appId VARCHAR(255) PRIMARY KEY,
  resourceIds VARCHAR(255),
  appSecret VARCHAR(255),
  scope VARCHAR(255),
  grantTypes VARCHAR(255),
  redirectUrl VARCHAR(255),
  authorities VARCHAR(255),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additionalInformation VARCHAR(4096),
  autoApproveScopes VARCHAR(255)
);

--------------- MySQL ---------------
--drop table if exists oauth_client_details;
--create table oauth_client_details (
--  client_id VARCHAR(255) PRIMARY KEY,
--  resource_ids VARCHAR(255),
--  client_secret VARCHAR(255),
--  scope VARCHAR(255),
--  authorized_grant_types VARCHAR(255),
--  web_server_redirect_uri VARCHAR(255),
--  authorities VARCHAR(255),
--  access_token_validity INTEGER,
--  refresh_token_validity INTEGER,
--  additional_information VARCHAR(4096),
--  autoapprove VARCHAR(255)
--);
--
--create table if not exists oauth_client_token (
--  token_id VARCHAR(255),
--  token LONG VARBINARY,
--  authentication_id VARCHAR(255) PRIMARY KEY,
--  user_name VARCHAR(255),
--  client_id VARCHAR(255)
--);
--
--create table if not exists oauth_access_token (
--  token_id VARCHAR(255),
--  token LONG VARBINARY,
--  authentication_id VARCHAR(255) PRIMARY KEY,
--  user_name VARCHAR(255),
--  client_id VARCHAR(255),
--  authentication LONG VARBINARY,
--  refresh_token VARCHAR(255)
--);
--
--create table if not exists oauth_refresh_token (
--  token_id VARCHAR(255),
--  token LONG VARBINARY,
--  authentication LONG VARBINARY
--);
--
--create table if not exists oauth_code (
--  code VARCHAR(255), authentication LONG VARBINARY
--);
--
--create table if not exists oauth_approvals (
--	userId VARCHAR(255),
--	clientId VARCHAR(255),
--	scope VARCHAR(255),
--	status VARCHAR(10),
--	expiresAt TIMESTAMP,
--	lastModifiedAt TIMESTAMP
--);
--
--create table if not exists ClientDetails (
--  appId VARCHAR(255) PRIMARY KEY,
--  resourceIds VARCHAR(255),
--  appSecret VARCHAR(255),
--  scope VARCHAR(255),
--  grantTypes VARCHAR(255),
--  redirectUrl VARCHAR(255),
--  authorities VARCHAR(255),
--  access_token_validity INTEGER,
--  refresh_token_validity INTEGER,
--  additionalInformation VARCHAR(4096),
--  autoApproveScopes VARCHAR(255)
--);

-- for authority and user tables
CREATE TABLE if not exists AUTHORITY (
   ID BIGINT GENERATED BY DEFAULT AS IDENTITY,
   NAME VARCHAR(255),
   PRIMARY KEY (ID),
   UNIQUE(NAME),
);

-- ALTER TABLE AUTHORITY WITH NOCHECK ADD CONSTRAINT AUTHORITY_NAME UNIQUE(NAME);

CREATE TABLE if not exists USER_ (
  ID BIGINT GENERATED BY DEFAULT AS IDENTITY,
  PASSWORD VARCHAR(255),
  USER_NAME VARCHAR(255),
  ACCOUNT_EXPIRED BOOLEAN,
  ACCOUNT_LOCKED BOOLEAN,
  CREDENTIALS_EXPIRED BOOLEAN,
  ENABLED BOOLEAN,
  PRIMARY KEY (ID),
  UNIQUE(USER_NAME),
);

--ALTER TABLE USER_ WITH CHECK ADD CONSTRAINT USER_USER_NAME UNIQUE(USER_NAME);

CREATE TABLE if not exists USERS_AUTHORITIES (
   USER_ID BIGINT NOT NULL,
   AUTHORITY_ID BIGINT NOT NULL,
   PRIMARY KEY (USER_ID, AUTHORITY_ID),
   CONSTRAINT USERS_AUTHORITIES_AUTHORITY
   FOREIGN KEY (AUTHORITY_ID) REFERENCES AUTHORITY ,
   CONSTRAINT USERS_AUTHORITIES_USER_
   FOREIGN KEY (USER_ID) REFERENCES USER_ ,
);

--ALTER TABLE USERS_AUTHORITIES  ADD CONSTRAINT USERS_AUTHORITIES_AUTHORITY
--   FOREIGN KEY (AUTHORITY_ID) REFERENCES AUTHORITY;

--ALTER TABLE USERS_AUTHORITIES ADD CONSTRAINT USERS_AUTHORITIES_USER_
--   FOREIGN KEY (USER_ID) REFERENCES USER_;