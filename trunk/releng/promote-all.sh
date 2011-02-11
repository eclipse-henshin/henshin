#!/bin/bash

# Environment:
JAVA_HOME=/opt/public/common/jdk-1.6.x86_64
ANT_HOME=/opt/public/common/apache-ant-1.7.1
ANT=/opt/public/common/apache-ant-1.7.1/bin/ant
DROPS=/home/data/httpd/download.eclipse.org/modeling/emft/henshin/downloads/drops

function promote {

	// run ant:
	$ANT -f $1/promote.xml -Dpromote.properties=$1/promote.properties
	
	// clean up:
	rm $DROPS/*/*/Henshin-examples*
	rm $DROPS/*/*/Henshin-Update*
	rm $DROPS/*/*/build.cfg
	rm $DROPS/*/*/echoproperties*
	rm $DROPS/*/*/directory.txt
	rm -R $DROPS/*/*/compilelogs
	
}

promote /shared/jobs/cbi_henshin_nightly/workspace/build/org.eclipse.henshin.releng
promote /shared/jobs/cbi_henshin_release/workspace/build/org.eclipse.henshin.releng

