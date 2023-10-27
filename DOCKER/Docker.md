# Docker

## 简介

```
Docker 是一个开源的应用容器引擎，基于 Go 语言 并遵从Apache2.0协议开源。
Docker 可以让开发者打包他们的应用以及依赖包到一个轻量级、可移植的容器中，然后发布到任何流行的 Linux 机器上，也可以实现虚拟化。
容器是完全使用沙箱机制，相互之间不会有任何接口（类似 iPhone 的 app）,更重要的是容器性能开销极低。
Docker 从 17.03 版本之后分为 CE（Community Edition: 社区版） 和 EE（Enterprise Edition: 企业版），我们用社区版就可以了

官网地址
https://www.docker.com/

Docker的应用场景
Web 应用的自动化打包和发布。
自动化测试和持续集成、发布。
在服务型环境中部署和调整数据库或其他的后台应用。
从头编译或者扩展现有的 OpenShift 或 Cloud Foundry 平台来搭建自己的 PaaS 环境。
Docker 的优点
1、简化程序：
Docker 让开发者可以打包他们的应用以及依赖包到一个可移植的容器中，然后发布到任何流行的 Linux 机器上，便可以实现虚拟化。Docker改变了虚拟化的方式，使开发者可以直接将自己的成果放入Docker中进行管理。方便快捷已经是 Docker的最大优势，过去需要用数天乃至数周的	任务，在Docker容器的处理下，只需要数秒就能完成。
2、避免选择恐惧症：
如果你有选择恐惧症，还是资深患者。那么你可以使用 Docker 打包你的纠结！比如 Docker 镜像；Docker 镜像中包含了运行环境和配置，所以 Docker 可以简化部署多种应用实例工作。比如 Web 应用、后台应用、数据库应用、大数据应用比如 Hadoop 集群、消息队列等等都可以打包成一个镜像部署。
3、节省开支：
一方面，云计算时代到来，使开发者不必为了追求效果而配置高额的硬件，Docker 改变了高性能必然高价格的思维定势。Docker 与云的结合，让云空间得到更充分的利用。不仅解决了硬件管理的问题，也改变了虚拟化的方式。
```

![1570629855968088860](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309291608138.jpg)

## 安装

基于Linux操作系统

```
Docker从1.13版本之后采用时间线的方式作为版本号，分为社区版CE和企业版EE。
社区版是免费提供给个人开发者和小型团体使用的，企业版会提供额外的收费服务，比如经过官方测试认证过的基础设施、容器、插件等。
社区版按照stable和edge两种方式发布，每个季度更新stable版本，如17.06，17.09；每个月份更新edge版本，如17.09，17.10。
我们平时用社区版就足够了。所以我们安装社区版；
我们主要参考：https://docs.docker.com/install/linux/docker-ce/centos/  来安装；


切换到root用户
1、Docker 要求 CentOS 系统的内核版本高于 3.10 ，查看本页面的前提条件来验证你的CentOS 版本是否支持 Docker 。
通过 uname -r 命令查看你当前的内核版本
 $ uname -r
2、使用 root 权限登录 Centos。确保 yum 包更新到最新。
$ yum update
3、卸载旧版本(如果安装过旧版本的话)
$ yum remove docker  docker-common docker-selinux docker-engine
4、安装需要的软件包， yum-util 提供yum-config-manager功能，另外两个是devicemapper驱动依赖的
$ yum install -y yum-utils device-mapper-persistent-data lvm2
5、设置yum源
$ yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
6，安装最新版本的Docker
$ yum install docker-ce docker-ce-cli containerd.io
7，启动Docker并设置开机启动
$ systemctl start docker
$ systemctl enable docker
8，验证Docker
$ docker version
```

![QQ截图20191009234333.jpg](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309291615331.jpeg)

```
9，Docker HelloWorld测试；
$ docker run hello-world

因为本地没有这个镜像，所以从远程官方仓库去拉取，下载；
```

![QQ截图20191009234544.jpg](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309291615052.jpeg)

![QQ截图20191009234657.jpg](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309291615655.jpeg)

## HelloWorld运行原理解析

```
运行  docker run hello-world
本地仓库未能找到该镜像，然后去远程仓库寻找以及下载该镜像；
```

![QQ截图20191010190440.jpg](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309291616634.jpeg)

