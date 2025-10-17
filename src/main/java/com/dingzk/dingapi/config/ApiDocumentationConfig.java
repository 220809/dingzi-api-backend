package com.dingzk.dingapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(info = @Info(
    title = "API 开放平台",
    description = "接口开放平台项目",
    version = "0.0.1",
    contact = @Contact(name = "dingzi", url = "http://example.com", email = "contact@example.com"),
    license = @License(name = "许可证", url = "http://example.com/license")
))
public class ApiDocumentationConfig {
}