package cloud.wilbur.apachecamel.beans;

import cloud.wilbur.apachecamel.domain.EventComplete;
import cloud.wilbur.apachecamel.domain.payload.Event;
import cloud.wilbur.apachecamel.domain.payload.Payload;
import cloud.wilbur.apachecamel.domain.payload.Record;
import org.apache.camel.Body;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class Bean extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:clean-payload").routeId("clean-payload")
                .bean(this, "cleanPayload")
                .to("direct:decider");
    }

    public ArrayList<EventComplete> cleanPayload(@Body Payload payload) {
        ArrayList<EventComplete> eventCompleteList = new ArrayList<>();
        ArrayList<Record> records = payload.getRecords();

        for (Record record : records) {
            ArrayList<Event> events = record.getEvent();
            for (Event event : events) {
                EventComplete eventComplete = new EventComplete();
                eventComplete.setTransId(record.getTransId());
                eventComplete.setTransTms(record.getTransTms());
                eventComplete.setRcNum(record.getRcNum());
                eventComplete.setClientId(record.getClientId());
                eventComplete.setEventCnt(event.getEventCnt());
                eventComplete.setLocationCd(event.getLocationCd());
                eventComplete.setLocationId1(event.getLocationId1());
                eventComplete.setLocationId2(event.getLocationId2());
                eventComplete.setAddrNbr(event.getAddrNbr());
                eventCompleteList.add(eventComplete);
            }
        }

        return eventCompleteList;
    }
}