```
从左到右 client客户端，Docker运行主机，远程仓库；
docker build ,pull，run分别是 构建，拉取，运行命令，后面再细讲；
中间Docker主机里有 Docker daemon主运行线程，以及Containers容器，容器里可以运行很多实例，（实例是从右侧Images镜像实例化出来的）Images是存储再本地的镜像文件，比如 Redis，Tomat这些镜像文件；
右侧是Registry镜像仓库，默认远程镜像仓库 https://hub.docker.com/  不过是国外主机，下载很慢，不稳定，所以我们后面要配置成阿里云仓库镜像地址，稳定快捷；
```

![QQ截图20191010191137.jpg](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309291616616.jpeg)

执行 docker run hello-world的过程看如下图例：

![QQ截图20191010190301.jpg](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309291617607.jpeg)

## 配置阿里云镜像仓库

```
Docker默认远程仓库是https://hub.docker.com/
由于是国外主机，类似Maven仓库，慢得一腿，经常延迟，破损；
所以我们一般都是配置国内镜像，比如阿里云，网易云等；推荐阿里云，稳定点；

配置步骤如下：
1，登录进入阿里云镜像服务中心，获取镜像地址
进入阿里云容器镜像服务地址：  https://cr.console.aliyun.com/
使用你的淘宝账号密码登录
```

![image-20230929161950103](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309291619321.png)

```
2，在/etc/docker目录下找到在daemon.json文件（没有就新建），将下面内容写入
{
 "registry-mirrors": ["https://f8p5n2uv.mirror.aliyuncs.com"]
}
3，重启daemon
systemctl daemon-reload
4，重启docker服务
systemctl restart docker
5，测试
例如我们下载一个nginx的镜像并允许它
docker run nginx

```

![image-20230929185824475](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309291858601.png)

## docker基本命令

```
启动Docker
systemctl start docker

停止Docker
systemctl stop docker

重启Docker
systemctl restart docker

开机启动Docker
systemctl enable docker

查看Docker概要信息
docker info

查看Docker帮助文档
docker --help

查看Docker版本信息
docker version
```

## docker镜像常用命令

```
1，docker images 列出本机所有镜像
docker images
列出本机所有镜像


REPOSITORY	镜像的仓库源
TAG		镜像的标签（版本）同一个仓库有多个TAG的镜像，多个版本；我们用REPOSITORY:TAG来定义不同的镜像；
IMAGE ID	镜像ID，镜像的唯一标识
CREATE		镜像创建时间
SIZE		镜像大小
	
OPTIONS 	可选参数：
-a	显示所有镜像（包括中间层）
-q	只显示镜像ID
-qa	可以组合
--digests	显示镜像的摘要信息
--no-trunc	显示完整的镜像信息 
```

![image-20230929190003021](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309291900012.png)

```
2，docker search 搜索镜像
和 https://hub.docker.com/ 这里的搜索效果一样；

OPTIONS可选参数：
--no-trunc	显示完整的镜像描述
-s	列出收藏数不小于指定值的镜像
--automated	只列出Docker Hub自动构建类型的镜像
```

![image-20230929190256691](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309291902825.png)

```
3，docker pull 下载镜像
docker pull 镜像名称:[TAG]
注意：不加TAG，默认下载最新版本latest
```

![image-20230929233837577](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309292338784.png)

```
4，docker rmi 删除镜像
1，删除单个：docker rmi 镜像名称:[TAG]
如果不写TAG，默认删除最新版本latest
有镜像生成的容器再运行时候，会报错，删除失败
我们需要加 -f 强制删除

2，删除多个：docker rmi -f 镜像名称1:[TAG] 镜像名称2:[TAG]
中间空格隔开

3，删除全部：docker rmi -f $(docker images -qa)
```

![image-20230929190408160](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309291904827.png)

![image-20230929190457386](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309291904618.png)

## Docker推送镜像到hub服务器

我们可以通过docker push命令 把自己本地定制的镜像推送到Hub服务器，方便全球开发者使用，包括自己

```
步骤一：
https://hub.docker.com/ 注册下 得到docker id和密码

步骤二：
我们用docker login登陆hub服务器

步骤三：
docker push推送
docker push tomcat7:1.1

推送成功：
登陆 https://hub.docker.com/   点击 Repositories 菜单

hub.docker.com经常访问失败 在我们搜索镜像时 可以通过下列网址进行检索
hub.axlinux.top
```







