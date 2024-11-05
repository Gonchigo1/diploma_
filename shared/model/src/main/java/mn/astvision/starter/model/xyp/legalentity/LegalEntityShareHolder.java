package mn.astvision.starter.model.xyp.legalentity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class LegalEntityShareHolder {

    private String companyRegnum;
    private String countryName;

    private String stakeholderTypeName;
    private String regnum;
    private String lastname;
    private String firstname;

    private String createdDate;
    private String createdUserName;
}
