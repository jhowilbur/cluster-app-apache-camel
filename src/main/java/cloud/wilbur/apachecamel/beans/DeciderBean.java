package cloud.wilbur.apachecamel.beans;

import org.apache.camel.Body;
import org.apache.camel.Headers;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DeciderBean {

    public boolean decideStatus(@Body String body, @Headers Map<String, String> headers) {
        System.out.println("DeciderBean.decideStatus: " + body);
        System.out.println("DeciderBean.decideStatus: " + headers);
        return true;
    }

}