## Docker创建并启动容器

```
docker run [OPTIONS] IMAGE [COMMAND] [ARG...]

--name="容器新名字"：为容器指定一个名称；
-i：以交互模式运行容器，通常与-t或者-d同时使用；
-t：为容器重新分配一个伪输入终端，通常与-i同时使用；
-d: 后台运行容器，并返回容器ID；
-P: 随机端口映射，容器内部端口随机映射到主机的端口
-p: 指定端口映射，格式为：主机(宿主)端口:容器端口


 启动普通容器： docker run --name 别名 镜像ID  
 启动交互式容器：  docker run -it --name 别名 镜像ID 
 	来运行一个容器，取别名，交互模式运行，以及分配一个伪终端
 	
守护式方式创建并启动容器
docker run -di --name 别名 镜像ID 
执行完命令后，终端依然再宿主机上；

例如 启动一个centos
docker run -di --name centosA  5d0da3dc9764

```

![image-20230929233404292](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309292335563.png)

![image-20230929233654914](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309292336061.png)

```
启动容器，并执行/bin/bash命令；
 docker run -it --name 别名 镜像ID  /bin/bash命令
端口映射；
docker run -it -p 8888:8080 tomcat
docker run -it -P tomcat
```

![image-20230929234019130](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309292340299.png)

有时会遇到一些镜像访问出错，我们可以换成官网镜像

docker pull tomcat:8.0.15-jre7

在次运行

docker run -it --name tomcat8 -p 8888:8080 tomcat:8.0.15-jre7

![image-20230929235149831](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309292351077.png)

## Docker列出容器

```
docker ps [OPTIONS]

OPTIONS说明：
-a :显示所有的容器，包括未运行的。
-f :根据条件过滤显示的内容。
--format :指定返回值的模板文件。
-l :显示最近创建的容器。
-n :列出最近创建的n个容器。
--no-trunc :不截断输出。
-q :静默模式，只显示容器编号。
-s :显示总的文件大小。

例如
docker ps 查看正在运行的容器
docker ps -a 查看所有容器
docker ps -n 2  显示最近创建的2个容器
docker ps -f status=exited 查看停止的容器
```

![image-20230929235343945](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309292353100.png)

## Docker退出容器

```
exit 容器停止退出
	需要进入到容器后在执行exit
ctrl+P+Q 容器不停止退出
```

![image-20230929235721801](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309292357960.png)

## Docker进入到容器

```
docker exec -it 容器ID or 容器名 /bin/bash
例如 进入到tomcat
docker exec -it 071e492c35be /bin/bash
```

![image-20230930001449243](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309300014688.png)

## Docker提交成镜像

```
docker commit -a="作者" -m="备注" 运行时容器ID 新的镜像名
例如 将tomcat提交成jerrycat
docker commit -a="jerry" -m="remark" 071e492c35be jerrycat
```

![image-20230930001939316](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309300019695.png)

## 推送到docker hub(略)

docker hub 访问不到

## 推送到阿里镜像仓库

```
仓库地址
https://cr.console.aliyun.com/


```

新建命名空间

在新建仓库时需要选择命名空间

![image-20230930003447750](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309300034906.png)

新建镜像仓库

![image-20230930003356550](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309300033038.png)

![image-20230930003520800](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309300035276.png)

然后选择本地仓库

新建完成

![image-20230930003647515](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309300036643.png)

下面进行推送

