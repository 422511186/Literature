FROM openjdk:8
#FROM registry.cn-shanghai.aliyuncs.com/wld-prototype/baseimage:0.7
#RUN apt-get -y install redis-server
WORKDIR app
ADD target/Literature.jar /root/Literature/Literature.jar
EXPOSE 8999
CMD java -jar /root/Literature/Literature.jar
#docker run -it -d -p 80:80 -v /wdl_workspace/upload:/wdl_workspace/upload -v /wdl_workspace/work:/wdl_workspace/work -v /usr/bin/docker:/usr/bin/docker -v /wdl_workspace/logs:/app/logs -v /etc/localtime:/etc/localtime -v /etc/timezone:/etc/timezone --rm --name proto $image