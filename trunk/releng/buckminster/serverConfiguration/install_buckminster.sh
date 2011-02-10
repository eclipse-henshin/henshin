#!/bin/bash

mkdir /opt/public/modeling/emft/henshin/buckminsterx86
cd /opt/public/modeling/emft/henshin/buckminsterx86
wget http://download.eclipse.org/tools/buckminster/products/director_latest.zip
unzip director_latest.zip

cd director

#basic:
./director -r http://download.eclipse.org/tools/buckminster/headless-3.6/ -r http://download.cloudsmith.com/buckminster/external-3.6/ -d /opt/public/modeling/mdt/modisco/buckminsterx86/install -p Buckminster -i org.eclipse.buckminster.cmdline.product -i org.eclipse.buckminster.core.headless.feature.feature.group -i org.eclipse.buckminster.pde.headless.feature.feature.group -i org.eclipse.buckminster.cvs.headless.feature.feature.group -i org.eclipse.buckminster.subclipse.headless.feature.feature.group -i org.eclipse.buckminster.emma.headless.feature.feature.group

#with Mylyn:
#./director -r http://download.eclipse.org/tools/buckminster/headless-3.6/ -r http://download.cloudsmith.com/buckminster/external-3.6/ -r #http://download.eclipse.org/tools/mylyn/update/e3.4 -r http://download.eclipse.org/tools/orbit/downloads/drops/S20100721061755/repository/ -d /#opt/public/modeling/mdt/modisco/buckminster/install -p Buckminster -i org.eclipse.buckminster.cmdline.product -i #org.eclipse.buckminster.core.headless.feature.feature.group -i org.eclipse.buckminster.pde.headless.feature.feature.group -i #org.eclipse.buckminster.cvs.headless.feature.feature.group -i org.eclipse.buckminster.subclipse.headless.feature.feature.group -i #org.eclipse.buckminster.emma.headless.feature.feature.group -i org.eclipse.mylyn.wikitext.core -i org.eclipse.mylyn.wikitext.mediawiki.core -i #org.apache.ant 


#avoid contacting mirrors:

#In org.eclipse.equinox.internal.p2.repository.FileReader#sendRetrieveRequest(..), wrap existing code with:

#if(uri.getHost().endsWith("eclipse.org")) {
#...
#} else {
#    System.out.println("[mirrors disabled] " + uri);
#    throw new CoreException(Status.CANCEL_STATUS);
#}

#and then copy the FileReader.class into the "org.eclipse.equinox.p2.repository" jar in the Buckminster install.

