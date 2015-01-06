package org.eclipse.emf.henshin.giraph.templates;

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
  protected final String TEXT_1 = "<project name=\"Get Hadoop\" default=\"main\" basedir=\".\">" + NL + "\t<description>" + NL + "\t\tDownload Hadoop and set up test environment" + NL + "\t</description>" + NL + "" + NL + "\t<property name=\"hadoop.folder\" value=\"hadoop-0.20.203.0\" />" + NL + "\t<property name=\"hadoop.archive\" value=\"hadoop-0.20.203.0rc1.tar.gz\" />" + NL + "" + NL + "\t<target name=\"main\">" + NL + "\t\t<get src=\"http://archive.apache.org/dist/hadoop/core/${hadoop.folder}/${hadoop.archive}\" dest=\"${hadoop.archive}\" verbose=\"true\" usetimestamp=\"true\" />" + NL + "\t\t<untar src=\"${hadoop.archive}\" dest=\".\" compression=\"gzip\" overwrite=\"false\" />" + NL + "\t</target>" + NL + "" + NL + "\t<target name=\"clean\">" + NL + "\t\t<delete dir=\"${hadoop.archive}\" />" + NL + "\t</target>" + NL + "</project>";
  protected final String TEXT_2 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    stringBuffer.append(TEXT_2);
    return stringBuffer.toString();
  }
}