```
1. 登录阿里云Docker Registry
$ docker login --username=qzmu*****@163.com registry.cn-hangzhou.aliyuncs.com
用于登录的用户名为阿里云账号全名，密码为开通服务时设置的密码。
您可以在访问凭证页面修改凭证密码。
2. 从Registry中拉取镜像
$ docker pull registry.cn-hangzhou.aliyuncs.com/etjava/et-images:[镜像版本号]
3. 将镜像推送到Registry
$ docker login --username=qzmu*****@163.com registry.cn-hangzhou.aliyuncs.com
$ docker tag [ImageId] registry.cn-hangzhou.aliyuncs.com/etjava/et-images:[镜像版本号]
$ docker push registry.cn-hangzhou.aliyuncs.com/etjava/et-images:[镜像版本号]
请根据实际镜像信息替换示例中的[ImageId]和[镜像版本号]参数。
4. 选择合适的镜像仓库地址
从ECS推送镜像时，可以选择使用镜像仓库内网地址。推送速度将得到提升并且将不会损耗您的公网流量。
如果您使用的机器位于VPC网络，请使用 registry-vpc.cn-hangzhou.aliyuncs.com 作为Registry的域名登录。
5. 示例
使用"docker tag"命令重命名镜像，并将它通过专有网络地址推送至Registry。
$ docker images
REPOSITORY                                                         TAG                 IMAGE ID            CREATED             VIRTUAL SIZE
registry.aliyuncs.com/acs/agent                                    0.7-dfb6816         37bb9c63c8b2        7 days ago          37.89 MB
$ docker tag 37bb9c63c8b2 registry-vpc.cn-hangzhou.aliyuncs.com/acs/agent:0.7-dfb6816
使用 "docker push" 命令将该镜像推送至远程。
$ docker push registry-vpc.cn-hangzhou.aliyuncs.com/acs/agent:0.7-dfb6816
```

![image-20230930004509944](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309300045953.png)

可以看到刚刚推送上来的镜像

![image-20230930004609545](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309300046835.png)



## 拉取推送成功的镜像并启动测试

```
docker pull registry.cn-hangzhou.aliyuncs.com/etjava/et-images:1.0
```

![image-20230930005100302](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309300051381.png)

启动并测试

![image-20230930005230180](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309300052690.png)

![image-20230930005258141](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309300052118.png)

## Docker启动容器

```
docker start 容器ID or 容器名
```

![image-20230930000035677](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309300000728.png)

## Docker重启容器

```
docker restart 容器ID or 容器名
```

## Docker停止容器

```
docker stop 容器ID or 容器名

暴力删除，直接杀掉进程 （不推荐）
docker kill 容器ID or 容器名
```

## Docker删除容器

```
docker rm 容器ID  
如果删除正在运行的容器，会报错，我们假如需要删除的话，需要强制删除；
强制删除docker rm -f 容器ID

删除多个容器 
docker rm -f 容器ID1  容器ID2 中间空格隔开

删除所有容器
docker rm -f $(docker ps -qa)
```

![image-20230930000445444](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309300004569.png)

## Docker 查看容器详细信息

```
docker inspect 容器ID
可以查看容器相关信息
例如 
将应用程序(放在docker中tomcat) 与 mysql(docker中mysql)都放在同一个宿主机上
此时 他们不能直接通过宿主机的IP+端口进行访问
需要使用宿主机给容器分配的IP地址进行访问
因此 就需要先获取到docker给容器的IP是什么

```



## Docker启动镜像

```
docker run -it -p 8888:8080 镜像ID或镜像名:tag
```

![image-20230930000750480](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309300007554.png)

## 容器目录挂在

```
我们可以在创建容器的时候将宿主机的目录与容器的内的目录进行映射，这样就可以实现宿主机和容器目录的双向数据自动同步
作用
	通过容器目录挂在 能够轻松实现代码的上传，配置与修改等
语法
	单目录挂在
	docker run -it -v /宿主机目录:/容器目录 镜像ID或镜像名
		注意 宿主机的目录必须要存在，容器内的目录如果不存在会自动创建
	多目录挂在
	docker run -it -v /宿主机目录1:/容器目录1 -v /宿主机目录2:/容器目录2 镜像ID或镜像名
		注意：
			如果挂在多个目录 可能会出现权限不足的提示，这是因为centos7的安全模块把权限禁用导致的
			需要添加 --privileged=true来解决挂在目录没有权限的问题
```

给tomcat挂在一个目录

docker run -it --name mytomcat81 -p 8888:8080 -v /home/hData/:/webapp b342c8d87e3d

![image-20230930010649642](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309300106669.png)

![image-20230930010817425](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309300108487.png)



![image-20230930011009046](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309300110012.png)

## 挂在只读目录

```
有时候 容器内部的目录可能因为某些需求导致 文件不允许修改 在做挂在的时候通过ro实现即可
docker run -it --name mytomcat81 -p 8888:8080 -v /home/hData/:/webapp:ro b342c8d87e3d
```

