package de.aice.roster.core.registry;

/**
 * A simple immutable service implementation with name and environment given on construction.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public final class SimpleService implements Service {

	private final String name;
	private final String environment;

	public SimpleService(final String name, final String environment) {
		this.name = name;
		this.environment = environment;
	}

	@Override
	public String name() {
		return this.name;
	}

	@Override
	public String environment() {
		return this.environment;
	}

}
