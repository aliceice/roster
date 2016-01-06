package de.aice.roster.core.registry;

/**
 * Fake implementation of {@link Service}.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public final class FakeService implements Service {

	@Override
	public String name() {
		return "my-service";
	}

	@Override
	public String environment() {
		return "dev";
	}
}
