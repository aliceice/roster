package de.aice.roster.web.registry;

import de.aice.roster.core.registry.Service;
import org.takes.facets.fork.RqRegex;

import java.util.regex.Matcher;

/**
 * Service from an {@link RqRegex}.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public final class RqService implements Service {

	private final Matcher matcher;

	public RqService(final RqRegex req) {
		this.matcher = req.matcher();
	}

	@Override
	public String name() {
		return this.matcher.group("name");
	}

	@Override
	public String environment() {
		return this.matcher.group("environment");
	}

}
