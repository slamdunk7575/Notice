package notice;

import notice.entity.Point;
import notice.repository.PointRedisRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "job.name=c")
public class RedisTest4 {

    @Autowired
    private PointRedisRepository pointRedisRepository;

    @After
    public void tearDown() throws Exception {
        pointRedisRepository.deleteAll();
    }

    @Test
    public void 수정_기능() {

        String id = "slamdunk7575";
        LocalDateTime refreshTime = LocalDateTime.of(2019, 6, 24, 01, 05);
        pointRedisRepository.save(Point.builder()
                .id(id)
                .amount(2000L)
                .refreshTime(refreshTime)
                .build());

        Point savedPoint = pointRedisRepository.findById(id).get();
        savedPoint.refresh(2000L, LocalDateTime.of(2019,7,12,07,12));
        pointRedisRepository.save(savedPoint);

        Point refreshPoint = pointRedisRepository.findById(id).get();
        assertThat(refreshPoint.getAmount()).isEqualTo(2000L);

    }
}
