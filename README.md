# chaosmin-framework
Base web application framework

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=chaosmin_chaosmin-framework&metric=alert_status)](https://sonarcloud.io/dashboard?id=chaosmin_chaosmin-framework)
[![Build Status](https://travis-ci.org/chaosmin/chaosmin-framework.svg?branch=master)](https://travis-ci.org/chaosmin/chaosmin-framework)
![License](https://img.shields.io/github/license/chaosmin/chaosmin-framework.svg)
![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/chaosmin/chaosmin-framework)
![GitHub repo size](https://img.shields.io/github/repo-size/chaosmin/chaosmin-framework)

---

## 系统配置

### 网关拦截

> 对请求连接有权限校验, 目前支持读写操作区分
>
> 默认读写用户 admin/nimda
>
> 默认只读用户 reader/redaer
>
> 不过滤以下url

1. /auth/**
2. /event/**
3. /ddl-change
4. /system/ping-without-auth

### HTTP access log

> 通过配置`log.access.enable=true`来打开HTTP请求记录日志。
>
> 日志实现类为`tech.chaosmin.framework.web.filter.AccessLogFilter`。
>
> 底层日志依托于logback配置中的`requestInfoAppenderAsync`。

### Web handler provider log

> 通过配置`log.provider.enable=true`来打开WebHandlerProvider处理记录日志。
>
> 日志基于切面实现, 通过文本组装出入参并打印至日志文件。