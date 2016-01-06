package de.aice.roster.core.registry.memory;

import de.aice.roster.core.registry.Service;
import de.aice.roster.core.registry.ServiceRegistry;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In memory implementation of {@link ServiceRegistry}.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public final class MemoryServiceRegistry implements ServiceRegistry {

	private static final int INITIAL_CAPACITY = 20;

	private final Map<Service, String> endpointsByService;

	public MemoryServiceRegistry() {
		this.endpointsByService = new ConcurrentHashMap<>(INITIAL_CAPACITY);
	}

	@Override
	public void register(final Service service, final String endpoint) {
		this.endpointsByService.put(new ComparableService(service), endpoint);
	}

	@Override
	public void unregister(final Service service) {
		this.endpointsByService.remove(new ComparableService(service));
	}

	@Override
	public Optional<String> getEndpoint(final Service service) {
		return Optional.ofNullable(this.endpointsByService.get(new ComparableService(service)));
	}

	@Override
	public Set<Map.Entry<Service, String>> allServices() {
		return this.endpointsByService.entrySet();
	}
}
