## MLJR Spider
### Java Version 1.8.0_111

# basic image
FROM	ucloud20:5000/ckex/java:1.8

MAINTAINER Ckex.zha jinmin.zha@mljr.com

ENV REFRESHED_AT 2017-01-04

ENV SPIDER_HOME /var/run/spider
ENV DATA_HOME /data

ADD get_page.js 			/root/
ADD ucloud_umq.properties 	/root/
ADD ./target/mljr-spider*.tar.gz $SPIDER_HOME

RUN mkdir $DATA_HOME

ENV PATH=$SPIDER_HOME/bin:$PATH
ENV DETACHED=false

WORKDIR $SPIDER_HOME/bin

# docker build -t ckex/spider:develop .
# docker tag ckex/spider:develop ucloud20:5000/ckex/spider:develop
# docker push ucloud20:5000/ckex/spider:develop
#  --restart=always
# docker run --name spider -v /data:/data -d ucloud20:5000/ckex/spider:develop restartws.sh



  