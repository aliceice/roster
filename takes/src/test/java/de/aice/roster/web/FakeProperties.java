package de.aice.roster.web;

import java.util.Random;

/**
 * FakeProperties
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public final class FakeProperties implements Properties {

	private int port;

	@Override
	public int port() {
		if (port == 0) {
			Random random = new Random();
			do {
				port = random.nextInt(50000);
			} while (port < 10000);
		}
		return port;
	}
}
