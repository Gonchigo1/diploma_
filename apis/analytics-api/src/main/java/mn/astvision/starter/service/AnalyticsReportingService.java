//package mn.astvision.starter.service;
//
//import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
//import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.gson.GsonFactory;
//import com.google.api.services.analyticsreporting.v4.AnalyticsReporting;
//import com.google.api.services.analyticsreporting.v4.AnalyticsReportingScopes;
//import com.google.api.services.analyticsreporting.v4.model.*;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import java.io.IOException;
//import java.security.GeneralSecurityException;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
///**
// * @author Sembe
// */
//@Slf4j
//@Service
//public class AnalyticsReportingService {
//
//    public static final LocalDate FIRST_DATE = LocalDate.of(2021, 1, 1);
//
//    private static final String viewId = "113452332";
//    private static final String applicationName = "autoland";
//
//    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
//
//    private AnalyticsReporting analyticsReporting;
//
//    @PostConstruct
//    public void init() throws IOException {
//        log.info("Preparing analytics reporting service");
//
//        try {
//            HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
//            GoogleCredential credential = GoogleCredential
//                    .fromStream(AnalyticsReportingService.class.getResourceAsStream("/ga-old.json"))
//                    .createScoped(Collections.singleton(AnalyticsReportingScopes.ANALYTICS_READONLY));
//
//            // Construct the Analytics Reporting service object
//            analyticsReporting = new AnalyticsReporting.Builder(httpTransport, JSON_FACTORY, credential)
//                    .setApplicationName(applicationName).build();
//        } catch (GeneralSecurityException | IOException e) {
//            log.error("analytics init", e.getMessage(), e);
//        }
//    }
//
//    private GetReportsResponse getReport(LocalDate start, LocalDate end) throws IOException {
//        DateRange dateRange = new DateRange();
//        dateRange.setStartDate(start.toString());
//        dateRange.setEndDate(end.toString());
//        //dateRange.setStartDate("7DaysAgo");
//        //dateRange.setEndDate("today");
//
//        Metric sessions = new Metric().setExpression("ga:sessions"); //.setAlias("sessions");
//
//        //Dimension pageTitle = new Dimension().setName("ga:pageTitle");
//        // Create the ReportRequest object.
//        ReportRequest request = new ReportRequest()
//                .setViewId(viewId)
//                .setDateRanges(Arrays.asList(dateRange))
//                .setMetrics(Arrays.asList(sessions));
//        //.setDimensions(Arrays.asList(pageTitle))
//
//        List<ReportRequest> requests = new ArrayList<>();
//        requests.add(request);
//        GetReportsRequest getReport = new GetReportsRequest().setReportRequests(requests);
//        return analyticsReporting.reports().batchGet(getReport).execute();
//    }
//
//    public Integer getSessionCount(LocalDate start, LocalDate end) {
//        Integer sessions = null;
//
//        try {
//            GetReportsResponse response = getReport(start, end);
//            Report report = response.getReports().get(0);
//            ReportRow row = report.getData().getRows().get(0);
//            DateRangeValues metric = row.getMetrics().get(0);
//
//            sessions = Integer.parseInt(metric.getValues().get(0));
//        } catch (NumberFormatException | ArrayIndexOutOfBoundsException | IOException | NullPointerException e) {
//            log.error("analytics get session count", e.getMessage());
//        }
//
//        return sessions;
//    }
//}
