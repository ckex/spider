#!/bin/bash

function start_spider(){
	command -v docker >/dev/null 2>&1 || { echo >&2 "Command not found: docker. please install docker."; exit 1; }
	echo "start spider ... "
	docker pull ucloud20:5000/ckex/spider:develop
	docker stop spider
	docker rm spider
	docker run --name spider -v /data:/data -d ucloud20:5000/ckex/spider:develop restartws.sh
	hn=`hostname` &&  docker run --name spider --hostname="docker$hn" -v /data:/data -d ucloud20:5000/ckex/spider:develop restartws.sh
}

case $1 in
	"start-spider")
		start_spider
	;;
	*)
		echo "wrong select" && exit 1
	;;
esac