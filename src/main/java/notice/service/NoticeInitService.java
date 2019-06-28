package notice.service;

import lombok.AllArgsConstructor;
import notice.domain.NoticeEntity;
import notice.domain.NoticeRedis;
import notice.repository.NoticeRedisRepository;
import notice.repository.NoticeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@AllArgsConstructor
public class NoticeInitService implements CommandLineRunner {

    // private NoticeRedisRepository noticeRedisRepository;
    // private RedisTemplate redisTemplate;

    private NoticeRepository noticeRepository;

    @Override
    public void run(String... args) throws Exception {

        for (int i = 0; i < 154; i++) {
            NoticeEntity noticeEntity = NoticeEntity.builder()
                    .title("Notice 샘플 DATA")
                    .contents("페이징 TEST")
                    .creatorId("Admin")
                    .hitCnt(new Integer(0))
                    .createdDatetime(LocalDateTime.now())
                    .build();
            noticeRepository.save(noticeEntity);
        }


        /*RedisAtomicInteger uniqueNum =
                new RedisAtomicInteger("RedisUniqueNum", redisTemplate.getConnectionFactory(), 1);

        for (int i = 0; i < 154; i++) {
            NoticeRedis noticeRedis = NoticeRedis.builder()
                    .noticeIdx(uniqueNum.getAndIncrement())
                    // .noticeIdx(i)
                    .title("Notice 샘플 DATA")
                    .contents("페이징 TEST")
                    .creatorId("Admin")
                    .hitCnt(new Integer(0))
                    .createdDatetime(LocalDateTime.now())
                    .build();
            noticeRedisRepository.save(noticeRedis);
        }*/


        // 테스트
        /*String sortKey = "noticeKey";

        if (!redisTemplate.hasKey(sortKey)) {
            for (int i = 0; i < 154; i++) {
                // redisTemplate.boundSetOps(sortKey).add(String.valueOf(i));

                String hashKey = "notice" + i;
                NoticeRedis noticeRedis = NoticeRedis.builder()
                        .noticeIdx(i)
                        .title("Redis 초기 DATA")
                        .contents("페이징 TEST")
                        .creatorId("admin")
                        .hitCnt(new Integer(0))
                        .createdDatetime(LocalDateTime.now())
                        .build();
                redisTemplate.boundHashOps(sortKey).put(hashKey, noticeRedis);
                // boundHashOps(hashKey, noticeRedis);
            }
        }*/

        /*SortQuery<String> sortQuery = SortQueryBuilder.sort("noticeKey").by("notice*->noticeIdx").order(SortParameters.Order.DESC)
                .get("notice*->noticeIdx")
                .build();

        List<NoticeRedis> sortResult = redisTemplate.sort(sortQuery);*/

    }

    /*public void boundHashOps(String hashKey, NoticeRedis noticeRedis) {
        redisTemplate.boundHashOps(hashKey).put("noticeIdx", noticeRedis.getNoticeIdx().toString());
        redisTemplate.boundHashOps(hashKey).put("title", noticeRedis.getTitle());
        redisTemplate.boundHashOps(hashKey).put("contents", noticeRedis.getContents());
        redisTemplate.boundHashOps(hashKey).put("creatorId", noticeRedis.getCreatorId());
        redisTemplate.boundHashOps(hashKey).put("hitCnt", noticeRedis.getHitCnt().toString());
        redisTemplate.boundHashOps(hashKey).put("createdDatetime", noticeRedis.getCreatedDatetime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }*/
}
