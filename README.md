# chaosmin-framework
Base web application framework

[![Build Status](https://travis-ci.org/chaosmin/chaosmin-framework.svg?branch=master)](https://travis-ci.org/chaosmin/chaosmin-framework)
![License](https://img.shields.io/github/license/chaosmin/chaosmin-framework.svg)
![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/chaosmin/chaosmin-framework)
![GitHub repo size](https://img.shields.io/github/repo-size/chaosmin/chaosmin-framework)

---

## 网关拦截

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
