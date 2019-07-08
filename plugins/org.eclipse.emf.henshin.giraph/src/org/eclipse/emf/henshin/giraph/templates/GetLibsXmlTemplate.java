package org.eclipse.emf.henshin.giraph.templates;

import java.util.*;
import org.eclipse.emf.henshin.giraph.*;
import org.eclipse.emf.common.util.*;

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
  protected final String TEXT_1 = "<project name=\"get-libs\" default=\"main\" basedir=\".\">" + NL + "\t<description>" + NL + "\t\tDownload Giraph and Hadoop JARs for developing in Eclipse" + NL + "\t</description>" + NL + "" + NL + "\t<target name=\"main\">";
  protected final String TEXT_2 = NL + "\t\t<get src=\"";
  protected final String TEXT_3 = "\"" + NL + "\t\t\t dest=\"";
  protected final String TEXT_4 = "\" usetimestamp=\"true\" verbose=\"true\" />";
  protected final String TEXT_5 = NL + "\t</target>" + NL + "" + NL + "</project>";
  protected final String TEXT_6 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
     for (Map.Entry<URI,URI> entry : GiraphLibraries.LIBRARIES.entrySet()) { 
    stringBuffer.append(TEXT_2);
    stringBuffer.append( entry.getKey() );
    stringBuffer.append(TEXT_3);
    stringBuffer.append( entry.getKey().lastSegment() );
    stringBuffer.append(TEXT_4);
       if (entry.getValue() != null) { 
    stringBuffer.append(TEXT_2);
    stringBuffer.append( entry.getValue() );
    stringBuffer.append(TEXT_3);
    stringBuffer.append( entry.getValue().lastSegment() );
    stringBuffer.append(TEXT_4);
       }
   } 
    stringBuffer.append(TEXT_5);
    stringBuffer.append(TEXT_6);
    return stringBuffer.toString();
  }
}
