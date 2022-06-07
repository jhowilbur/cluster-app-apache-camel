package cloud.wilbur.apachecamel.domain.payload;

import lombok.*;

import java.util.ArrayList;
import java.util.UUID;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Record {
    private UUID transId;
    public String transTms;
    public Integer rcNum;
    public String clientId;
    public ArrayList<Event> event;
}
