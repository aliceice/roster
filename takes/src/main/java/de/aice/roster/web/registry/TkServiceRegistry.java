package de.aice.roster.web.registry;

import de.aice.roster.core.registry.ServiceRegistry;
import org.takes.facets.fork.FkFixed;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.TkFork;
import org.takes.tk.TkWrap;

/**
 * Services interface of Takes server.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public class TkServiceRegistry extends TkWrap {

	public TkServiceRegistry(final ServiceRegistry registry) {
		super(
			new TkFork(
				new FkRegex("/(?<name>.*)/(?<environment>.*)", new TkServiceRequest(registry)),
				new FkFixed(new TkAllServices(registry))
			)
		);
	}
}
