package de.aice.roster.ratpack.registry;

import de.aice.roster.core.registry.Service;
import de.aice.roster.core.registry.ServiceRegistry;
import de.aice.roster.ratpack.util.StringUtils;
import ratpack.func.Action;
import ratpack.handling.Context;
import ratpack.http.TypedData;
import ratpack.registry.Registry;

import java.net.HttpURLConnection;
import java.util.Map;
import java.util.Optional;

import static de.aice.roster.ratpack.util.Response.defaultErrorHandler;
import static de.aice.roster.ratpack.util.Response.doSend;
import static de.aice.roster.ratpack.util.Response.doSendOk;
import static de.aice.roster.ratpack.util.Response.doSendStatus;
import static de.aice.roster.ratpack.util.Response.sendOk;
import static de.aice.roster.ratpack.util.Response.sendStatus;

/**
 * Web interface of the ServiceRegistry.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public final class Services {

	private static final String LIST_SEPARATOR = ", ";

	/**
	 * Get all services.
	 *
	 * @param ctx Ratpack context
	 */
	public static void allServices(final Context ctx) {
		ctx.getResponse().send(
			String.format(
				"[%s]",
				ctx.get(ServiceRegistry.class)
				   .allServices()
				   .stream()
				   .map(Services::toJson)
				   .reduce(StringUtils.EMPTY, StringUtils.concat(LIST_SEPARATOR))
				   .replaceFirst(LIST_SEPARATOR, StringUtils.EMPTY)
			)
		);
	}

	private static String toJson(final Map.Entry<Service, String> e) {
		return String.format(
			"{\"name\": \"%s\", \"env\": \"%s\", \"endpoint\": \"%s\"}",
			e.getKey().name(), e.getKey().environment(), e.getValue()
		);
	}

	/**
	 * Handle service request on path "/:name/:environment".
	 *
	 * @param ctx Ratpack context
	 */
	public static void handleServiceRequest(final Context ctx) {
		ctx.insert(
			Registry.single(Service.class, new PathTokenService(ctx.getPathTokens())),
			dispatch -> dispatch.byMethod(method -> method
				.get(() -> ctx.insert(Services::getEndpoint))
				.post(() -> ctx.insert(Services::register))
				.delete(() -> ctx.insert(Services::unregister))
			)
		);
	}

	private static void getEndpoint(final Context context) {
		Service service = context.get(Service.class);
		Optional<String> endpoint = context.get(ServiceRegistry.class).getEndpoint(service);
		sendEndpointOrElseNotFound(context, endpoint);
	}

	private static void sendEndpointOrElseNotFound(final Context context, final Optional<String> endpoint) {
		if (endpoint.isPresent()) {
			doSend(context, endpoint::get);
		} else {
			doSendStatus(context, HttpURLConnection.HTTP_NOT_FOUND);
		}
	}

	private static void register(final Context context) {
		Service service = context.get(Service.class);
		context.getRequest()
		       .getBody()
		       .map(TypedData::getText)
		       .route(String::isEmpty, sendStatus(context, HttpURLConnection.HTTP_BAD_REQUEST))
		       .next(registerService(context, service))
		       .onError(defaultErrorHandler(context))
		       .then(sendOk(context));
	}

	private static Action<String> registerService(final Context context, final Service service) {
		return endpoint -> context.get(ServiceRegistry.class).register(service, endpoint);
	}

	private static void unregister(final Context context) {
		Service service = context.get(Service.class);
		context.get(ServiceRegistry.class).unregister(service);
		doSendOk(context);
	}

	private Services() { }
}
