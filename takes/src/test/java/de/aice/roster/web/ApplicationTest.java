package de.aice.roster.web;

import com.jcabi.http.request.JdkRequest;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public final class ApplicationTest {

	private final FakeProperties properties = new FakeProperties();
	private final FakeExit       exit       = new FakeExit();
	private final Application    subject    = new Application(properties, exit);

	@After
	public void tearDown() {
		this.exit.stop();
	}

	@Test
	public void testHomeURL() throws Exception {
		String home = subject.start();
		assertNotNull(home);
		assertEquals(expectedHome(), home);
	}

	private String expectedHome() throws UnknownHostException {
		return String.format("http://%s:%s/", InetAddress.getLocalHost().getCanonicalHostName(), properties.port());
	}

	@Test
	public void testStopShutdownsTheServer() throws Exception {
		String home = subject.start();

		JdkRequest request = new JdkRequest(home + "health");
		assertEquals(200, request.fetch().status());

		this.exit.stop();
		try {
			request.fetch();
			fail("Server should be stopped.");
		} catch (IOException e) {
			assertThat(e).hasCauseExactlyInstanceOf(ConnectException.class);
			assertThat(e.getCause()).hasMessage("Connection refused");
		}
	}

	private String localHostAddress() {
		return String.format("http://localhost:%s/", this.properties.port());
	}

}
