package de.aice.roster.core.registry.memory;

import de.aice.roster.core.registry.Service;
import de.aice.roster.core.registry.Services;
import de.aice.roster.core.registry.SimpleService;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * In memory implementation of {@link Services}.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public final class ImServices implements Services {

	private final List<Service> services = new ArrayList<>();

	@Override
	public void add(final String name, final String environment, final String endpoint) {
		this.services.add(new SimpleService(name, environment, endpoint));
	}

	@Override
	public void remove(final String name, final String environment) {
		this.services.stream()
		             .filter(matches(name, environment))
		             .findFirst()
		             .ifPresent(this.services::remove);
	}

	@Override
	public Service get(final String name, final String environment) {
		return this.services.stream()
		                    .filter(matches(name, environment))
		                    .findAny()
		                    .orElseGet(() -> new SimpleService(name, environment));
	}

	private Predicate<? super Service> matches(final String name, final String environment) {
		return service -> name.equals(service.name()) && environment.equals(service.environment());
	}

	@Override
	public Stream<Service> all() {
		return this.services.stream();
	}

}
