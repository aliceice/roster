package de.aice.roster.web.registry;

import de.aice.roster.core.registry.Services;
import java.io.IOException;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.RsJSON;

/**
 * Take all services and return them as json.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public final class TkAllServices implements Take {

	private final Services services;

	public TkAllServices(final Services services) {
		this.services = services;
	}

	@Override
	public Response act(final Request req) throws IOException {
		return new RsJSON(new JsonServices(this.services.all()));
	}
}
