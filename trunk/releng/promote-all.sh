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
if [ ! "$(ls --hide=index.php $DROPS/../../updates/nightly/plugins)" ]; then
	echo "Check out https://hudson.eclipse.org/hudson/job/cbi_henshin_nightly" | mail -s "Possible build problem for the nightly build of Henshin" henshin.ck@gmail.com
fi

# Update the repository meta-data so that 
# we can collect download stats:
function add_stats_update {
	updatesite="$DROPS/../../updates/$1";
	tool="RepositoryStatsTool";
	cp "tools/$tool.java" $updatesite
	cd $updatesite;
	if [ -f "artifacts.jar" ]; then
		unzip "artifacts.jar"
		javac $tool.java
		java $tool artifacts.xml $2
		rm $tool.*
	else
		echo "Error updating $1/artifacts.jar for download stats."
	fi
	cd -
}
add_stats_update "nightly" "N"
add_stats_update "release" "R"


