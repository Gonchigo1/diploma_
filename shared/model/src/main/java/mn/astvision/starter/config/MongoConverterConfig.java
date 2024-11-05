package mn.astvision.starter.config;

import org.bson.types.Decimal128;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.math.BigDecimal;
import java.util.List;

/**
 * Үндсэн баазын тохиргоо
 * @author digz6666
 */
@Configuration
public class MongoConverterConfig {

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(
                List.of(
                        new BigDecimalDecimal128Converter(),
                        new Decimal128BigDecimalConverter(),
                        new EnumsConverter.ApplicationRoleConverter(),
                        new EnumsConverter.SharedStatusConverter(),
                        new EnumsConverter.SystemCronTypeConverter()
                )
        );
    }


    @WritingConverter
    private static class BigDecimalDecimal128Converter implements Converter<BigDecimal, Decimal128> {
        @Override
        public Decimal128 convert(@com.mongodb.lang.NonNull BigDecimal source) {
            return new Decimal128(source);
        }
    }

    @ReadingConverter
    private static class Decimal128BigDecimalConverter implements Converter<Decimal128, BigDecimal> {
        @Override
        public BigDecimal convert(@com.mongodb.lang.NonNull Decimal128 source) {
            return source.bigDecimalValue();
        }
    }
}
