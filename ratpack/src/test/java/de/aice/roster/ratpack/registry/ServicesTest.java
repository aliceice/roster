package de.aice.roster.ratpack.registry;

import com.google.common.collect.ImmutableMap;
import de.aice.roster.core.registry.FakeService;
import de.aice.roster.core.registry.Service;
import de.aice.roster.core.registry.ServiceRegistry;
import de.aice.roster.core.registry.memory.MemoryServiceRegistry;
import org.junit.Test;
import ratpack.func.Action;
import ratpack.http.MediaType;
import ratpack.test.handling.HandlingResult;
import ratpack.test.handling.RequestFixture;

import java.net.HttpURLConnection;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public final class ServicesTest {

	private static final Service SERVICE  = new FakeService();
	private static final String  ENDPOINT = "http://www.example.org/";

	private final ServiceRegistry        registry = new MemoryServiceRegistry();
	private final Action<RequestFixture> fixture  = fixture -> {
		fixture.getRegistry().add(ServiceRegistry.class, this.registry);
		fixture.pathBinding(ImmutableMap.of("name", SERVICE.name(), "environment", SERVICE.environment()));
	};

	@Test
	public void getEndpointOfUnknownServiceShouldReturn404NotFound() throws Exception {
		HandlingResult result = get();
		assertEquals(HttpURLConnection.HTTP_NOT_FOUND, result.getStatus().getCode());
	}

	@Test
	public void getEndpointOfKnownServiceShouldReturn200Ok() throws Exception {
		this.registry.register(SERVICE, ENDPOINT);

		HandlingResult result = get();
		assertEquals(HttpURLConnection.HTTP_OK, result.getStatus().getCode());
		assertEquals(ENDPOINT, result.getBodyText());
	}

	private HandlingResult get() throws Exception {
		return RequestFixture.handle(Services::handleServiceRequest, this.fixture);
	}

	@Test
	public void registerServiceWithNoBodyReturns400BadRequest() throws Exception {
		HandlingResult result = execute("POST");
		assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, result.getStatus().getCode());
	}

	@Test
	public void registerServiceWithInvalidBodyReturns500InternalError() throws Exception {
		HandlingResult result = RequestFixture.handle(
			Services::handleServiceRequest,
			this.fixture.append(
				f -> f.method("POST")
				      .body(ENDPOINT, MediaType.PLAIN_TEXT_UTF8)
				      .getRegistry().add(ServiceRegistry.class, new FailingServiceRegistry()))
		);
		assertEquals(HttpURLConnection.HTTP_INTERNAL_ERROR, result.getStatus().getCode());
		assertEquals("register not supported", result.getBodyText());
	}

	@Test
	public void registerServiceWithBodyReturns200OkAndRegistersServiceInRegistry() throws Exception {
		HandlingResult result = post(ENDPOINT);
		assertEquals(HttpURLConnection.HTTP_OK, result.getStatus().getCode());
		assertEqualsEndpoint();
	}

	private HandlingResult post(String data) throws Exception {
		return RequestFixture.handle(
			Services::handleServiceRequest,
			this.fixture.append(f -> f.method("POST").body(data, MediaType.PLAIN_TEXT_UTF8))
		);
	}

	private void assertEqualsEndpoint() {
		Optional<String> endpoint = this.registry.getEndpoint(SERVICE);
		assertTrue(endpoint.isPresent());
		assertEquals(ENDPOINT, endpoint.get());
	}

	@Test
	public void unregisterUnknownServiceReturns200Ok() throws Exception {
		HandlingResult result = execute("DELETE");
		assertEquals(HttpURLConnection.HTTP_OK, result.getStatus().getCode());
	}

	@Test
	public void unregisterKnownServiceReturns200OkAndUnregistersTheService() throws Exception {
		this.registry.register(SERVICE, ENDPOINT);
		HandlingResult result = execute("DELETE");
		assertEquals(HttpURLConnection.HTTP_OK, result.getStatus().getCode());
		assertFalse(this.registry.getEndpoint(SERVICE).isPresent());
	}

	private HandlingResult execute(String method) throws Exception {
		return RequestFixture.handle(Services::handleServiceRequest, this.fixture.append(f -> f.method(method)));
	}

}
