#!/bin/bash -x

# This script compares the date of a signal file touched by the hudson build
# with the date of a reference file touched at the end of each promote.
# It then promotes the result of the build. The promote is not done from
# Hudson because it does not have the necessary rights.

lastPromoteFileN=/opt/public/modeling/emft/henshin/lastPromoteRefN
lastPromoteFileI=/opt/public/modeling/emft/henshin/lastPromoteRefI
promoteSignalN=/opt/public/modeling/emft/henshin/henshinBuildPromoteSignalN
promoteSignalI=/opt/public/modeling/emft/henshin/henshinBuildPromoteSignalI
promoteVersionN=/opt/public/modeling/emft/henshin/henshinBuildPromoteVersionN
promoteVersionI=/opt/public/modeling/emft/henshin/henshinBuildPromoteVersionI
promoteDirNameN=/opt/public/modeling/emft/henshin/henshinBuildPromoteDirNameN
promoteDirNameI=/opt/public/modeling/emft/henshin/henshinBuildPromoteDirNameI
logFile=/opt/public/modeling/emft/henshin/log-cronPromote
integrationTags=/opt/public/modeling/emft/henshin/integrationTags

updates_nightly=/home/data/httpd/download.eclipse.org/modeling/emft/henshin/updates/nightly/
updates_integration=/home/data/httpd/download.eclipse.org/modeling/emft/henshin/updates/integration/


#ANT=/opt/public/common/apache-ant-1.7.1/bin/ant
#RELENG_DIR=/opt/users/hudsonbuild/.hudson/jobs/cbi-henshin-nightly/workspace/build/org.eclipse.henshin.releng
HUDSON_METRICS=/opt/users/hudsonbuild/.hudson/jobs/cbi-henshin-nightly/workspace/metrics.html
DOWNLOAD_METRICS=/home/data/httpd/download.eclipse.org/modeling/emft/henshin/metrics.html
DOWNLOAD_ITAGS=/home/data/httpd/download.eclipse.org/modeling/emft/henshin/integrationTags
PROMO_LOGS_DIR=/opt/public/modeling/emft/henshin/promo_logs
DROPS_DIR=/home/data/httpd/download.eclipse.org/modeling/emft/henshin/downloads/drops
BUILD_LOC_N=/opt/public/modeling/emft/henshin/lastSuccessfulBuildN
BUILD_LOC_I=/opt/public/modeling/emft/henshin/lastSuccessfulBuildI

ADD_DOWNLOAD_STATS=/opt/public/modeling/emft/henshin/addDownloadStats.sh

DATE=`date +%Y%m%d-%H%M`

# trim the log : keep about last 10 days of log
function trimLog {
        tail -n 3000 $logFile > ${logFile}Trimmed && mv ${logFile}Trimmed $logFile
}


function prune() {
        # prefix: N for nightlies, I for integration, ...
        kind=$1
        filesPath=$2
        nBuildsToKeep=$3

        allFiles=$(ls -trd $filesPath/${kind}20*)
        nFiles=$(ls -1d $filesPath/${kind}20* | wc -l)
        nToDelete=$((nFiles-nBuildsToKeep))

        if [ $nToDelete -gt 0 ]; then
                for file in $allFiles; do
                        echo "$DATE: deleting $file" >> $logFile
                        rm -rf $file
                        nToDelete=$((nToDelete-1))
                        if [ $nToDelete -le 0 ]; then break; fi
                done
        fi
}



if [ ! -e $lastPromoteFileN ]; then touch $lastPromoteFileN; fi
if [ ! -e $lastPromoteFileI ]; then touch $lastPromoteFileI; fi
if [ ! -e $promoteSignalN ]; then echo "$DATE: ERROR: $promoteSignalN not found" >> $logFile; exit 1; fi
if [ ! -e $promoteSignalI ]; then echo "$DATE: ERROR: $promoteSignalI not found" >> $logFile; exit 1; fi

signalDateN=`stat --format=%Y $promoteSignalN`
signalDateI=`stat --format=%Y $promoteSignalI`
lastPromoteDateN=`stat --format=%Y $lastPromoteFileN`
lastPromoteDateI=`stat --format=%Y $lastPromoteFileI`

