#!/bin/sh

WORKDIR=/home/deploy/dolphin

SOURCE_NAME=dolphin.source

PROJECT_NAME=dolphin.standalone.datamove

WANGJUBAO_APP=wangjubao.app

cd $WORKDIR

#rm -rf $WORKDIR/*
rm -rf $WORKDIR/$WANGJUBAO_APP/$PROJECT_NAME/*

#svn co http://svn.wangjubao.com/svn/repos/app/dolphin/trunk $WORKDIR/$SOURCE_NAME
cd $WORKDIR/$SOURCE_NAME/$PROJECT_NAME

svn up

cd $WORKDIR/$SOURCE_NAME/$PROJECT_NAME

mvn clean install -Dmaven.test.skip -Denv=release

cd ../target

cp $PROJECT_NAME.tar.gz  $WORKDIR/$WANGJUBAO_APP/$PROJECT_NAME

cd $WORKDIR/$WANGJUBAO_APP/$PROJECT_NAME

tar -zxvf $PROJECT_NAME.tar.gz 

rm -rf $PROJECT_NAME.tar.gz

mvn com.alibaba.maven.plugins:maven-autoconf-plugin:0.5:autoconf  -Dproperties=/home/deploy/antx.properties

tar -zcvf $PROJECT_NAME.tar.gz *

scp $PROJECT_NAME.tar.gz deploy@10.239.16.59:/data/home/deploy/feiying.wangjubao.app
scp $PROJECT_NAME.tar.gz deploy@10.239.16.60:/home/deploy/feiying.wangjubao.app
