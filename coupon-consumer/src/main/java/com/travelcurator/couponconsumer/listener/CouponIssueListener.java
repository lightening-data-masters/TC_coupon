package com.travelcurator.couponconsumer.listener;


import com.travelcurator.couponcore.repository.redis.RedisRepository;
import com.travelcurator.couponcore.repository.redis.dto.CouponIssueRequest;
import com.travelcurator.couponcore.service.CouponIssueService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.travelcurator.couponcore.util.CouponRedisUtils.getIssueRequestQueueKey;
@RequiredArgsConstructor
@EnableScheduling
@Component
public class CouponIssueListener {
    private final RedisRepository redisRepository;
    private final CouponIssueService couponIssueService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String issueRequestQueueKey = getIssueRequestQueueKey();

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    // @Scheduled는 Sprint Bean에 Component로 등록되기 때문에 따로 메서드를 호출하지 않아도 일정 시간/주기 마다 실행된다.
    @Scheduled(fixedDelay = 1000L)
    public void issue() throws JsonProcessingException {
        log.info("listen...");
        while(existCouponIssueTarget()){
            CouponIssueRequest target = getIssueTarget();
            log.info("발급 시작 target: %s".formatted(target));
            couponIssueService.issue(target.couponId(), target.userId());
            log.info("발급 완료 tartget: %s".formatted(target));
            removeIssuedTarget();
        }
    }

    private boolean existCouponIssueTarget() {
        return redisRepository.lSize(issueRequestQueueKey) > 0;
    }

    private CouponIssueRequest getIssueTarget() throws JsonProcessingException {
        return objectMapper.readValue(redisRepository.lIndex(issueRequestQueueKey, 0), CouponIssueRequest.class);
    }

    private void removeIssuedTarget(){
        redisRepository.lPop(issueRequestQueueKey);
    }
}
