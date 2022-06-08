package cloud.wilbur.apachecamel.routes;

import cloud.wilbur.apachecamel.domain.EventComplete;
import cloud.wilbur.apachecamel.domain.payload.Payload;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class RandoliRestAggregatorRoute extends RouteBuilder  {

    @Override
    public void configure() throws Exception {
        // errorHandler(deadLetterChannel("activemq:dead-letter-queue")); // if exists activemq in environment

        from("direct:rest-get-all").routeId("rest-GET-all-events")
                .removeHeaders("CamelHttp*")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .toD("{{randoli.url}}")
                .unmarshal().json(JsonLibrary.Jackson, EventComplete[].class);

        from("direct:rest-get-eventId").routeId("rest-GET-eventId")
                .removeHeaders("CamelHttp*")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .log("EventComplete Id: ${header.eventId}")
                .toD("{{randoli.url}}/${header.eventId}")
                .unmarshal().json(JsonLibrary.Jackson, EventComplete.class);

        from("direct:rest-delete-eventId").routeId("rest-DELETE-eventId")
                .removeHeaders("CamelHttp*")
                .setHeader(Exchange.HTTP_METHOD, constant("DELETE"))
                .log("Event Id: ${header.eventId}")
                .toD("{{randoli.url}}/${header.eventId}");

        from("direct:rest-post-eventId").routeId("rest-POST-eventId")
                .removeHeaders("CamelHttp*")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .marshal().json(JsonLibrary.Jackson, EventComplete.class)
                .log("Body: ${body}")
                .toD("{{randoli.url}}")
                .unmarshal().json(JsonLibrary.Jackson, EventComplete.class);

        from("direct:rest-put-eventId").routeId("rest-PUT-eventId")
                .removeHeaders("CamelHttp*")
                .setHeader(Exchange.HTTP_METHOD, constant("PUT"))
                .marshal().json(JsonLibrary.Jackson, EventComplete.class)
                .log("EventComplete Id: ${header.eventId}")
                .toD("{{randoli.url}}/${header.eventId}")
                .unmarshal().json(JsonLibrary.Jackson, EventComplete.class);


        // From challange Part 2
        from("direct:rest-post-payload").routeId("rest-POST-payload")
                .removeHeaders("CamelHttp*")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                    .marshal().json(JsonLibrary.Jackson, Payload.class)
                    .unmarshal().json(JsonLibrary.Jackson, Payload.class)
                .to("direct:clean-payload")
                    .split(body())
                    .parallelProcessing()
                .log("Send Payload: ${body}")
                    .marshal().json(JsonLibrary.Jackson, EventComplete.class)
                .toD("{{randoli.url}}")
                    .unmarshal().json(JsonLibrary.Jackson, EventComplete.class)
                .log("Received Payload: ${body}");
    }

}