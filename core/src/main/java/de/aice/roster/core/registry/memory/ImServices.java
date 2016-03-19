package de.aice.roster.core.registry.memory;

import de.aice.roster.core.registry.Service;
import de.aice.roster.core.registry.Services;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * In memory implementation of {@link Services}.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public final class ImServices implements Services {

	private final List<Service> services = new ArrayList<>();

	@Override
	public Optional<String> getEndpoint(final String name, final String environment) {
		return this.services.stream()
		                    .filter(matches(name, environment))
		                    .findFirst()
		                    .map(service -> service.endpoint);
	}

	@Override
	public void put(final String name, final String environment, final String endpoint) {
		remove(name, environment);
		this.services.add(new Service(name, environment, endpoint));
	}

	@Override
	public void remove(final String name, final String environment) {
		this.services.stream()
		             .filter(matches(name, environment))
		             .findFirst()
		             .ifPresent(this.services::remove);
	}

	private Predicate<? super Service> matches(final String name, final String environment) {
		return service -> service.equals(name, environment);
	}

	@Override
	public Collection<Service> getAll() {
		return this.services.stream().collect(Collectors.toList());
	}

}
