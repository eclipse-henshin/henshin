#!/bin/bash

# Environment:
JAVA_HOME=/opt/public/common/jdk-1.6.x86_64
ANT_HOME=/opt/public/common/apache-ant-1.7.1
ANT=/opt/public/common/apache-ant-1.7.1/bin/ant

# Project:
WORKSPACE=/shared/jobs/cbi_henshin_nightly/workspace
RELENG=$WORKSPACE/build/org.eclipse.henshin.releng

# Run ant...
$ANT -f $RELENG/promote.xml -Dpromote.properties=$RELENG/promote-N.properties

