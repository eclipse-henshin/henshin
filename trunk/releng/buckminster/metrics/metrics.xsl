<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:exsl="http://exslt.org/common"
              extension-element-prefixes="exsl">
	<xsl:output indent="yes" />
	<xsl:template match="/">
		<html>
			<head>
				<title>Henshin's Metrics</title>
				<style type="text/css">
					table { padding:100; margin:0;
					border-collapse:collapse;}
					td, th {border-bottom-width: thin;
					border-bottom-style:solid ;
					padding-right:30px; margin:0;
					text-align:left}
				</style>
			</head>

			<body>
				<h1>Henshin's Metrics</h1>
				<h2>Project Metrics</h2>
				<table>
					<tr>
						<th>Metrics</th>
						<th>Values</th>
					</tr>
					<tr>
						<td> Committers </td>
						<td> 5 </td>
					</tr>
					<xsl:apply-templates select="//bugs" />
					<xsl:apply-templates select="//plugins" />
					<xsl:apply-templates select="document('testresults.xml')//testresults" />
				</table>
				<xsl:apply-templates select="document('cloc.xml')//languages" 
					/>
				<h1> How are the metrics computed </h1>
				<h2>Project Metrics</h2>
				<table>
					<tr>
						<th>Metrics</th>
						<th>How to get it</th>
					</tr>
					<tr>
						<td> Number of bundles </td>
						<td>
							Count the number of directories in
							<a href="https://dev.eclipse.org/svnroot/modeling/org.eclipse.emft.henshin/plugins/trunk">https://dev.eclipse.org/svnroot/modeling/org.eclipse.emft.henshin/plugins/trunk</a>
						</td>
					</tr>
					<tr>
						<td> Number of committers </td>
						<td>
							<a
								href="http://www.eclipse.org/projects/ip_log.php?projectid=modeling.emft.henshin">http://www.eclipse.org/projects/ip_log.php?projectid=modeling.emft.henshin
							</a>
						</td>
					</tr>

				</table>

				<h2>Code metrics</h2>

				<p>
					The code metrics are computed using
					<a href="http://cloc.sourceforge.net/">CLOC</a>.
				</p>
			</body>
		</html>
	</xsl:template>

	<xsl:template match="bugs">
		<tr>
			<td> Open bugs </td>
			<td>
				<xsl:value-of select="@open" />
			</td>
		</tr>
		<tr>
			<td> Closed bugs </td>
			<td>
				<xsl:value-of select="@closed" />
			</td>
		</tr>
	</xsl:template>

	<xsl:template match="plugins">
		<tr>
			<td> Plug-ins </td>
			<td>
				<xsl:value-of select="@count" />
			</td>
		</tr>
	</xsl:template>

	<xsl:template match="languages">
		<h2>Code Metrics</h2>
		<table>
			<tr>
				<th> Name </th>
				<th> Files </th>
				<th> Blank lines </th>
				<th> Comment lines </th>
				<th> Lines of code </th>
				<th> Scale </th>
				<th> 3rd gen. equiv </th>
			</tr>
			<xsl:apply-templates select="language[@name!='Assembly']" />
		</table>
	</xsl:template>

	<xsl:template match="language">
		<tr>
			<td>
				<xsl:value-of select="@name" />
			</td>
			<td>
				<xsl:value-of select="@files_count" />
			</td>
			<td>
				<xsl:value-of select="@blank" />
			</td>
			<td>
				<xsl:value-of select="@comment" />
			</td>
			<td>
				<xsl:value-of select="@code" />
			</td>
			<td>
				<xsl:value-of select="@factor" />
			</td>
			<td>
				<xsl:value-of select="@scaled" />
			</td>
		</tr>
	</xsl:template>

	<xsl:template match="testresults">
      <xsl:variable name="varTests">
          <xsl:for-each select="testresult">
          <xsl:for-each select="document(.)//testsuite">
              <number><xsl:value-of select="@tests" /></number>
          </xsl:for-each>
          </xsl:for-each>
      </xsl:variable>
      <tr>
          <td> Unit tests </td>
          <td>
              <xsl:value-of select="sum(exsl:node-set($varTests)/number)" />
          </td>
      </tr>
	</xsl:template>
</xsl:stylesheet>
