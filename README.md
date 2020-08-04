# glmall

## 人人renren-generator使用
a).下载项目[renren-generator](https://gitee.com/renrenio/renren-generator)

b).修改`application.yml`中数据库的地址
```yml
spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    #MySQL配置
    driverClassName: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/gulimall_wms?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: root
```
c).修改`generator.properties`文件，具体可以查看`resources/template/`下的文件
```properties

mainPath=com.xj.glmall
#包名
package=com.xj.glmall
#模块名
moduleName=ware
author=yu
email=yu
#数据库表前缀，生成的实体类会自动去掉
ablePrefix=wms_
```
d).修改`resources/template/Controller.java.vm`文件（可不修改），将`@RequestMapping`修改成对应restful请求注解

e).运行程序，将生成的代码复制到对应的模块中

## SpringCloud Alibaba
这里`spring-boot`版本为`2.1.8.RELEASE`，修改`glmall-common`的`pom.xml`文件
```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-alibaba-dependencies</artifactId>
            <version>2.2.0.RELEASE</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

### Nacos注册中心
Nacos 是阿里巴巴开源的一个更易于构建云原生应用的动态服务发现、配置管理和服务管理平台。

下载`nacos`：[nacos下载](https://github.com/alibaba/nacos/releases)，然后运行

- 修改模块的`pom.xml`文件，引入Nacos Discovery Starter。
```xml
<dependency>
     <groupId>com.alibaba.cloud</groupId>
     <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
 </dependency>
```
- 在`application.yml`文件中配置Nacos Server地址，并配置服务命名
```yml
spring
    application: 
        name: glmall-coupon
    cloud:
        nacos:
          discovery:
            server-addr: 127.0.0.1:8848
```
- 在启动类上添加`@EnableDiscoveryClient`注解，开启服务注册功能

#### 使用`openfeign`
Nacos Discovery Starter 默认集成了 Ribbon ，所以对于使用了 Ribbon 做负载均衡的组件，可以直接使用 Nacos 的服务发现。

- 在`glmall-common`模块下添加`openfeign`的依赖（通用组件依赖都在`glmall-common`模块下添加）
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```
- 在`glmall-coupon`模块下创建一个`client`包，在此包下创建`CouponFeignService`用于测试，具体代码如下：
```java
@FeignClient(name = "glmall-coupon")//被远程调用的服务名
public interface CouponFeignService {

    @GetMapping("coupon/coupon/info/{id}")//请求路径，要写全
    public R info(@PathVariable("id") Long id);//参数和返回值要和被调用方法一样
}
```
- 在启动类上添加`@EnableFeignClients(basePackages = "com.xj.glmall.member.client")`注解，并配置接口所在包
- 测试:
```java

@RequestMapping("test/")
@RestController
public class TestController {
    @Autowired
    private CouponFeignService couponFeignService;

    @GetMapping("/coupon/info")
    public R test() {
        return couponFeignService.info(2l);
    }
}
```
- 启动这两个服务，并访问此方法。

### Nacos配置中心
Nacos配置中心可将Spring Cloud应用的配置交由Nacos管理

- 引入所需依赖
```xml
<dependency>
     <groupId>com.alibaba.cloud</groupId>
     <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
 </dependency>
```
- 在`resources`目录下创建`bootstrap.properties`文件，配置Nacos Config的元数据，以`glmall-member`模块为例：
```properties
spring.application.name=glmall-member
spring.cloud.nacos.config.server-addr=127.0.0.1:8848
```
- 访问`localhost:8848/nacos`，在配置列表中新建配置，Data ID为`服务命.properties`，还是以`glmall-membe`模块为例：

![](./assets/J%7B%604DBV4%7BA3NS70EY63IQ%601.png)
- 修改`TestController`：
```java
@RequestMapping("test")
@RestController
@RefreshScope//动态获取配置中心中的配置
public class TestController {
    @Autowired
    private CouponFeignService couponFeignService;
    @Value("${member.user.name}")
    private String name;

    @Value("${member.user.age}")
    private String age;

    @GetMapping("/coupon/info")
    public R test() {
        return couponFeignService.info(2l);
    }
    @GetMapping("/user")
    public R user(){
        Map map = new HashMap();
        map.put("name",name);
        map.put("age",age);
        return R.ok(map);
    }
}
```
- 启动服务测试

