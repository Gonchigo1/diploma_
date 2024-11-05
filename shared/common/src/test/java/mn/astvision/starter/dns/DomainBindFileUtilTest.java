package mn.astvision.starter.dns;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
@Slf4j
public class DomainBindFileUtilTest {

    @Test
    public void testConvert() {
        DomainBindFileUtil.convertJsonToBind("/Users/digz6666/Library/CloudStorage/Dropbox/Work Files/telco com/cloudflare/telcocom.json");
    }
}
