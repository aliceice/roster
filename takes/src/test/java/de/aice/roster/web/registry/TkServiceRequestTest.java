package de.aice.roster.web.registry;

import de.aice.roster.core.registry.memory.ImServices;
import de.aice.roster.web.TkRoot;
import java.io.IOException;
import java.util.Arrays;
import org.junit.Test;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.fork.RqRegex;
import org.takes.rq.RqFake;
import org.takes.rs.RsPrint;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

public final class TkServiceRequestTest {

	private static final String PATH           = "/service";
	private static final String REGEX_PATTERN  = PATH + "/(?<name>.*)/(?<environment>.*)";
	private static final String QUERY          = PATH + "/example-service/dev";
	private static final String ENDPOINT       = "http://www.example-service.com/dev";
	private static final String HTTP_OK        = "HTTP/1.1 200 OK";
	private static final String HTTP_NOT_FOUND = "HTTP/1.1 404 Not Found";

	private final Take subject = new TkRoot(new ImServices());

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
	public void testPUTReturnsHttpOkOnSuccess() throws Exception {
		Response response = registerServiceBy("PUT");
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
		return registerServiceBy("POST");
	}

	private Response registerServiceBy(String method) throws IOException {
		RqRegex request = new RqRegex.Fake(new RqFake(method, QUERY, ENDPOINT), REGEX_PATTERN, QUERY);
		return subject.act(request);
	}

	private Response unregisterService() throws IOException {
		RqRegex request = new RqRegex.Fake(new RqFake("DELETE", QUERY), REGEX_PATTERN, QUERY);
		return subject.act(request);
	}

	@Test
	public void testGETAllRegisteredServicesAsJSON() throws Exception {
		Response response = getAllServices("application/json");
		assertThat(response.head()).contains(HTTP_OK);
		assertThat(new RsPrint(response).printBody()).isEqualTo("[]");

		registerService();

		response = getAllServices("application/json");
		assertThat(response.head()).contains(HTTP_OK);
		assertThat(new RsPrint(response).printBody()).isEqualTo(
			"[{\"name\":\"example-service\",\"env\":\"dev\",\"endpoint\":\"http://www.example-service.com/dev\"}]"
		);
	}

	@Test
	public void testGETAllRegisteredServicesAsXML() throws Exception {
		Response response = getAllServices("text/xml");
		assertThat(response.head()).contains(HTTP_OK);
		assertThat(new RsPrint(response).printBody()).contains("<services/>");

		registerService();

		response = getAllServices("text/xml");
		assertThat(response.head()).contains(HTTP_OK);
		assertThat(new RsPrint(response).printBody()).contains(
			"<service>",
			"<service><name>example-service</name><env>dev</env><endpoint>http://www.example-service.com/dev</endpoint></service>",
		    "</services>"
		);
	}

	private Response getAllServices(String accept) throws IOException {
		return this.subject.act(
			new RqFake(Arrays.asList("GET " + PATH + " HTTP/1.1", "Host: localhost", "Accept: " + accept), "")
		);
	}

}