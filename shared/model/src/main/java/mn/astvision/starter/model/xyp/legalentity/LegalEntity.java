package mn.astvision.starter.model.xyp.legalentity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import mn.astvision.starter.model.BaseEntity;
import org.springframework.data.mongodb.core.mapping.Sharded;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@EqualsAndHashCode(callSuper = true)
@Sharded(shardKey = {"legalEntityNumber"})
public class LegalEntity extends BaseEntity {

    private String legalEntityNumber;

    private LegalEntityGeneral general; // ерөнхий мэдэээлэл
    private List<LegalEntityAddress> address;
    private List<Object> branch;
    private List<LegalEntityCapital> capital;
    private List<LegalEntityFounder> founder;
    private LegalEntityGeneralR generalR;
    private List<LegalEntityInduty> induty;
    private List<LegalEntityShareHolder> shareHolders;
    private List<LegalEntityStakeHolder> stakeHolders;
    private LegalEntityLiquidation liquidation;

    private List<Object> changeFond;
    private List<LegalEntityChangeName> changeName;

    private String regnum;
    private String surname;
    private String lastName;
    private String firstName;
}
