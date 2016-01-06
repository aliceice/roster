package de.aice.roster.web;

/**
 * Properties overridable by system properties.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public final class SystemProperties implements Properties {

	private static final int DEFAULT_PORT = 8080;

	@Override
	public int port() {
		return Integer.getInteger("de.aice.roster.port", DEFAULT_PORT);
	}
}
