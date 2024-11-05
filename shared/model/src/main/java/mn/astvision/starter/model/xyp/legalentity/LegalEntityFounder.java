package mn.astvision.starter.model.xyp.legalentity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class LegalEntityFounder {

    private String companyRegnum;
    private String companyStateRegnum;
    private String countryName;
    private String stakeHolderRegnum;
    private String stakeHolderTypeName;
    private String lastName;
    private String firstName;
    private String sharePercent;
    private String shareQty;
    private int shareTotalAmount;
    private String shareUnitPrice;
    private String status;

    private String createdDate;
    private String createdUserName;
}
