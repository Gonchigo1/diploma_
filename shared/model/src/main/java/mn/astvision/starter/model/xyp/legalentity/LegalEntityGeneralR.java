package mn.astvision.starter.model.xyp.legalentity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class LegalEntityGeneralR {

    private String companyRegnum;
    private String countryName;

    private String positionName;
    private String positionId;
    private String regnum;
    private String lastName;
    private String firstName;
    private String status;

    private String createdDate;
    private String createdUserName;
}
