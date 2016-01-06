package de.aice.roster.core.registry;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Service registry contract.
 * A service registry handles the registration and deregistration of {@link Service} and corresponding endpoint.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public interface ServiceRegistry {

	/**
	 * Register endpoint for given service.
	 *
	 * @param service  service to register
	 * @param endpoint endpoint to register
	 */
	void register(Service service, String endpoint);

	/**
	 * Unregister given service.
	 * If the service is not registered this method should silently ignore the request.
	 *
	 * @param service service to unregister
	 */
	void unregister(Service service);

	/**
	 * Get the endpoint for given service.
	 *
	 * @param service service to get endpoint for
	 * @return Optional with endpoint if registered. Otherwise {@link Optional#empty()}
	 */
	Optional<String> getEndpoint(Service service);

	/**
	 * Get all registered services.
	 *
	 * @return Set of registered services and endpoints.
	 */
	Set<Map.Entry<Service, String>> allServices();
}
