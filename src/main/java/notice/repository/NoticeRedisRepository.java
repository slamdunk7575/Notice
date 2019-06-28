package notice.repository;

import notice.domain.NoticeRedis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NoticeRedisRepository extends CrudRepository<NoticeRedis, Integer> {
    // public interface NoticeRedisRepository extends PagingAndSortingRepository<NoticeRedis, Integer> {

    List<NoticeRedis> findAllByOrderByNoticeIdxDesc();

    Page<NoticeRedis> findAllByOrderByNoticeIdxDesc(Pageable pageable);
}
