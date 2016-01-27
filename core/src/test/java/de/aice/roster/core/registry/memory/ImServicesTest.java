package de.aice.roster.core.registry.memory;

import de.aice.roster.core.registry.FakeService;
import de.aice.roster.core.registry.Service;
import de.aice.roster.core.registry.Services;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

public final class ImServicesTest {

	private static final Service SERVICE = new FakeService();

	private final Services subject = new ImServices();

	@Test
	public void isEmptyIfNoServicesAreAdded() throws Exception {
		assertIsEmpty();
	}

	@Test
	public void addServiceToInMemoryList() throws Exception {
		addService();
		assertHasOne();

		Service actual = this.subject.all().findFirst().get();
		assertEquals(SERVICE.name(), actual.name());
		assertEquals(SERVICE.environment(), actual.environment());
		assertEquals(SERVICE.endpoint().get(), actual.endpoint().get());
	}

	@Test
	public void removeServiceFromInMemoryList() throws Exception {
		addService();
		assertHasOne();
		this.subject.remove(SERVICE.name(), SERVICE.environment());
		assertIsEmpty();
	}

	private void assertHasOne() {
		assertEquals(1, this.subject.all().count());
	}

	private void assertIsEmpty() {
		assertEquals(0, this.subject.all().count());
	}

	@Test
	public void getExistingService() throws Exception {
		addService();
		Service actual = this.subject.get(SERVICE.name(), SERVICE.environment());
		assertSame(SERVICE.name(), actual.name());
		assertSame(SERVICE.environment(), actual.environment());
		assertSame(SERVICE.endpoint().get(), actual.endpoint().get());
	}

	@Test
	public void getUnknownService() throws Exception {
		addService();
		String name = "unknown-service";
		String environment = "test";
		Service service = this.subject.get(name, environment);
		assertNotNull(service);
		assertSame(name, service.name());
		assertSame(environment, service.environment());
		assertFalse(service.endpoint().isPresent());
	}

	@Test
	public void getServiceWithUnknownEnvironment() throws Exception {
		addService();
		String environment = "unknown";
		Service service = this.subject.get(SERVICE.name(), environment);
		assertNotNull(service);
		assertSame(SERVICE.name(), service.name());
		assertSame(environment, service.environment());
		assertFalse(service.endpoint().isPresent());
	}

	@Test
	public void getUnknownServiceWithKnownEnvironment() throws Exception {
		addService();
		String name = "unknown";
		Service service = this.subject.get(name, SERVICE.environment());
		assertNotNull(service);
		assertSame(name, service.name());
		assertSame(SERVICE.environment(), service.environment());
		assertFalse(service.endpoint().isPresent());
	}

	private void addService() {
		this.subject.add(SERVICE.name(), SERVICE.environment(), SERVICE.endpoint().get());
	}
}