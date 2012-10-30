#!/bin/sh

# ECLIPSE_HOME:
if [ -d "$HOME/eclipse" ]; then
    ECLIPSE_HOME="$HOME/eclipse"
elif [ -d "$HOME/opt/eclipse" ]; then
    ECLIPSE_HOME="$HOME/opt/eclipse"
elif [ -d "/usr/local/eclipse" ]; then
    ECLIPSE_HOME="/usr/local/eclipse"
else
    ECLIPSE_HOME="/opt/eclipse"
fi

# WORKSPACE:
WORKSPACE=`cd ../../../../../../../../..; pwd`

MAX_MEMORY="512m"

echo "ECLIPSE_HOME : $ECLIPSE_HOME"
echo "WORKSPACE    : $WORKSPACE"
echo "MAX_MEMORY   : $MAX_MEMORY"

DEPS="org.eclipse.core.contenttype
org.eclipse.core.jobs
org.eclipse.core.resources
org.eclipse.core.runtime
org.eclipse.osgi
org.eclipse.equinox.common
org.eclipse.equinox.registry
org.eclipse.equinox.preferences
org.eclipse.equinox.app
org.eclipse.emf.ecore
org.eclipse.emf.common
org.eclipse.emf.ecore.xmi
org.eclipse.emf.codegen.ecore
org.eclipse.emf.codegen
org.eclipse.emf.ecore.change
org.eclipse.ocl
org.eclipse.ocl.common
lpg.runtime.java
org.eclipse.ocl.ecore"

HENSHIN="org.eclipse.emf.henshin.model
org.eclipse.emf.henshin.examples
org.eclipse.emf.henshin.interpreter
org.eclipse.emf.henshin.statespace
org.eclipse.emf.henshin.statespace.ocl
org.eclipse.emf.henshin.statespace.external"

# Classpath:
CP=""
for dep in $DEPS; do
  NEXT=`ls $ECLIPSE_HOME/plugins/$dep*.jar | head -1`
  CP="$CP:$NEXT"
done
for hen in $HENSHIN; do
  NEXT="$WORKSPACE/$hen/bin"
  CP="$CP:$NEXT"
done

sync
cd $WORKSPACE/org.eclipse.emf.henshin.examples
java -Xmx$MAX_MEMORY -Dfile.encoding=UTF-8 -classpath $CP org.eclipse.emf.henshin.examples.probbroadcast.timed.TimedProbBroadcast
cd - > /dev/null
