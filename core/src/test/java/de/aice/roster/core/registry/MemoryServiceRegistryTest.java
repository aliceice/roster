package de.aice.roster.core.registry;

import de.aice.roster.core.registry.memory.MemoryServiceRegistry;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public final class MemoryServiceRegistryTest {

	private final ServiceRegistry subject = new MemoryServiceRegistry();

	@Test
	public void getEndpointsReturnsRegisteredEndpointForGivenService() throws Exception {
		Pair<Service, String> registration1 = registerService();
		Pair<Service, String> registration2 = registerService();

		assertEndpointIsSet(registration1);
		assertEndpointIsSet(registration2);
	}

	private void assertEndpointIsSet(Pair<Service, String> registration) {
		Optional<String> endpoint = subject.getEndpoint(registration.getLeft());
		assertTrue(endpoint.isPresent());
		assertSame(registration.getRight(), endpoint.get());
	}

	@Test
	public void getEndpointReturnsAnEmptyOptionalOfNoEndpointIsRegisteredForGivenService() throws Exception {
		assertEndpointIsNotSet(new FakeService());
	}

	@Test
	public void unregisterRemovesTheEndpointFromTheRegistry() throws Exception {
		Service service = registerService().getLeft();
		subject.unregister(service);
		assertEndpointIsNotSet(service);
	}

	private void assertEndpointIsNotSet(Service service) {
		Optional<String> endpoint = subject.getEndpoint(service);
		assertFalse(endpoint.isPresent());
	}

	@Test
	public void getEndpointWithEqualButDifferentServiceInstancesStillReturnsTheCorrectEndpoint() throws Exception {
		Pair<Service, String> registration = registerService();
		Service service = new SimpleService(registration.getLeft().name(), registration.getLeft().environment());

		String endpoint1 = subject.getEndpoint(registration.getLeft()).get();
		String endpoint2 = subject.getEndpoint(service).get();

		assertSame(endpoint1, endpoint2);
	}

	private int serviceCount;

	private Pair<Service, String> registerService() {
		serviceCount++;
		Service service = new SimpleService(String.format("my-service%s", serviceCount), "dev");
		String endpoint = String.format("http://www.%s.com/endpoint%s", service.name(), serviceCount);
		subject.register(service, endpoint);
		return new ImmutablePair<>(service, endpoint);
	}

}
