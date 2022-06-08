package cloud.wilbur.apachecamel.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class RestRoute extends RouteBuilder {

    private final String HOST = "localhost";
    private final String PORT = "8082";

    @Autowired
    private Environment env;

    @Override
    public void configure() throws Exception {

        restConfiguration()
                .component("servlet")
                .host(HOST)
                .contextPath("/integration")
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "REST API for processing Order")
                .apiProperty("api.version", "1.0")
                .apiProperty("cors", "true")
                .apiContextRouteId("doc-api")
                .port(env.getProperty("server.port", PORT))
                .bindingMode(RestBindingMode.auto);

        rest("/event")
            .get()
                .route().routeId("route-GET-all-events")
                .to("direct:rest-get-all")
                .endRest()

            .get("/{eventId}")
                .route().routeId("route-GET-eventId")
                .to("direct:rest-get-eventId")
                .endRest()

            .delete("/{eventId}")
                .route().routeId("route-DELETE-eventId")
                .to("direct:rest-delete-eventId")
                .endRest()

            .post()
                .route().routeId("route-POST-eventId")
                .to("direct:rest-post-eventId")
                .endRest()

            .put("/{eventId}")
                .route().routeId("route-PUT-eventId")
                .to("direct:rest-put-eventId")
                .endRest()

            // From challange Part 2
            .post("/payload")
                .route().routeId("route-POST-payload")
                .to("direct:rest-post-payload")
                .endRest();
    }
}
