package de.aice.roster.core.registry;

import java.util.Optional;

/**
 * Fake implementation of {@link Service}.
 *
 * @author Selena Lowell (selena.lowell@unknown.com)
 * @version $Id$
 */
public final class FakeService implements Service {

	private final Service origin;

	public FakeService() {
		this("example-service", "dev", "http://www.example.com/");
	}

	public FakeService(final String name, final String environment) {
		this(name, environment, Optional.empty());
	}

	public FakeService(final String name, final String environment, final String endpoint) {
		this(name, environment, Optional.of(endpoint));
	}

	public FakeService(final String name, final String environment, final Optional<String> endpoint) {
		this.origin = new SimpleService(name, environment, endpoint);
	}

	@Override
	public String name() {
		return this.origin.name();
	}

	@Override
	public String environment() {
		return this.origin.environment();
	}

	@Override
	public Optional<String> endpoint() {
		return this.origin.endpoint();
	}
}
