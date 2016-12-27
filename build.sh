#!/bin/bash




# ./build spider
# ./build data-sync

function build_spider(){
	echo "build spider"
	mvn clean
	mvn -T1C -P production -pl mljr-common,umq-client,spider-config,grpc,mljr-spider package -Dmaven.test.skip -U -X
}

function build_data_sync(){
	echo "build data-sync"
	mvn clean 
	mvn -T1C -P production -pl mljr-common,umq-client,mljr-dao,data-sync package -Dmaven.test.skip -U -X
}

function build_spider_monitor(){
	echo "build spider monitor"
	mvn clean 
	mvn -T1C -P production -pl mljr-common,umq-client,spider-config,spider-monitor package -Dmaven.test.skip -U -X
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
