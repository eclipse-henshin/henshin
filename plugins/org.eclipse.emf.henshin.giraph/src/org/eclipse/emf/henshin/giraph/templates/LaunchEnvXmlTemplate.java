package org.eclipse.emf.henshin.giraph.templates;

import java.util.*;

public class LaunchEnvXmlTemplate
{
  protected static String nl;
  public static synchronized LaunchEnvXmlTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    LaunchEnvXmlTemplate result = new LaunchEnvXmlTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "<project name=\"launch-env\" basedir=\".\">" + NL + "\t<description>" + NL + "\t\tCommon properties for launch environment" + NL + "\t</description>" + NL + "" + NL + "\t<dirname property=\"root.dir\" file=\"${ant.file.launch-env}\"/>" + NL + "" + NL + "\t<property name=\"java.home\" location=\"";
  protected final String TEXT_2 = "\" />" + NL + "\t<property name=\"hadoop.home\" location=\"${root.dir}/";
  protected final String TEXT_3 = "\" />" + NL + "\t<property name=\"giraph.jar.with.deps\" location=\"${root.dir}/";
  protected final String TEXT_4 = "\" />" + NL + "" + NL + "</project>";
  protected final String TEXT_5 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
@SuppressWarnings("unchecked")
Map<String,Object> args = (Map<String,Object>) argument;
String javaHome = (String) args.get("javaHome");
String hadoopHome = "testenv/hadoop-0.20.203.0";
String giraphJarWithDeps = "target/giraph-henshin-examples-1.1.0-for-hadoop-0.20.203.0-jar-with-dependencies.jar";


    stringBuffer.append(TEXT_1);
    stringBuffer.append( javaHome );
    stringBuffer.append(TEXT_2);
    stringBuffer.append( hadoopHome );
    stringBuffer.append(TEXT_3);
    stringBuffer.append( giraphJarWithDeps );
    stringBuffer.append(TEXT_4);
    stringBuffer.append(TEXT_5);
    return stringBuffer.toString();
  }
}
