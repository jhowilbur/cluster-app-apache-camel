package cloud.wilbur.apachecamel.domain.payload;

import lombok.*;
import java.util.ArrayList;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payload {
    public String batchId;
    public ArrayList<Record> records;
}
