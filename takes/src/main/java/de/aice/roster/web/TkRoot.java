package de.aice.roster.web;

import de.aice.roster.core.registry.Services;
import de.aice.roster.web.health.TkHealth;
import de.aice.roster.web.registry.TkServices;
import java.net.HttpURLConnection;
import org.takes.facets.fallback.FbFixed;
import org.takes.facets.fallback.TkFallback;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.TkFork;
import org.takes.rs.RsText;
import org.takes.rs.RsWithStatus;
import org.takes.tk.TkClasspath;
import org.takes.tk.TkSlf4j;
import org.takes.tk.TkWithType;
import org.takes.tk.TkWrap;

/**
 * Root take of web interface.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public final class TkRoot extends TkWrap {

	public TkRoot(final Services services) {
		super(
			new TkSlf4j(
				new TkFallback(
					new TkFork(
						new FkRegex("/health", new TkHealth()),
						new FkRegex("/css/.*", new TkSlf4j(new TkWithType(new TkClasspath(), "text/css"))),
						new FkRegex("/js/.*", new TkSlf4j(new TkWithType(new TkClasspath(), "text/javascript"))),
						new FkRegex("/xsl/.*", new TkSlf4j(new TkWithType(new TkClasspath(), "text/xsl"))),
						new FkRegex("/service(/.*)?", new TkServices(services))
					),
					new FbFixed(new RsWithStatus(new RsText("404 Not Found"), HttpURLConnection.HTTP_NOT_FOUND))
				)
			)
		);
	}

}
