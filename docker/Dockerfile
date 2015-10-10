FROM ubuntu:14.04

MAINTAINER raccoon 

ENV KAFKA_VERSION="0.8.2.1" SCALA_VERSION="2.10"

RUN apt-get update && apt-get install -y \ 
	unzip \
	openjdk-7-jdk \
	wget \
	curl \
	docker.io \
	jq

ADD kafka/download-kafka.sh /tmp/download-kafka.sh
RUN chmod +x /tmp/download-kafka.sh
RUN /tmp/download-kafka.sh
RUN tar xf /tmp/kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz -C /opt

VOLUME ["/kafka"]

ENV KAFKA_HOME /opt/kafka_${SCALA_VERSION}-${KAFKA_VERSION}
ADD kafka/start-kafka.sh /usr/bin/start-kafka.sh
ADD kafka/broker-list.sh /usr/bin/broker-list.sh
RUN chmod +x /usr/bin/start-kafka.sh
CMD start-kafka.sh