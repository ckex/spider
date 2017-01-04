#!/bin/bash

# ./build spider
# ./build data-sync
# ./build.sh spider-monitor

function build_spider(){
	echo "build spider"

	# backdir="/data/backup/$(date +%Y%m%d)/"
	backdir="/data/backup/spider-sources/"

	if [ ! -d $backdir ]; then
		echo "create backdir $backdir"
		mkdir -p $backdir
	fi

	file='./target/mljr-spider*'
	if [ -f $file ];then
		echo "backup $file"
		mv $file $backdir
	fi
	
	model="mljr-common,umq-client,spider-config,grpc,mljr-spider"
	mvn -pl $model clean
	mvn -T1C -P production -pl $model package -Dmaven.test.skip -U -X
}

function build_data_sync(){
	echo "build data-sync"
	model="mljr-common,umq-client,mljr-dao,data-sync"
	mvn -pl $model clean 
	mvn -T1C -P production -pl $model package -Dmaven.test.skip -U -X
}

function build_spider_monitor(){
	echo "build spider monitor"
	model="mljr-common,umq-client,spider-config,spider-monitor" 
	mvn -pl $model clean 
	mvn -T1C -P production -pl $model package -Dmaven.test.skip -U -X
}

case $1 in
	"spider")
		build_spider
	;;
	"data-sync")
		build_data_sync
	;;
	"spider-monitor")
		build_spider_monitor
	;;
	*)
		echo "wrong select" && exit 1
	;;
esac