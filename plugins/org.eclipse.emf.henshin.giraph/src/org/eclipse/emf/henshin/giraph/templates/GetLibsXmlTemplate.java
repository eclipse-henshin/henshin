package org.eclipse.emf.henshin.giraph.templates;

public class GetLibsXmlTemplate
{
  protected static String nl;
  public static synchronized GetLibsXmlTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    GetLibsXmlTemplate result = new GetLibsXmlTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "<project name=\"Get Giraph Libraries\" default=\"main\" basedir=\".\">" + NL + "\t<description>" + NL + "\t\tDownload Giraph and Hadoop JARs for compiling in Eclipse" + NL + "\t</description>" + NL + "" + NL + "\t<property name=\"lib\" location=\"lib\" />" + NL + "" + NL + "\t<target name=\"main\">" + NL + "\t\t<mkdir dir=\"${lib}\" />" + NL + "\t\t<get" + NL + "\t\t\tsrc=\"http://repo.maven.apache.org/maven2/org/apache/giraph/giraph-core/1.1.0/giraph-core-1.1.0.jar\"" + NL + "\t\t\tdest=\"${lib}/giraph-core-1.1.0.jar\" verbose=\"true\" usetimestamp=\"true\" />" + NL + "\t\t<get" + NL + "\t\t\tsrc=\"http://repo.maven.apache.org/maven2/org/apache/giraph/giraph-examples/1.1.0/giraph-examples-1.1.0.jar\"" + NL + "\t\t\tdest=\"${lib}/giraph-examples-1.1.0.jar\" verbose=\"true\" usetimestamp=\"true\" />" + NL + "\t\t<get" + NL + "\t\t\tsrc=\"http://repo.maven.apache.org/maven2/org/apache/hadoop/hadoop-core/0.20.203.0/hadoop-core-0.20.203.0.jar\"" + NL + "\t\t\tdest=\"${lib}/hadoop-core-0.20.203.0.jar\" verbose=\"true\" usetimestamp=\"true\" />" + NL + "\t\t<get" + NL + "\t\t\tsrc=\"http://central.maven.org/maven2/log4j/log4j/1.2.15/log4j-1.2.15.jar\"" + NL + "\t\t\tdest=\"${lib}/log4j-1.2.15.jar\" verbose=\"true\" usetimestamp=\"true\" />" + NL + "\t\t<get" + NL + "\t\t\tsrc=\"http://central.maven.org/maven2/org/json/json/20090211/json-20090211.jar\"" + NL + "\t\t\tdest=\"${lib}/json-20090211.jar\" verbose=\"true\" usetimestamp=\"true\" />" + NL + "\t</target>" + NL + "" + NL + "\t<target name=\"clean\">" + NL + "\t\t<delete dir=\"${lib}\" />" + NL + "\t</target>" + NL + "</project>";
  protected final String TEXT_2 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    stringBuffer.append(TEXT_2);
    return stringBuffer.toString();
  }
}
