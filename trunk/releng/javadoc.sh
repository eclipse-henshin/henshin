#!/bin/sh

JAVADOC="/usr/lib/jvm/java-7-openjdk-amd64/bin/javadoc"

# WORKSPACE:
WORKSPACE="/home/ckrause/Dropbox/Workspaces/henshin"

# ECLIPSE:
ECLIPSE="/opt/eclipse"

# DESTINATION:
DEST="/home/ckrause/javadoc"

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
org.eclipse.emf.henshin.statespace.validation
org.eclipse.emf.henshin.testframework
org.eclipse.emf.henshin.trace
org.eclipse.emf.henshin.trace.impl"

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
  CP="$CP:$WORKSPACE/$s/bin"
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


