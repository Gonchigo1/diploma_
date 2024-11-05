package mn.astvision.starter.model.xyp.citizen;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;
import mn.astvision.starter.model.BaseEntity;
import org.springframework.data.mongodb.core.mapping.Sharded;

/**
 * @author digz6666
 */
@Sharded(shardKey = {"regnum"})
@FieldNameConstants
@Data
@EqualsAndHashCode(callSuper = true)
public class CitizenImage extends BaseEntity {

    private String regnum;
    private byte[] image;
}
