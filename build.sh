#!/bin/bash

ls /shared
ls /shared/common
ls /shared/common/apache-maven-3.0-beta-1

export PATH=$PATH:/shared/common/apache-maven-3.0-beta-1/bin
mvn clean install
