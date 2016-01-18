package de.aice.roster.core.registry;

import org.junit.Test;

import static org.junit.Assert.assertSame;

public final class SimpleServiceTest {

	private static final String NAME        = "my-service";
	private static final String ENVIRONMENT = "dev";

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
