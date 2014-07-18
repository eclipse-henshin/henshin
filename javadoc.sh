#!/bin/sh

JAVADOC="/opt/public/common/jdk1.6.0_27.x86_64/bin/javadoc"

# WORKSPACE:
WORKSPACE="/opt/public/jobs/cbi_henshin_nightly/workspace/henshin/plugins"

# ECLIPSE:
ECLIPSE="/home/data/users/ckrause/eclipse-juno-x86_64"

# DESTINATION:
DEST="/home/data/httpd/download.eclipse.org/modeling/emft/henshin/javadoc/nightly"

# TITLE:
TITLE="Henshin"

# PACKAGES:
PACKAGES="org.eclipse.emf.henshin
org.eclipse.emf.henshin.model
org.eclipse.emf.henshin.model.exporters
org.eclipse.emf.henshin.model.importers
org.eclipse.emf.henshin.model.impl
org.eclipse.emf.henshin.model.resource
org.eclipse.emf.henshin.model.util
org.eclipse.emf.henshin.interpreter
org.eclipse.emf.henshin.interpreter.impl
org.eclipse.emf.henshin.interpreter.util
org.eclipse.emf.henshin.statespace
org.eclipse.emf.henshin.statespace.impl
org.eclipse.emf.henshin.statespace.resource
org.eclipse.emf.henshin.statespace.util
org.eclipse.emf.henshin.statespace.external
org.eclipse.emf.henshin.statespace.external.cadp
org.eclipse.emf.henshin.statespace.external.mcrl2
org.eclipse.emf.henshin.statespace.external.prism
org.eclipse.emf.henshin.statespace.external.tikz
org.eclipse.emf.henshin.trace
org.eclipse.emf.henshin.trace.impl
org.eclipse.emf.henshin.trace.util
org.eclipse.emf.henshin.wrap
org.eclipse.emf.henshin.wrap.impl
org.eclipse.emf.henshin.wrap.util"

# DEPENDENCIES:
DEPS="org.eclipse.core.runtime
org.eclipse.osgi
org.eclipse.equinox.common
org.eclipse.emf.ecore
org.eclipse.emf.ecore.xmi
org.eclipse.emf.common
org.eclipse.emf.compare
org.eclipse.emf.compare.diff
org.eclipse.emf.compare.match
org.eclipse.emf.query.ocl
org.junit
org.eclipse.ocl
org.eclipse.ocl.ecore"

# LINKS:
LINKS="http://java.sun.com/javase/6/docs/api/
http://download.eclipse.org/modeling/emf/emf/javadoc/2.8.0/"

# Build source path and classpath:
SP=""
CP=""
for s in $PACKAGES; do
  SP="$SP:$WORKSPACE/$s/src"
#  CP="$CP:$WORKSPACE/$s/bin"
done
SP=`echo $SP | cut -c2-`
CP=`echo $CP | cut -c2-`
for d in $DEPS; do
  NEXT=`ls $ECLIPSE/plugins | grep "${d}_" | head -1`
  NEXT="$ECLIPSE/plugins/$NEXT"
  if [ "$d" = "org.junit" ]; then
     NEXT=`ls $ECLIPSE/plugins | grep "org.junit_4" | head -1`
     NEXT="$ECLIPSE/plugins/$NEXT/junit.jar"
  fi
  CP="$CP:$NEXT"
done

# Build the offline links:
LN=""
for l in $LINKS; do
  LN="$LN -linkoffline $l $l"
done

$JAVADOC -doctitle $TITLE -windowtitle $TITLE -d $DEST -sourcepath $SP -classpath $CP $LN $PACKAGES
