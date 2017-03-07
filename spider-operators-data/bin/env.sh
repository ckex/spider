#!/bin/bash
#set ($jmx_port = 8883 )
#set ($debug_port = 1088 )
APP_NAME=spider-operators-data
MAIN_PATH=com.mljr.operators.SpringBootOperatorsMain
PRODUCTION=run
JAVA_HOME=/usr/local/jdk1.8.0_111/
OUTPUT_HOME=/data/var/log/spider-operators-data
DEBUG_PORT=$debug_port
JMX_PORT=8883
RMI_HOST=127.0.0.1
JMX_ACCESS=/root/jmxremote.access
JMX_PASSWD=/root/jmxremote.passwd

LOG_ROOT=$OUTPUT_HOME

CHAR_SET=UTF-8

export PRODUCTION JAVA_HOME OUTPUT_HOME DEBUG_PORT JMX_PORT LOG_ROOT 
export LANG=zh_CN.UTF-8
export LC_ALL=zh_CN.UTF-8 
