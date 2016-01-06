package de.aice.roster.core.registry;

import org.junit.Test;

import static org.junit.Assert.assertSame;

public final class SimpleServiceTest {

	public static final String NAME        = "my-service";
	public static final String ENVIRONMENT = "dev";

	private final SimpleService subject = new SimpleService(NAME, ENVIRONMENT);

	@Test
	public void nameReturnsValueOfConstruction() throws Exception {
		assertSame(NAME, subject.name());
	}

	@Test
	public void environmentReturnsValueOfConstruction() throws Exception {
		assertSame(ENVIRONMENT, subject.environment());
	}

}
