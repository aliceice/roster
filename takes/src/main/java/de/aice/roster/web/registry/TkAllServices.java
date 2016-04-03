package de.aice.roster.web.registry;

import de.aice.roster.core.registry.Service;
import de.aice.roster.core.registry.Services;
import java.io.IOException;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonStructure;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.fork.FkTypes;
import org.takes.facets.fork.RsFork;
import org.takes.rs.RsJSON;
import org.takes.rs.RsXSLT;
import org.takes.rs.xe.RsXembly;
import org.takes.rs.xe.XeStylesheet;
import org.xembly.Directive;
import org.xembly.Directives;

/**
 * Take all services and return them as HTML, XML or JSON.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
final class TkAllServices implements Take {

	private static final String NAME = "name";
	private static final String ENV = "env";
	private static final String ENDPOINT = "endpoint";

	private final Services services;

	TkAllServices(final Services services) {
		this.services = services;
	}

	@Override
	public Response act(final Request req) throws IOException {
		return new RsFork(
			req,
			new FkTypes(
				"text/html",
				new RsXSLT(new RsXembly(new XeStylesheet("/xsl/allServices.xsl"), this::servicesXML))
			),
			new FkTypes("text/xml,application/xml", new RsXembly(this::servicesXML)),
			new FkTypes("application/json", new RsJSON(this::servicesJSON))
		);
	}

	private Iterable<Directive> servicesXML() {
		return this.services.stream()
		                    .map(this::toXml)
		                    .collect(() -> new Directives().add("services"), Directives::append, Directives::append);
	}

	private Directives toXml(final Service service) {
		return new Directives().add("service")
		                       .add(NAME).set(service.name).up()
		                       .add(ENV).set(service.env).up()
		                       .add(ENDPOINT).set(service.endpoint).up()
		                       .up();
	}

	private JsonStructure servicesJSON() {
		return this.services.stream()
		                    .map(this::toJson)
		                    .collect(Json::createArrayBuilder, JsonArrayBuilder::add, JsonArrayBuilder::add)
		                    .build();
	}

	private JsonObject toJson(final Service service) {
		return Json.createObjectBuilder()
		           .add(NAME, service.name)
		           .add(ENV, service.env)
		           .add(ENDPOINT, service.endpoint)
		           .build();
	}

}
