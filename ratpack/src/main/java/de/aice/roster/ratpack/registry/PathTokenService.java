package de.aice.roster.ratpack.registry;

import de.aice.roster.core.registry.Service;
import ratpack.path.PathTokens;

/**
 * Service implementation based on {@link PathTokens}.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public final class PathTokenService implements Service {

	private final PathTokens tokens;

	public PathTokenService(final PathTokens tokens) {
		this.tokens = tokens;
	}

	@Override
	public String name() {
		return this.tokens.get("name");
	}

	@Override
	public String environment() {
		return this.tokens.get("environment");
	}
}
