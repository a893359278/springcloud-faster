## 为什么会有这个项目
+ 见过很多的 springcloud 的项目，通用的配置类，从这个项目复制到另外一个项目
+ 更加快速开发 springcloud。不需要添加太多的依赖，简称再封装

## 有什么功能
### 已完成
+ 权限的自动收集
+ spring-security 整合，多登陆方式。
+ 基于 rabc 的权限模块
+ swagger starter
+ fastjson starter
+ openfeign hystrix 统一全局处理
### 待开发
+ 日志配置统一配置
+ redis 扩展，分布式锁


### rabc 模块
目前的 rabc 模块比较简单，功能还没加，但是主要的框架已经搭起来。大概的展示了如何快速的利用项目中的 starter 进一步简化开发，更加的专注业务开发。

### 权限自动收集  base.resource
resource 目前具备自动收集项目中定义的权限注解。@ResourceCollection， 还支持 Swagger 权限的收集

resource 工作流程：
+ 过滤

目前过滤把有 Controller, RestController 注解的类过滤出来，支持自定义
+ 收集

收集默认是收集带有 @ResourceCollection 注解的，也可以收集 swagger 的注解。 支持自定义
+ 处理收集到的资源（需要自己处理）

这部分需要自己处理，目前使用的是 redis 的发布订阅，比较简单（缺陷当然有很多，先快速实现功能）



