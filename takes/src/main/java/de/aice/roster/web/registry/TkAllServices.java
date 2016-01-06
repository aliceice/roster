package de.aice.roster.web.registry;

import de.aice.roster.core.registry.ServiceRegistry;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.RsJSON;

import java.io.IOException;

/**
 * Take all services and return them as json.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public final class TkAllServices implements Take {

	private final ServiceRegistry registry;

	public TkAllServices(final ServiceRegistry registry) {
		this.registry = registry;
	}

	@Override
	public Response act(final Request req) throws IOException {
		return new RsJSON(new JsonServices(this.registry.allServices()));
	}
}
