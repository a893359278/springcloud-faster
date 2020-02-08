## 为什么会有这个项目

+ 多租户模式的开源项目较少
+ 打算做成一个项目的基础框架。开发过程中，只需做好业务开发即可。不需要做其他额外的配置。
+ Github 上有部分关于 SpringCloud 的开源项目，大多数没办法直接拿过来修改为公司的基础性框架，大部分只有单独的一个小模块。

## 有什么功能
### 已完成
+ 权限的自动收集
+ 多租户模块 tenant
+ swagger starter
+ fastjson starter
+ openfeign hystrix 统一全局处理
+ 分布式权限认证



### 待开发

+ 日志配置统一配置

+ redis 扩展，分布式锁

+ docker 容器化部署

+ 独立的 spring security 权限校验模块（代码中的 auth 模块，是由个人基于 mall 开源项目二次开发的 多租户商城（单体架构），剥离出来的，所以代码中有很多 该项目的业务逻辑，为了不报错，大部分代码都被注释掉了）

  


## 目前缺陷
目前将是否需要登录的判断放在了网关模块。如果新增一个微服务。有了不需要登录的新接口，那么需要重启网关，这样的设计，还是比较麻烦。

**方案A：**

将不需要登录的路由放到 redis 中，依然由网关做是否登录的校验

**方案B：**

重新梳理网关的职能，重新开发，将是否登录下放到各个服务提供者。



## 快速开发Demo

+ 继承快速开发的 starter 
+ 如果需要有 swagger 的能力，依赖 swagger，并在在启动类上面添加 @EnableSwagger 注解即可

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <groupId>com.csp.github.base</groupId>
        <artifactId>starter</artifactId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>cs</artifactId>

    <dependencies>
    <!-- swagger starter 不提供 ui 能力，需要单独依赖     -->
        <dependency>
            <groupId>com.csp.github.base</groupId>
            <artifactId>swagger</artifactId>
            <optional>true</optional>
        </dependency>
    </dependencies>

</project>
```

+ 直接用 @StarterApplication 注解即可。便拥有了 spring cloud 的能力。

```java
@EnableSwagger
@StarterApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }
}
```

+ 对于 feign，不需要写 fallback，只需 fallbackFactory 填 DefaultHystrixFallbackFactory 即可，并且 feign 的返回值，可直接填数据对象，无需包装为统一的业务对象。

```java
@FeignClient(value = tenant, path = "/adminUser", fallbackFactory = DefaultHystrixFallbackFactory.class)
public interface RabcFeign {
    @GetMapping("/{id}")
    AdminUser userFullInfo(@PathVariable Long id);
}
```

+ 对于控制层，同样无需包装，直接返回数据对象即可

```java
@ApiOperation(value = "获取用户信息，包括权限，角色")
@GetMapping("/{id}")
public AdminUser userFullInfo(@PathVariable Long id) {
  AdminUser info = userService.getUserInfoWithRolesAndPermissions(id);
  return info;
}
```

这样做还要另外一个目的：swagger 上，可以直接展示实体的属性
![image-20191210001927847](https://github.com/a893359278/springcloud-faster/blob/master/images/image-20191210000616099.png)

+ 服务间的依赖，只需打包成 client，然后引入即可，无需重复定义同样的实体类。 client 打包机制，只会把对应的实体， feign 打包进去。（jar 包也会被打包进去，需要自己手动排除）

![image-20191210001927847](https://github.com/a893359278/springcloud-faster/blob/master/images/image-20191210001927847.png)

```xml
 <dependency>
    <groupId>com.csp.github</groupId>
    <artifactId>rabc</artifactId>
    <version>1.0-SNAPSHOT</version>
    <classifier>client</classifier>
 </dependency>
```



## 模块规划

+ **base.dependency**

统一依赖使用

+ **base.parent**

统一 nexus 

+ **base.common**

公共通用类

+ **base.fastjson**

fastJson starter

+ **base.resource**

资源收集模块

+ **base.redis-starter**

redis starter，封装了 protobuf

+ **base.swagger**

swagger starter

+ **base.web**

主要封装了 histrix，全局的返回值处理，全局的 feign 返回值解码

+ **base.starter**

封装了快速开发应用的依赖，以及 client 打包机制

+ **tenant**

多租户模块，实现了基本的 rbac 权限认证。

+ **auth**

auth 模块 是由个人基于 [mall](https://github.com/macrozheng/mall "mall") 开源项目二次开发的 多租户商城（单体架构），剥离出来的，所以代码中有很多 该项目的业务逻辑，为了不报错，大部分代码都被注释掉了，**后续会考虑把该项目开源**

## 作者联系方式
**欢迎探讨改进项目**

微信：a792966514
思否：[思否](https://segmentfault.com/u/xinwusitiandikuan "思否")
Email: 18250073990@163.com