package mn.astvision.starter.service;

import com.google.analytics.data.v1beta.*;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

@Slf4j
@Service
public class AnalyticsDataService {

    private static final String PROPERTY_ID = "360599562";
    public static final LocalDate FIRST_DATE = LocalDate.of(2023, 3, 28);

    private BetaAnalyticsDataClient analyticsDataClient;

    @PostConstruct
    private void prepare() {
        try {
            GoogleCredentials credentials = GoogleCredentials
                    .fromStream(Objects.requireNonNull(
                            getClass().getResourceAsStream("/ga.json")));

            BetaAnalyticsDataSettings betaAnalyticsDataSettings =
                    BetaAnalyticsDataSettings.newBuilder()
                            .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                            .build();

            analyticsDataClient = BetaAnalyticsDataClient.create(betaAnalyticsDataSettings);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @PreDestroy
    private void cleanup() {
        if (analyticsDataClient != null)
            analyticsDataClient.close();
    }

    public Long fetchSessionCount(LocalDate start, LocalDate end) {
        if (analyticsDataClient == null)
            return null;

        RunReportRequest request =
                RunReportRequest.newBuilder()
                        .setProperty("properties/" + PROPERTY_ID)
                        .addMetrics(Metric.newBuilder().setName("sessions"))
                        .addDateRanges(
                                DateRange.newBuilder()
                                        .setStartDate(start.toString())
                                        .setEndDate(end.toString()))
                        .build();

        Long count = null;
        RunReportResponse response = analyticsDataClient.runReport(request);
        if (!response.getRowsList().isEmpty()) {
            try {
                count = Long.parseLong(response.getRowsList()
                        .get(0)
                        .getMetricValues(0)
                        .getValue());
            } catch (NumberFormatException e) {
            }
        }
        return count;
    }
}
