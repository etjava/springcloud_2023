# Springboot自动装配原理

```
官方解释：
Spring Boot 的自动装配原理是通过使用注解和条件注解来实现的。当我们在 Spring Boot 项目中引入一个依赖时，Spring Boot 会根据依赖中的 @Configuration 注解，自动加载配置类，并根据其中的其他注解实现相应的自动装配。

具体来说，Spring Boot 通过以下几个步骤来完成自动装配：

扫描 META-INF/spring.factories 文件，该文件按照键值对的形式记录了各个依赖的自动装配配置类。
根据配置文件中的键值对，获取自动装配配置类的全类名。
实例化配置类，并执行其中标记有 @Bean 注解的方法，将返回的对象注册到 Spring 容器中。
根据条件注解（如 @ConditionalOnClass、@ConditionalOnMissingBean 等），判断是否满足某些条件，进而决定是否进行自动装配。
通过这种方式，Spring Boot 实现了依赖的自动装配。这样一来，我们在编写 Spring Boot 项目时，只需要添加相应的依赖，无需手动配置太多内容，就能够快速搭建和运行一个基于 Spring Boot 的应用。
```

```
pom.xml中
	spring boot的核心依赖在父工程中 springboot为了简化开发可以选择不使用父工程的方式进行项目构建。这意味着你不需要在pom.xml中引入父工程的依赖和配置。
通常情况下，使用Spring Boot的父工程可以提供一些默认的依赖和配置，帮助简化项目的搭建和管理。但是在某些场景下，你可能希望自己完全控制项目的依赖和配置，这时可以选择不使用父工程。
	我们为了理解springboot的装配原理 从父工程开始下手
```

## pom.xml

```
springboot的核心依赖在父工程中(spring-boot-dependencies)
我们在者引入一些springboot依赖的时候 不需要指定版本 就是因为父工程中定义了很多相关的依赖版本
```

## 启动器

```
启动器
	<dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
    </dependency>
   简单理解 springboot的启动器就是springboot的启动场景
   例如spring-boot-starter-web 会帮我们自动导入web环境所需要的依赖
   springboot会将所有的功能场景都变成一个个的启动器 我们需要使用什么功能只需要找到对应的启动器即可
   		spring-boot-starter-xxx
   查看springboot启动器有那些
   		找到对应的文档 然后找到Using Spring Boot最后找到starters 
   		这个页面中是springboot对应的所有启动器
   		https://docs.spring.io/spring-boot/docs/2.7.15/reference/html/using.html#using.build-systems.starters
   		
```

## 主程序

```
主程序
	@SpringbootApplication 用来标注这个类是一个springboot的应用 启动类下的所有资源被导入
	SpringbootApplication.run(xxx.class,args); 用来启动springboot应用
    
    注解：
    	@SpringBootConfiguration
    		springboot的配置
    		@Configuration 表示springboot的配置类
    		@Component 这说明本身也是一个spring的组件
    		
		@EnableAutoConfiguration 自动配置
			@AutoConfigurationPackage  自动配置包
				@Import(AutoConfigurationPackages.Registrar.class)  导入自动配置 包注册 
					规定了扫描的路径 如果不是在主程序同级或下级目录 无法被扫描到
			@Import(AutoConfigurationImportSelector.class) 自动配置导入选择
				List<String> configurations = 
					getCandidateConfigurations(annotationMetadata, attributes);
					
```

获取所有的配置

```java
protected List<String> getCandidateConfigurations(AnnotationMetadata metadata, AnnotationAttributes attributes) {
		List<String> configurations = SpringFactoriesLoader.loadFactoryNames(getSpringFactoriesLoaderFactoryClass(),
				getBeanClassLoader());
		Assert.notEmpty(configurations, "No auto configuration classes found in META-INF/spring.factories. If you "
				+ "are using a custom packaging, make sure that file is correct.");
		return configurations;
	}
```

META-INF/spring.factories 是自动配置的核心文件

![image-20230920005222591](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310101545687.png)

## 自动配置原理分析

![img](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310101721375.png)



## 结论

```
springboot所有的自动配置都是在启动的时候扫描并加载  所有的自动配置类都在spring.factories文件中 但不一定会生效，要判断条件是否成立，只有导入了对应的starter 就有对应的启动器了 有了启动器 我们自动装配才会生效
也就是必须同时满足@ConditionalOnClass注解中所有的属性才会生效

1. springboot在启动的时候 从类路径下/META-INF/spring.factories文件中获取指定的值
	spring.factories文件在xxxx包下
2. 将这些自动配置的类导入容器，自动配置就会生效
3. 以前我们需要自动配置的东西 现在springboot帮我们做了
4. 整合J2EE解决方案和自动配置的东西都在spring-boot-autoconfigure-xxxx.jar包下
5. 它会把所有需要导入的组件，以类名的方式返回，这些组件就会被添加到容器中
6. 容器中也会存在非常多的xxxAutoConfiguration文件(@Bean) 就是这些类给同期中导入了这个场景所需要的组件并自动配置 @Configuration 
7. 有了自动配置类 免去了我们手动编写配置文件的工作
```

## 元注解解释

```
Spring Boot 元注解是指用于定义注解的注解。它们通常用于组合多个注解，以简化配置和减少重复性代码的编写。Spring Boot 提供了一些常用的元注解，包括：

1. @Target：用于指定注解可以应用的目标元素类型，如类、方法、字段等。
2. @Retention：用于指定注解的保留策略，如源代码级别的保留、编译时保留或运行时保留。
3. @Documented：用于指定注解是否包含在生成的文档中。
4. @Inherited：用于指定注解是否可以被子类继承。

这些元注解可以结合自定义注解使用，帮助我们更灵活地定义和使用注解。
```

Springboot是怎么运行的

```
最初以为只是运行了一个main方法 结果却是开启了一个服务
SpringApplication.run分析
	分析该方法主要分为两部分，一部分是SpringApplication的实例化，一部分是run方法的执行
SpringApplication主要作用：
	1 推断当前应用的类型是普通的项目还是web项目
	2 查找并加载所有可用初始化器，设置到initializers属性中
	3 找出所有的应用程序监听器 设置到listeners属性中
	4 推断并设置main方法的定义类 找到运行的主类
run方法：
	1. 执行该方法时会先判断当前项目是普通项目还是web项目
	2. 推断并设置main方法的定义类并找到运行的主类
```

