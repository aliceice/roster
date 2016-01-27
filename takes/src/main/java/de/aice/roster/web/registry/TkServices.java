package de.aice.roster.web.registry;

import de.aice.roster.core.registry.Services;
import org.takes.facets.fork.FkFixed;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.TkFork;
import org.takes.tk.TkWrap;

/**
 * Services interface routing of Takes server.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public class TkServices extends TkWrap {

	public TkServices(final Services services) {
		super(
			new TkFork(
				new FkRegex("/service/(?<name>.*)/(?<environment>.*)", new TkServiceRequest(services)),
				new FkFixed(new TkAllServices(services))
			)
		);
	}
}
