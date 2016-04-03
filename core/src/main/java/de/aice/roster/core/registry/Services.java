package de.aice.roster.core.registry;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Services contract.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public interface Services {

	/**
	 * Get endpoint of service.
	 *
	 * @param name        name of service
	 * @param environment environment of service
	 * @return endpoint
	 */
	Optional<String> getEndpoint(String name, String environment);

	/**
	 * Add new service endpoint or update already existing one.
	 *
	 * @param name        name of service
	 * @param environment environment of service
	 * @param endpoint    endpoint of service
	 */
	void put(String name, String environment, String endpoint);

	/**
	 * Remove endpoint of service.
	 *
	 * @param name        name of service
	 * @param environment environment of service
	 */
	void remove(String name, String environment);

	/**
	 * Stream of all registered services.
	 *
	 * @return Stream of registered services.
	 */
	Stream<Service> stream();

}
