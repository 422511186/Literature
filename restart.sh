#!/bin/sh
echo =================================
echo  自动化部署脚本启动
echo =================================

echo 停止原来运行中的工程
APP_NAME=modeshape

# 查找进程id是否存在，存在则停止进程
tpid=`ps -ef|grep $APP_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
if [ ${tpid} ]; then
    echo 'Stop Process...'
    kill -15 $tpid
fi

# 睡眠两秒
sleep 2

# 再次查找进程id，存在则杀死进程（双重保障进程已停止）
tpid=`ps -ef|grep $APP_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
if [ ${tpid} ]; then
    echo 'Kill Process!'
    kill -9 $tpid
else
    echo 'Stop Success!'
fi

#echo 准备从Git仓库拉取最新代码
#cd /usr/local/ssm-crud
#
#echo 开始从Git仓库拉取最新代码
#git pull
#echo 代码拉取完成
#
#echo 开始打包
## 先clear在打包，取消单元测试
#output=`mvn clean package -Dmaven.test.skip=true`

echo 进入项目打包后的文件夹
cd /home/admin/application/target

echo 启动项目...
nohup java -jar modeshape-2.3.4.RELEASE.jar &> modeshape-2.3.4.RELEASE.log &
echo 项目启动完成.