![image-20230930011752984](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309300117332.png)

## 综合练习

```
写个静态页面挂在tomcat中运行
docker run -it --name mytomcat8 -p 8888:8080 -v /home/eData1/:/usr/local/webapps/ROOT b342c8d87e3d
```

```
1 运行tomcat容器
docker run -it --name tomcat -p 8080:8080 tomcat的镜像名称或镜像ID
2 宿主机中home目录下新建tomcat目录，复制容器中的conf，webapps到宿主机中home目录下的tomcat中
语法
	docker cp 容器ID:/容器中的目录 /宿主机目录

docker cp d04b0e62732b:/usr/local/tomcat/conf/ /home/tomcat8/
docker cp d04b0e62732b:/usr/local/tomcat/webapps/ /home/tomcat8/

3 将宿主机中的homt/tomcat目录挂在到tomcat容器中
语法
docker run -it -p 8088:8080 --name tomcat8 -v /home/tomcat8/conf/:/usr/local/tomcat/conf/ -v/home/tomcat8/webapps/:/usr/local/tomcat/webapps/ tomcat镜像ID


	


```

复制目录到宿主机然后在将宿主机目录挂在到容器中

![image-20231001095804165](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310010958602.png)

测试

1 在宿主机中的webapps总新建一个页面

![image-20231001100121977](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310011001411.png)

2 查看容器中对应的目录有没有生成该页面

![image-20231001100322584](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310011003654.png)

3 本地访问该静态页面测试

直接访问webapps下的hello.html出现无法访问情况 我们可以将hello.html文件放到一个目录中在进行访问即可

![image-20231001101026132](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310011010892.png)

![image-20231001101039819](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310011010801.png)

## 综合练习2

```
使用docker 搭建mysql数据库
要求本地可以正常使用到docker中的mysql数据库
```

### 到镜像仓库搜索mysql镜像

```
https://hub.axlinux.top/
```

![image-20231001102246429](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310011022216.png)

![image-20231001102321974](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310011023932.png)

### 拉取镜像

```
docker pull mysql:5.7.5
```

![image-20231001104136108](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310011041484.png)

### 挂载mysql目录

#### 1 复制mysql中的目录到宿主机

```
复制容器中目录前需要先启动下容器 因为需要查看容器中目录的具体问题
docker run -it --name mysqlA -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 镜像ID
例如
	docker run -p 3306:3306 --name mysqlA -e MYSQL_ROOT_PASSWORD=123456 6448ea6ff3b5
复制容器目录到宿主机
docker cp 容器ID:/etc/mysql/conf.d /home/mysql57/
docker cp 容器ID:/var/log/ /home/mysql57/
docker cp 容器ID:/var/lib/mysql/ /home/mysql/data/

例如
	docker cp dbd0cd6c69d8:/etc/mysql/ /home/mysql57/
	docker cp dbd0cd6c69d8:/var/log/ /home/mysql57/log/
	docker cp dbd0cd6c69d8:/var/lib/mysql/ /home/mysql57/data/
```

![image-20231001110234547](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310011102111.png)

![image-20231001111009485](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310011110583.png)

#### 2 挂载容器目录

```
挂载前 需要先关闭启动的mysql容器 因为我们取的名字和端口等会与之前的相同 
docker stop 容器ID

例如
docker run -d --name mysql57 -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 -v /etc/mysql/:/home/mysql57/conf/ -v /etc/mysql/log/:/home/mysql57/log/ -v /var/lib/mysql/:/home/mysql57/data/ 6448ea6ff3b5
d051fe43f9931708f164b14af3ffe17957811836e597ca31f827084b86ada100

```

![image-20231001111648319](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310011116613.png)



#### 3 本地连接测试

![image-20231001111847728](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310011118345.png)

#### 4 使用容器中的mysql注意事项

```
如果你的应用程序是在docker中web容器运行，数据库是docker中另个容器运行
此时 容器与容器之间是无法互通访问的，需要通过宿主机给容器分配的IP地址+端口进行访问
查看宿主机给容器分配的IP等信息 使用 docker inspect 容器ID
例如
docker inspect d051fe43f993
```

![image-20231001112226953](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310011122382.png)

## Docker镜像备份与恢复

