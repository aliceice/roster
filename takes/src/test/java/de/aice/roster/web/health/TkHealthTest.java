package de.aice.roster.web.health;

import org.junit.Test;
import org.takes.Response;
import org.takes.Take;
import org.takes.rq.RqFake;
import org.takes.rs.RsPrint;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

public final class TkHealthTest {

	private final Take subject = new TkHealth();

	@Test
	public void testGETHealthEndpoint() throws Exception {
		Response response = subject.act(new RqFake("GET", "/health"));
		assertNotNull(response);
		assertThat(response.head()).contains("HTTP/1.1 200 OK")
		                           .contains("Content-Type: application/json");
		assertThat(new RsPrint(response).printBody()).isEqualTo("{\"status\": \"UP\"}");
	}

}
