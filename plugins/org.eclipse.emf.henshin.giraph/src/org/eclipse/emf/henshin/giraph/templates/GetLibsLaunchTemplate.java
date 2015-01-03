package org.eclipse.emf.henshin.giraph.templates;

import java.util.*;

public class GetLibsLaunchTemplate
{
  protected static String nl;
  public static synchronized GetLibsLaunchTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    GetLibsLaunchTemplate result = new GetLibsLaunchTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + NL + "<launchConfiguration type=\"org.eclipse.ant.AntBuilderLaunchConfigurationType\">" + NL + "<booleanAttribute key=\"org.eclipse.ant.ui.ATTR_TARGETS_UPDATED\" value=\"true\"/>" + NL + "<booleanAttribute key=\"org.eclipse.ant.ui.DEFAULT_VM_INSTALL\" value=\"false\"/>" + NL + "<stringAttribute key=\"org.eclipse.debug.core.ATTR_REFRESH_SCOPE\" value=\"${project}\"/>" + NL + "<booleanAttribute key=\"org.eclipse.debug.ui.ATTR_LAUNCH_IN_BACKGROUND\" value=\"false\"/>" + NL + "<stringAttribute key=\"org.eclipse.jdt.launching.CLASSPATH_PROVIDER\" value=\"org.eclipse.ant.ui.AntClasspathProvider\"/>" + NL + "<booleanAttribute key=\"org.eclipse.jdt.launching.DEFAULT_CLASSPATH\" value=\"true\"/>" + NL + "<stringAttribute key=\"org.eclipse.jdt.launching.PROJECT_ATTR\" value=\"";
  protected final String TEXT_2 = "\"/>" + NL + "<stringAttribute key=\"org.eclipse.ui.externaltools.ATTR_LOCATION\" value=\"${workspace_loc:/";
  protected final String TEXT_3 = "/get-libs.xml}\"/>" + NL + "<stringAttribute key=\"org.eclipse.ui.externaltools.ATTR_RUN_BUILD_KINDS\" value=\"full,incremental,\"/>" + NL + "<booleanAttribute key=\"org.eclipse.ui.externaltools.ATTR_TRIGGERS_CONFIGURED\" value=\"true\"/>" + NL + "<stringAttribute key=\"org.eclipse.ui.externaltools.ATTR_WORKING_DIRECTORY\" value=\"${workspace_loc:/";
  protected final String TEXT_4 = "}\"/>" + NL + "</launchConfiguration>";
  protected final String TEXT_5 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    

@SuppressWarnings("unchecked")
Map<String,Object> args = (Map<String,Object>) argument;

String projectName = (String) args.get("projectName");


    stringBuffer.append(TEXT_1);
    stringBuffer.append( projectName );
    stringBuffer.append(TEXT_2);
    stringBuffer.append( projectName );
    stringBuffer.append(TEXT_3);
    stringBuffer.append( projectName );
    stringBuffer.append(TEXT_4);
    stringBuffer.append(TEXT_5);
    return stringBuffer.toString();
  }
}
