package mn.astvision.starter.model.xyp.legalentity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class LegalEntityStakeHolder {

    private String companyRegnum;
    private String stateRegnum;
    private String status;

    private String lastname;
    private String firstname;
    private String positionName;

    private String createdDate;
    private String createdUserName;
}
