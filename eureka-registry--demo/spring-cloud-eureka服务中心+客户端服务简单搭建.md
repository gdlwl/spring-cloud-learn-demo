# spring-cloud-eureka服务搭建(boot-verson-1.5.21.Release)

## 1.搭建eureka注册服务中心

- 用[sping-initializr](https://start.spring.io/)构建spirng-cloud-eureka-server项目,选择下面的依赖
  - **Eureka Server**
  - **Spring Web Starter**
  - **Spting Boot Acutator**
- IDEA工具导入项目，并配置`application.properties`或`application.yml`

```properties
#Eureka Server 应用名称
spring.application.name = eureka-server-register
### Eureka Server 服务端口
server.port= 9999
##取消服务器自我注册
eureka.client.register-with-eureka=false
#注册中心的服务器，没必要再去检索服务
eureka.client.fetch-registry = false
# Eureka Server 服务 URL,用于客户端注册
eureka.client.serviceUrl.defaultZone=\
  http://localhost:${server.port}/eureka
```

- 启动eureka-server服务

![1560408255749](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\1560408255749.png)

## 2.搭建eureka客户端

- 用[sping-initializr](https://start.spring.io/)构建sping-cloud-eureka-client项目,选择下面的依赖
  - **Eureka Discover Client**
  - **Spring Web Starter**
  - **Spting Boot Acutator**

### 创建子模块

- IDEA工具导入项目后，创建3个子继承sping-cloud-eureka-client项目的子模块user-api、user-service-consumer、user-service-provider,

  创建完后项目结构如下:

- **spring-cloud-eureka-client**

  - **user-api**
  - **user-service-consumer**
  - **user-service-provider**

### 搭建user-service-provider子模块

- 配置**user-service-provider**的`application.properties`或`application.yml`

```properties
##应用名称
spring.application.name = user-service-provider
## Eureka 注册中心服务器端口
eureka.server.port =9999
## 服务端口
server.port = 8888
## Eureka Server 服务 URL,用于客户端注册
eureka.client.serviceUrl.defaultZone=\
  http://localhost:${eureka.server.port}/eureka
```

在**user-api**定义User Bean和服务接口UserService

```java
public class User {
    private long id;
    private String name;
    private int age;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}

public interface UserService {
    //保存用户
    boolean save(User user);
	//查询所有用户
    Collection<User> findAll();
}

```



创建启动类ProviderApplication

```java
@SpringBootApplication
//启用客户端发现
@EnableDiscoveryClient
public class ProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class,args);
    }
}
```



创建用户control,lerservice实现,和仓储repository

UserServiceProviderRestApiController

```JAVA
@RestController
public class UserServiceProviderRestApiController {

    private UserService userService;
    @Autowired
    public UserServiceProviderRestApiController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/save")
    public Message saveUser(@RequestBody User user){
        return userService.save(user);
    }

    @GetMapping("/user/list")
    public Collection list(){
        Collection collection =userService.findAll();
        return collection;
    }
}
```

UserServiceImpl

```java
@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
   public Message save(User user){
        return userRepository.save(user);
    }
    @Override
    public Collection<User> findAll(){
        return userRepository.findAll();
    }
}
```

UserRepository

```
@Repository
public class UserRepository {
    private ConcurrentHashMap<Long, User> repository = new ConcurrentHashMap();
    public Message save(User user){
        Message msg = new Message();
        if(repository.putIfAbsent(user.getId(),user)==null){
            msg.setCode(1);
            msg.setMessage("增加成功!");
        }else {
            msg.setCode(0);
            msg.setMessage("增加失败!");
        }
        return msg;
    }

    public Collection<User> findAll(){
        return repository.values();
    }

}
```

### 搭建user-sevice-consumer子模块

配置**user-service-consumer**的`application.properties`或`application.yml`

```properties
#应用名称
spring.application.name = user-service-consumer
## Eureka 注册中心服务器端口
eureka.server.port = 9999
## 服务提供方端口
server.port = 7777
## Eureka Server 服务 URL,用于客户端注册
eureka.client.serviceUrl.defaultZone=\
  http://localhost:${eureka.server.port}/eureka
## Management 安全失效
#management.security.enabled = false
```

创建ConsumeApplication启动类

```java
@SpringBootApplication
@EnableDiscoveryClient
public class ConsumeApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumeApplication.class,args);
    }
    //负载均衡
    @LoadBalanced
    //注册RestTemplate bean
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
```

创建controller、serviceProxy

UserController

```java
@RestController
public class UserController {
    private UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/user/save")
    public Message saveUser(@RequestBody User user){
        return userService.save(user);
    }

    @GetMapping("/user/list")
    public Collection list(){
        Collection collection =userService.findAll();
        return collection;
    }
}
```

UserServiceProxy

```java
@Service
public class UserServiceProxy implements UserService{
    private static final String PROVIDER_SERVER_URL_PREFIX = "http://user-service-provider";
    /**
     * 通过 REST API 代理到服务器提供者
     */
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Message save(User user) {
        return restTemplate.postForObject(PROVIDER_SERVER_URL_PREFIX + "/user/save", user, Message.class);
    }

    @Override
    public Collection<User> findAll() {
        return restTemplate.getForObject(PROVIDER_SERVER_URL_PREFIX + "/user/list",Collection.class);
    }
}
```

至此，简单的eureka注册服务搭建完成，服务启动后就可以进行测试

### 进行测试

查询测试

![1560437486168](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\1560437486168.png)



