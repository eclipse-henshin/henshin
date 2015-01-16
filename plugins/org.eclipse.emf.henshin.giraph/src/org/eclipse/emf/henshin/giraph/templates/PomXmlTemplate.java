package org.eclipse.emf.henshin.giraph.templates;

import java.util.*;

public class PomXmlTemplate
{
  protected static String nl;
  public static synchronized PomXmlTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    PomXmlTemplate result = new PomXmlTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" + NL + "\txsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd\">" + NL + "\t<modelVersion>4.0.0</modelVersion>" + NL + "" + NL + "\t<parent>" + NL + "\t\t<groupId>org.apache.giraph</groupId>" + NL + "\t\t<artifactId>giraph-parent</artifactId>" + NL + "\t\t<version>1.1.0</version>" + NL + "\t</parent>" + NL + "" + NL + "\t<artifactId>";
  protected final String TEXT_2 = "</artifactId>" + NL + "\t<name>Giraph Henshin Examples</name>" + NL + "\t<description>Generated example code</description>" + NL + "\t<url>https://wiki.eclipse.org/Henshin_Code_Generator_for_Giraph</url>" + NL + "\t<packaging>jar</packaging>" + NL + "" + NL + "\t<properties>" + NL + "\t\t<top.dir>${project.basedir}/..</top.dir>" + NL + "\t\t<forHadoop>for-hadoop-${hadoop.version}</forHadoop>" + NL + "\t</properties>" + NL + "" + NL + "\t<build>" + NL + "\t\t<finalName>";
  protected final String TEXT_3 = "-${project.version}-${forHadoop}</finalName>" + NL + "\t\t<plugins>" + NL + "\t\t\t<plugin>" + NL + "\t\t\t\t<groupId>org.apache.maven.plugins</groupId>" + NL + "\t\t\t\t<artifactId>maven-assembly-plugin</artifactId>" + NL + "\t\t\t</plugin>" + NL + "\t\t\t<plugin>" + NL + "\t\t\t\t<groupId>org.apache.maven.plugins</groupId>" + NL + "\t\t\t\t<artifactId>maven-compiler-plugin</artifactId>" + NL + "\t\t\t</plugin>" + NL + "\t\t\t<plugin>" + NL + "\t\t\t\t<groupId>org.apache.maven.plugins</groupId>" + NL + "\t\t\t\t<artifactId>maven-enforcer-plugin</artifactId>" + NL + "\t\t\t</plugin>" + NL + "\t\t\t<plugin>" + NL + "\t\t\t\t<groupId>org.apache.maven.plugins</groupId>" + NL + "\t\t\t\t<artifactId>maven-jar-plugin</artifactId>" + NL + "\t\t\t</plugin>" + NL + "\t\t\t<plugin>" + NL + "\t\t\t\t<groupId>org.apache.maven.plugins</groupId>" + NL + "\t\t\t\t<artifactId>maven-site-plugin</artifactId>" + NL + "\t\t\t\t<configuration>" + NL + "\t\t\t\t\t<siteDirectory>${project.basedir}/src/site</siteDirectory>" + NL + "\t\t\t\t</configuration>" + NL + "\t\t\t</plugin>" + NL + "\t\t\t<plugin>" + NL + "\t\t\t\t<groupId>org.apache.maven.plugins</groupId>" + NL + "\t\t\t\t<artifactId>maven-source-plugin</artifactId>" + NL + "\t\t\t</plugin>" + NL + "\t\t\t<plugin>" + NL + "\t\t\t\t<groupId>org.apache.maven.plugins</groupId>" + NL + "\t\t\t\t<artifactId>maven-surefire-plugin</artifactId>" + NL + "\t\t\t\t<version>2.6</version>" + NL + "\t\t\t\t<configuration>" + NL + "\t\t\t\t\t<systemProperties>" + NL + "\t\t\t\t\t\t<property>" + NL + "\t\t\t\t\t\t\t<name>prop.jarLocation</name>" + NL + "\t\t\t\t\t\t\t<value>${project.basedir}/target/";
  protected final String TEXT_4 = "-${project.version}-${forHadoop}-jar-with-dependencies.jar</value>" + NL + "\t\t\t\t\t\t</property>" + NL + "\t\t\t\t\t</systemProperties>" + NL + "\t\t\t\t</configuration>" + NL + "\t\t\t</plugin>" + NL + "\t\t\t<plugin>" + NL + "\t\t\t\t<groupId>org.apache.rat</groupId>" + NL + "\t\t\t\t<artifactId>apache-rat-plugin</artifactId>" + NL + "\t\t\t</plugin>" + NL + "\t\t\t<plugin>" + NL + "\t\t\t\t<groupId>org.codehaus.mojo</groupId>" + NL + "\t\t\t\t<artifactId>findbugs-maven-plugin</artifactId>" + NL + "\t\t\t</plugin>" + NL + "\t\t</plugins>" + NL + "\t</build>" + NL + "" + NL + "\t<profiles>" + NL + "\t\t<profile>" + NL + "\t\t\t<id>hadoop_0.20.203</id>" + NL + "\t\t\t<activation>" + NL + "\t\t\t\t<activeByDefault>true</activeByDefault>" + NL + "\t\t\t</activation>" + NL + "\t\t\t<properties>" + NL + "\t\t\t\t<hadoop.version>0.20.203.0</hadoop.version>" + NL + "\t\t\t\t<munge.symbols>HADOOP_NON_JOBCONTEXT_IS_INTERFACE,HADOOP_1_SECURITY,HADOOP_1_SECRET_MANAGER,STATIC_SASL_SYMBOL</munge.symbols>" + NL + "\t\t\t</properties>" + NL + "\t\t\t<build>" + NL + "\t\t\t\t<plugins>" + NL + "\t\t\t\t\t<plugin>" + NL + "\t\t\t\t\t\t<groupId>org.apache.maven.plugins</groupId>" + NL + "\t\t\t\t\t\t<artifactId>maven-compiler-plugin</artifactId>" + NL + "\t\t\t\t\t</plugin>" + NL + "\t\t\t\t\t<plugin>" + NL + "\t\t\t\t\t\t<groupId>org.sonatype.plugins</groupId>" + NL + "\t\t\t\t\t\t<artifactId>munge-maven-plugin</artifactId>" + NL + "\t\t\t\t\t</plugin>" + NL + "\t\t\t\t</plugins>" + NL + "\t\t\t</build>" + NL + "\t\t</profile>" + NL + "\t</profiles>" + NL + "" + NL + "\t<dependencies>" + NL + "" + NL + "\t\t<!-- compile dependencies. sorted lexicographically. -->" + NL + "\t\t<dependency>" + NL + "\t\t\t<groupId>commons-collections</groupId>" + NL + "\t\t\t<artifactId>commons-collections</artifactId>" + NL + "\t\t</dependency>" + NL + "\t\t<dependency>" + NL + "\t\t\t<groupId>log4j</groupId>" + NL + "\t\t\t<artifactId>log4j</artifactId>" + NL + "\t\t</dependency>" + NL + "\t\t<dependency>" + NL + "\t\t\t<groupId>org.json</groupId>" + NL + "\t\t\t<artifactId>json</artifactId>" + NL + "\t\t</dependency>" + NL + "\t\t<dependency>" + NL + "\t\t\t<groupId>org.apache.giraph</groupId>" + NL + "\t\t\t<artifactId>giraph-core</artifactId>" + NL + "\t\t</dependency>" + NL + "\t\t<dependency>" + NL + "\t\t\t<groupId>org.apache.giraph</groupId>" + NL + "\t\t\t<artifactId>giraph-examples</artifactId>" + NL + "\t\t</dependency>" + NL + "" + NL + "\t\t<!-- runtime dependency -->" + NL + "\t\t<dependency>" + NL + "\t\t\t<groupId>org.slf4j</groupId>" + NL + "\t\t\t<artifactId>slf4j-api</artifactId>" + NL + "\t\t\t<scope>runtime</scope>" + NL + "\t\t</dependency>" + NL + "" + NL + "\t\t<!-- test dependencies. sorted lexicographically. -->" + NL + "\t\t<dependency>" + NL + "\t\t\t<groupId>junit</groupId>" + NL + "\t\t\t<artifactId>junit</artifactId>" + NL + "\t\t\t<scope>test</scope>" + NL + "\t\t</dependency>" + NL + "\t\t<dependency>" + NL + "\t\t\t<groupId>org.mockito</groupId>" + NL + "\t\t\t<artifactId>mockito-core</artifactId>" + NL + "\t\t\t<scope>test</scope>" + NL + "\t\t</dependency>" + NL + "" + NL + "\t</dependencies>" + NL + "</project>";
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
