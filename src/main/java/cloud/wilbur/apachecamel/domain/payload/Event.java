package cloud.wilbur.apachecamel.domain.payload;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    public Integer eventCnt;
    public String locationCd;
    public String locationId1;
    public String locationId2;
    public Integer addrNbr;
}
