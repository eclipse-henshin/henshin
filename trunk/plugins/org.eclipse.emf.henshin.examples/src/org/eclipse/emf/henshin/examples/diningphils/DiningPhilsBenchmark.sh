
HE=org.eclipse.emf.henshin
EC=/home/christian/opt/eclipse
WS=/home/christian/Dropbox/Workspaces/henshin

cd $WS/$HE.examples
java -Xms7G -Xmx7G -Dfile.encoding=UTF-8 -classpath $WS/$HE.examples/bin:$EC/plugins/org.eclipse.core.runtime_3.6.0.v20100505.jar:$EC/plugins/org.eclipse.osgi_3.6.1.R36x_v20100806.jar:$EC/plugins/org.eclipse.equinox.common_3.6.0.v20100503.jar:$EC/plugins/org.eclipse.core.jobs_3.5.1.R36x_v20100824.jar:$EC/plugins/org.eclipse.core.runtime.compatibility.registry_3.3.0.v20100520/runtime_registry_compatibility.jar:$EC/plugins/org.eclipse.equinox.registry_3.5.0.v20100503.jar:$EC/plugins/org.eclipse.equinox.preferences_3.3.0.v20100503.jar:$EC/plugins/org.eclipse.core.contenttype_3.4.100.v20100505-1235.jar:$EC/plugins/org.eclipse.equinox.app_1.3.1.R36x_v20100803.jar:$EC/plugins/org.eclipse.core.resources_3.6.0.R36x_v20100825-0600.jar:$EC/plugins/org.eclipse.emf.ecore_2.6.1.v20100914-1218.jar:$EC/plugins/org.eclipse.emf.common_2.6.0.v20100914-1218.jar:$EC/plugins/org.eclipse.emf.ecore.xmi_2.5.0.v20100521-1846.jar:$EC/plugins/org.eclipse.emf.codegen.ecore_2.6.1.v20100914-1218.jar:$EC/plugins/org.eclipse.emf.codegen_2.6.0.v20100914-1218.jar:$EC/plugins/org.eclipse.emf.ecore.change_2.5.1.v20100907-1643.jar:$WS/$HE.matching/bin:$WS/$HE.model/bin:$EC/plugins/org.eclipse.ocl_3.0.2.R30x_v201101110610.jar:$EC/plugins/lpg.runtime.java_2.0.17.v201004271640.jar:$EC/plugins/org.eclipse.ocl.ecore_3.0.2.R30x_v201101110610.jar:$WS/$HE.interpreter/bin:$WS/$HE.trace/bin:$WS/$HE.statespace/bin $HE.examples.diningphils.DiningPhilsBenchmark
cd - > /dev/null
