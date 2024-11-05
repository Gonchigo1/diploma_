package mn.astvision.starter.model.xyp.legalentity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class LegalEntityCapital {

    private String companyRegnum;
    private String totalAmount;
    private String rowStatusName;

    private String createdDate;
    private String createdUserName;
}
