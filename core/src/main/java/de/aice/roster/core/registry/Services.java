package de.aice.roster.core.registry;

import java.util.stream.Stream;

/**
 * Services contract.
 * Handles addition and removal of {@link Service} instances.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public interface Services {

	/**
	 * Get service.
	 *
	 * @param name        of service
	 * @param environment to get
	 * @return service for given information
	 */
	Service get(String name, String environment);

	/**
	 * Add service.
	 *
	 * @param name        of service
	 * @param environment of service
	 * @param endpoint    of service
	 */
	void add(String name, String environment, String endpoint);

	/**
	 * Remove service.
	 *
	 * @param name        of service
	 * @param environment of service
	 */
	void remove(String name, String environment);

	/**
	 * All registered services.
	 *
	 * @return stream of registered services
	 */
	Stream<Service> all();
}
