package de.aice.roster.ratpack;

import com.jcabi.log.Logger;
import de.aice.roster.core.registry.ServiceRegistry;
import de.aice.roster.core.registry.memory.MemoryServiceRegistry;
import de.aice.roster.ratpack.registry.Services;
import ratpack.func.Action;
import ratpack.handling.RequestLogger;
import ratpack.handling.RequestOutcome;
import ratpack.http.Request;
import ratpack.http.SentResponse;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;
import ratpack.server.ServerConfigBuilder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Entry point to the Ratpack server of Roster.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public final class Main {

	public static void main(final String[] args) throws Exception {
		RatpackServer.start(server -> server
			.serverConfig(serverConfigBuilder(args))
			.handlers(chain -> chain
				.all(RequestLogger.of(Main::requestLogger))
				.register(r -> r.add(ServiceRegistry.class, new MemoryServiceRegistry()))
				.get("health", ctx -> ctx.render("{\"status\": \"UP\"}"))
				.prefix("service", serviceChain -> serviceChain
					.get(Services::allServices)
					.prefix("/:name/:environment", registryChain -> registryChain
						.all(Services::handleServiceRequest)
					)
				)
			)
		);
	}

	private static Action<ServerConfigBuilder> serverConfigBuilder(final String[] args) {
		return c -> {
			c.baseDir(BaseDir.find()).yaml("/config/ratpack.yaml");
			profileOf(args).ifPresent(profile -> c.yaml(url("http://wherever.cloud.is/" + profile)));
		};
	}

	private static Optional<String> profileOf(final String[] args) {
		return Stream.of(args)
		             .filter(s -> s.startsWith("--profile"))
		             .findFirst()
		             .map(s -> s.split("=")[1]);
	}

	private static URL url(final String url) {
		try {
			return new URL(url);
		} catch (final MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	private static void requestLogger(final RequestOutcome requestOutcome) {
		Request req = requestOutcome.getRequest();
		SentResponse response = requestOutcome.getResponse();
		Logger.info(
			Main.class,
			String.format(
				"[%s] %s http://%s%s - %s %s",
				requestOutcome.getSentAt(),
				req.getMethod(),
				req.getLocalAddress().toString().replace("[", "").replace("]", ""),
				req.getUri(),
				response.getStatus().getCode(),
				response.getStatus().getMessage()
			)
		);
	}


	private Main() { }
}