if [ $signalDateN -gt $lastPromoteDateN ]; then
        touch $lastPromoteFileN
        echo "$DATE: getting last successful build" >> $logFile
        mkdir -p $BUILD_LOC_N
        #rm -f $BUILD_LOC_N/build.zip
        #rm -rf $BUILD_LOC_N/build
        cd $BUILD_LOC_N
        zipName=$(cat $promoteSignalN).zip
        # see http://wiki.hudson-ci.org/display/HUDSON/Remote+access+API
        wget --no-check-certificate "https://build.eclipse.org/hudson/job/henshin-nightly/lastSuccessfulBuild/artifact/${zipName}"
        if [ ! -f $zipName ]; then echo "ERROR:$zipName (from Hudson) not found"; exit -2; fi
        VERSION=$(cat $promoteVersionN)
        if [ -z "$VERSION" ]; then echo "ERROR:version not found"; exit -2; fi
        BUILDS_DIR=$DROPS_DIR/$VERSION
        prune N $BUILDS_DIR 4
        echo "$DATE: publishing nightly build (on $VERSION) to $BUILDS_DIR..." >> $logFile
        unzip -o $zipName -d $BUILDS_DIR
        rm -rf $updates_nightly/*
        # extract the zip in which there is the update site zip to a tmp dir
        tmpDrop=$(mktemp -d)
        unzip $zipName -d $tmpDrop
        unzip -o $tmpDrop/?20*/Henshin-Update-*.zip -d $updates_nightly
        cp index.php $updates_nightly/
        $ADD_DOWNLOAD_STATS $updates_nightly
        chmod -R 755 $BUILDS_DIR
        chmod -R 755 $updates_nightly
        #cp -f $HUDSON_METRICS $DOWNLOAD_METRICS
        #cp -f /opt/users/hudsonbuild/.hudson/jobs/cbi-henshin-nightly/workspace/build/org.eclipse.henshin.releng/psfs/infrastructure.subversive.psf /home/data/httpd/download.eclipse.org/modeling/emft/henshin/henshin.psf
        echo "$DATE: done" >> $logFile
        trimLog
fi


if [ $signalDateI -gt $lastPromoteDateI ]; then
        touch $lastPromoteFileI
        echo "$DATE: getting last successful build" >> $logFile
        mkdir -p $BUILD_LOC_I
        cd $BUILD_LOC_I
        zipName=$(cat $promoteSignalI).zip
        # see http://wiki.hudson-ci.org/display/HUDSON/Remote+access+API
        wget --no-check-certificate "https://build.eclipse.org/hudson/job/henshin-integration/lastSuccessfulBuild/artifact/${zipName}"
        if [ ! -f $zipName ]; then echo "ERROR:$zipName (from Hudson) not found"; exit -2; fi
        VERSION=$(cat $promoteVersionI)
        if [ -z "$VERSION" ]; then echo "ERROR:version not found"; exit -2; fi
        BUILDS_DIR=$DROPS_DIR/$VERSION
        prune I $BUILDS_DIR 4
        echo "$DATE: publishing integration build (on $VERSION) to $BUILDS_DIR..." >> $logFile
        unzip -o $zipName -d $BUILDS_DIR
        mkdir -p $updates_integration/$VERSION
        rm -rf $updates_integration/$VERSION/*
        # extract the zip in which there is the update site zip to a tmp dir
        tmpDrop=$(mktemp -d)
        unzip $zipName -d $tmpDrop
        unzip -o $tmpDrop/?20*/Henshin-Update-*.zip -d $updates_integration/$VERSION
        cp index.php $updates_integration/$VERSION
        $ADD_DOWNLOAD_STATS $updates_integration/$VERSION
        chmod -R 755 $BUILDS_DIR
        chmod -R 755 $updates_integration/$VERSION
        #cp -f $HUDSON_METRICS $DOWNLOAD_METRICS
        #cp -f /opt/users/hudsonbuild/.hudson/jobs/cbi-henshin-nightly/workspace/build/org.eclipse.henshin.releng/psfs/infrastructure.subversive.psf /home/data/httpd/download.eclipse.org/modeling/emft/henshin/henshin.psf
        cp $integrationTags $DOWNLOAD_ITAGS
        echo "$DATE: done" >> $logFile
        trimLog
fi
