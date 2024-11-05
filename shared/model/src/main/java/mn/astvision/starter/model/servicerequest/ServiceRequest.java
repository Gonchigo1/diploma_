package mn.astvision.starter.model.servicerequest;

import lombok.*;
import lombok.experimental.FieldNameConstants;
import mn.astvision.starter.model.BaseEntityWithUser;
import mn.astvision.starter.model.IdentityType;
import mn.astvision.starter.model.servicerequest.enums.ServiceRequestStatus;
import mn.astvision.starter.model.xyp.citizen.Citizen;
import mn.astvision.starter.model.xyp.legalentity.LegalEntity;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Sharded;

/**
 * @author Ariuka
 * Үйлчилгээний хүсэлтийн үндсэн модел
 */
@Sharded(shardKey = {"id"})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@EqualsAndHashCode(callSuper = true)
public class ServiceRequest extends BaseEntityWithUser {

    private String number;
    private ServiceRequestStatus status; // үндсэн төлөв

    private String govAgencyId; // Үйлчилгээ үзүүлэгч байгууллага
    private String serviceId;

    // хүсэлт гаргагч
    private IdentityType identityType;
    private String citizenRegistryNumber; // иргэний дугаар
    private String legalEntityNumber; // байгууллагын регистрийн дугаар

    private String contactEmail; // холбоо барих мэйл
    private String contactPhone1; // холбоо барих утас 1
    private String contactPhone2; // холбоо барих утас 2

    @Transient
    private String statusName;
    @Transient
    private Citizen citizen;
    @Transient
    private LegalEntity legalEntity;
}
