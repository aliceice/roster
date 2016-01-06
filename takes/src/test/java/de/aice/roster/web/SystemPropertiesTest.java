package de.aice.roster.web;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class SystemPropertiesTest {

	private final SystemProperties subject = new SystemProperties();

	@Test
	public void testDefaultPort() throws Exception {
		assertPortIs(8080);
	}

	private void assertPortIs(int expectedPort) {
		int port = subject.port();
		assertEquals(expectedPort, port);
	}

	@Test
	public void testPortCanBeOverwrittenBySystemProperty() throws Exception {
		System.setProperty("de.aice.roster.port", "9090");
		assertPortIs(9090);
		System.clearProperty("de.aice.roster.port");
	}
}
