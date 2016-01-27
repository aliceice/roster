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
public final class TkServiceRequest implements TkRegex {

	private final Services services;

	public TkServiceRequest(final Services services) {
		this.services = services;
	}

	@Override
	public Response act(final RqRegex req) throws IOException {
		String name = req.matcher().group("name");
		String environment = req.matcher().group("environment");
		return new TkFork(
			new FkMethods("GET", getEndpoint(name, environment)),
			new FkMethods("POST", register(name, environment)),
			new FkMethods("DELETE", unregister(name, environment))
		).act(req);
	}

	private Take getEndpoint(final String name, final String environment) {
		return req ->
			this.services.get(name, environment).endpoint()
			             .map(e -> (Response) new RsText(e))
			             .orElseGet(() -> new RsWithStatus(HttpURLConnection.HTTP_NOT_FOUND));
	}

	private Take register(final String name, final String environment) {
		return req -> {
			String endpoint = new RqPrint(req).printBody();
			this.services.add(name, environment, endpoint);
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
