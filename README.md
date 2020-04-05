## 为什么会有这个项目

+ 多租户模式的开源项目较少
+ 打算做成一个项目的基础框架。开发过程中，只需做好业务开发即可。不需要做其他额外的配置。

## 功能
### 已完成
+ Resource 权限的自动收集
+ 多租户模块 tenant
+ swagger starter
+ fastjson starter
+ Redis starter
+ openfeign hystrix 统一全局处理
+ 分布式权限认证

### 待开发

+ 限流，幂等
+ 通用的接口；如 长链转短链服务。
+ 日志配置统一配置
+ k8s
+ 独立的 spring security 权限校验模块（代码中的 auth 模块，是由个人基于 mall 开源项目二次开发的 多租户商城（单体架构），剥离出来的，所以代码中有很多 该项目的业务逻辑，为了不报错，大部分代码都被注释掉了）



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


## 模块规划
```
framework
├──parent 统一依赖
├────parent.common 公共通用类
├────parent.mybatis-plus-entity mybatis-plus 基本实体依赖
├────parent.fastjson fastJson starter
├────parent.resource 资源收集模块
├────parent.redis-starter redis starter，封装了 protobuf，原子锁，限流
├────parent.swagger swagger stater，ui 使用 swagger-bootstrap-ui
├────parent.web 
├──────web.common 全局异常处理、返回结果统一处理
├──────web-feign-hystrix 全局统一处理 feign failback
├────parent.starter 快速开发应用基本包

├──auth auth 模块 是由个人开发的多租户商城（单体架构），剥离出来的，所以代码中有很多 该项目的业务逻辑，为了不报错，大部分代码都被注释掉了，

├──tenant 多租户模块，实现了基本的 rbac 权限认证。
├──────tenant-client   feign 模块，第三方依赖重用
├──────tenant-entity   实体模块
├──────tenant-mapper   mybatis mapper 模块
├──────tenant-rest     controller 模块

├──gateway 网关，处理登录，鉴权，集成了 Swaggger-bootstrap-ui
```



## 快速启动所有服务

cd 到 docker 目录下，直接使用 docker-compose 启动所有服务。

```
docker-compose up -d
```

PS:（个人本地跑的时候，非常卡，非常耗资源。所以建议用 docker 启动 mysql、redis、**rocketMQ(巨卡,所以被我注释掉了)**，应用程序直接用 IDE 跑）。

## 主要模块介绍

### gateway

启动 gateway、tennat 后，可再浏览器中 输入 http://localhost:11110/doc.html 访问 swagger

### resource 权限自动收集模块

支持 local、redis、rocketMQ 3种收集器，默认使用redis。

#### 过滤器

默认过滤具有 Controller，RestController。支持扩展。

#### 收集器

resource 支持 swagger 注解的收集（默认使用 Swagger 注解收集），同时也支持 @ResourceCollection 注解的收集。Swagger 的收集策略，具体请看 com.csp.github.resource.collection.SwaggerResourceCollectionStrategy 类的实现。 支持扩展

#### 基本使用

1、引入依赖

```xml
<dependency>
    <groupId>com.csp.github.base</groupId>
    <artifactId>resource</artifactId>
</dependency>
```

2、启用收集器

```yaml
resources:
  collector:
    enable: true
```

如果需要消费收集到的资源，需要启用消费者，并实现 **com.csp.github.resource.consumer.ResourceConsumerListener**, 监听回调的资源

**这里建议，专门有一个程序启用消费者即可。**

```yaml
resources:
  collector:
    consumer-listener-cls: com.csp.github.tenant.ConsumerTest
    consumer-enable: true
```

其中 consumer-enable 表示是否启用，默认false。consumer-listener-cls 即实现 ResourceConsumerListener 接口的类

#### 工作原理

![resource-work-flow.png](https://github.com/a893359278/springcloud-faster/blob/master/images/resource-work-flow.png)



### redis-starter

Redis-starer 提供 protobuf 序列化、分布式以及单机环境下的锁实现

##### 基本使用

```xml
<dependency>
  <groupId>com.csp.github.base</groupId>
  <artifactId>redis-starter</artifactId>
</dependency>
```

##### 使用 protobuf 序列化

```java
@Autowire
ProtobufRedisTemplate protobufRedisTemplate;
```

**注意：protobuf 不支持 hash 操作**

##### 使用锁

在程序入口，启用。

```java
@EnableLockAspect
```

在需要锁的方法上添加注解。**默认用 redis 实现锁**

```
@Lock
```

默认使用 redis。全局更换锁实现。

```yaml
lock:
  type: LOCAL
```

此配置，会将所有锁实现强制更换为 Local。如果某个特定方法，不想被强制更换。将 Lock 的 override 置为 false 即可

```java
@Lock(override=false)
```

### tenant

原项目使用的是 client 打包机制，后考虑到 client 打包有可能造成 maven 间的循环依赖问题，将项目拆分成 client，entity，mapper，rest。以最大的程度，进行重用。

例如，如果有别的 Application 要使用 tenant 提供的服务。只需 引入 tenant-client 模块即可。

## 目前缺陷

1、目前将是否需要登录的判断放在了网关模块。如果新增一个微服务。有了不需要登录的新接口，那么需要重启网关，这样的设计，还是比较麻烦。

重新梳理网关的职能，重新开发，将是否登录下放到各个服务提供者。需不需要登录，应该是各个服务提供者说了算。


## 作者联系方式

**欢迎探讨改进项目**

+ 微信：a792966514
+ 思否：[思否](https://segmentfault.com/u/xinwusitiandikuan "思否")
+ Email: 18250073990@163.com

## Q&A
Q: 微服务还有那么多组件为什么不集成

A：相对的这些组件的集成比较简单，所以就没集成。并且也不是每个企业的微服务有那么复杂。因为只集成开发基本够用的。

Q：为什么之前将 Auth 集成到网关之后又移除了

A：微服务中 使用 spring security， 个人感觉还是比较麻烦。因此又将 Auth 移除了。自己写了 简单的用户验证。