package notice;

import notice.repository.PointRedisRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.query.SortQuery;
import org.springframework.data.redis.core.query.SortQueryBuilder;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Random;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "job.name=c")
public class RedisTest_5 {

    @Autowired
    private PointRedisRepository pointRedisRepository;

    @After
    public void tearDown() throws Exception {
        pointRedisRepository.deleteAll();
    }

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void 레디스_테스트() {

        // 테스트
        String sortKey = "sortKey";

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(stringRedisSerializer);

        redisTemplate.delete(sortKey);
        if (!redisTemplate.hasKey(sortKey)) {
            for (int i = 0; i < 10; i++) {
                redisTemplate.boundSetOps(sortKey).add(String.valueOf(i));
                String hashKey = "hash" + i,
                        strId = String.valueOf(i),
                        strName = getRandomStr(),
                        strSite = getRandomStr();
                redisTemplate.boundHashOps(hashKey).put("_id", strId);
                redisTemplate.boundHashOps(hashKey).put("Name", strName);
                redisTemplate.boundHashOps(hashKey).put("Site", strSite);

                System.out.printf("%s : {\"_id\": %s, \"Name\": %s, \"Site\", %s}\n",
                        hashKey, strId, strName, strSite);
            }
        }

        SortQuery<String> sortQuery = SortQueryBuilder.sort(sortKey).by("hash*->Name").order(SortParameters.Order.DESC)
                .get("hash*->_id").get("hash*->Name").get("hash*->Site").build();
        List<String> sortRslt = redisTemplate.sort(sortQuery);

        for (int i = 0; i < sortRslt.size(); ) {
            System.out.printf("{\"_id\": %s, \"Name\": %s, \"Site\", %s}\n", sortRslt.get(i+2), sortRslt.get(i+1), sortRslt.get(i));
            i += 3;
        }
    }

    public String getRandomStr() {
        return String.valueOf(new Random().nextInt(100));
    }
}
