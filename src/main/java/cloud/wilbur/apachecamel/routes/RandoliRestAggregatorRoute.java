package cloud.wilbur.apachecamel.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class RandoliRestAggregatorRoute extends RouteBuilder  {

    @Override
    public void configure() throws Exception {
        // errorHandler(deadLetterChannel("activemq:dead-letter-queue")); // if exists activemq in environment

        from("direct:rest-get-all").routeId("rest-GET-all")
                .removeHeaders("CamelHttp*")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .to("{{randoli.url}}");

        from("direct:rest-get-eventId").routeId("rest-GET-eventId")
                .removeHeaders("CamelHttp*")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .log("Event Id: ${header.eventId}")
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
                .log("Event Id: ${header.eventId}")
                .toD("{{randoli.url}}/${header.eventId}");

        from("direct:rest-post-eventId").routeId("rest-POST-eventId")
                .removeHeaders("CamelHttp*")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .toD("{{randoli.url}}");

        from("direct:rest-put-eventId").routeId("rest-PUT-eventId")
                .removeHeaders("CamelHttp*")
                .setHeader(Exchange.HTTP_METHOD, constant("PUT"))
                .log("Event Id: ${header.eventId}")
                .toD("{{randoli.url}}/${header.eventId}");
    }

}