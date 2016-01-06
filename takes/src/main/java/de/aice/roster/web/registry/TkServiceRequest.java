package de.aice.roster.web.registry;

import de.aice.roster.core.registry.Service;
import de.aice.roster.core.registry.ServiceRegistry;
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

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Optional;

/**
 * Take of service registry interface.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public final class TkServiceRequest implements TkRegex {

	private final ServiceRegistry registry;

	public TkServiceRequest(final ServiceRegistry registry) {
		this.registry = registry;
	}

	@Override
	public Response act(final RqRegex req) throws IOException {
		Service service = new RqService(req);
		return new TkFork(
			new FkMethods("GET", getEndpoint(service)),
			new FkMethods("POST", register(service)),
			new FkMethods("DELETE", unregister(service))
		).act(req);
	}

	private Take getEndpoint(final Service service) {
		return req -> {
			Optional<String> endpoint = this.registry.getEndpoint(service);
			return endpoint.map(e -> (Response) new RsText(e))
			               .orElseGet(() -> new RsWithStatus(HttpURLConnection.HTTP_NOT_FOUND));
		};
	}

	private Take register(final Service service) {
		return req -> {
			String endpoint = new RqPrint(req).printBody();
			this.registry.register(service, endpoint);
			return new RsEmpty();
		};
	}

	private Take unregister(final Service service) {
		return req -> {
			this.registry.unregister(service);
			return new RsEmpty();
		};
	}
}
