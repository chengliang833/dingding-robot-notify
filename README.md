#dingding-robot-notify

##docker添加镜像
```
//添加dockerfile到本地镜像 最后的.表示Dockerfile执行相对路径(若Dockerfile内存在相对路径)
docker build -t ulane/notify:1.0 .

//启动
docker run -itd -p 8090:8090 --name notify ulane/notify:1.0
```



