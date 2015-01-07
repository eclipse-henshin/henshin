package org.eclipse.emf.henshin.giraph.templates;

import java.util.*;

public class GetHadoopXmlTemplate
{
  protected static String nl;
  public static synchronized GetHadoopXmlTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    GetHadoopXmlTemplate result = new GetHadoopXmlTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "<project name=\"Get Hadoop\" default=\"main\" basedir=\".\">" + NL + "\t<description>" + NL + "\t\tDownload Hadoop and set up test environment" + NL + "\t</description>" + NL + "" + NL + "\t<property name=\"java.home\" location=\"";
  protected final String TEXT_2 = "\" />" + NL + "\t<property name=\"hadoop.folder\" value=\"hadoop-0.20.203.0\" />" + NL + "\t<property name=\"hadoop.archive\" value=\"hadoop-0.20.203.0rc1.tar.gz\" />" + NL + "\t<property name=\"hadoop.tmpdir\" location=\"${hadoop.folder}/data\" />" + NL + "" + NL + "\t<target name=\"main\">" + NL + "\t\t<get src=\"http://archive.apache.org/dist/hadoop/core/${hadoop.folder}/${hadoop.archive}\" dest=\"${hadoop.archive}\" verbose=\"true\" usetimestamp=\"true\" />" + NL + "\t\t<untar src=\"${hadoop.archive}\" dest=\".\" compression=\"gzip\" overwrite=\"false\" />" + NL + "\t\t<replaceregexp file=\"${hadoop.folder}/conf/hadoop-env.sh\" match=\"#\\s*export JAVA_HOME=\\S*\" replace=\"export JAVA_HOME=${java.home}\" byline=\"true\" />" + NL + "\t\t<mkdir dir=\"${hadoop.tmpdir}\" />" + NL + "\t\t<replaceregexp file=\"${hadoop.folder}/conf/core-site.xml\" flags=\"g\" match=\"&lt;configuration&gt;\\s*&lt;/configuration&gt;\" replace=\"&lt;configuration&gt;&#10;&lt;property&gt;&#10;&lt;name&gt;hadoop.tmp.dir&lt;/name&gt;&#10;&lt;value&gt;${hadoop.tmpdir}&lt;/value&gt;&#10;&lt;/property&gt;&#10;&#10;&lt;property&gt;&#10;&lt;name&gt;fs.default.name&lt;/name&gt;&#10;&lt;value&gt;hdfs://localhost:54310&lt;/value&gt;&#10;&lt;/property&gt;&#10;&lt;/configuration&gt;&#10;\" />" + NL + "\t\t<replaceregexp file=\"${hadoop.folder}/conf/mapred-site.xml\" flags=\"g\" match=\"&lt;configuration&gt;\\s*&lt;/configuration&gt;\" replace=\"&lt;configuration&gt;&#10;&lt;property&gt;&#10;&lt;name&gt;mapred.job.tracker&lt;/name&gt;&#10;&lt;value&gt;localhost:54311&lt;/value&gt;&#10;&lt;/property&gt;&#10;&#10;&lt;property&gt;&#10;&lt;name&gt;mapred.tasktracker.map.tasks.maximum&lt;/name&gt;&#10;&lt;value&gt;4&lt;/value&gt;&#10;&lt;/property&gt;&#10;&#10;&lt;property&gt;&#10;&lt;name&gt;mapred.map.tasks&lt;/name&gt;&#10;&lt;value&gt;4&lt;/value&gt;&#10;&lt;/property&gt;&#10;&lt;/configuration&gt;&#10;\" />" + NL + "\t\t<replaceregexp file=\"${hadoop.folder}/conf/hdfs-site.xml\" flags=\"g\" match=\"&lt;configuration&gt;\\s*&lt;/configuration&gt;\" replace=\"&lt;configuration&gt;&#10;&lt;property&gt;&#10;&lt;name&gt;dfs.replication&lt;/name&gt;&#10;&lt;value&gt;1&lt;/value&gt;&#10;&lt;/property&gt;&#10;&lt;/configuration&gt;&#10;\" />" + NL + "\t</target>" + NL + "" + NL + "\t<target name=\"clean\">" + NL + "\t<!--" + NL + "\t\t<delete file=\"${hadoop.archive}\" />" + NL + "\t\t<delete dir=\"${hadoop.tmpdir}\" />" + NL + "\t-->" + NL + "\t</target>" + NL + "</project>";
  protected final String TEXT_3 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
@SuppressWarnings("unchecked")
Map<String,Object> args = (Map<String,Object>) argument;
String javaHome = (String) args.get("javaHome");

    stringBuffer.append(TEXT_1);
    stringBuffer.append( javaHome );
    stringBuffer.append(TEXT_2);
    stringBuffer.append(TEXT_3);
    return stringBuffer.toString();
  }
}
