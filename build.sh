#!/bin/bash




# ./build spider
# ./build data-sync

function build_spider(){
	echo "build spider"
	mvn clean 
	mvn -T1C -P production -pl mljr-common,umq-client,grpc,mljr-spider,spider-monitor,spider-config package -Dmaven.test.skip -U -X
}

function build_data_sync(){
	echo "build data-sync"
	mvn clean 
	mvn -T1C -P production -pl mljr-common,umq-client,mljr-dao,data-sync package -Dmaven.test.skip -U -X
}

case $1 in
	"spider")
		build_spider
	;;
	"data-sync")
		build_data_sync
	;;
	*)
		echo "wrong select" && exit 1
	;;
esac	
