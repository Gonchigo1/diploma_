package mn.astvision.starter.model.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mn.astvision.starter.model.payment.enums.PaymentMethodType;
import mn.astvision.starter.model.payment.enums.PaymentType;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Sharded;

import java.util.List;

/**
 * Төлбөр төлөх хэлбэр
 * @author digz6666
 */
@Sharded(shardKey = {"id"})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class PaymentMethod {

    @Id
    private PaymentMethodType type; // төрөл

    @Transient
    private String name; // нэршил
    private String imgUrl; // зургийн URL
    private String color; // өнгө

    private List<PaymentType> paymentTypes; // төлбөр төлөх боломжтой төрлүүд
    private boolean requireLogin; // нэвтэрч орж байж ашиглах эсэх
    private boolean enabled; // нээлттэй эсэх
    private boolean visible; // харагдах эсэх
    private int order; // харагдах дараалал
}
