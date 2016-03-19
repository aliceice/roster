package de.aice.roster.core.registry;

/**
 * Registered service value object.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public final class Service {

	public final String name;
	public final String env;
	public final String endpoint;

	public Service(final String name, final String env, final String endpoint) {
		this.name = name;
		this.env = env;
		this.endpoint = endpoint;
	}

	/**
	 * Service equals by name and environment.
	 *
	 * @param name name to match
	 * @param env  environment to match
	 * @return true if service name and env match given.
	 */
	public boolean equals(final String name, final String env) {
		return this.name.equals(name) && this.env.equals(env);
	}
}
