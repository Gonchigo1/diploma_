package mn.astvision.starter.dns.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ResourceRecordSet {

    @JsonProperty("Type")
    private String type;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("TTL")
    private Long ttl;
    @JsonProperty("AliasTarget")
    private AliasTarget aliasTarget;
    @JsonProperty("ResourceRecords")
    private List<ResourceRecord> resourceRecords;

    /*
    geo location
     */
    @JsonProperty("SetIdentifier")
    private String setIdentifier;
    @JsonProperty("GeoLocation")
    private GeoLocation geoLocation;
}
