package de.aice.roster.ratpack.registry;

import de.aice.roster.core.registry.Service;
import de.aice.roster.core.registry.ServiceRegistry;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * FailingServiceRegistry
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public final class FailingServiceRegistry implements ServiceRegistry {
	@Override
	public void register(Service service, String endpoint) {
		throw new UnsupportedOperationException("register not supported");
	}

	@Override
	public void unregister(Service service) {
		throw new UnsupportedOperationException("unregister not supported");
	}

	@Override
	public Optional<String> getEndpoint(Service service) {
		throw new UnsupportedOperationException("get endpoint not supported");
	}

	@Override
	public Set<Map.Entry<Service, String>> allServices() {
		throw new UnsupportedOperationException("all services not supported");
	}
}
