package mn.astvision.starter.model.service;

import lombok.*;
import mn.astvision.starter.model.BaseEntityWithUser;
import mn.astvision.starter.model.FileData;
import mn.astvision.starter.model.IdentityType;
import org.springframework.data.mongodb.core.mapping.Sharded;

import java.util.List;

/**
 * Үйлчилгээ
 * @author Ariuka
 */
@Sharded(shardKey = {"id"})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Service extends BaseEntityWithUser {

    private String categoryCode; // ангиллын код
    private String name; // нэр орчуулгын field
    private String description; // тайлбар орчуулгын field
    private String code; // үйлчилгээний код
    private List<IdentityType> identityTypes; // зөвшөөрөгдсөн хэрэглэгчийн төрлүүд
    private FileData icon;
    private Boolean useVisaType; // визийн үндсэн ангилал ашиглах эсэх
    private boolean requirePayment; // төлбөртэй эсэх

    private Boolean visible; // харагдах эсэх
    private Boolean active; // идэвхтэй эсэх
    private Boolean maintenance; // засвартай эсэх
    private int order;
}
