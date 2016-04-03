package de.aice.roster.core.registry.memory;

import de.aice.roster.core.registry.Service;
import de.aice.roster.core.registry.Services;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

/**
 * In memory implementation of {@link Services}.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public final class ImServices implements Services {

	private final Map<Pair<String, String>, String> endpointsByServiceEnv = new ConcurrentHashMap<>(16);

	@Override
	public Optional<String> getEndpoint(final String name, final String environment) {
		return Optional.ofNullable(this.endpointsByServiceEnv.get(new ImmutablePair<>(name, environment)));
	}

	@Override
	public void put(final String name, final String environment, final String endpoint) {
		this.endpointsByServiceEnv.put(new ImmutablePair<>(name, environment), endpoint);
	}

	@Override
	public void remove(final String name, final String environment) {
		this.endpointsByServiceEnv.remove(new ImmutablePair<>(name, environment));
	}

	@Override
	public Stream<Service> stream() {
		return this.endpointsByServiceEnv.entrySet()
		                                 .stream()
		                                 .map(entry -> new Service(entry.getKey().getKey(),
		                                                           entry.getKey().getValue(),
		                                                           entry.getValue()));
	}

}
