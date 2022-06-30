# 找到APP运行的进程ID
ps -ef|grep java
# 结束进程
kill -9 1343
# 进入app目录后启动版本的app
nohup java -jar ms.jar & echo $! > /apps/ms/ms.pid