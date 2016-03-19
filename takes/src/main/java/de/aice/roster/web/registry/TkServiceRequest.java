package de.aice.roster.web.registry;

import de.aice.roster.core.registry.Services;
import java.io.IOException;
import java.net.HttpURLConnection;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.fork.FkMethods;
import org.takes.facets.fork.RqRegex;
import org.takes.facets.fork.TkFork;
import org.takes.facets.fork.TkRegex;
import org.takes.rq.RqPrint;
import org.takes.rs.RsEmpty;
import org.takes.rs.RsText;
import org.takes.rs.RsWithStatus;

/**
 * Take of service registry interface.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
final class TkServiceRequest implements TkRegex {

	private final Services services;

	TkServiceRequest(final Services services) {
		this.services = services;
	}

	@Override
	public Response act(final RqRegex req) throws IOException {
		String name = req.matcher().group("name");
		String environment = req.matcher().group("environment");
		return new TkFork(
			new FkMethods("GET", getEndpoint(name, environment)),
			new FkMethods("POST,PUT", register(name, environment)),
			new FkMethods("DELETE", unregister(name, environment))
		).act(req);
	}

	private Take getEndpoint(final String name, final String environment) {
		return req ->
			this.services.getEndpoint(name, environment)
			             .map(RsText::new)
			             .map(Response.class::cast)
			             .orElseGet(() -> new RsWithStatus(HttpURLConnection.HTTP_NOT_FOUND));
	}

	private Take register(final String name, final String environment) {
		return req -> {
			this.services.put(name, environment, new RqPrint(req).printBody());
			return new RsEmpty();
		};
	}

	private Take unregister(final String name, final String environment) {
		return req -> {
			this.services.remove(name, environment);
			return new RsEmpty();
		};
	}
}
