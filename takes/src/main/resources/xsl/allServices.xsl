<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns="http://www.w3.org/1999/xhtml">
	<xsl:template match="services">
		<html>
			<head>
				<title>Roster - Registered Services</title>
				<link rel="stylesheet" href="http://yegor256.github.io/tacit/tacit.min.css"/>
			</head>
			<body>
				<table>
					<thead>
						<tr>
							<th>Name</th>
							<th>Env</th>
							<th>Endpoint</th>
						</tr>
					</thead>
					<tbody>
						<xsl:apply-templates select="service"/>
					</tbody>
				</table>
			</body>
		</html>
	</xsl:template>

	<xsl:template match="service">
		<tr>
			<td><xsl:value-of select="name"/></td>
			<td><xsl:value-of select="env"/></td>
			<td><xsl:value-of select="endpoint"/></td>
		</tr>
	</xsl:template>
</xsl:stylesheet>