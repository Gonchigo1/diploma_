package mn.astvision.starter.model.xyp.legalentity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class LegalEntityGeneral {

    private String companyRegnum;
    private String companyName;
    private String stateRegnum;
    private String ownershipTypeName;
    private String profitTypeName;
    private String contractDate;
    private String regulationDate;

    private int companyFounderCount;
    private String founderDecisionNumber;
    private String founderDecisionName;
    private String founderDocumentName;
    private String description;
    private String decisionNumber;
    private String decisionDate;
    private String departmentName;
    private String operationDate; // үйл ажиллагаа эрхлэх хугацаа
    private String operationBeginDate; // үйл ажиллагаа эрхлэх эхлэх огноо
    private String operationDueDate; // үйл ажиллагаа эрхлэх дуусах огноо

    private String createdDate;
    private String createdUserName;
}
