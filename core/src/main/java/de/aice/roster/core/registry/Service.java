package de.aice.roster.core.registry;

import java.util.Optional;

/**
 * Service contract.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public interface Service {

	/**
	 * Name of service.
	 *
	 * @return Service name
	 */
	String name();

	/**
	 * Service environment.
	 *
	 * @return Environment
	 */
	String environment();

	/**
	 * Endpoint of service. Can be absent.
	 *
	 * @return Optional service endpoint.
	 */
	Optional<String> endpoint();

}
