package mn.astvision.starter.dns.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ResourceRecordSets {

    @JsonProperty("ResourceRecordSets")
    private List<ResourceRecordSet> resourceRecordSets;
}
