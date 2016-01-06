package de.aice.roster.core.registry;

/**
 * Service contract.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public interface Service {

	/**
	 * Name of the service.
	 *
	 * @return service name
	 */
	String name();

	/**
	 * Environment of service.
	 *
	 * @return service environment
	 */
	String environment();

}
