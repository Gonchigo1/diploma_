package mn.astvision.starter.dns.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GeoLocation {

    @JsonProperty("CountryCode")
    private String countryCode;
}
