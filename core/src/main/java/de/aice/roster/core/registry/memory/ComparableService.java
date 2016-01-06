package de.aice.roster.core.registry.memory;

import de.aice.roster.core.registry.Service;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Objects;

/**
 * Comparable service implementation for the {@link MemoryServiceRegistry}.
 * It implements {@link Object#equals(Object)} and {@link Object#hashCode()} based on the service name and environment.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
final class ComparableService implements Service {

	private final String name;
	private final String environment;

	ComparableService(final Service service) {
		this.name = service.name();
		this.environment = service.environment();
	}

	@Override
	public String name() {
		return this.name;
	}

	@Override
	public String environment() {
		return this.environment;
	}

	@Override
	public boolean equals(final Object obj) {
		return obj == this
		       || obj instanceof ComparableService && nameAndEnvironmentAreEqual((ComparableService) obj);
	}

	private boolean nameAndEnvironmentAreEqual(final ComparableService obj) {
		return Objects.equals(this.name(), obj.name()) && Objects.equals(this.environment(), obj.environment());
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.name()).append(this.environment()).build();
	}

	@Override
	public String toString() {
		return String.format("%s/%s", this.name(), this.environment);
	}
}
