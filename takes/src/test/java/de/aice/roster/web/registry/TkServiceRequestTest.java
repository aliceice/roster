package de.aice.roster.web.registry;

import de.aice.roster.core.registry.memory.MemoryServiceRegistry;
import de.aice.roster.web.TkRoot;
import org.junit.Test;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.fork.RqRegex;
import org.takes.rq.RqFake;
import org.takes.rs.RsPrint;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

public final class TkServiceRequestTest {

	private static final String REGEX_PATTERN  = "/service/(?<name>.*)/(?<environment>.*)";
	private static final String QUERY          = "/service/example-service/dev";
	private static final String ENDPOINT       = "http://www.example-service.com/dev";
	private static final String HTTP_OK        = "HTTP/1.1 200 OK";
	private static final String HTTP_NOT_FOUND = "HTTP/1.1 404 Not Found";

	private final Take subject = new TkRoot(new MemoryServiceRegistry());

	@Test
	public void testGETReturns404ForUnknownService() throws Exception {
		Response response = getEndpoint();
		assertNotNull(response);
		assertThat(response.head()).containsExactly(HTTP_NOT_FOUND);
	}

	@Test
	public void testPOSTReturnsHttpOkOnSuccess() throws Exception {
		Response response = registerService();
		assertNotNull(response);
		assertThat(response.head()).containsExactly(HTTP_OK);
	}

	@Test
	public void testGETReturnsEndpointOfRegisteredService() throws Exception {
		registerService();
		Response response = getEndpoint();
		assertNotNull(response);
		assertThat(response.head()).contains(HTTP_OK)
		                           .contains("Content-Type: text/plain");
		assertThat(new RsPrint(response).printBody()).isEqualTo(ENDPOINT);
	}

	@Test
	public void testDELETEReturnsHttpOkForUnknownService() throws Exception {
		Response response = unregisterService();
		assertNotNull(response);
		assertThat(response.head()).containsExactly(HTTP_OK);
	}

	@Test
	public void testDELETERemovesServiceFromRegistry() throws Exception {
		registerService();
		unregisterService();
		assertThat(getEndpoint().head()).containsExactly(HTTP_NOT_FOUND);
	}

	private Response getEndpoint() throws IOException {
		RqRegex.Fake request = new RqRegex.Fake(new RqFake("GET", QUERY), REGEX_PATTERN, QUERY);
		return subject.act(request);
	}

	private Response registerService() throws IOException {
		RqRegex request = new RqRegex.Fake(new RqFake("POST", QUERY, ENDPOINT), REGEX_PATTERN, QUERY);
		return subject.act(request);
	}

	private Response unregisterService() throws IOException {
		RqRegex request = new RqRegex.Fake(new RqFake("DELETE", QUERY), REGEX_PATTERN, QUERY);
		return subject.act(request);
	}

}