### 概述

```
在开发的时候 镜像会自定义镜像，然后通过commit提交到镜像仓库 但有时处于一些机密性 禁止提交到镜像仓库
此时 通常会通过镜像的物理copy到另台docker服务器 然后在另台docker服务器上面进行恢复


```

### 镜像备份

```
语法
docker save -o 需要备份的镜像名称 原镜像名称:tag版本 
	这里需要注意 不能使用镜像ID 否则无法恢复
	备份的镜像会在执行指令的当前目录进行创建
```

#### 备份tomcat镜像

```
docker save -o tomcat8.tar registry.cn-hangzhou.aliyuncs.com/etjava/et-images:1.0
```

![image-20231001113521820](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310011135225.png)

### 镜像恢复

```
恢复镜像
docker load -i 镜像文件名称
例如
docker load -i tomcat8.tar
```

![image-20231001114234879](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310011142376.png)

## dockerfile

### 概述

```
DockerFile是由一系列命令和参数构成的脚本，这些命令应用于擦做系统（centos或unbuntu）基础镜像并最终创建一个新的镜像
我们前面都是使用手工方式 修改配置文件，或是添加 删除文件目录 来构建一种新的镜像，这种手工的方式容易出错且不能复用
这里我们可以借助dockerfile脚本方式来实现自动化构建
优点：
	对于开发人员 可以为开发团队提高一个完全一致的开发环境
	对于测试人员 可以直接拿开发时所构建的镜像环境直接使用
	对于运维人员 在部署时 可以实现应用的无缝移植
```

### dockerfile常用命令

| 命令       | 描述                                                         |
| ---------- | ------------------------------------------------------------ |
| FROM       | 定义基于哪个镜像启动构建流程                                 |
| MAINTAINER | 生命镜像维护者信息                                           |
| LABEL      | 镜像的描述信息                                               |
| ENV        | 设置镜像的环境变量                                           |
| RUN        | 构建镜像时需要运行的命令                                     |
| WORKDIR    | 终端登录时默认进入的目录                                     |
| EXPOSE     | 当前容器对外暴漏的端口号                                     |
| ADD        | 将宿主机中的文件复制到容器中 如果是一个压缩文件 在复制后会自动解压缩 |
| COPY       | 与ADD类似 只是不会自动解压缩                                 |
| VOLUME     | 创建一个可以从本地主机或其他容器挂载的挂载点 通常用来存放数据库和需要保存的数据等 |
| CMD        | 指定容器启动时要运行的命令                                   |
| ENRTYPOINT | 指定容器启动时要运行的命令                                   |
| ONBUILD    | 当定义一个被继承的Dockerfile时运行的命令，父镜像在被子镜像继承后父镜像的onbuild被触发，可以把onbuild理解成一个触发器 触发的是父镜像 |

### 构建centos

```
1. 编写dockerfile时 dockerfile文件不需要添加任何后缀

脚本内容
FROM centos:7
MAINTAINER etjava<etjava@hotmail.com>

LABEL name="etjava centos Image"\
	build=date="20231001"

ENV WORKPATH /home/
WORKDIR $WORKPATH

RUN yum -y install net-tools
RUN yum -y install vim

EXPOSE 80
CMD /bin/bash

2. 构建dockerfile
指令
	docker build -f dockerfile文件名 -t 指定镜像名:版本 .
		注意 末尾需要加英文的句号
	例如
	docker build -f /home/dockerfile/myCentosDockerfile -t etjavacoentos:7.0 .

```

编译构建镜像时遇到的错误

```
 => ERROR [3/4] RUN yum -y install net-tools
 该错误是在构建时 源镜像的问题 
 解决方案
 在FROM centos后面加上版本号
```

![image-20231001134529461](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310011345621.png)

![image-20231001134640804](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310011346708.png)

![image-20231001134942312](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310011349424.png)

### 查看镜像历史

```
docker history 镜像ID
例如
docker history 3400ac5da22e
```

![image-20231001135134691](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310011351081.png)

### 构建tomcat

#### 前期准备

```
需要准备linux版本的JDK或JRE及tomcat压缩包 另外在准备一个文本文件 用于演示COPY和ADD
COPY 不会自动解压缩，ADD会自动解压缩
```

![image-20231001162625845](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310011626206.png)