![](./assets/{E07]P}{_N@Y`ZLB{WRO3A8.png)

#### 命名空间
默认的命名空间为`public`，常用场景：
- 不同环境的注册的区分隔离，例如开发测试环境和生产环境的资源（如配置、服务）隔离等
- 不同服务的配置隔离

配置指定的命名空间，想要在`bootstrap.properites`中配置：
```properties
spring.cloud.nacos.discovery.namespace=命名空间ID
```
#### 配置分组
默认配置分组为`DEFAULT_GROUP`，在创建配置集（Data ID）时可以指定配置分组。
配置指定的分组，在`bootstrap.properites`中配置：
```properties
spring.cloud.nacos.config.group=组名
```
#### 加载多个配置集（Data ID）
在实际应用中，多个服务的一些配置可能相同，这时我们可以将这些配置拆分，如数据库配置、redis配置等，将这些配置拆分出来，放在配置中心，这样就避免了代码的重复编写，有可动态加载
在`bootstarp.properties`中配置：
```properties
spring.cloud.nacos.config.ext-config[0].data-id=DataID_1
spring.cloud.nacos.config.ext-config[0].group=group_1
spring.cloud.nacos.config.ext-config[0].refresh=true #是否支持动态加载配置，默认为false

spring.cloud.nacos.config.ext-config[0].data-id=DataID_2
spring.cloud.nacos.config.ext-config[0].group=group_2
spring.cloud.nacos.config.ext-config[0].refresh=true #是否支持动态加载配置，默认为false
```
按住`Ctrl`鼠标左键点击`ext-config`查看源码发现，`ext-config`是一个`List`集合，所以加载多个配置可以通过数组形式配置，若要获取配置中心的配置，以前`spring boot`获取配置文件的所有方式都可以
### Gateway网关
- 创建`glmall-gateway`模块，添加相关依赖：
```xml
<dependency>
    <groupId>com.xj.glmall</groupId>
    <artifactId>glmall-common</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
```
因为`nacos`相关的依赖都在`glmall-common`模块中，所以需要引入`glmall-common`的依赖
- 修改启动类：
```java
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})//不加数据库相关配置
@EnableDiscoveryClient
@RefreshScope
public class GlmallGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GlmallGatewayApplication.class, args);
    }
}
```
- 在`glmall-common`模块中引入了`mybatis`相关依赖，而网关服务不需要数据库配置。

- 修改`resources`下的配置文件，将网关服务注册到`nacos`，具体可参照其他模块配置

#### 路由断言工厂（Route Predicate Factories）
满足某种条件就路由去哪里
##### 路由参数断言工厂（The Query Route Predicate Factory）
路由参数满足某种条件
`application.yml`配置：
```yaml
server:
  port: 10086
spring:
  application:
    name: glmall-gateway
  cloud:
    gateway:
      routes:
        - id: baidu
          uri: https://www.baidu.com
          predicates:
            - Query=url, baidu
        - id: qq
          uri: https://www.qq.com
          predicates:
            - Query=url, qq
