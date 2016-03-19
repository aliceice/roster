package de.aice.roster.core.registry;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public final class ServiceTest {

	private static final String NAME = "example-service";
	private static final String ENV = "dev";

	private final Service subject = new Service(NAME, ENV, "");

	@Test
	public void equalsWithSameNameAndEnvReturnsTrue() throws Exception {
		assertTrue(subject.equals(NAME, ENV));
	}

	@Test
	public void equalsWithDifferentNameReturnsFalse() throws Exception {
		assertFalse(subject.equals("other-service", ENV));
	}

	@Test
	public void equalsWithDifferentEnvReturnsFalse() throws Exception {
		assertFalse(subject.equals(NAME, "test"));
	}

	@Test
	public void equalsWithDifferentNameAndEnvReturnsFalse() throws Exception {
		assertFalse(subject.equals("other-service", "test"));
	}
}