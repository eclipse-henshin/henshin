#!/bin/bash

wget http://ftp.halifax.rwth-aachen.de/apache/maven/maven-3/3.2.2/binaries/apache-maven-3.2.2-bin.tar.gz
tar xvfz apache-maven-3.2.2-bin.tar.gz
export PATH=$PATH:$PWD/apache-maven-3.2.2/bin
mvn clean install
