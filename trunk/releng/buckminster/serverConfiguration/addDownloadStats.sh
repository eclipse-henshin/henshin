#!/bin/bash
# Copyright (c) 2010 Mia-Software
# All rights reserved. This program and the accompanying materials
# are made available under the terms of the Eclipse Public License v1.0
# which accompanies this distribution, and is available at
# http://www.eclipse.org/legal/epl-v10.html
#
XSLT_FILE=/opt/public/modeling/emft/henshin/addDownloadStats.xsl

if [ $# -ne 1 ]; then
echo "usage: $0 <update_site_location>"
exit
fi

if [ ! -d $1 ] ; then echo "not a directory"; exit 1; fi
if [ ! -f $1/artifacts.jar ] ; then echo "artifacts.jar not found"; exit 1; fi

echo "Adding download stats to repository"

cd $1
unzip artifacts.jar
mv artifacts.xml artifacts.xml.original
if grep p2.statsURI artifacts.xml.original ; then echo "p2.statsURI already defined: exiting"; exit 1; fi

xsltproc -o artifacts.xml $XSLT_FILE artifacts.xml.original
zip artifacts.jar artifacts.xml
rm artifacts.xml

echo "done."
