package mn.astvision.starter.model.bank;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import mn.astvision.starter.model.BaseEntityWithUser;
import org.springframework.data.mongodb.core.mapping.Sharded;

/**
 * Банк
 * @author digz6666
 */
@Sharded(shardKey = {"id"})
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bank extends BaseEntityWithUser {

    public static final String BANK_STATE_TREASURY = "900000";

    @NotNull(message = "Код бичнэ уу")
    private String code;

    @NotNull(message = "Нэр бичнэ үү")
    private String name;
}
