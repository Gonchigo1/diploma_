package mn.astvision.starter.model.payment.enums;

import lombok.Getter;

/**
 * Валют, мөнгөн тэмдэгт
 * @author digz6666
 */
@Getter
public enum CurrencySymbol {

    MNT("MNT", "₮"),
//    USD("USD", "$"),
//    CNY("CNY", "¥"),
//    KRW("KRW", "₩"),
    ;

    final String value;
    final String symbol;

    CurrencySymbol(String value, String symbol) {
        this.value = value;
        this.symbol = symbol;
    }

    public static CurrencySymbol fromString(String input) {
        CurrencySymbol currency = null;
        try {
            currency = CurrencySymbol.valueOf(input);
        } catch (NullPointerException | IllegalArgumentException e) {
        }
        return currency;
    }

    @Override
    public String toString() {
        return value;
    }
}