#### 编写dockerfile

```
FROM centos:7
MAINTAINER etjava<etjava@hotmail.com>

LABEL name="etjava centos Image"\
	build=date="20231001"

COPY copyright.txt /homt/dockerfile/copyright.txt
ADD jdk-8u301-linux-x64.tar.gz /home/
ADD apache-tomcat-8.5.73.tar.gz /home/

ENV WORKPATH /home/apache-tomcat-8.5.73/
WORKDIR $WORKPATH

ENV JAVA_HOME /home/jdk1.8.0_301
ENV CLASSPATH $JAVA_HOME/lib/dt.jar;$JAVA_HOME/lib/tools.jar
ENV CATALINA_HOME /home/apache-tomcat-8.5.73/
ENV CATALINA_BASE /home/apache-tomcat-8.5.73/
ENV PATH $PATH:$JAVA_HOME/bin:CATALINA_HOME/lib:$CATALINA_HOME/bin

EXPOSE 8080
CMD ["/home/apache-tomcat-8.5.73/bin/catalina.sh","run"]
```



#### 执行构建

docker build -f /home/dockerfile/tomcatdockerfile -t mytomcat:85 .

![image-20231001142907664](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310011429053.png)

#### 运行测试

docker run -d -p 80:8080 mytomcat:85

![image-20231001143233169](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310011432101.png)

## Dockerfile通过VOLUME实现容器卷

```
前面我们通过启动命令 -v 宿主机目录:容器卷目录 来实现容器卷目录挂载，但由于定义dockerfile并不能保证所在的宿主机上都有这样的特定目录 所以在定义dockerfile时 只能指定容器卷目录
虽然通过VOLUME 可以实现在容器卷的自动挂载 但挂载到宿主机的目录会随机自动生成 查找起来比较麻烦 因此还是建议使用前面的方式实现宿主机目录与容器卷目录的挂载
```

### VOLUME测试脚本

```
FROM centos:7
VOLUME ["/home/v1","/home/v2"]
CMD /bin/bash
```

### 构建

```
docker build -f /home/dockerfile/myvolumedockerfile -t mycentoswithvolume:1.0 .
```

![image-20231001162756031](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310011627388.png)



### 启动测试

```
启动时直接进入到容器中  使用-it指令
然后新开一个窗口 查看容器的历史记录(详细信息) 主要是看下挂载目录是哪个(Mounts节点下)


启动新建的镜像
docker run -it mycentoswithvolume:1.0
查看容器的历史记录
docker inspect 容器ID
```

![image-20231001163312286](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310011633359.png)

![image-20231001163525963](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310011635916.png)

### CMD ENTRYPOINT的区别和联系

```
dockerfile中CMD，ENTRYPOINT的区别和联系
CMD和ENTRYPOINT都是容器在启动的时候 执行命令用的，都支持exec和shell方式，一般用法是单独一个CMD或先ENTRYPOINT 后结合CMD使用
假如有多个CMD 启动的时候带命令参数 会覆盖前面的CMD命令，只有最后一个CMD生效，因此 我们平时使用CMD时 都是单独一个CMD命令即可 启动命令也不带参数


演示：
例如 
	docker run -it -p 8080:8080 tomcat:8.5  
		会正常启动 原因是官方的tomcat镜像最后有一个 CMD ["catalina.sh","run"]
	如果 我们使用如下命令启动
	docker run -it -p 8080:8080 tomcat:8.5 /bin/bash
	或 
	docker run -it -p 8080:8080 tomcat:8.5 ls -l
	则不会启动tomcat 而是会执行最后的指令
	此时可以看出 CMD 只有最后一个指令才会生效

```

![image-20231001173135841](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310011731892.png)

```
CMD语法
CMD ["executable","param1","param2"] (exec格式 推荐使用)
	运行一个可执行的文件并提供参数
CMD ["param1","param2"] (默认的 为ENTRYPOINT指定参数)
CMD command param1 param2 (shell)
	shell用法 以/bin/sh -C 的方法执行的命令

ENTRYPOINT 语法

ENTRYPOINT ["executable","param1","param2"] (exec格式 推荐使用)
ENTRYPOINT command param1 param2 (shell格式)

```

#### 测试CMD

