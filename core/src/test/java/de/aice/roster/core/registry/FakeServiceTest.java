package de.aice.roster.core.registry;

import java.util.Optional;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public final class FakeServiceTest {

	private static final String           NAME     = "example-service";
	private static final String           ENV      = "dev";
	private static final Optional<String> ENDPOINT = Optional.of("http://www.example.com/");

	private final Service subject = new FakeService(NAME, ENV, ENDPOINT);

	@Test
	public void returnNameInjectedThroughConstructor() throws Exception {
		assertSame(NAME, this.subject.name());
	}

	@Test
	public void returnEnvironmentInjectedThroughConstructor() throws Exception {
		assertSame(ENV, this.subject.environment());

	}

	@Test
	public void returnEndpointInjectedAsOptionalThroughConstructor() throws Exception {
		assertSame(ENDPOINT, this.subject.endpoint());
	}

	@Test
	public void returnEmptyEndpointIfNotSet() throws Exception {
		Service service = new FakeService(NAME, ENV);
		assertNotNull(service.endpoint());
		assertFalse(service.endpoint().isPresent());
	}

	@Test
	public void returnEndpointInjectedAsPlainValueThroughConstructor() throws Exception {
		Service service = new FakeService(NAME, ENV, ENDPOINT.get());
		assertNotNull(service.endpoint());
		assertTrue(service.endpoint().isPresent());
		assertSame(ENDPOINT.get(), service.endpoint().get());
	}

	@Test
	public void setDefaultValues() throws Exception {
		assertEquals(NAME, new FakeService().name());
		assertEquals(ENV, new FakeService().environment());
		assertTrue(new FakeService().endpoint().isPresent());
		assertEquals(ENDPOINT.get(), new FakeService().endpoint().get());

	}
}