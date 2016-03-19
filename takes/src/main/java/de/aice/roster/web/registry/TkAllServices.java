package de.aice.roster.web.registry;

import de.aice.roster.core.registry.Service;
import de.aice.roster.core.registry.Services;
import java.io.IOException;
import java.util.Collection;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
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
final class TkAllServices implements Take {

	private final Services services;

	TkAllServices(final Services services) {
		this.services = services;
	}

	@Override
	public Response act(final Request req) throws IOException {
		return new RsJSON(toJSON(this.services.getAll()));
	}

	private static JsonArray toJSON(final Collection<Service> services) {
		JsonArrayBuilder builder = Json.createArrayBuilder();
		services.forEach(
			service -> builder.add(
				Json.createObjectBuilder()
				    .add("name", service.name)
				    .add("env", service.env)
				    .add("endpoint", service.endpoint)
				    .build()
			)
		);
		return builder.build();
	}
}
