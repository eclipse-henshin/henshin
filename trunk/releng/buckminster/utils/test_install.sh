#!/bin/bash -x
# check that the Henshin SDK feature is installable from Helios

f=/home/data/httpd/download.eclipse.org/eclipse/downloads/drops/S-3.6M5-201001291300/eclipse-SDK-3.6M5-linux-gtk-ppc64.tar.gz

cd /home/data/users/ckrause/testinstall || exit 1
rm -rf eclipse
tar xzf $f
cd eclipse

sites=http://download.eclipse.org/releases/staging/,http://download.eclipse.org/tools/orbit/downloads/drops/S20100120144102/updateSite/,http://download.eclipse.org/modeling/emft/henshin/updates/nightly/
java=/opt/public/common/ibm-java2-ppc64-50-SR11/bin/java

$java -jar plugins/org.eclipse.equinox.launcher_*.jar -application org.eclipse.equinox.p2.director -installIU org.eclipse.gmt.modisco.sdk.feature.group -metadataRepository $sites -artifactRepository $sites

