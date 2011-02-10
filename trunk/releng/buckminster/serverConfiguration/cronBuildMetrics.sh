#!/bin/bash

tmpdir=$(mktemp -d)
cd $tmpdir


svn export svn://dev.eclipse.org/svnroot/modeling/org.eclipse.emft.henshin/features/trunk features
svn export svn://dev.eclipse.org/svnroot/modeling/org.eclipse.emft.henshin/plugins/trunk plugins
#svn export svn://dev.eclipse.org/svnroot/modeling/org.eclipse.emft.henshin/tests/trunk tests
#svn export svn://dev.eclipse.org/svnroot/modeling/org.eclipse.emft.henshin/examples/trunk examples
#svn export svn://dev.eclipse.org/svnroot/modeling/org.eclipse.emft.henshin/doc/trunk doc

svn export svn://dev.eclipse.org/svnroot/modeling/org.eclipse.emft.henshin/releng/buckminster/metrics/metrics.xsl


perl /opt/public/modeling/mdt/Henshin/cloc-1.08.pl --quiet --xml --out cloc.xml plugins features tests examples doc
nTrunk=$( ls -1 plugins | wc -l )
nExamples=$( ls -1 examples | wc -l )
nPlugins=$(( $nTrunk + $nExamples ))
nOpenBugs=$( wget --no-check-certificate -q -O - 'https://bugs.eclipse.org/bugs/buglist.cgi?query_format=advanced;bug_status=UNCONFIRMED;bug_status=NEW;bug_status=ASSIGNED;bug_status=REOPENED;component=Henshin;component=Henshin-Contribution;component=Henshin-Infrastructure;component=Henshin-Technologies;component=Henshin-UseCases;classification=Modeling;product=MDT' | grep 'bugs found' | head -n 1 | sed -e 's/.*>\([0-9]*\) bugs found./\1/' - )
nClosedBugs=$( wget --no-check-certificate -q -O - 'https://bugs.eclipse.org/bugs/buglist.cgi?classification=Modeling;query_format=advanced;bug_status=RESOLVED;bug_status=CLOSED;component=Henshin;component=Henshin-Contribution;component=Henshin-Infrastructure;component=Henshin-Technologies;component=Henshin-UseCases;product=EMFT' | grep 'bugs found' | head -n 1 | sed -e 's/.*>\([0-9]*\) bugs found./\1/' - )
echo "<?xml version=\"1.0\"?>" > metrics.xml
echo "<metrics>" >> metrics.xml
echo "<plugins count=\"$nPlugins\"/>" >> metrics.xml
echo "<bugs open=\"$nOpenBugs\" closed=\"$nClosedBugs\"/>" >> metrics.xml
echo "</metrics>" >> metrics.xml

echo "<?xml version=\"1.0\"?>" > testresults.xml
echo "<testresults>" >> testresults.xml
for i in $( ls -1 /opt/users/hudsonbuild/.hudson/jobs/henshin-nightly/workspace/tmp/N20*/testresults/xml/org.eclipse.emft.henshin.tests.xml); do echo "<testresult>$i</testresult>" >> testresults.xml; done
echo "</testresults>" >> testresults.xml

xsltproc -o metrics.html metrics.xsl metrics.xml
chmod +r metrics.html

cp -f metrics.html /home/data/httpd/download.eclipse.org/modeling/emft/henshin/metrics.html

rm -rf $tmpdir

