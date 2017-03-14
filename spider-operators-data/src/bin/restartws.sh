#!/bin/bash

cd `dirname $0`
BIN_DIR=`pwd`

cd ..
DEPLOY_HOME=`pwd`

pid=`ps aux | grep -v gerp | grep java| grep "$DEPLOY_HOME" | awk '{print $2}'`

if [ -n "$pid" ] && [ "$pid" > 0 ];
then 
	"$BIN_DIR"/killws.sh && "$BIN_DIR"/startws.sh $1
else
	"$BIN_DIR"/startws.sh $1	
fi


