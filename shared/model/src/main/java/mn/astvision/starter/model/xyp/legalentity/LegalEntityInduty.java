package mn.astvision.starter.model.xyp.legalentity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class LegalEntityInduty {

    private String companyRegnum;

    private String main; // үндсэн үйл ажиллагаа эсэх
    private String licenseNumber;
    private String industryCode;
    private String industryName;
    private String industryStatus;

    private String issuedDate;
    private String issuedBy;
    private String endDate; // дуусах огноо

    private String createdDate;
    private String createdUserName;
}
