package mn.astvision.starter.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import mn.astvision.starter.constants.GlobalDateFormat;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author digx6666
 */
public class IsoDateTimeTDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return LocalDateTime.parse(parser.readValueAs(String.class), GlobalDateFormat.DATE_TIME_ISO_T);
    }
}
