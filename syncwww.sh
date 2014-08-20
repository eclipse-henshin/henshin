#!/bin/bash

SOURCE="doc/www"
TARGET="../henshin-www"

if [ ! -d $SOURCE ]
then
    echo "Directory not found: $SOURCE"
    exit 1
fi

if [ ! -d $TARGET ]
then
    echo "Directory not found: $TARGET"
    exit 1
fi

cp -f -R $SOURCE/* $TARGET
CW=$(pwd)
cd $TARGET
git add --all && git commit && git push
cd $CW
