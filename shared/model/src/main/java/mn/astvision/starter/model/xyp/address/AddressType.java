package mn.astvision.starter.model.xyp.address;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AddressType {

    private String code;
    private String name;
}
