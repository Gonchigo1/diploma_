package mn.astvision.starter.cron;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.dao.analytics.SessionCountDao;
import mn.astvision.starter.model.analytics.SessionCount;
import mn.astvision.starter.model.analytics.SessionCountData;
import mn.astvision.starter.repository.analytics.SessionCountRepository;
import mn.astvision.starter.service.AnalyticsDataService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class AnalyticsCron {

    private final SessionCountRepository sessionCountRepository;
    private final SessionCountDao sessionCountDao;
    private final AnalyticsDataService analyticsDataService;

    @Getter
    private final SessionCountData sessionCountData = new SessionCountData();

    // update every hour
    @Scheduled(initialDelay = 5000, fixedRate = 3600000)
    public void update() {
        log.info("Updating google analytics counts");
        try {
            SessionCount lastData = sessionCountRepository.findTop1ByOrderByDateDesc();

            LocalDate start;
            if (lastData != null) {
                start = lastData.getDate();
            } else {
                start = AnalyticsDataService.FIRST_DATE;
            }
            LocalDate end = LocalDate.now();

            for (LocalDate date = start;
                 date.equals(end) || date.isBefore(end);
                 date = date.plusDays(1)) {
                SessionCount sessionCount = sessionCountRepository.findTop1ByDate(date);
                if (sessionCount == null) {
                    sessionCount = new SessionCount();
                    sessionCount.setDate(date);
                }

                Long count = analyticsDataService.fetchSessionCount(date, date);
                if (count != null) {
                    sessionCount.setCount(count);
                }

                sessionCountRepository.save(sessionCount);
            }
        } catch (Exception e) {
            log.error("SessionCount cron "+e.getMessage(), e);
        }
        log.info("Updated google analytics counts");

        log.info("Caching google analytics counts");
        try {
            // cache data
            SessionCount lastData = sessionCountRepository.findTop1ByOrderByDateDesc();
            if (lastData != null) {
                sessionCountData.setToday(lastData.getCount());
            }

            LocalDate now = LocalDate.now();

            long thisWeekCount = sessionCountDao.getSessionCount(
                    now.with(DayOfWeek.MONDAY).minusDays(1),
                    now.with(DayOfWeek.SUNDAY).minusDays(1));
            if (thisWeekCount == 0) {
                thisWeekCount = sessionCountData.getToday();
            }
            sessionCountData.setThisWeek(thisWeekCount);

            long thisMonthCount = sessionCountDao.getSessionCount(
                    now.withDayOfMonth(1).minusDays(1),
                    now.withDayOfMonth(now.lengthOfMonth()).minusDays(1));
            if (thisMonthCount == 0) {
                thisMonthCount = sessionCountData.getToday();
            }
            sessionCountData.setThisMonth(thisMonthCount);

            sessionCountData.setTotal(sessionCountDao.getTotalSessionCount());
        } catch (Exception e) {
            log.error("cache data cron "+e.getMessage(), e);
        }
        log.info("Cached google analytics counts");
    }
}
