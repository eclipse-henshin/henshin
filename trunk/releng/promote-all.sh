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

# We need to rebuild the artifacts.jar from scratch:
rm "$DROPS/../../updates/nightly/artifacts.jar"
rm "$DROPS/../../updates/releases/artifacts.jar"

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
#


############################################################
# Update the repository meta-data so that 
# we can collect download stats:
#function add_stats_update {
#	echo
#	echo "*** Updating repository $1 ***"
#	updatesite="$DROPS/../../updates/$1"
#	tool="RepositoryStatsTool"
#	cp "tools/$tool.class" $updatesite
#	cd $updatesite
#	if [ -f "artifacts.jar" ]; then
#		unzip "artifacts.jar"
#		java $tool artifacts.xml $2
#		zip -u artifacts.jar artifacts.xml
#		rm artifacts.xml
#		rm $tool.class
#	else
#		echo "Error updating $1/artifacts.jar for download stats."
#	fi
#	rm $updatesite/$tool.class
#	cd -
#}
#add_stats_update "nightly" "N"
#add_stats_update "releases" "R"
############################################################



############################################################
### TEMPORARY CODE TO FIX MISPLACED DROPS ###
############################################################

if [ -d "$DROPS/0.7.0/R201109190426" ]; then
	mkdir $DROPS/0.8.0
	mkdir $DROPS/0.8.0/R201109190426
	mv $DROPS/0.7.0/R201109190426/Henshin-SDK-Incubation-0.7.0.zip $DROPS/0.8.0/R201109190426/Henshin-SDK-Incubation-0.8.0.zip
	rm -R $DROPS/0.7.0/R201109190426
fi

# CLEANUP UPDATE SITE

# Features:
rm "$DROPS/../../updates/releases/features/*0.7.0*-18*.jar"

# Plug-ins:
rm "$DROPS/../../updates/releases/plugins/*0.7.0*930.jar"
rm "$DROPS/../../updates/releases/plugins/*0.7.0*506.jar"
rm "$DROPS/../../updates/releases/plugins/*0.7.0*635.jar"

# Stats tool:
rm "$DROPS/../../updates/releases/*.class"
rm "$DROPS/../../updates/nightly/*.class"

# Correct the site.xml
if [ ! -f "$DROPS/../../updates/releases/site.bak" ]; then
	cp $DROPS/../../updates/releases/site.xml $DROPS/../../updates/releases/site.bak
	sed 's/0\.7\.0/0\.8\.0/g' $DROPS/../../updates/releases/site.xml > $DROPS/../../updates/releases/site.new
	rm $DROPS/../../updates/releases/site.xml
	mv $DROPS/../../updates/releases/site.new $DROPS/../../updates/releases/site.xml
fi
