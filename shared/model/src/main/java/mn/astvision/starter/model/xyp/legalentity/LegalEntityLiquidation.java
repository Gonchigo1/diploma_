package mn.astvision.starter.model.xyp.legalentity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class LegalEntityLiquidation {

    private String companyRegnum;
    private String companyName;

    private String newspaperName;
    private String newspaperNumber;
    private String newspaperPrintedDate;

    private String decisionDate;
    private String decisionNumber;

    private String createdDate;
    private String createdUserName;
}
