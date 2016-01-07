package de.aice.roster.web.registry;

import de.aice.roster.core.registry.Service;
import org.takes.rs.RsJSON;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonStructure;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Json representation of a set of services.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public final class JsonServices implements RsJSON.Source {

	private final Set<Map.Entry<Service, String>> entries;

	public JsonServices(final Set<Map.Entry<Service, String>> entries) {
		this.entries = entries;
	}

	@Override
	public JsonStructure toJSON() throws IOException {
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		this.entries.stream()
		            .map(entry -> Json
			            .createObjectBuilder()
			            .add("name", entry.getKey().name())
			            .add("env", entry.getKey().environment())
			            .add("endpoint", entry.getValue()))
		            .forEach(arrayBuilder::add);
		return arrayBuilder.build();
	}
}
