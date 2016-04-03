package de.aice.roster.core.registry.memory;

import de.aice.roster.core.registry.Services;
import java.util.Optional;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public final class ImServicesTest {

	private static final String NAME     = "example-service";
	private static final String ENV      = "dev";
	private static final String ENDPOINT = "http://www.example.com/dev";

	private final Services subject = new ImServices();

	@Test
	public void getUnknownServiceResultIsEmpty() throws Exception {
		Optional<String> endpoint = this.subject.getEndpoint("unknown-service", ENV);
		assertFalse(endpoint.isPresent());
	}

	@Test
	public void getServiceInUnknownEnvResultIsEmpty() throws Exception {
		Optional<String> endpoint = this.subject.getEndpoint(NAME, "unknown-env");
		assertFalse(endpoint.isPresent());
	}

	@Test
	public void getRegisteredServiceResultIsPresentAndHasCorrectEndpoint() throws Exception {
		registerService();
		assertServiceExistsWithEndpoint(ENDPOINT);
	}

	@Test
	public void registeringAServiceTwiceUpdatesTheOldEndpoint() throws Exception {
		String updatedEndpoint = "http://www.example2.com/dev";

		registerService();
		registerService(updatedEndpoint);

		assertServiceExistsWithEndpoint(updatedEndpoint);
	}

	@Test
	public void getAllReturnsAllRegisteredServices() throws Exception {
		registerService(NAME, ENV, ENDPOINT);
		registerService(NAME, "test", ENDPOINT);
		registerService(NAME, "training", ENDPOINT);
		registerService(NAME, "prod", ENDPOINT);

		assertEquals(4, this.subject.stream().count());
	}

	@Test
	public void removingARegisteredServiceRemovesTheAssignedEndpoint() throws Exception {
		registerService();
		this.subject.remove(NAME, ENV);
		assertFalse(this.subject.getEndpoint(NAME, ENV).isPresent());
	}

	private void assertServiceExistsWithEndpoint(String expected) {
		Optional<String> endpoint = this.subject.getEndpoint(NAME, ENV);
		assertTrue(endpoint.isPresent());
		assertSame(expected, endpoint.get());
	}

	private void registerService() {
		registerService(ENDPOINT);
	}

	private void registerService(String endpoint) {
		registerService(NAME, ENV, endpoint);
	}

	private void registerService(String name, String env, String endpoint) {
		this.subject.put(name, env, endpoint);
	}
}