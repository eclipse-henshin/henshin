package org.eclipse.emf.henshin.giraph.templates;

public class CompileXmlTemplate
{
  protected static String nl;
  public static synchronized CompileXmlTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    CompileXmlTemplate result = new CompileXmlTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "<assembly" + NL + "\txmlns=\"http://maven.apache.org/plugins/maven-assembly-plugin/assembly/1.1.0\"" + NL + "\txmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" + NL + "\txsi:schemaLocation=\"http://maven.apache.org/plugins/maven-assembly-plugin/assembly/1.1.0 http://maven.apache.org/xsd/assembly-1.1.0.xsd\">" + NL + "\t<id>jar-with-dependencies</id>" + NL + "\t<formats>" + NL + "\t\t<format>jar</format>" + NL + "\t</formats>" + NL + "\t<includeBaseDirectory>false</includeBaseDirectory>" + NL + "\t<dependencySets>" + NL + "\t\t<dependencySet>" + NL + "\t\t\t<useProjectArtifact>true</useProjectArtifact>" + NL + "\t\t\t<outputDirectory>/</outputDirectory>" + NL + "\t\t\t<unpackOptions>" + NL + "\t\t\t\t<excludes>" + NL + "\t\t\t\t\t<exclude>META-INF/LICENSE</exclude>" + NL + "\t\t\t\t</excludes>" + NL + "\t\t\t</unpackOptions>" + NL + "\t\t\t<unpack>true</unpack>" + NL + "\t\t\t<scope>runtime</scope>" + NL + "\t\t</dependencySet>" + NL + "\t</dependencySets>" + NL + "</assembly>";
  protected final String TEXT_2 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    stringBuffer.append(TEXT_2);
    return stringBuffer.toString();
  }
}
