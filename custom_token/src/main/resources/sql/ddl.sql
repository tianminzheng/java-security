CREATE TABLE `oauth2_client`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `client_id` varchar(255) NOT NULL COMMENT '客户端编号',
  `secret` varchar(255) NOT NULL COMMENT '客户端密钥',
  `name` varchar(255) NOT NULL COMMENT '客户端名称',
  `description` varchar(255) NULL DEFAULT NULL COMMENT '客户端描述',
  `status` tinyint NOT NULL COMMENT '状态',
  `access_token_validity_period` int NOT NULL COMMENT '访问令牌的有效时间',
  `refresh_token_validity_period` int NOT NULL COMMENT '刷新令牌的有效时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = 'OAuth2 客户端表';


CREATE TABLE `oauth2_access_token`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `user_type` tinyint NOT NULL COMMENT '用户类型',
  `access_token` varchar(255) NOT NULL COMMENT '访问令牌',
  `refresh_token` varchar(32) NOT NULL COMMENT '刷新令牌',
  `client_id` varchar(255) NOT NULL COMMENT '客户端编号',
  `scopes` varchar(255) NULL DEFAULT NULL COMMENT '授权范围',
  `expire_time` datetime NOT NULL COMMENT '过期时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = 'OAuth2 访问令牌表';


CREATE TABLE `oauth2_refresh_token`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `refresh_token` varchar(32) NOT NULL COMMENT '刷新令牌',
  `user_type` tinyint NOT NULL COMMENT '用户类型',
  `client_id` varchar(255) NOT NULL COMMENT '客户端编号',
  `scopes` varchar(255) NULL DEFAULT NULL COMMENT '授权范围',
  `expire_time` datetime NOT NULL COMMENT '过期时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = 'OAuth2 刷新令牌表';