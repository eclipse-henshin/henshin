#!/bin/bash

if [ "$#" -ne 1 ]; then
    echo "Illegal number of parameters"
    exit 1
fi

if [ ! -d "p2updatesite" ]; then
    echo "Missing directory: p2updatesite"
    exit 2
fi

BUILD=$1

SRC="p2updatesite/target/repository"
TRG="/home/data/httpd/download.eclipse.org/modeling/emft/henshin/updates/$BUILD"

rm -R $TRG/* 2> /dev/null
cp -R $SRC/* $TRG
