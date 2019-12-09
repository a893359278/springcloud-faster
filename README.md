## 为什么会有这个项目

+ 见过很多的 springcloud 的项目，通用的配置类，从这个项目复制到另外一个项目
+ 更加快速开发 springcloud。不需要添加太多的依赖，简称再封装。
+ 打算做成一个项目的基础框架。开发过程中，只需做好业务开发即可。不需要做其他额外的配置。

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
+ docker 容器化部署
+ spring security 集成到 网关（目前只是简单整合 spring security ）

## 快速开发Demo

+ 继承快速开发的 starter 
+ 如果需要有 swagger 的能力，依赖 swagger，并在在启动类上面添加 @EnableSwagger 注解即可

```java
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
        <dependency>
            <groupId>com.csp.github.base</groupId>
            <artifactId>swagger</artifactId>
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
@FeignClient(value = "rabc-service", path = "/adminUser", fallbackFactory = DefaultHystrixFallbackFactory.class)
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

+ 服务间的依赖，只需打包成 client，然后引入即可，无需重复定义同样的实体类。 client 打包机制，只会把对应的实体， feign 打包进去。（jar 包也会被打包进去，需要自己手动排除）

![image-20191210001927847](/Users/chenshaoping/Library/Application Support/typora-user-images/image-20191210001927847.png)

```maven
 <dependency>
    <groupId>com.csp.github</groupId>
    <artifactId>rabc</artifactId>
    <version>1.0-SNAPSHOT</version>
    <classifier>client</classifier>
 </dependency>
```



## 模块

+ dependency：统一依赖
+ parent：统一的父 pom
+ starter：快速开发 web 继承的父 pom
+ common：通用包，定义全局工具类。等等。。。
+ fastjson：全局配置 fastjson
+ log：全局配置日志
+ resource：权限自动收集
+ swagger：全局 swagger 配置
+ web：openFeign 的 fallFactory 统一配置，web开发注解的基本封装
+ auth：spring security。准备集成进 网关（待开发）
+ rabc：权限模块，架子已搭建完成，待完善



### web 模块

+ 为了在 swagger 上面能够显示对应实体的信息，接口直接返回的是数据对象，而没有做返回值的统一封装，而是通过 ResponseBodyAdvice 接口，统一封装返回的对象。
+ 为了 openFeign 调用的时候，也能得到数据对象，而不是封装的返回对象。编写了 feign 的解码器，将上游封装过的返回对象解码为我们需要的数据对象。

#### 编码风格大概如下：

服务A：直接返回数据对象

```java
@ApiOperation(value = "获取用户信息，包括权限，角色")
@GetMapping("/{id}")
public AdminUser userFullInfo(@PathVariable Long id) {
  AdminUser info = userService.getUserInfoWithRolesAndPermissions(id);
  return info;
}
```



调用服务 A 的服务 B：

Feign

```java
@FeignClient(value = "rabc-service", path = "/adminUser", fallbackFactory = DefaultHystrixFallbackFactory.class)
public interface RabcFeign {
    @GetMapping("/{id}")
    AdminUser userFullInfo(@PathVariable Long id);
}
```

Controller：直接接收数据对象

```java
@ApiOperation(value = "测试")
@GetMapping("/test")
public AdminUser tt() {
  return rabcFeign.userFullInfo(1193445540674965506L);
}
```

#### swagger 上面长这样

可以直接查看到返回的数据对象的数据格式。

![image-20191210000616099](https://github.com/a893359278/springcloud-faster/blob/master/images/image-20191210000616099.png)



### rabc 模块

目前的 rabc 模块比较简单，功能还没加，但是主要的框架已经搭起来。大概的展示了如何快速的利用项目中的 starter 进一步简化开发，更加的专注业务开发。

### 权限自动收集  base.resource
resource 目前具备自动收集项目中定义的权限注解。@ResourceCollection， 还支持 Swagger 权限的收集

resource 工作流程：
+ 过滤

目前过滤把有 Controller, RestController 注解的类过滤出来，支持自定义
+ 收集

收集默认是收集带有 @ApiOperation 注解的。收集到的权限会被封装为 ResourceEntity 实体。 支持自定义
+ 处理收集到的资源（需要自己处理）

这部分需要自己处理，目前使用的是 redis 的发布订阅，比较简单（缺陷当然有很多，先快速实现功能）



