# GitLab搭建（Docker版）

## 搜索镜像

```
docker search gitlab
```

## 拉取镜像

```
# 拉取Gitlab镜像 不加tag默认最新的（letest）
docker pull gitlab/gitlab-ce
```

## 启动容器

```
# 启动容器 
 docker run \
 -d -p 443:443 -p 80:80 -p 32:22 \
 --name gitlab \
 --restart always \
 -v /home/gitlab/config:/etc/gitlab \
 -v /home/gitlab/logs:/var/log/gitlab \
 -v /home/gitlab/data:/var/opt/gitlab  \
 --privileged=true \
 gitlab/gitlab-ce
```

| 命令                             | 描述                                                         |
| -------------------------------- | ------------------------------------------------------------ |
| -i                               | 以交互模式运行容器，通常与 -t 同时使用命令解释               |
| -t                               | 为容器重新分配一个伪输入终端，通常与 -i 同时使用             |
| -d                               | 后台运行容器，并返回容器ID                                   |
| -p 9980:80                       | 将容器内80端口映射至宿主机9980端口，这是访问gitlab的端口     |
| -p 9922:22                       | 将容器内22端口映射至宿主机9922端口，这是访问ssh的端口        |
| -v /home/gitlab/etc:/home/gitlab | 将容器/etc/gitlab目录挂载到宿主机/home/gitlab目录下，若宿主机内此目录不存在将会自动创建，其他两个挂载同这个一样 |
| --restart always                 | 容器自启动                                                   |
| --privileged=true                | 让容器获取宿主机root权限                                     |
| --name gitlab                    | 设置容器名称为gitlab                                         |
| gitlab/gitlab-ce                 | 镜像的名称，这里也可以写镜像ID                               |

![image-20231031082746919](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310312250514.png)

## 查看启动日志

```
docker logs -f gitlab
```



## 配置gitlab

```
直接在宿主机执行
vim /home/gitlab/config/gitlab.rb
```

```
#配置http协议所使用的访问地址,不加端口号默认为80
external_url 'http://localhost'
#配置ssh协议所使用的访问地址和端口
gitlab_rails['gitlab_ssh_host'] = 'localhost'
#此端口是run时22端口映射的32端口
gitlab_rails['gitlab_shell_ssh_port'] = 32
```

![image-20231031083031527](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310312250557.png)



## 重启容器

```
docker restart gitlab
```

## 查看初始root密码

```
docker默认生成的登录密码
sudo docker exec -it gitlab grep 'Password:' /etc/gitlab/initial_root_password
```

aluAjsEMfVE43ywzPW22rqMq1BgFy+NGVO3wHyLUmtI=

## 修改root密码

如果是物理机安装 则执行从step2开始执行

```
1. 进入容器 
	docker exec -it gitlab /bin/bash
2. 进入gitlab控制台
	gitlab-rails console -e production
3. 获取用户数据，修改root密码
    user = User.where(id: 1).first
    user.password='12345678' #输入新密码
    user.password_confirmation='12345678' #确认新密码
    user.save! #保存
    quit #退出

```





