#!/bin/bash

# Environment:
JAVA_HOME=/opt/public/common/jdk-1.6.x86_64
ANT_HOME=/opt/public/common/apache-ant-1.7.1
ANT=/opt/public/common/apache-ant-1.7.1/bin/ant

# Directory for the drops:
DROPS=/home/data/httpd/download.eclipse.org/modeling/emft/henshin/downloads/drops

# Releng directories:
NIGHTLY=/shared/jobs/cbi_henshin_nightly/workspace/build/org.eclipse.henshin.releng
RELEASE=/shared/jobs/cbi_henshin_release/workspace/build/org.eclipse.henshin.releng


# Run the promote script:
$ANT -f $NIGHTLY/promote.xml -Dpromote.properties=$NIGHTLY/promote.properties
$ANT -f $RELEASE/promote.xml -Dpromote.properties=$RELEASE/promote.properties

# Clean up:
rm $DROPS/*/*/Henshin-examples* 2> /dev/null
rm $DROPS/*/*/Henshin-Update* 2> /dev/null
rm $DROPS/*/*/build.cfg 2> /dev/null
rm $DROPS/*/*/echoproperties* 2> /dev/null
rm $DROPS/*/*/directory.txt 2> /dev/null
rm -R $DROPS/*/*/compilelogs 2> /dev/null

