# 找到APP运行的进程ID
cd /home/admin/application/target
# 进入app目录后启动版本的app
nohup java -jar modeshape-2.3.4.RELEASE.jar & echo $! > /apps/ms/ms.pid