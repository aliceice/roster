package de.aice.roster.web.registry;

import de.aice.roster.core.registry.Service;
import java.io.IOException;
import java.util.stream.Stream;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonStructure;
import org.takes.rs.RsJSON;

/**
 * Json representation of a set of services.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public final class JsonServices implements RsJSON.Source {

	private final Stream<Service> entries;

	public JsonServices(final Stream<Service> entries) {
		this.entries = entries;
	}

	@Override
	public JsonStructure toJSON() throws IOException {
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		this.entries
			.map(entry -> Json
				.createObjectBuilder()
				.add("name", entry.name())
				.add("env", entry.environment())
				.add("endpoint", entry.endpoint().get()))
			.forEach(arrayBuilder::add);
		return arrayBuilder.build();
	}
}
