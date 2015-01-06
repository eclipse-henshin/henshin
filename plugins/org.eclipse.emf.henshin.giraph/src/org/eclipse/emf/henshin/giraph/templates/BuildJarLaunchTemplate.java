package org.eclipse.emf.henshin.giraph.templates;

import java.util.*;

public class BuildJarLaunchTemplate
{
  protected static String nl;
  public static synchronized BuildJarLaunchTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    BuildJarLaunchTemplate result = new BuildJarLaunchTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + NL + "<launchConfiguration type=\"org.eclipse.m2e.Maven2LaunchConfigurationType\">" + NL + "<booleanAttribute key=\"M2_DEBUG_OUTPUT\" value=\"false\"/>" + NL + "<stringAttribute key=\"M2_GOALS\" value=\"package\"/>" + NL + "<booleanAttribute key=\"M2_NON_RECURSIVE\" value=\"false\"/>" + NL + "<booleanAttribute key=\"M2_OFFLINE\" value=\"false\"/>" + NL + "<stringAttribute key=\"M2_PROFILES\" value=\"\"/>" + NL + "<listAttribute key=\"M2_PROPERTIES\"/>" + NL + "<stringAttribute key=\"M2_RUNTIME\" value=\"EMBEDDED\"/>" + NL + "<booleanAttribute key=\"M2_SKIP_TESTS\" value=\"false\"/>" + NL + "<intAttribute key=\"M2_THREADS\" value=\"";
  protected final String TEXT_2 = "\"/>" + NL + "<booleanAttribute key=\"M2_UPDATE_SNAPSHOTS\" value=\"false\"/>" + NL + "<stringAttribute key=\"M2_USER_SETTINGS\" value=\"\"/>" + NL + "<booleanAttribute key=\"M2_WORKSPACE_RESOLUTION\" value=\"false\"/>" + NL + "<stringAttribute key=\"org.eclipse.debug.core.ATTR_REFRESH_SCOPE\" value=\"${project}\"/>" + NL + "<stringAttribute key=\"org.eclipse.jdt.launching.WORKING_DIRECTORY\" value=\"${workspace_loc:/";
  protected final String TEXT_3 = "}\"/>" + NL + "</launchConfiguration>";
  protected final String TEXT_4 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    

@SuppressWarnings("unchecked")
Map<String,Object> args = (Map<String,Object>) argument;

String projectName = (String) args.get("projectName");


    stringBuffer.append(TEXT_1);
    stringBuffer.append( Runtime.getRuntime().availableProcessors() );
    stringBuffer.append(TEXT_2);
    stringBuffer.append( projectName );
    stringBuffer.append(TEXT_3);
    stringBuffer.append(TEXT_4);
    return stringBuffer.toString();
  }
}
