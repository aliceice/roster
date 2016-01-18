package de.aice.roster.core.registry;

import de.aice.roster.core.registry.memory.MemoryServiceRegistry;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

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

	@Test
	public void allServicesIsEmptySetWithoutRegistrations() throws Exception {
		Set<Map.Entry<Service, String>> services = this.subject.allServices();
		assertNotNull(services);
		assertTrue(services.isEmpty());
	}

	@Test
	public void allServicesReturnsAllRegisteredServices() throws Exception {
		IntStream.rangeClosed(0, new Random().nextInt(10)).forEach(i -> registerService());
		Set<Map.Entry<Service, String>> entries = this.subject.allServices();
		assertEquals(this.serviceCount, entries.size());

		entries.stream()
		       .forEach(entry -> {
			       Optional<String> endpoint = this.subject.getEndpoint(entry.getKey());
			       assertTrue(endpoint.isPresent());
			       assertSame(entry.getValue(), endpoint.get());
		       });
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
