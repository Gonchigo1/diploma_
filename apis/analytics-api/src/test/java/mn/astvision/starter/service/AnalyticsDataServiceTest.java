package mn.astvision.starter.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@Disabled
@Slf4j
@SpringBootTest
public class AnalyticsDataServiceTest {

    @Autowired
    private AnalyticsDataService analyticsDataService;

    @Test
    public void testFetchSessionCount() {
        try {
            log.info("Sessions -> " + analyticsDataService.fetchSessionCount(
                    LocalDate.of(2023, 3, 28),
                    LocalDate.of(2023, 3, 29)));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
