# API 接口开发平台

## 项目介绍

ding-api-bd：管理业务模块。包含用户登录、注册，接口发布、编辑、删除等功能

ding-api-common：公用模块。包含项目模型实体类（也可保存 DTO，VO 等 model 类）、公共业务

ding-interface-sdk：接口服务SDK。包含签名生成算法、接口服务客户端

ding-interface：接口服务提供方项目。练习项目中仅提供了一个测试接口

ding-gateway：网关。Spring Cloud Gateway 实现，负责接口服务的请求转发、签名认证