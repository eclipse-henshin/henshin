package org.eclipse.emf.henshin.giraph.templates;

import java.util.*;

public class InstallHadoopXmlTemplate
{
  protected static String nl;
  public static synchronized InstallHadoopXmlTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    InstallHadoopXmlTemplate result = new InstallHadoopXmlTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "<project name=\"install-hadoop\" default=\"main\" basedir=\".\">" + NL + "\t<description>" + NL + "\t\tDownload Hadoop and configure test environment" + NL + "\t</description>" + NL + "" + NL + "\t<include file=\"../launch-env.xml\" />" + NL + "" + NL + "\t<property name=\"hadoop.dir\" value=\"hadoop-0.20.203.0\" />" + NL + "\t<property name=\"hadoop.archive\" value=\"hadoop-0.20.203.0rc1.tar.gz\" />" + NL + "\t<property name=\"hadoop.data\" location=\"${hadoop.dir}/data\" />" + NL + "" + NL + "\t<target name=\"main\">" + NL + "\t\t<get src=\"http://archive.apache.org/dist/hadoop/core/${hadoop.dir}/${hadoop.archive}\" dest=\"${hadoop.archive}\" verbose=\"true\" usetimestamp=\"true\" />" + NL + "\t\t<untar src=\"${hadoop.archive}\" dest=\".\" compression=\"gzip\" overwrite=\"false\" />" + NL + "\t\t<chmod dir=\"${hadoop.dir}/bin\" perm=\"u+rx\" includes=\"*\" />" + NL + "\t\t<replaceregexp file=\"${hadoop.dir}/conf/hadoop-env.sh\" match=\"#\\s*export JAVA_HOME=\\S*\" replace=\"export JAVA_HOME=${java.home}\" byline=\"true\" />" + NL + "\t\t<replaceregexp file=\"${hadoop.dir}/conf/core-site.xml\" flags=\"g\" match=\"&lt;configuration&gt;\\s*&lt;/configuration&gt;\" replace=\"&lt;configuration&gt;&#10;&lt;property&gt;&#10;&lt;name&gt;hadoop.tmp.dir&lt;/name&gt;&#10;&lt;value&gt;${hadoop.data}&lt;/value&gt;&#10;&lt;/property&gt;&#10;&#10;&lt;property&gt;&#10;&lt;name&gt;fs.default.name&lt;/name&gt;&#10;&lt;value&gt;hdfs://";
  protected final String TEXT_2 = ":54310&lt;/value&gt;&#10;&lt;/property&gt;&#10;&lt;/configuration&gt;&#10;\" />" + NL + "\t\t<replaceregexp file=\"${hadoop.dir}/conf/mapred-site.xml\" flags=\"g\" match=\"&lt;configuration&gt;\\s*&lt;/configuration&gt;\" replace=\"&lt;configuration&gt;&#10;&lt;property&gt;&#10;&lt;name&gt;mapred.job.tracker&lt;/name&gt;&#10;&lt;value&gt;";
  protected final String TEXT_3 = ":54311&lt;/value&gt;&#10;&lt;/property&gt;&#10;&#10;&lt;property&gt;&#10;&lt;name&gt;mapred.tasktracker.map.tasks.maximum&lt;/name&gt;&#10;&lt;value&gt;4&lt;/value&gt;&#10;&lt;/property&gt;&#10;&#10;&lt;property&gt;&#10;&lt;name&gt;mapred.map.tasks&lt;/name&gt;&#10;&lt;value&gt;4&lt;/value&gt;&#10;&lt;/property&gt;&#10;&lt;/configuration&gt;&#10;\" />" + NL + "\t\t<replaceregexp file=\"${hadoop.dir}/conf/hdfs-site.xml\" flags=\"g\" match=\"&lt;configuration&gt;\\s*&lt;/configuration&gt;\" replace=\"&lt;configuration&gt;&#10;&lt;property&gt;&#10;&lt;name&gt;dfs.replication&lt;/name&gt;&#10;&lt;value&gt;1&lt;/value&gt;&#10;&lt;/property&gt;&#10;&lt;/configuration&gt;&#10;\" />" + NL + "\t\t<replaceregexp file=\"${hadoop.dir}/conf/masters\" match=\"localhost\" replace=\"";
  protected final String TEXT_4 = "\" />" + NL + "\t\t<replaceregexp file=\"${hadoop.dir}/conf/slaves\" match=\"localhost\" replace=\"";
  protected final String TEXT_5 = "\" />" + NL + "\t\t<delete dir=\"${hadoop.data}\" />" + NL + "\t\t<mkdir dir=\"${hadoop.data}\" />" + NL + "\t\t<echo message=\"Formatting namenode...\" />" + NL + "\t\t<exec executable=\"${hadoop.dir}/bin/hadoop\" failonerror=\"true\">" + NL + "\t\t\t<arg value=\"namenode\" />" + NL + "\t\t\t<arg value=\"-format\" />" + NL + "\t\t</exec>" + NL + "\t\t<echo message=\"Checking public-key login to ";
  protected final String TEXT_6 = "...\" />" + NL + "\t\t<exec executable=\"ssh\" failonerror=\"true\">" + NL + "\t\t\t<arg value=\"-o\" />" + NL + "\t\t\t<arg value=\"BatchMode=yes\" />" + NL + "\t\t\t<arg value=\"-o\" />" + NL + "\t\t\t<arg value=\"ConnectTimeout=10\" />" + NL + "\t\t\t<arg value=\"-o\" />" + NL + "\t\t\t<arg value=\"StrictHostKeyChecking=no\" />" + NL + "\t\t\t<arg value=\"";
  protected final String TEXT_7 = "\" />" + NL + "\t\t\t<arg value=\"echo\" />" + NL + "\t\t\t<arg value=\"OK\" />" + NL + "\t\t</exec>" + NL + "\t</target>" + NL + "" + NL + "\t<target name=\"clean\">" + NL + "\t\t<delete file=\"${hadoop.archive}\" />" + NL + "\t\t<delete dir=\"${hadoop.data}\" />" + NL + "\t</target>" + NL + "" + NL + "</project>";
  protected final String TEXT_8 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    

@SuppressWarnings("unchecked")
Map<String,Object> args = (Map<String,Object>) argument;

String hostName = (String) args.get("hostName");


    stringBuffer.append(TEXT_1);
    stringBuffer.append( hostName );
    stringBuffer.append(TEXT_2);
    stringBuffer.append( hostName );
    stringBuffer.append(TEXT_3);
    stringBuffer.append( hostName );
    stringBuffer.append(TEXT_4);
    stringBuffer.append( hostName );
    stringBuffer.append(TEXT_5);
    stringBuffer.append( hostName );
    stringBuffer.append(TEXT_6);
    stringBuffer.append( hostName );
    stringBuffer.append(TEXT_7);
    stringBuffer.append(TEXT_8);
    return stringBuffer.toString();
  }
}
