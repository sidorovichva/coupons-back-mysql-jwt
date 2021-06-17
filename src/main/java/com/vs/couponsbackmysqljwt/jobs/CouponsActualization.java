package com.vs.couponsbackmysqljwt.jobs;

import com.vs.couponsbackmysqljwt.repositories.CouponRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;

/**
 *a thread runner that once a day checks an existence of invalid (with end date in the past) coupons in the system
 * and deletes them with all related purchases
 */
@Component
@Data
@RequiredArgsConstructor
public class CouponsActualization {

    private static final Logger LOGGER = LoggerFactory.getLogger(CouponsActualization.class);

    private final CouponRepository couponRepository;

    //@Scheduled(fixedRate = 2000) // 2000 after previous start
    //@Scheduled(fixedDelay = 2000) //2000 after last finish
    //@Scheduled(initialDelay = 5000, fixedDelay = 2000) //5000 before first run
    //@Scheduled(fixedDelayString = "PT10M") //every 10 minutes
    //@Scheduled(fixedDelayString = "${actualization.delay}") //from properties file
    //@Scheduled(cron = "0 */5 * * * *") //every 5 minutes //0 0 18 * * MON-FRI - every work day at 6p.m.
    @Scheduled(cron = "0 10 00 * * *")
    public void actualization() {
        LOGGER.warn("actualization has started");
        couponRepository.getCouponsByEndDateBefore(new Date(System.currentTimeMillis())).stream()
                .forEach(p -> couponRepository.deleteById(p.getId()));
    }
}
