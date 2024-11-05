package mn.astvision.starter.dns.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AliasTarget {

    @JsonProperty("HostedZoneId")
    private String hostedZoneId;
    @JsonProperty("DNSName")
    private String dnsName;
    @JsonProperty("EvaluateTargetHealth")
    private Boolean evaluateTargetHealth;
}
