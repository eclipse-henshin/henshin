#!/bin/bash

# Check the parameters:
if [ $# -ne 1 ]; then
  echo "Usage: promote.sh TARGET"
  exit 1
fi

# Target build:
TARGET=$1

# Derive the name of the build job:
JOB=cbi_henshin_$TARGET

# Environment:
JAVA_HOME=/opt/public/common/jdk-1.6.x86_64
ANT_HOME=/opt/public/common/apache-ant-1.7.1
ANT=/opt/public/common/apache-ant-1.7.1/bin/ant

# Directory for the drops:
DROPS=/home/data/httpd/download.eclipse.org/modeling/emft/henshin/downloads/drops

# Releng directory:
RELENG=/shared/jobs/$JOB/workspace/build/org.eclipse.henshin.releng

# Run the promote script:
$ANT -f $RELENG/promote.xml -Dpromote.properties=$RELENG/promote.properties

# Clean up:
rm $DROPS/*/*/Henshin-examples* 2> /dev/null
rm $DROPS/*/*/Henshin-Update* 2> /dev/null
rm $DROPS/*/*/build.cfg 2> /dev/null
rm $DROPS/*/*/*.md5 2> /dev/null
rm $DROPS/*/*/echoproperties* 2> /dev/null
rm $DROPS/*/*/directory.txt 2> /dev/null
rm -R $DROPS/*/*/compilelogs 2> /dev/null

# Fix incubation suffix:
function fix_incubation {
    for file in $1/* 
    do
	if [ -d "$file" ]; then
	    fix_incubation $file
	else
	    ( echo $file | grep 'SDK-[RN0-9]' ) && mv $file $(echo $file | sed s/SDK-/SDK-Incubation-/)
	fi
    done
}
fix_incubation $DROPS

# Report possible problems:
if [ ! "$(ls --hide=index.php $DROPS/../../updates/$TARGET/plugins)" ]; then
	echo "Check out https://hudson.eclipse.org/hudson/job/$JOB" | mail -s "Possible build problem for $JOB" henshin.ck@gmail.com
fi

