#!/bin/bash

sourceTree=~/building/HenshinTree
SVN=/usr/local/bin/svn

##### update plugins #####
echo "updating plugins"
if [ -d $sourceTree/plugins ]; then
    $SVN update $sourceTree/plugins
else
    $SVN checkout svn://dev.eclipse.org/svnroot/modeling/org.eclipse.emft.henshin/plugins/trunk $sourceTree/plugins
fi

##### update features #####
echo "updating features"
if [ -d $sourceTree/features ]; then
    $SVN update $sourceTree/features
else
    $SVN checkout svn://dev.eclipse.org/svnroot/modeling/org.eclipse.emft.henshin/features/trunk $sourceTree/features
fi

echo "creating $sourceTree/all"

echo "removing old files"
rm -rf $sourceTree/all
mkdir -p $sourceTree/all/features
mkdir -p $sourceTree/all/plugins

echo "creating symlinks"
for i in $( ls -1 HenshinTree/plugins ); do ln -s "$sourceTree/plugins/$i" "$sourceTree/all/plugins/$i"; done
for i in $( ls -1 HenshinTree/features ); do ln -s "$sourceTree/features/$i" "$sourceTree/all/features/$i"; done
for i in $( ls -1 HenshinTree/tests ); do ln -s "$sourceTree/tests/$i" "$sourceTree/all/plugins/$i"; done
ln -s "$sourceTree/tests/org.eclipse.emft.henshin.tests.feature" "$sourceTree/all/features/org.eclipse.emft.henshin.tests.feature"
for i in $( ls -1 HenshinTree/doc ); do ln -s "$sourceTree/doc/$i" "$sourceTree/all/plugins/$i"; done

echo "done."
