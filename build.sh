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

function docker_spider(){
	command -v docker >/dev/null 2>&1 || { echo >&2 "Command not found: docker. please install docker."; exit 1; }
	echo "docker build spider image"
	docker build --no-cache -t ckex/spider:develop .
	docker tag ckex/spider:develop ucloud20:5000/ckex/spider:develop
	docker push ucloud20:5000/ckex/spider:develop
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
	"docker-spider-image")
		build_spider && docker_spider
	;;
	*)
		echo "wrong select" && exit 1
	;;
esac