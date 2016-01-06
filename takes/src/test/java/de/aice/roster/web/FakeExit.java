package de.aice.roster.web;

import org.takes.http.Exit;

/**
 * FakeExit
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public final class FakeExit implements Exit {

	private boolean ready = true;

	@Override
	public boolean ready() {
		return this.ready;
	}

	public void stop() {
		this.ready = false;
	}
}
