package mn.astvision.starter.dns.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResourceRecord {

    @JsonProperty("Value")
    private String value;
}
