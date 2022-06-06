package cloud.wilbur.apachecamel.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class RestRoute extends RouteBuilder {

    private final String HOST = "localhost";
    private final int PORT = 8082;

    @Override
    public void configure() throws Exception {
        restConfiguration().bindingMode(RestBindingMode.auto).host(HOST).port(PORT);

        rest("/integration/event")
            .get()
                .route().routeId("route-all-authors")
                .to("direct:rest-get-all")
                .endRest()

            .get("/{eventId}")
                .route().routeId("route-get-eventId")
                .to("direct:rest-get-eventId")
                .endRest()

            .delete("/{eventId}")
                .route().routeId("route-delete-eventId")
                .to("direct:rest-delete-eventId")
                .endRest()

            .post()
                .route().routeId("route-post-eventId")
                .to("direct:rest-post-eventId")
                .endRest()

            .put("/{eventId}")
                .route().routeId("route-put-eventId")
                .to("direct:rest-put-eventId")
                .endRest();
    }
}
