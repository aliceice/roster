package de.aice.roster.core.registry;

import java.util.Optional;

/**
 * In memory implementation of {@link Service}.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public final class SimpleService implements Service {

	private final String           name;
	private final String           environment;
	private final Optional<String> endpoint;

	public SimpleService(final String name, final String environment) {
		this(name, environment, Optional.empty());
	}

	public SimpleService(final String name, final String environment, final String endpoint) {
		this(name, environment, Optional.of(endpoint));
	}

	public SimpleService(final String name, final String environment, final Optional<String> endpoint) {
		this.name = name;
		this.environment = environment;
		this.endpoint = endpoint;
	}

	@Override
	public String name() {
		return this.name;
	}

	@Override
	public String environment() {
		return this.environment;
	}

	@Override
	public Optional<String> endpoint() {
		return this.endpoint;
	}
}
