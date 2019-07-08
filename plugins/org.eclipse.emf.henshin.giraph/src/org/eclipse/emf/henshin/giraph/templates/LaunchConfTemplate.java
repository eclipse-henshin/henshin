package org.eclipse.emf.henshin.giraph.templates;

import java.util.*;

public class LaunchConfTemplate
{
  protected static String nl;
  public static synchronized LaunchConfTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    LaunchConfTemplate result = new LaunchConfTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + NL + "<launchConfiguration type=\"org.eclipse.ant.AntLaunchConfigurationType\">" + NL + "<booleanAttribute key=\"org.eclipse.ant.ui.DEFAULT_VM_INSTALL\" value=\"true\"/>" + NL + "<listAttribute key=\"org.eclipse.debug.core.MAPPED_RESOURCE_PATHS\">" + NL + "<listEntry value=\"/";
  protected final String TEXT_2 = "/launch/";
  protected final String TEXT_3 = ".xml\"/>" + NL + "</listAttribute>" + NL + "<listAttribute key=\"org.eclipse.debug.core.MAPPED_RESOURCE_TYPES\">" + NL + "<listEntry value=\"1\"/>" + NL + "</listAttribute>" + NL + "<stringAttribute key=\"org.eclipse.jdt.launching.CLASSPATH_PROVIDER\" value=\"org.eclipse.ant.ui.AntClasspathProvider\"/>" + NL + "<stringAttribute key=\"org.eclipse.jdt.launching.MAIN_TYPE\" value=\"org.eclipse.ant.internal.launching.remote.InternalAntRunner\"/>" + NL + "<stringAttribute key=\"org.eclipse.jdt.launching.PROJECT_ATTR\" value=\"";
  protected final String TEXT_4 = "\"/>" + NL + "<stringAttribute key=\"org.eclipse.jdt.launching.SOURCE_PATH_PROVIDER\" value=\"org.eclipse.ant.ui.AntClasspathProvider\"/>" + NL + "<stringAttribute key=\"org.eclipse.ui.externaltools.ATTR_LAUNCH_CONFIGURATION_BUILD_SCOPE\" value=\"${none}\"/>" + NL + "<stringAttribute key=\"org.eclipse.ui.externaltools.ATTR_LOCATION\" value=\"${workspace_loc:/";
  protected final String TEXT_5 = ".xml}\"/>" + NL + "<stringAttribute key=\"process_factory_id\" value=\"org.eclipse.ant.ui.remoteAntProcessFactory\"/>" + NL + "</launchConfiguration>";
  protected final String TEXT_6 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
@SuppressWarnings("unchecked")
Map<String,Object> args = (Map<String,Object>) argument;
String projectName = (String) args.get("projectName");
String className = (String) args.get("className");

    stringBuffer.append(TEXT_1);
    stringBuffer.append( projectName );
    stringBuffer.append(TEXT_2);
    stringBuffer.append( className );
    stringBuffer.append(TEXT_3);
    stringBuffer.append( projectName );
    stringBuffer.append(TEXT_4);
    stringBuffer.append( projectName );
    stringBuffer.append(TEXT_2);
    stringBuffer.append( className );
    stringBuffer.append(TEXT_5);
    stringBuffer.append(TEXT_6);
    return stringBuffer.toString();
  }
}