```
有多个CMD时 只执行最后一个
测试脚本
FROM centos:7
CMD echo "abc"
CMD ["/bin/echo","defg"]
```

创建dockerfile内容如下

```
FROM centos:7
CMD echo "abc"
CMD ["/bin/echo","defg"]
```

![image-20231001174226034](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310011742131.png)

#### 构建

```
docker build -f /home/dockerfile/myvolumedockerfile -t cmdcommand:1.0 .
```

![image-20231001175213969](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310011752873.png)

#### 启动镜像测试

![image-20231001180215474](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310011802872.png)

#### 测试ENTORYPOINT

测试脚本

```
FROM centos:7
ENTRYPOINT ["ls"]
CMD ["-l"]
```

构建镜像

```
docker build -f /home/dockerfile/entorypointdockerfile -t entorypoint:11 .
```

启动测试

```
docker run -it 6298db2cadae
```

![image-20231001180639571](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310011806798.png)

总结

```
CMD 是默认命令 其中的参数可以被动态替换
	在执行容器时 可直接跟上命令参数 替换的就是CMD中的命令参数
	例如 docker run -it -p 8080:8080 tomcat:8.5 ls -l
ENTORYPOINT 用来指定要执行的命令参数 
	用它来指定的参数不会被动态的替换掉
通常两者结合使用 也就是说 当我们在启动容器时 不加任何额外参数 则使用ENTORYPINT+CMD中的
如果加了额外参数 则会替换CMD中的参数
```

![image-20231001181212451](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310011812396.png)

### ONBUILD

```
当构建一个被继承的dockerfile时运行的命令，父镜像在被子镜像继承后 夫精选的onbuild就会被触发，可以把onbuild理解为一个触发器
编写dockerfile时 其他命令都是为了自身镜像服务的 只有ONBUILD是为了子镜像服务

简单实例
父镜像dockerfile
FROM centos:7
ONBUILD RUN yum -y install vim
CMD /bin/bash

子镜像简单点
FROM parent
	这个parent是父镜像执行构建后的名字

当构建子镜像时 父镜像中的ONBUILD会被触发 子镜像会直接安装vim

实际应用中 一般是ONBUILD里执行的是一些父镜像暂时无法执行的东西 例如 一些COPY ADD等 可以留给子镜像去执行
父镜像仅仅提供基础支持 然后具体实行是子镜像操作


```

测试

```
1. 先构建父镜像
	观察父镜像构建时是否执行了安装vim操作
2. 在构建子镜像
	观察子镜像构建时是否执行了安装vim操作
3. 分别在父镜像和子镜像中使用vim指令 看下区别
```

![image-20231001182758490](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310011827596.png)

![image-20231001182941971](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310011829260.png)



## docker私库搭建

```
docker的私有仓库主要是企业内部用来存放镜像的，相对官方仓库 具有高安全性
```

### 搭建

```
1. 拉取私有仓库的镜像(私有仓库本身也是一个镜像)
docker pull registry
2. 启动私有仓库容器
docker run -di --name registry -p 5000:5000 registry:latest
3. 启动容器测试
页面可通过IP+端口进行访问测试
会看到是空的 这是因为我们还没有推送镜像到私有仓库
4. 修改daemon.json文件 让docker信任私有仓库地址
	/etc/docker/daemon.json
	添加
		"insecure-registries":["192.168.199.106:5000"]


我们可以将自己的镜像推送到私有仓库中
规则
	tag名称必须是私有仓库的IP地址:端口/自定义的镜像名
1. 打tag
	docker tag tomcat:8.0.15-jre7 私有仓库的IP:端口/mytomcat7:77
	例如 docker tag tomcat:8.0.15-jre7 192.168.199.106:5000/tomcat8
2. 提交到私有仓库
	docker push 私有仓库的IP:端口/mytomcat7:77
		推送时 不需要指定私库的名称 直接推送tag即可
		例如
		docker push 192.168.199.106:5000/tomcat8
3. 页面访问
	192.168.199.106:5000/v2/_catalog
	
```

![image-20231001184203101](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310011842093.png)

![image-20231001200342574](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310012003518.png)

```
拉取私有仓库中的镜像并运行
docker run -it -p 8080:8080 192.168.199.106:5000/tomcat8
```

![image-20231001201000492](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20231001201000492.png)