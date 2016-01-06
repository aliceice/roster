package de.aice.roster.web.health;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.RsJSON;
import org.takes.rs.RsText;

import java.io.IOException;

/**
 * Health take.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public final class TkHealth implements Take {

	@Override
	public Response act(final Request req) throws IOException {
		return new RsJSON(new RsText("{\"status\": \"UP\"}"));
	}
}
