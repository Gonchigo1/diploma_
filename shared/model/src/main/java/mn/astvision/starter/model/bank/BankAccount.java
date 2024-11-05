package mn.astvision.starter.model.bank;

import lombok.*;
import mn.astvision.starter.model.BaseEntityWithUser;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Sharded;

/**
 * Банкны данс
 * @author digz6666
 */
@Sharded(shardKey = {"id"})
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount extends BaseEntityWithUser {

    private String bankCode; // банкны код -> Bank
    private String accountNumber; // дансны дугаар
    private String accountName; // дансны нэр

    @Transient
    private String bankName;
}
