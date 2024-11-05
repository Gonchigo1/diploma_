package mn.astvision.starter.model.xyp.legalentity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class LegalEntityChangeName {

    private String companyRegnum;
    private String companyType;
    private String requestedName;

    private String createdDate;
    private String createdUserName;
}