```
配置说明：`- `表示一个数组，可配置多个，这是`yml`的语法；`id`：路由的id；`url`：满足条件后跳转的地址；`predicates`：这是一个数组，`- Query=...`可配置多个，`Query=url,baidu`表示请求路径中的参数`url=baidu`才会路由到`uri`这个地址，如`http://localhost:88/baidu?url=baidu`，这里符合第一个路由断言，所有它会跳转到`https://www.baidu.com`，参数的值可用正则表达式。
其他断言配置可参照官网：[spring cloud gateway](https://cloud.spring.io/spring-cloud-static/spring-cloud-gateway/2.2.3.RELEASE/reference/html/#gateway-request-predicates-factories)
## 后台管理界面搭建
下载好[renren-fast](https://gitee.com/renrenio/renren-fast)和[renren-fast-vue](https://gitee.com/renrenio/renren-fast-vue)。
### `renren-fast`配置
1.创建一个数据库`gulimall_admin`，执行`db`目录下的数据库文件，登录后台管理界面要用到的数据。

2.修改配置文件中数据库的地址，并配置服务名为`renren-fast`。

3.添加`glmall-common`模块的依赖，需要用到`nacos`。

4.创建`bootstrap.yml`文件，并配置`nacos`的地址（尝试在application.yml文件中配置，但是不起作用）：
```yaml
spring:
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
      config:
        server-addr: 127.0.0.1:8848
  application:
    name: renren-fast
```

5.在启动类上添加`@EnableDiscovery`注解开启服务注册与发现功能

### `renren-fast-vue`配置
1.在命令行窗口执行`npm install`，可能会报`Failed at the chromedriver@2.37.0 install script 'node install.js'`，这是因为下载源被封了，可使用淘宝的下载源下载：
```
npm install chromedriver --chromedriver_cdnurl=http://cdn.npm.taobao.org/dist/chromedriver
```
如果错误日志中有`node-sass`这样的字符串，那就是要安装`node-sass`：
```
npm install --save-dev node-sass
```
执行完这些命令后再执行`npm install`基本就不会报什么错了

3.配置向后台请求数据的地址，配置文件是`static/config`目录下的`index.js`：
```js
//window.SITE_CONFIG['baseUrl'] = '后台IP地址:{端口号}'
//例
window.SITE_CONFIG['baseUrl'] = '127.0.0.1:8080'
```

4.执行`npm run dev`，数据都要通过后台获取，所以要先启动`renren-fast`服务，登录后台界面账号为admin，密码也是

## 网关配置与路由重写
前端发送的请求全部路由到网关，再由网关分发给各个服务。
修改前端项目`static/config`目录下的`index.js`文件，将api请求地址修改为网关地址：
```javascript
window.SITE_CONFIG['baseUrl'] = '127.0.0.1:88/api'
```
以`glmall-product`模块为例。前端发送的请求都以`/api`开头，在网关中配置路由：
```yaml
server:
  port: 88
spring:
  application:
    name: glmall-gateway
  cloud:
    gateway:
      routes:
        - id: product_route
          uri: lb://glmall-product
          predicates:
            - Path=/api/product/**
          filters:
            - RewritePath=/api(?<segment>/?.*),/$\{segment}
        - id: admin_route
          uri: lb://renren-fast
          predicates:
            - Path=/api/**
          filters:
            - RewritePath=/api(?<segment>/?.*),/renren-fast/$\{segment}
```
网关这里拦截所有以`/api`开头的请求，然后通过断言和过滤匹配到精确的地址。精确的路由配置要在前面，不然会被其他路由匹配。
## 解决跨域问题
前端发送后台发送请求时会遇到跨域问题，`spring boot`为我们提供了解决跨域的方法，统一在网关配置：
```java

@Configuration
public class GlmallCorsConfiguration {
    @Bean
    public CorsWebFilter corsWebFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addAllowedOrigin("*");
        config.setAllowCredentials(true);
        source.registerCorsConfiguration("/**",config);
        return new CorsWebFilter(source);
    }
}
```
## mybatis配置逻辑删除
在实际开发中，数据是最重要的，所以一般删除数据都不是真正的删除，而是逻辑删除，让这些数据不再显示。

以`gulimall_pms`数据库中的`pms_category`表为例。在此表中，有一个`show_status`字段，这里规定0-不显示，1-显示。

在`application.yml`文件中配置：
```yaml
mybatis-plus:
  mapper-locations: classpath:/mapper/**/*.xml
  global-config:
    db-config:
      id-type: auto
      logic-delete-value: 0 # 逻辑已删除值(默认为 1)
      logic-not-delete-value: 1 # 逻辑未删除值(默认为 0)
```

在对应实体类的字段上添加`@TableLogic`注解。参考[Mybatis-Plus官方文档](https://mp.baomidou.com/guide/logic-delete.html)

## spring cloud alibaba oss使用
新建一个模块`glmall-third-party`。 
引入`glmall-common`和`alicloud-oss starter`
```xml
<dependency>
     <groupId>com.xj.glmall</groupId>
     <artifactId>glmall-common</artifactId>
 </dependency>
<dependency>
     <groupId>com.alibaba.cloud</groupId>
     <artifactId>spring-cloud-starter-alicloud-oss</artifactId>
 </dependency>
```
在启动类上添加`@EnableDiscovery`注解
添加在`bootstrap.yml`中添加nacos的服务注册地址和配置中心地址（看前文配置），以及在`application.yml`文件中添加OOS服务对应的`accessKey`,`secretKey`,`endpoint`
```ymal
spring:
  cloud:
    alicloud:
        access-key: your-ak
        secret-key=your-sk
        oss:
           endpoint=***
```
在`glmall-third-party`模块下创建一个`OSSController.java`，用于处理文件上次
```java
@RequestMapping("/oss")
@RestController
public class OSSController {

    @Autowired
    private OSS ossClient;
    @Value("${spring.cloud.alicloud.access-key}")
    private String accessId;
    @Value("${spring.cloud.alicloud.secret-key}")
    private String accessKey;
    @Value("${spring.cloud.alicloud.oss.endpoint}")
    private String endpoint;
    @Value("${spring.cloud.alicloud.oss.bucket}")
    private String bucket;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    @GetMapping("/policy")
    public R policy(){

        String host = "https://" + bucket + "." + endpoint; // host的格式为 bucketname.endpoint
        String format = dateFormat.format(new Date());
        String dir = format + "/"; // 用户上传文件时指定的前缀。
        Map<String, String> respMap = null;
        // 创建OSSClient实例。
        try {
            long expireTime = 30;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            // PostObject请求最大可支持的文件大小为5 GB，即CONTENT_LENGTH_RANGE为5*1024*1024*1024。
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);

            respMap = new LinkedHashMap<String, String>();
            respMap.put("accessid", accessId);
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("dir", dir);
            respMap.put("host", host);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));
        } catch (Exception e) {
            // Assert.fail(e.getMessage());
            System.out.println(e.getMessage());
        } finally {
            ossClient.shutdown();
        }
        return R.ok().put("data",respMap);
    }
}
```
详情查看[OSS对象存储](https://help.aliyun.com/document_detail/91868.html?spm=a2c4g.11186623.2.15.40b46e28LIgXMz#concept-ahk-rfz-2fb)
### 前端配置
将封装好的组件放在前端工程下的`src/components`目录下，在`brand-add-or-update.vue`下引入该组件
```js
import SingleUpload from "@/components/upload/singleUpload";
export default{
    components:{SingleUpload}
}
```
其余自行修改

### JSR303

1. 给实体类添加校验注解：javax.validation.constrains，并定义自己的错误提示信息
![](./assets/1596456153(1).jpg)
2. 开启校验功能@Valid，效果：校验错误以后会有默认的响应
![](./assets/1596456422(1).jpg)

#### JSR303分组校验
为JSR303校验注解添加`group`属性，`group`是一个接口类型的数组。创建两个空接口，分别为`AddGroup`、`UpdateGroup`。
这两个接口分别表示新增异常信息和更新异常信息。想要分组校验生效还需修改`Controller`类中的`@valid`注解，将此注解修改为`@Validated`注解，此注解有个值为接口类型数组的属性，指定校验的分组。
注意，如果要使用校验分组那所有的字段都必须使用，否则未使用校验分组的字段将不进行校验
例：
```java
//实体类代码
@TableId
@NotNull(message = "品牌id不能为空",groups = UpdateGroup.class)
@Null(message = "新增品牌id必须为空",groups = AddGroup.class)
private Long brandId;

//Controller代码
@PostMapping("/update")
public R update(@Validated(UpdateGroup.class) @RequestBody BrandEntity brand){
	brandService.updateById(brand);

    return R.ok();
}
```
在此例中，新增时它会校验id必须为空，修改时校验id不能为空
### 统一异常处理
1. 编写异常处理类，在类上添加`@RestontrollerAdvice`，以json字符串返回结果
2. 编写异常处理方法，并在方法上添加`@ExceptionHandler`注解，标注要捕获的异常
```java
@Slf4j
@RestControllerAdvice(basePackages = "com.xj.glmall.product.controller")//捕获com.xj.glmall.product.controller包下的异常
public class GlmallExceptionControllerAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlmallExceptionControllerAdvice.class);
    @ExceptionHandler(value = MethodArgumentNotValidException.class)//捕获MethodArgumentNotValidException异常
    public R handleVaildException(MethodArgumentNotValidException e) {
        LOGGER.error("数据校验出现问题{}，异常类型：{}",e.getMessage(),e.getClass());
        BindingResult result = e.getBindingResult();//获取出现异常的字段及信息
        Map<String,String> errorMap = new HashMap<>();
        result.getFieldErrors().forEach((fieldError -> {
            errorMap.put(fieldError.getField(),fieldError.getDefaultMessage());
        }));
        return R.error().put("data",errorMap);
    }

}
```