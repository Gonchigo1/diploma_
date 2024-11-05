package mn.astvision.starter.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import mn.astvision.starter.constants.GlobalDateFormat;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author digz6666
 */
public class IsoDateTimeTSerializer extends LocalDateTimeSerializer {

    @Override
    public void serialize(LocalDateTime date, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(date.format(GlobalDateFormat.DATE_TIME_ISO_T));
    }
}
