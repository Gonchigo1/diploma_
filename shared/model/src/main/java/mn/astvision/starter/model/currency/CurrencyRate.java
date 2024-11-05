package mn.astvision.starter.model.currency;

import lombok.*;
import lombok.experimental.FieldNameConstants;
import mn.astvision.starter.model.BaseEntity;
import org.springframework.data.mongodb.core.mapping.Sharded;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Валютын ханш
 *
 * @author digz6666
 */
@Sharded(shardKey = {"day", "from", "to"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
public class CurrencyRate extends BaseEntity {

    private LocalDate day; // огноо
    private String from; // валют 1
    private String to; // валют 2
    private BigDecimal rate; // ханш
}
