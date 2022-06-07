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
                //.to("direct:decider")
                .toD("{{randoli.url}}")
                .unmarshal().json(JsonLibrary.Jackson, EventComplete[].class)
                .log("${body}");

        from("direct:rest-get-eventId").routeId("rest-GET-eventId")
                .removeHeaders("CamelHttp*")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .unmarshal().json(JsonLibrary.Jackson, EventComplete.class)
                .log("EventComplete Id: ${header.eventId}")
                .toD("{{randoli.url}}/${header.eventId}");
                /*
                .choice()
                    .when(header(Exchange.HTTP_RESPONSE_CODE).isEqualTo(HttpStatus.OK))
                        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HttpStatus.OK)).endChoice()
                    .when(header(Exchange.HTTP_RESPONSE_CODE).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR))
                        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HttpStatus.INTERNAL_SERVER_ERROR)).setBody(constant("INTERNAL SERVER ERROR")).endChoice()
                    .when(header(Exchange.HTTP_RESPONSE_CODE).isEqualTo(HttpStatus.NOT_FOUND))
                        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HttpStatus.NOT_FOUND)).setBody(constant("NOT FOUND")).endChoice()
                    .when(header(Exchange.HTTP_RESPONSE_CODE).isEqualTo(HttpStatus.BAD_REQUEST))
                        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HttpStatus.BAD_REQUEST)).setBody(constant("BAD REQUEST")).endChoice();\
                */

        from("direct:rest-delete-eventId").routeId("rest-DELETE-eventId")
                .removeHeaders("CamelHttp*")
                .setHeader(Exchange.HTTP_METHOD, constant("DELETE"))
                .log("EventComplete Id: ${header.eventId}")
                .toD("{{randoli.url}}/${header.eventId}");

        from("direct:rest-post-eventId").routeId("rest-POST-eventId")
                .removeHeaders("CamelHttp*")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .marshal().json(JsonLibrary.Jackson, EventComplete.class)
                .log("Header: ${body}")
                .toD("{{randoli.url}}")
                .unmarshal().json(JsonLibrary.Jackson, EventComplete.class);

        from("direct:rest-put-eventId").routeId("rest-PUT-eventId")
                .removeHeaders("CamelHttp*")
                .setHeader(Exchange.HTTP_METHOD, constant("PUT"))
                .log("EventComplete Id: ${header.eventId}")
                .choice()
                    .when(body().isNull())
                        .unmarshal().json(JsonLibrary.Jackson, EventComplete.class)
                        .toD("{{randoli.url}}/${header.eventId}")
                    .otherwise()
                        .toD("{{randoli.url}}/${header.eventId}")
                .endChoice();


        // From challange Part 2
        from("direct:rest-post-payload").routeId("rest-POST-payload")
                .removeHeaders("CamelHttp*")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .marshal().json(JsonLibrary.Jackson, Payload.class)
                .unmarshal().json(JsonLibrary.Jackson, Payload.class)
                .to("direct:clean-payload")
                .split(body())
                .log("Send Payload: ${body}")
                .marshal().json(JsonLibrary.Jackson, EventComplete.class)
                .toD("{{randoli.url}}")
                .unmarshal().json(JsonLibrary.Jackson, EventComplete.class);
        ;
    }

}