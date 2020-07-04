#!/bin/bash
# 功能： http 请求 -> nginx服务器转发 -> 3个springboot日志服务器系统
#        springboot日志服务器系统 将数据写入到 app.log 和 kafka

JAVA_BIN=/opt/module/jdk1.8/bin/java
APPNAME=gmail-logger-0.0.1-SNAPSHOT.jar
SERVER_PORT=8080

case $1 in
 "start")
   {

    for i in hadoop102 hadoop103 hadoop104
    do
     echo "========: $i==============="
     ssh $i  "$JAVA_BIN -Xms32m -Xmx64m  -jar /opt/project/realtimewarehouse0704/jar/gmail-logger-0.0.1-SNAPSHOT.jar --server.port=$SERVER_PORT >/dev/null 2>&1  &"
    done
     echo "========NGINX==============="
     /opt/module/nginx/sbin/nginx
  };;
  "stop")
  {
     echo "======== NGINX==============="
    /opt/module/nginx/sbin/nginx  -s stop
    for i in  hadoop102 hadoop103 hadoop104
    do
     echo "========: $i==============="
     ssh $i "ps -ef|grep $APPNAME |grep -v grep|awk '{print \$2}'|xargs kill" >/dev/null 2>&1
    done

  };;
esac