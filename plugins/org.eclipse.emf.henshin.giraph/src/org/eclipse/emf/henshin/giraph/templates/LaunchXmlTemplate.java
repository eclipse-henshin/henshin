package org.eclipse.emf.henshin.giraph.templates;

import java.util.*;

public class LaunchXmlTemplate
{
  protected static String nl;
  public static synchronized LaunchXmlTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    LaunchXmlTemplate result = new LaunchXmlTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "<project name=\"";
  protected final String TEXT_2 = "\" default=\"main\" basedir=\".\">" + NL + "\t<description>" + NL + "\t\tLaunch ";
  protected final String TEXT_3 = NL + "\t</description>" + NL + "" + NL + "\t<include file=\"../launch-env.xml\" />" + NL + "" + NL + "\t<property name=\"hadoop.cmd\" value=\"${hadoop.home}/bin/hadoop\" />" + NL + "" + NL + "\t<target name=\"main\">" + NL + "\t\t<exec executable=\"${hadoop.home}/bin/start-dfs.sh\" failonerror=\"true\" />" + NL + "\t\t<exec executable=\"${hadoop.home}/bin/start-mapred.sh\" failonerror=\"true\" />" + NL + "\t\t<exec executable=\"${hadoop.cmd}\">" + NL + "\t\t\t<arg value=\"dfsadmin\" />" + NL + "\t\t\t<arg value=\"-safemode\" />" + NL + "\t\t\t<arg value=\"leave\" />" + NL + "\t\t</exec>" + NL + "\t\t<exec executable=\"${hadoop.cmd}\">" + NL + "\t\t\t<arg value=\"fs\" />" + NL + "\t\t\t<arg value=\"-mkdir\" />" + NL + "\t\t\t<arg value=\"/input\" />" + NL + "\t\t</exec>" + NL + "\t\t<exec executable=\"${hadoop.cmd}\">" + NL + "\t\t\t<arg value=\"fs\" />" + NL + "\t\t\t<arg value=\"-put\" />" + NL + "\t\t\t<arg value=\"${root.dir}/input/";
  protected final String TEXT_4 = ".json\" />" + NL + "\t\t\t<arg value=\"/input/\" />" + NL + "\t\t</exec>" + NL + "\t\t<exec executable=\"${hadoop.cmd}\">" + NL + "\t\t\t<arg value=\"jar\" />" + NL + "\t\t\t<arg value=\"${giraph.jar.with.deps}\" />" + NL + "\t\t\t<arg value=\"org.apache.giraph.GiraphRunner\" />" + NL + "\t\t\t<arg value=\"";
  protected final String TEXT_5 = ".";
  protected final String TEXT_6 = "\" />" + NL + "\t\t\t<arg value=\"-vif\" />" + NL + "\t\t\t<arg value=\"";
  protected final String TEXT_7 = ".HenshinUtil$InputFormat\" />" + NL + "\t\t\t<arg value=\"-vof\" />" + NL + "\t\t\t<arg value=\"";
  protected final String TEXT_8 = ".HenshinUtil$OutputFormat\" />" + NL + "\t\t\t<arg value=\"-vip\" />" + NL + "\t\t\t<arg value=\"/input/";
  protected final String TEXT_9 = ".json\" />" + NL + "\t\t\t<arg value=\"-op\" />" + NL + "\t\t\t<arg value=\"/output\" />" + NL + "\t\t\t<arg value=\"-w\" />" + NL + "\t\t\t<arg value=\"2\" />" + NL + "\t\t\t<arg value=\"-mc\" />" + NL + "\t\t\t<arg value=\"";
  protected final String TEXT_10 = "$MasterCompute\" />" + NL + "\t\t</exec>" + NL + "\t\t<delete dir=\"${root.dir}/output\" />" + NL + "\t\t<mkdir dir=\"${root.dir}/output\" />" + NL + "\t\t<exec executable=\"${hadoop.cmd}\">" + NL + "\t\t\t<arg value=\"fs\" />" + NL + "\t\t\t<arg value=\"-get\" />" + NL + "\t\t\t<arg value=\"/output/*\" />" + NL + "\t\t\t<arg value=\"${root.dir}/output\" />" + NL + "\t\t</exec>" + NL + "\t\t<exec executable=\"${hadoop.cmd}\">" + NL + "\t\t\t<arg value=\"fs\" />" + NL + "\t\t\t<arg value=\"-rmr\" />" + NL + "\t\t\t<arg value=\"/input\" />" + NL + "\t\t</exec>" + NL + "\t\t<exec executable=\"${hadoop.cmd}\">" + NL + "\t\t\t<arg value=\"fs\" />" + NL + "\t\t\t<arg value=\"-rmr\" />" + NL + "\t\t\t<arg value=\"/output\" />" + NL + "\t\t</exec>" + NL + "\t\t<exec executable=\"${hadoop.home}/bin/stop-all.sh\" failonerror=\"true\" />" + NL + "\t</target>" + NL + "" + NL + "</project>";
  protected final String TEXT_11 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
@SuppressWarnings("unchecked")
Map<String,Object> args = (Map<String,Object>) argument;
String packageName = (String) args.get("packageName");
String className = (String) args.get("className");
String inputName = (String) args.get("inputName");

    stringBuffer.append(TEXT_1);
    stringBuffer.append( className );
    stringBuffer.append(TEXT_2);
    stringBuffer.append( className );
    stringBuffer.append(TEXT_3);
    stringBuffer.append( inputName );
    stringBuffer.append(TEXT_4);
    stringBuffer.append( packageName );
    stringBuffer.append(TEXT_5);
    stringBuffer.append( className );
    stringBuffer.append(TEXT_6);
    stringBuffer.append( packageName );
    stringBuffer.append(TEXT_7);
    stringBuffer.append( packageName );
    stringBuffer.append(TEXT_8);
    stringBuffer.append( inputName );
    stringBuffer.append(TEXT_9);
    stringBuffer.append( packageName );
    stringBuffer.append(TEXT_5);
    stringBuffer.append( className );
    stringBuffer.append(TEXT_10);
    stringBuffer.append(TEXT_11);
    return stringBuffer.toString();
  }
}
