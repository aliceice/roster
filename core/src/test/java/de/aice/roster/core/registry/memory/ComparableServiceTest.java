package de.aice.roster.core.registry.memory;

import de.aice.roster.core.registry.SimpleService;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public final class ComparableServiceTest {

	private static final String NAME        = "my-service";
	private static final String ENVIRONMENT = "dev";

	private final ComparableService subject = new ComparableService(new SimpleService(NAME, ENVIRONMENT));

	@Test
	public void nameReturnsInputName() throws Exception {
		assertSame(NAME, this.subject.name());
	}

	@Test
	public void environmentReturnsInputEnvironment() throws Exception {
		assertSame(ENVIRONMENT, this.subject.environment());
	}

	@Test
	public void theSameIsEqual() throws Exception {
		assertTrue(this.subject.equals(this.subject));
	}

	@Test
	public void otherComparableServiceWithSameNameAndEnvironmentIsEqual() throws Exception {
		ComparableService other = new ComparableService(new SimpleService(NAME, ENVIRONMENT));
		assertTrue(this.subject.equals(other));
	}

	@Test
	public void otherComparableServiceWithDifferentNameIsNotEqual() throws Exception {
		ComparableService other = new ComparableService(new SimpleService("my-other-service", ENVIRONMENT));
		assertFalse(this.subject.equals(other));
	}

	@Test
	public void otherComparableServiceWithDifferentEnvironmentIsNotEqual() throws Exception {
		ComparableService other = new ComparableService(new SimpleService(NAME, "test"));
		assertFalse(this.subject.equals(other));
	}

	@Test
	public void otherObjectIsNotEqual() throws Exception {
		assertFalse(this.subject.equals(new Object()));
	}

	@Test
	public void hashCodeIsBuiltFromNamAndEnvironment() throws Exception {
		int hashCode = new HashCodeBuilder().append(NAME).append(ENVIRONMENT).build();
		assertEquals(hashCode, this.subject.hashCode());
	}

	@Test
	public void toStringReturnsNameAndEnvironment() throws Exception {
		assertEquals(String.format("%s/%s", NAME, ENVIRONMENT), this.subject.toString());
	}
}