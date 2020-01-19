## 为什么会有这个项目

+ 提供多租户的 springcloud 开发系统，目前开源的多租户 springcloud 较少
+ 见过很多的 springcloud 的项目，通用的配置类，从这个项目复制到另外一个项目
+ 更加快速开发 springcloud。不需要添加太多的依赖，简称再封装。
+ 打算做成一个项目的基础框架。开发过程中，只需做好业务开发即可。不需要做其他额外的配置。


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

+ 服务间的依赖，只需打包成 client，然后引入即可，无需重复定义同样的实体类。 client 打包机制，只会把对应的实体， feign 打包进去。（jar 包也会被打包进去，需要自己手动排除）

![image-20191210001927847](https://github.com/a893359278/springcloud-faster/blob/master/images/image-20191210001927847.png)

```maven
 <dependency>
    <groupId>com.csp.github</groupId>
    <artifactId>rabc</artifactId>
    <version>1.0-SNAPSHOT</version>
    <classifier>client</classifier>
 </dependency>
```



## 模块

### dependency 模块

统一依赖使用

### parent 模块

统一 nexus 的
