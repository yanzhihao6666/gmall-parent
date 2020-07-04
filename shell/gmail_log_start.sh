#!/usr/bin/env bash

# 启动日志脚本
# >/dev/null 输出级别 1 输出到黑洞
# 2>&1      输出级别 2 按照1的方式输出
java -jar /opt/project/realtimewarehouse0704/jar/gmail-logger-0.0.1-SNAPSHOT.jar >/dev/null 2>&1 &