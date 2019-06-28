package notice;

import notice.domain.Point;
import notice.repository.PointRedisRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest_1 {

    @Autowired
    private PointRedisRepository pointRedisRepository;

    @After
    public void tearDown() throws Exception {
        pointRedisRepository.deleteAll();
    }

    @Test
    public void 기본_등록_조회_기능() {

        String id = "slamdunk7575";
        LocalDateTime refreshTime = LocalDateTime.of(2019, 6, 24, 01, 05);
        Point point = Point.builder()
                .id(id)
                .amount(1000L)
                .refreshTime(refreshTime)
                .build();

        pointRedisRepository.save(point);

        Point savedPoint = pointRedisRepository.findById(id).get();
        assertThat(savedPoint.getRefreshTime().isEqual(refreshTime));

    }

}
