package de.aice.roster.web;

import de.aice.roster.core.registry.memory.MemoryServiceRegistry;
import org.takes.http.Exit;
import org.takes.http.FtBasic;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Roster application.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public final class Application {

	/**
	 * Main method of roster application.
	 *
	 * @param args args
	 * @throws Exception if service can not be started.
	 */
	public static void main(final String[] args) throws Exception {
		new Application(new SystemProperties(), Exit.NEVER).start();
	}

	private final Properties properties;
	private final Exit       exit;

	public Application(final Properties properties, final Exit exit) {
		this.properties = properties;
		this.exit = exit;
	}

	/**
	 * Start application server.
	 *
	 * @return Home URL
	 * @throws Exception if something goes wrong.
	 */
	public String start() throws Exception {
		new Thread(this::startTakes).start();
		return String.format(
			"http://%s:%s/", InetAddress.getLocalHost().getCanonicalHostName(), this.properties.port()
		);
	}

	private void startTakes() {
		try {
			new FtBasic(
				new TkRoot(
					new MemoryServiceRegistry()), this.properties.port()
			).start(this.exit);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

}
