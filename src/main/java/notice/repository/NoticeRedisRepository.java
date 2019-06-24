package notice.repository;

import notice.entity.NoticeRedis;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NoticeRedisRepository extends CrudRepository<NoticeRedis, Integer> {
    List<NoticeRedis> findAllByOrderByNoticeIdxDesc();
}
