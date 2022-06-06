package cloud.wilbur.apachecamel.domain;

import lombok.*;

import java.util.UUID;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    private UUID eventId;
    private UUID transId;
    private String transTms;
    private Integer rcNum;
    private String clientId;
    private Integer eventCnt;
    private String locationCd;
    private String locationId1;
    private String locationId2;
    private Integer addrNbr;
    
}
